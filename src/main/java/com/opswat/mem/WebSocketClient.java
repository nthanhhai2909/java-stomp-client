package com.opswat.mem;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class WebSocketClient implements WebSocket.Listener {

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("Go...Open".concat(
                webSocket.getSubprotocol()));
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println(data.toString());
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("Bad day! ".concat(webSocket.toString()));
    }

    void startSocket(String connection) {
        CompletableFuture<WebSocket> server_cf = HttpClient.
                newHttpClient().
                newWebSocketBuilder().
                buildAsync(URI.create(connection),
                        new WebSocketClient());
        WebSocket server = server_cf.join();
        server.sendText("Hello!", true);
    }
}