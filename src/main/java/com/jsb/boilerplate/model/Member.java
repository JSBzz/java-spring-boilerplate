package com.jsb.boilerplate.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;
}