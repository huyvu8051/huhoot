package com.huhoot.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.huhoot.config.security.JwtUtil;
import com.huhoot.exception.UsernameExistedException;
import com.huhoot.model.Admin;
import com.huhoot.participate.ParticipateService;
import com.huhoot.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageEventHandler {
    private final AdminRepository adminRepository;


    private final SocketIOServer socketIOServer;

    @OnConnect
    public void onConnect(SocketIOClient client) throws NullPointerException {
        String jwt = client.getHandshakeData().getSingleUrlParam("challengeId");
        client.sendEvent("connected", "connect success");

        log.info("a client was connected, challenge id: " + jwt);
      //  client.getHandshakeData().getHttpHeaders().forEach(System.out::println);

    }


    @OnEvent("messageEvent")
    public void onEvent(SocketIOClient client, AckRequest request, String data) throws UsernameExistedException {
        client.sendEvent("abc", "chung ta cua hien tai");
        throw new UsernameExistedException(":v");
    }

    private final ParticipateService participateService;


    @OnEvent("clientConnectRequest")
    public void clientConnectRequest(SocketIOClient client, SocketAuthorizationRequest request) {


    }


}