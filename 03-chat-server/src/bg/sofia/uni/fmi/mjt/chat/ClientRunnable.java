package bg.sofia.uni.fmi.mjt.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientRunnable extends Thread {

    private static final int BUFFER_SIZE = 1024;

    private SocketChannel socketChannel;

    ClientRunnable(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                buffer.clear();
                int r = socketChannel.read(buffer);
                if (r <= 0) {
                    System.out.println("nothing to read, will close channel");
                    socketChannel.close();
                    return;
                }
                buffer.flip();
                String reply = new String(buffer.array(), 0, buffer.limit()); // buffer drain
                System.out.print(reply);
                if (reply.equals("Disconnected from server")) {
                    socketChannel.close();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading from buffer");
            e.printStackTrace();
        }
    }
}
