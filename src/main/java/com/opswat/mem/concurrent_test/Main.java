package com.opswat.mem.concurrent_test;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

public class Main {

    private static final int CONNECTION_PER_THREAD = 1000;
    private static final int MAX_THREAD = 3;

    public static void main(String[] argv) {
        for (int thread = 0; thread < MAX_THREAD; thread++) {
            new Thread(() -> {
                for (int connection = 0; connection < CONNECTION_PER_THREAD; connection++) {
                    WebSocketClient client = new StandardWebSocketClient();
                    WebSocketStompClient stompClient = new WebSocketStompClient(client);
                    stompClient.setMessageConverter(new StringMessageConverter());
                    stompClient.connect("ws://localhost:8999/ws", new StompSessionHandler(connection));
                }
            }).start();
        }

        new Scanner(System.in).nextLine();
    }
}
