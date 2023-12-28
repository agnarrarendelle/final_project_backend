package practice.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class MyWebSocketHandler {
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        System.out.println("WS CONNECTED");
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println("WS DISCONNECTED");
    }
}
