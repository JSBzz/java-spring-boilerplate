package com.jsb.boilerplate.service;

import com.jsb.boilerplate.dto.MemberRequest;
import com.jsb.boilerplate.global.error.CustomException;
import com.jsb.boilerplate.global.error.ErrorCode;
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
                .ifPresent(m -> { throw new CustomException(ErrorCode.LOGIN_ID_DUPLICATION); });
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member newMember = request.toEntity(encodedPassword);

        memberMapper.save(newMember);
    }
}
