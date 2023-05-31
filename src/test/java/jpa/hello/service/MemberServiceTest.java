package jpa.hello.service;

import jpa.hello.domain.Member;
import jpa.hello.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Test
    void 회원가입() {
        //given
        Member member = Member.builder()
                .name("kim")
                .build();
        //when
        Long saveId = memberService.join(member);
        //test
        assertThat(member).isEqualTo(memberRepository.findById(saveId).orElse(null));
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = Member.builder()
                .name("kim")
                .build();
        Member member2 = Member.builder()
                .name("kim")
                .build();
        //when
        memberService.join(member1);
        //test
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}