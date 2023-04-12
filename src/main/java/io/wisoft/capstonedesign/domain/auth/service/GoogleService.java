package io.wisoft.capstonedesign.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.capstonedesign.domain.auth.web.dto.OauthUserInfoDto;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.enumerated.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleService {

    private final UserRepository userRepository;

    @Value("${oauth.google.client_id}")
    private String clientId;

    @Value("${oauth.google.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.google.client_secret}")
    private String clientSecret;

    @Value("${oauth.google.auth_uri}")
    private String authUri;

    @Value("${oauth.google.info_uri}")
    private String infoUri;

    /**
     * 로그인 프로세스
     */
    public void googleLogin(String code) throws JsonProcessingException {

        // 1. 인가 코드로 액세스 토큰 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        OauthUserInfoDto googleUserInfo = getGoogleUserInfo(accessToken);

        // 3. 필요시 회원 가입
        registerGoogleUserIfNeeded(googleUserInfo);
    }

    /**
     * 토큰 획득
     */
    public String getAccessToken(String code) throws JsonProcessingException {

        // HTTP 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 바디 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(
                authUri,
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    /**
     * 구글 유저 정보 가져오기
     */
    private OauthUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException {

        // HTTP 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(
                infoUri,
                HttpMethod.POST,
                googleUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        // 프로퍼티를 이용해 정보 가져오기
        String id = jsonNode.get("sub").asText();
        String nickname = jsonNode.get("name").asText();
        String email = jsonNode.get("email").asText();
        String profileImage = jsonNode.get("picture").asText();

        return new OauthUserInfoDto(nickname, id, email, profileImage);
    }

    /**
     * id 체크 및 필요시 회원가입 과정
     */
    private User registerGoogleUserIfNeeded(OauthUserInfoDto googleUserInfo) {

        // DB에 중복된 Kakao Id가 있는지 확인
        String googleId = googleUserInfo.getOauthId();
        User googleUser = userRepository.findByOauthId(googleId).orElse(null);

        if (googleUser == null) {

            googleUser = User.newInstance(googleId, googleUserInfo.getEmail(), googleUserInfo.getProfileImage(), 0, googleUserInfo.getNickname(), Role.GENERAL);
            userRepository.save(googleUser);
        }

        return googleUser;
    }
}
