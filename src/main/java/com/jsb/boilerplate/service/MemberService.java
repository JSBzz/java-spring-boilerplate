package com.jsb.boilerplate.service;

import com.jsb.boilerplate.dto.MemberRequest;
import com.jsb.boilerplate.global.error.CustomException;
import com.jsb.boilerplate.mapper.MemberMapper;
import com.jsb.boilerplate.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(MemberRequest request) {
        memberMapper.findByLoginId(request.getLoginId())
                .ifPresent(m -> { throw new CustomException("이미 존재하는 아이디입니다.", HttpStatus.CONFLICT.value(), "USER_DUPLICATE"); });
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member newMember = request.toEntity(encodedPassword);

        memberMapper.save(newMember);
    }
}
