package com.opswat.mem;

import com.opswat.mem.message.ServerNoti;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Log4j2
public class ServerNoficationHandler extends StompSessionHandlerAdapter {

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception" +  exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ServerNoti.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ServerNoti noti = ((ServerNoti) payload);
        String res = "Service notification : " + noti.getCode() + " - " + noti.getMessage();
        log.warn(res);
    }
}
