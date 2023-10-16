package hello.core.singleton;

import hello.core.AppConfing;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigutationSingletonTest {

    @Test
    void configrationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfing.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);


        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService.memberRepository = " + memberRepository1);
        System.out.println("orderService.memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(orderService.getMemberRepository());
    }
    
    @Test
    void configrationDeep(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfing.class);
        AppConfing bean = ac.getBean(AppConfing.class);

        System.out.println("bean.getClass() = " + bean.getClass());
    }
}
