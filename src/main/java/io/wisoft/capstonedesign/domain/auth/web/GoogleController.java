package io.wisoft.capstonedesign.domain.auth.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.wisoft.capstonedesign.domain.auth.service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleService googleService;

    @GetMapping("/api/oauth/google")
    public String getCode(@RequestParam String code) throws JsonProcessingException {

        googleService.googleLogin(code);
        return "redirect:/";
    }
}
