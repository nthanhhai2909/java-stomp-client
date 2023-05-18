package com.opswat.mem;

import com.opswat.mem.message.Greeting;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Connected to server");
        session.send("/app/hello", "Hello server " + session.getSessionId());
        StompSession.Subscription subscription = session.subscribe("/user/queue/1", this);
        subscription.unsubscribe();

        StompSession.Subscription subscriptionNoACK = session.subscribe("/user/queue/2", this);

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.set(StompHeaders.DESTINATION, "/user/queue/2");
        stompHeaders.setAck("client");
        StompSession.Subscription subscriptionClientACK =  session.subscribe(stompHeaders, this);

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("hbihi");
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
    }
}