package ru.trofimov.eventmanager.sequrity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.trofimov.common.errors.ErrorMessageResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Handling authentication exception", authException);
        ErrorMessageResponse responseMessage = new ErrorMessageResponse(
                "Необходима аутентификация",
                authException.getMessage(),
                LocalDateTime.now()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                objectMapper.writeValueAsString(responseMessage)
        );
    }
}
