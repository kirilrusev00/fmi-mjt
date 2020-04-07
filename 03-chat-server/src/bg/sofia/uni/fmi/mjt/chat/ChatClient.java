package bg.sofia.uni.fmi.mjt.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1111;

    private String nickname = null;

    private static final int MIN_LENGTH_OF_COMMAND = 1;

    private static final int IND_COMMAND = 0;
    private static final int LENGTH_NICKNAME_COMMAND = 2;
    private static final int IND_NICKNAME = 1;

    public ChatClient(String serverHost, int serverPort) {
        run(serverHost, serverPort);
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private void setNickname(Scanner scanner, PrintWriter printWriter) {
        String command = scanner.nextLine();
        String[] commandParts = splitCommand(command);
        if (!checkIfFullCommand(commandParts, LENGTH_NICKNAME_COMMAND)) {
            return;
        }
        if ("nick".equalsIgnoreCase(commandParts[IND_COMMAND])) {
            printWriter.println(command);
            setNickname(commandParts[IND_NICKNAME]);
        }
    }

    private String[] splitCommand(String command) {
        return command.replaceAll("=>", "").trim().split(" ");
    }

    private boolean checkIfFullCommand(String[] commandParts, int length) {
        if (commandParts.length < length) {
            System.out.println("Not full command");
            return false;
        }
        return true;
    }

    private void run(String serverHost, int serverPort) {
        try (SocketChannel socketChannel = SocketChannel.open();
             PrintWriter printWriter = new PrintWriter(Channels.newWriter(socketChannel, "UTF-8"), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(serverHost, serverPort));

            ClientRunnable clientRunnable = new ClientRunnable(socketChannel);
            clientRunnable.start();

            System.out.println("Set your nickname");
            setNickname(scanner, printWriter);

            while (true) {
                String command = scanner.nextLine();
                if (command.isEmpty()) {
                    continue;
                }
                String[] commandParts = splitCommand(command);
                if (!checkIfFullCommand(commandParts, MIN_LENGTH_OF_COMMAND)) {
                    continue;
                }
                printWriter.println(this.nickname + " " + command);
                if ("disconnect".equalsIgnoreCase(commandParts[IND_COMMAND])) {
                    //break;
                }
            }
        } catch (IOException e) {
            System.err.println("An error with chat client");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient(SERVER_HOST, SERVER_PORT);
    }
}
