package com.huhoot.socket;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.huhoot.config.security.JwtUtil;
import com.huhoot.model.Admin;
import com.huhoot.participate.ParticipateService;
import com.huhoot.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MessageEventHandler {
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    @OnConnect
    public void onConnect(SocketIOClient client) throws NullPointerException {
        client.sendEvent("connected", "connect success");
        log.info("a client was connected");
    }


    @OnEvent("messageEvent")
    public void onEvent(SocketIOClient client, AckRequest request, String data) {
        client.sendEvent("abc", "chung ta cua hien tai");
    }

    @OnEvent("registerHostSocket")
    public void registerHostSocket(SocketIOClient client, SocketAuthorizationRequest request) {
        try {
            String token = request.getToken().substring(7);
            String username = jwtUtil.extractUsername(token);
            Admin admin = adminRepository.findOneByUsername(username).orElseThrow(() -> new NullPointerException("Admin not found"));

            if (!jwtUtil.validateToken(token, admin)) {
                throw new Exception("Bad token");
            }


            client.joinRoom(String.valueOf(request.getChallengeId()));

            client.sendEvent("registerSuccess", HostRegisterSuccess.builder()
                    .totalStudentInChallenge(0)
                    .build());


            admin.setSocketId(client.getSessionId());
            adminRepository.save(admin);

        } catch (Exception e) {
            log.error(e.getMessage());
            client.sendEvent("joinError", "joinError");
            client.disconnect();
        }
    }

    private final ParticipateService participateService;

    @OnEvent("clientConnectRequest")
    public void clientConnectRequest(SocketIOClient client, SocketAuthorizationRequest request) {


    }


}