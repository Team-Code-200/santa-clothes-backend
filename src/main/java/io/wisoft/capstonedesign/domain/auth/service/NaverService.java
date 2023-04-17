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
public class NaverService {

    private final UserRepository userRepository;

    @Value("${oauth.naver.client_id}")
    private String clientId;

    @Value("${oauth.naver.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.naver.client_secret}")
    private String clientSecret;

    @Value("${oauth.naver.auth_uri}")
    private String authUri;

    @Value("${oauth.naver.info_uri}")
    private String infoUri;

    /**
     * 로그인 프로세스
     */
    public void naverLogin(final String code) throws JsonProcessingException {

        // 1. 인가 코드로 액세스 토큰 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        OauthUserInfoDto oauthUserInfo = getNaverUserInfo(accessToken);

        // 3. 필요시 회원 가입
        registerNaverUserIfNeeded(oauthUserInfo);
    }

    /**
     * 토큰 획득
     */
    public String getAccessToken(final String code) throws JsonProcessingException {

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
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(
                authUri,
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    /**
     * 네이버 유저 정보 가져오기
     */
    private OauthUserInfoDto getNaverUserInfo(final String accessToken) throws JsonProcessingException {

        // HTTP 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> naverUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(
                infoUri,
                HttpMethod.POST,
                naverUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        // 프로퍼티를 이용해 정보 가져오기
        String id = jsonNode.get("response").get("id").asText();
        String nickname = jsonNode.get("response").get("nickname").asText();
        String email = jsonNode.get("response").get("email").asText();
        String profileImage = jsonNode.get("response").get("profile_image").asText();

        return new OauthUserInfoDto(nickname, id, email, profileImage);
    }

    /**
     * id 체크 및 필요시 회원가입 과정
     */
    private User registerNaverUserIfNeeded(OauthUserInfoDto oauthUserInfo) {

        // DB에 중복된 Kakao Id가 있는지 확인
        String naverId = oauthUserInfo.oauthId();
        User naverUser = userRepository.findByOauthId(naverId).orElse(null);

        if (naverUser == null) {

            naverUser = User.newInstance(naverId, oauthUserInfo.email(), oauthUserInfo.profileImage(), 0, oauthUserInfo.nickname(), Role.GENERAL);
            userRepository.save(naverUser);
        }

        return naverUser;
    }
}
