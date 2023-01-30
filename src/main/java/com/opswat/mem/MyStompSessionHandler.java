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
//        session.subscribe("/user/queue/reply", this);
//        session.subscribe("/topic/news", new ServerNoficationHandler());
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
        String mess = "Message from server: " + ((Greeting) payload).getContent();
        System.out.println(mess);
    }
}