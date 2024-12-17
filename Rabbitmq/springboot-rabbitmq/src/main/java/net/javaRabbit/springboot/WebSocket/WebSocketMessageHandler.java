package net.javaRabbit.springboot.WebSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketMessageHandler implements WebSocketHandler {

    private static final Map<String, WebSocketSession> activeSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            activeSessions.put(userId, session);
            System.out.println("WebSocket connection established for user: " + userId);
        } else {
            System.err.println("Error: userId is null, could not establish connection.");
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, org.springframework.web.socket.WebSocketMessage<?> message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            activeSessions.remove(userId);
            System.out.println("WebSocket connection closed for user: " + userId);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket transport error: " + exception.getMessage());
    }

    public void sendMessageToClient(String userId, String message) throws Exception {
        WebSocketSession session = activeSessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        } else {
            System.out.println("Session for user " + userId + " not found or closed.");
        }
    }

    public void sendEnergyExceededNotification(String userId, String deviceId, float exceededAmount) throws Exception {
        String message = "Device " + deviceId + " has exceeded the maximum energy limit by " + exceededAmount + " kWh!";
        sendMessageToClient(userId, message);
    }

    private String getUserIdFromSession(WebSocketSession session) {
        URI uri = session.getUri();
        if (uri != null) {
            String query = uri.getQuery();
            if (query != null && query.startsWith("userId=")) {
                return query.split("=")[1];
            }
        }
        return null;
    }
}
