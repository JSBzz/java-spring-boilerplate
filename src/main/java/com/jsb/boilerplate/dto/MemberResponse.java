package com.jsb.boilerplate.dto;

import com.jsb.boilerplate.model.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private final String loginId;
    private final String name;
    private final String email;
    private final String role;

    public MemberResponse(Member member) {
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}