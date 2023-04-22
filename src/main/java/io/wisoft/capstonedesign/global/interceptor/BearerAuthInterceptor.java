package io.wisoft.capstonedesign.global.interceptor;

import io.wisoft.capstonedesign.global.jwt.AuthorizationExtractor;
import io.wisoft.capstonedesign.global.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        String name = jwtTokenProvider.getPayload(token);

        request.setAttribute("name", name);
        return true;
    }
}
