package jpa.hello.service;

import jpa.hello.domain.Member;
import jpa.hello.repository.MemberRepository;
import jpa.hello.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findMember(Long id){
       return memberRepository.findById(id);
    }
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.updateMember(name);
    }


}
