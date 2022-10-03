package com.huhoot.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.huhoot.config.security.JwtUtil;
import com.huhoot.organize.OrganizeService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageEventHandler {
    private final SocketIOServer socketIOServer;
    private final OrganizeService orgService;
    private final JwtUtil jwtUtil;

    @OnConnect
    public void onConnect(SocketIOClient client) throws NullPointerException {
        String challengeId = client.getHandshakeData().getSingleUrlParam("challengeId");

        client.joinRoom(challengeId);

        String authorization = client.getHandshakeData().getHttpHeaders().get("Authorization");
        String token = authorization.substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);

        Integer userId = claims.get("userId", Integer.class);
        Collection roles = claims.get("roles", Collection.class);

        client.set("userId", userId);
        client.set("roles", roles);

        client.sendEvent("connected", orgService.getCurrentPublishedExam(Integer.valueOf(challengeId)));
        log.info("a client was connected, challengeId: {}, userId: {}", challengeId, userId);
    }


}