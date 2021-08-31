package com.example.board.repository;

import com.example.board.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Insert Test")
    @Test
    public void insertMemberTest(){

        IntStream.rangeClosed(103,105).forEach(i->{
            Member member = Member.builder()
                    .email("user"+i+"@aaa.com")
                    .password("1111")
                    .name("USER"+i)
                    .build();

            memberRepository.save(member);
        });

    }

}
