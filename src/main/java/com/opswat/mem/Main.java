package com.opswat.mem;

import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        WebSocket ws = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:8999/ws"), new WebSocketClient())
                .join();

        while (true) {
            System.out.println("---------------------------------------- OPTIONS ----------------------------------------");
            System.out.println("1: Subscribe");
            System.out.println("2: Unsubscribe");
            System.out.println("3: Broadcast to all user");
            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "SUBSCRIBE");
                    jsonObject.addProperty("payload", destination);
                    ws.sendText(jsonObject.toString(), true);
                    break;
                }
                case "2": {
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "UNSUBSCRIBE");
                    jsonObject.addProperty("payload", destination);
                    ws.sendText(jsonObject.toString(), true);
                    break;
                }
                case "3": {
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter Message: ");
                    String message = scanner.nextLine();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "BROADCAST");
                    JsonObject payload = new JsonObject();
                    payload.addProperty("data", message);
                    payload.addProperty("destination", destination);
                    jsonObject.addProperty("payload", payload.toString());
                    ws.sendText(jsonObject.toString(), true);
                    break;
                }

                case "-1": {
                    return;
                }
                default: {
                    System.out.println("Incorrect options, please choose again!");
                }
            }
        }
    }


    private static class WebSocketClient implements WebSocket.Listener {

        public WebSocketClient() {
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("onOpen using subprotocol " + webSocket.getSubprotocol());
            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            System.out.println("onText received " + data);
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        @Override
        public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
            System.out.println("onBinary received " + data);
            return WebSocket.Listener.super.onBinary(webSocket, data, last);
        }



        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.out.println("Bad day! " + webSocket.toString());
            WebSocket.Listener.super.onError(webSocket, error);
        }
    }
}