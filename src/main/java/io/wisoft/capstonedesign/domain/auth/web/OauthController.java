package io.wisoft.capstonedesign.domain.auth.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.auth.application.OauthService;
import io.wisoft.capstonedesign.domain.auth.web.dto.LoginResponse;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiNotFoundError;
import io.wisoft.capstonedesign.global.annotation.SwaggerApiSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "소셜 로그인", description = "소셜 로그인 API")
@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @SwaggerApiSuccess(summary = "소셜 로그인", implementation = LoginResponse.class)
    @SwaggerApiNotFoundError
    @GetMapping("/api/oauth/{provider}")
    public ResponseEntity<LoginResponse> login(
            @PathVariable final String provider, @RequestParam final String code) {

        final LoginResponse loginResponse = oauthService.login(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }
}
