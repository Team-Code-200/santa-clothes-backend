package io.wisoft.capstonedesign.domain.auth.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.wisoft.capstonedesign.domain.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/api/oauth/kakao")
    public String getCode(@RequestParam String code) throws JsonProcessingException {

        kakaoService.kakaoLogin(code);
        return "redirect:/";
    }

    @GetMapping("/api/auth/signin")
    public String logIn() {
        return "index";
    }
}
