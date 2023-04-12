package io.wisoft.capstonedesign.domain.auth.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.wisoft.capstonedesign.domain.auth.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NaverController {

    private final NaverService naverService;

    @GetMapping("/api/oauth/naver")
    public String getCode(@RequestParam String code) throws JsonProcessingException {

        naverService.naverLogin(code);
        return "redirect:/";
    }
}
