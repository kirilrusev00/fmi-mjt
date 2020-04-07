package bg.sofia.uni.fmi.mjt.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatServer {

    private static final int SERVER_PORT = 1111;
    private static final int BUFFER_SIZE = 1024;

    private Selector selector;
    private ByteBuffer commandBuffer;
    private ServerSocketChannel serverSocketChannel;
    private boolean runServer = true;

    private static final int MIN_LENGTH_OF_COMMAND = 2;

    private static final int IND_SENDER_NICKNAME = 0;
    private static final int IND_COMMAND_WITHOUT_NICKNAME_OF_SENDER = 0;
    private static final int IND_NEW_NICKNAME_WITHOUT_NICKNAME_OF_SENDER = 1;
    private static final int IND_NICKNAME_WHILE_CHANGING = 2;
    private static final int IND_COMMAND_WITH_NICKNAME_OF_SENDER = 1;

    private static final int IND_RECEIVER = 2;

    private static final int FIRST_IND_OF_MESSAGE_TO_BE_SENT_TO_ALL = 2;
    private static final int FIRST_IND_OF_MESSAGE_TO_BE_SENT_TO_ONE_USER = 3;

    private Map<String, SocketChannel> activeUsers;

    public ChatServer(int port) {
        try {
            selector = Selector.open();
            commandBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            activeUsers = new HashMap<>();
        } catch (IOException e) {
            System.err.println("Error while creating chat server");
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (runServer) {
                int readyChannels = selector.select();
                if (readyChannels <= 0) {
                    System.out.println("Still waiting for a ready channel...");
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        System.out.println("new message!");
                        this.read(key);
                    } else if (key.isAcceptable()) {
                        System.out.println("new!");
                        this.accept(key);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            System.err.println("Error in starting server");
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            selector.close();
            serverSocketChannel.close();
        } catch (IOException e) {
            System.err.println("Error in stopping the server");
            e.printStackTrace();
        }
        runServer = false;
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            System.err.println("Error in accepting socket channel");
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            commandBuffer.clear();
            int r = socketChannel.read(commandBuffer);
            if (r <= 0) {
                System.out.println("nothing to read, will close channel");
                socketChannel.close();
                return;
            }
            commandBuffer.flip();
            String message = Charset.forName("UTF-8").decode(commandBuffer).toString();
            String result = executeCommand(message, socketChannel);

            System.out.println("message:" + message);
            System.out.println("result:" + result);

            commandBuffer.clear();
            commandBuffer.put((result + System.lineSeparator()).getBytes());
            commandBuffer.flip();
            socketChannel.write(commandBuffer);
            commandBuffer.clear();
        } catch (IOException e) {
            runServer = false;
            e.getMessage();
            e.printStackTrace();
        }
    }

    private String executeCommand(String commandLine, SocketChannel socketChannel) {
        String[] commandParts = commandLine.replaceAll("=>", "").trim().split(" ");
        if (commandParts.length >= MIN_LENGTH_OF_COMMAND &&
                commandParts[IND_COMMAND_WITHOUT_NICKNAME_OF_SENDER].equals("nick")) {
            return setNicknameOfNewUser(commandParts, socketChannel);
        }
        if (commandParts.length < MIN_LENGTH_OF_COMMAND) {
            return "Not full command";
        }
        String user = commandParts[IND_SENDER_NICKNAME];
        String command = commandParts[IND_COMMAND_WITH_NICKNAME_OF_SENDER];

        switch (command.toLowerCase()) {
            case "nick":
                return setNicknameOfExistingUser(user, commandParts[IND_NICKNAME_WHILE_CHANGING], socketChannel);
            case "list-users":
                return getListOfUsers();
            case "disconnect":
                return disconnect(user);
            case "send":
                return sendToOneUser(user, commandParts[IND_RECEIVER], commandLine);
            case "send-all":
                return sendToAllUsers(user, commandLine);
            default:
                return "Unknown command";
        }
    }

    private String setNicknameOfNewUser(String[] commandParts, SocketChannel socketChannel) {
        if (activeUsers.containsKey(commandParts[IND_NEW_NICKNAME_WITHOUT_NICKNAME_OF_SENDER])) {
            return "User with this nickname already exists";
        }
        activeUsers.put(commandParts[IND_NEW_NICKNAME_WITHOUT_NICKNAME_OF_SENDER], socketChannel);
        return "OK";
    }

    private String setNicknameOfExistingUser(String oldName, String newName, SocketChannel socketChannel) {
        if (activeUsers.containsKey(newName)) {
            return "User with this nickname already exists";
        }
        activeUsers.remove(oldName);
        activeUsers.put(newName, socketChannel);
        return "OK";
    }

    private String getListOfUsers() {
        System.out.println(activeUsers.keySet().toString());
        return activeUsers.keySet().toString().replaceAll("^[\\[\\]]+|[\\[\\]]", "");
    }

    private String disconnect(String user) {
        activeUsers.remove(user);
        return "Disconnected from server";
    }

    private String sendToOneUser(String user, String messageReceiver, String commandLine) {
        if (user.equals(messageReceiver)) {
            return "You cannot message yourself.";
        }
        if (!activeUsers.containsKey(messageReceiver)) {
            return "User [" + messageReceiver + "] seems to be offline";
        }
        String response = "OK";
        String message = getMessageToBeSent(user, commandLine);
        sendMessage(message, messageReceiver);
        return response;
    }

    private String sendToAllUsers(String user, String commandLine) {
        for (String messageReceiver : activeUsers.keySet()) {
            if (!messageReceiver.equals(user)) {
                sendToOneUser(user, messageReceiver, commandLine);
            }
        }
        return "OK";
    }

    private void sendMessage(String message, String messageReceiver) {
        commandBuffer.clear();
        commandBuffer.put((message).getBytes());
        commandBuffer.flip();
        try {
            activeUsers.get(messageReceiver).write(commandBuffer);
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("Error while writing to buffer");
            e.printStackTrace();
        }
        commandBuffer.clear();
    }

    private String getMessageToBeSent(String user, String commandLine) {
        String[] commandParts = commandLine.replaceAll("=>", "").trim().split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        int i;
        if (commandParts[IND_COMMAND_WITH_NICKNAME_OF_SENDER].equals("send-all")) {
            i = FIRST_IND_OF_MESSAGE_TO_BE_SENT_TO_ALL;
        } else {
            i = FIRST_IND_OF_MESSAGE_TO_BE_SENT_TO_ONE_USER;
        }

        for (; i < commandParts.length; i++) {
            stringBuilder.append(commandParts[i]).append(" ");
        }
        return "[" + LocalDate.now() + " " + LocalTime.now().getHour() + ":" +
                LocalTime.now().getMinute() + "] " + user + " : " + stringBuilder.toString() +
                System.lineSeparator();
    }

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer(SERVER_PORT);
        chatServer.start();
        chatServer.stop();
    }
}
