package com.jsb.boilerplate.api;

import com.jsb.boilerplate.dto.MemberRequest;
import com.jsb.boilerplate.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member API", description = "유저 관리 API")
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "계정 생성", description = "유저 계정 생성")
    @PostMapping("/join")
    public void join(@Valid @RequestBody MemberRequest dto) {
        memberService.join(dto);
    }

}