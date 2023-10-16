package hello.core.member;

import hello.core.AppConfing;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfing appConfing = new AppConfing();
        this.memberService = appConfing.memberService();
    }

    @Test
    void join(){
        //given
        Member member = new Member(1L,"memberA",Grade.VIP);


        //when
        memberService.join(member);
        Member findMember = memberService.findByMember(1L);


        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
