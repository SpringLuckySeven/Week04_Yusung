package com.sparta.prac01.controller;

import com.sparta.prac01.model.ApiUseTime;
import com.sparta.prac01.model.UserRoleEnum;
import com.sparta.prac01.repository.ApiUseTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //JSON 형태로 값을 반환할 수 있는 컨트롤러라는 의미의 어노테이션
public class ApiUseTimeController {
    private ApiUseTimeRepository apiUseTimeRepository;

    @Autowired //Repository와 연동
    public ApiUseTimeController(ApiUseTimeRepository apiUseTimeRepository) {
        this.apiUseTimeRepository = apiUseTimeRepository;
    }

    @Secured(UserRoleEnum.Authority.ADMIN) //관리자 권한이 있는 사용자만 접근할 수 있게하는 어노테이션
    @GetMapping("/api/use/time")
    public List<ApiUseTime> getAllApiUseTime() {
        return apiUseTimeRepository.findAll();
    }
}
