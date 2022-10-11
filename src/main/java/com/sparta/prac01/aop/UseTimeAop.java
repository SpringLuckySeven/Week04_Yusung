package com.sparta.prac01.aop;

import com.sparta.prac01.model.ApiUseTime;
import com.sparta.prac01.model.User;
import com.sparta.prac01.repository.ApiUseTimeRepository;
import com.sparta.prac01.security.UserDetailsImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect // 부가기능을 모듈화 할거야
@Component  //Spring에게 Bean으로 등록해줘 라고 하는 어노테이션
public class UseTimeAop {
    private final ApiUseTimeRepository apiUseTimeRepository;

    public UseTimeAop(ApiUseTimeRepository apiUseTimeRepository) {
        this.apiUseTimeRepository = apiUseTimeRepository;
    }

    //어라운드 : 핵심기능의 수행 전후에 모두 수행되는 어드바이스
    @Around("execution(public * com.sparta.springcore.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 측정 시작 시간
        long startTime = System.currentTimeMillis();

        try {
            // 핵심기능 수행
            Object output = joinPoint.proceed(); //joinPoint.proceed() 원래 수행했어야할 핵심기능
            return output;
        } finally {
            // 측정 종료 시간
            long endTime = System.currentTimeMillis();
            // 수행시간 = 종료 시간 - 시작 시간
            long runTime = endTime - startTime;

            // 로그인 회원이 없는 경우, 수행시간 기록하지 않음
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
                // 로그인 회원 정보
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                User loginUser = userDetails.getUser();

                // API 사용시간 및 DB 에 기록
                ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser)
                        .orElse(null);
                if (apiUseTime == null) {
                    // 로그인 회원의 기록이 없으면
                    apiUseTime = new ApiUseTime(loginUser, runTime);
                } else {
                    // 로그인 회원의 기록이 이미 있으면
                    apiUseTime.addUseTime(runTime);
                }

                System.out.println("[API Use Time] Username: " + loginUser.getUsername() + ", Total Time: " + apiUseTime.getTotalTime() + " ms");
                apiUseTimeRepository.save(apiUseTime);
            }
        }
    }
}
