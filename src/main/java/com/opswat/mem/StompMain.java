package com.opswat.mem;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class StompMain {

    public static void main(String[] argv) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.connect("ws://localhost:8999/ws", new MyStompSessionHandler());
        new Scanner(System.in).nextLine(); // Don't close immediately.
    }
}
