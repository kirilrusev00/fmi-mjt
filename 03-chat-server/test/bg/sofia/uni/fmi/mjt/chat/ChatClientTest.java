package bg.sofia.uni.fmi.mjt.chat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChatClientTest {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1111;

    private ChatServer chatServer;

    private ChatClient chatClient;

    @Before
    public void setUp() {
        chatServer = new ChatServer(SERVER_PORT);
        chatClient = new ChatClient(SERVER_HOST, SERVER_PORT);
    }

    @Test
    public void testSetNickname() {
        String testNickname = "nickname";


        //List<Event> actual = client.getEventsNearby();
        //assertTrue(actual.isEmpty());
    }
}
