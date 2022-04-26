
package com.seonbi.api.controller;

import com.seonbi.api.service.MemberService;
import com.seonbi.auth.SeonbiUserDetail;
import com.seonbi.db.entity.Member;
import com.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/member")
public class MemberController {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberService memberService;


    //로그인 후 필요한 요청
    @GetMapping("/auth")

    public ResponseEntity<String> authorize(@ApiIgnore Authentication authentication) {

//        SeonbiUserDetail details = (SeonbiUserDetail) authentication.getDetails();
//        details.getUsername();

        return ResponseEntity.ok("ok");
    }


    //회원가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody Member member) {

        System.out.println("--------컨트롤러 내부---------------");
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberService.register(member);

        System.out.println("----------------------------------");
        return ResponseEntity.ok("ok");


    }


    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {

        String email=member.getEmail();
        String password=member.getPassword();

        Member member1 = memberService.getMemberByEmail(email);

        if(member1==null)
        {
            return ResponseEntity.status(404).body("존재하지 않는 계정");
        }

        if(passwordEncoder.matches(password,member1.getPassword()))
        {
            String token = JwtTokenProvider.getToken(email, "user");

            return ResponseEntity.status(200).body(token);

        }



        return ResponseEntity.status(404).body("비밀번호가 일치하지 않습니다");
    }

}