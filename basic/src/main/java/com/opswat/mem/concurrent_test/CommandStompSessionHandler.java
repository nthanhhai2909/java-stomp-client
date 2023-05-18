package com.opswat.mem.concurrent_test;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class CommandStompSessionHandler extends StompSessionHandlerAdapter {

    private int index;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println(String.format("Connection %s Connected to server From Thread %s", index, Thread.currentThread().getId()));
        session.subscribe("/user/queue/1", this);
        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                session.send("/user/queue/1", "Hello: " + count);
            }

        }).start();

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error(exception);
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        if (payload == null) {
            return;
        }
        String mess = "Message from server: " + payload;
        System.out.println(mess);
        log.error(mess);
    }
}