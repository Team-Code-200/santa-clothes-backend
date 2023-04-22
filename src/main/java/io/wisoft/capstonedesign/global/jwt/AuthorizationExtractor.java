package io.wisoft.capstonedesign.global.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Slf4j
@Component
public class AuthorizationExtractor {

    public String extract(final HttpServletRequest request, final String type) {

        Enumeration<String> headers = request.getHeaders("Authorization");

        while (headers.hasMoreElements()) {

            String value = headers.nextElement();

            if (value.toLowerCase().startsWith(type.toLowerCase())) {
                return value.substring(type.length()).trim();
            }
        }

        return Strings.EMPTY;
    }
}
