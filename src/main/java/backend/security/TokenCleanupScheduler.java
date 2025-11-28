package backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

    private final JwtUtil jwtUtil;

    @Scheduled(cron = "0 0 2 * * ?")
    public void limpiarTokensExpirados() {
        jwtUtil.limpiarTokensExpirados();
    }
}