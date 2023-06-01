package jpa.hello.api;

import jpa.hello.domain.Member;
import jpa.hello.repository.MemberRepository;
import jpa.hello.service.MemberService;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request){
        Member member = Member.builder()
                .name(request.getName())
                .build();

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @PatchMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id, @RequestBody @Validated UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member member = memberService.findMember(id).orElseThrow();
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @Getter
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Getter
    @NoArgsConstructor
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @AllArgsConstructor
    @Getter
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @NoArgsConstructor
    @Getter
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }
}
