package com.opswat.mem;

import com.opswat.mem.message.Greeting;
import com.opswat.mem.message.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

@Log4j2
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.warn("New session established : " + session.getSessionId());
        session.send("/app/hello", "Hello server " + session.getSessionId());
        session.subscribe("/user/queue/reply", this);
        session.subscribe("/topic/news", new ServerNoficationHandler());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception" + exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        if (payload == null) {
            return;
        }
        String mess = "Message from server: " + ((Greeting) payload).getContent();
        log.warn(mess);
    }
}