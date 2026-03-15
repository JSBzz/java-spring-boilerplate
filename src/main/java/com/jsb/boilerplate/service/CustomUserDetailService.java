package com.jsb.boilerplate.service;

import com.jsb.boilerplate.model.Member;
import com.jsb.boilerplate.mapper.MemberMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final MemberMapper memberMapper;

    public CustomUserDetailService(@Lazy MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberMapper.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));

        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }
}
