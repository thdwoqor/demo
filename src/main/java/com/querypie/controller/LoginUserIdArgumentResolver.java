package com.querypie.controller;

import com.querypie.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUserId.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        if (authorization == null) {
            throw new IllegalArgumentException("Authorization 헤더가 누락되었습니다.");
        }

        String[] split = authorization.split(" ");
        String token = split[1];

        if (!split[0].toLowerCase().equals("bearer")) {
            throw new IllegalArgumentException("Authorization 타입이 일치하지 않습니다.");
        }
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("토큰의 유효 시간이 만료되었습니다. 다시 로그인해주세요.");
        }

        return jwtUtil.getUsernameByToken(token);
    }
}
