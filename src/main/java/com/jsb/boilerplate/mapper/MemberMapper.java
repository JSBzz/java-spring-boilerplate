package com.jsb.boilerplate.mapper;

import com.jsb.boilerplate.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<Member> findByLoginId(String loginId);

    void save(Member member);

    void updateRefreshToken(@Param("loginId") String loginId, @Param("refreshToken") String refreshToken);

    Optional<Member> findByRefreshToken(String refreshToken);
}