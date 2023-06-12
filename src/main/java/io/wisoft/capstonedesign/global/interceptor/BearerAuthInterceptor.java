package io.wisoft.capstonedesign.global.interceptor;

import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.jwt.AuthorizationExtractor;
import io.wisoft.capstonedesign.global.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class BearerAuthInterceptor implements HandlerInterceptor {

    private AuthorizationExtractor extractor;
    private JwtTokenProvider jwtTokenProvider;

    public BearerAuthInterceptor(final AuthorizationExtractor extractor, final JwtTokenProvider jwtTokenProvider) {
        this.extractor = extractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {

        String token = extractor.extract(request, "Bearer");

        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }

        String name = jwtTokenProvider.getPayload(token);

        if (!jwtTokenProvider.validateToken(token)) {

            String accessToken = jwtTokenProvider.createAccessToken(name);
            request.setAttribute("accessToken", accessToken);

            log.info(accessToken);

            log.info("refresh access token!");
        }

        request.setAttribute("name", name);
        return true;
    }
}
