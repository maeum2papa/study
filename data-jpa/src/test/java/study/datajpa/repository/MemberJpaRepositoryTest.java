package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import javax.annotation.security.RunAs;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member saveMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(saveMember.getId());

        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);


        List<Member> members = memberJpaRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deleteCount = memberJpaRepository.count();
        assertThat(deleteCount).isEqualTo(0);


    }

    @Test
    public void paging() {

        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("BBB", 10));
        memberJpaRepository.save(new Member("CCC", 10));
        memberJpaRepository.save(new Member("DDD", 10));
        memberJpaRepository.save(new Member("EEE", 10));

        int age = 10;
        int offset = 0; // 어디서 시작할 것인가
        int limit = 3;

        long totalCount = memberJpaRepository.totalCount(age);
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);


        System.out.println("members = " + members);
        assertThat(totalCount).isEqualTo(5);

    }

    @Test
    public void blukUpdate() {

        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("BBB", 20));
        memberJpaRepository.save(new Member("CCC", 30));
        memberJpaRepository.save(new Member("DDD", 40));
        memberJpaRepository.save(new Member("EEE", 50));

        int i = memberJpaRepository.blukAgePlus(20);

        assertThat(i).isEqualTo(4);

    }

}