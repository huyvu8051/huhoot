package com.huhoot.socket;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.huhoot.config.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author HuyVu
 * @CreatedDate 9/30/2022 오후 4:37
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SocketAuthorizationListener implements AuthorizationListener {
    private final JwtUtil jwtUtil;

    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
        String token = handshakeData.getHttpHeaders().get("Authorization").substring(7);
        String username = jwtUtil.extractUsername(token);
        // jwtUtil.validateToken()


        return true;
    }
}
