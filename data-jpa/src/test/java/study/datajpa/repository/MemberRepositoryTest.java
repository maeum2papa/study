package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);


        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);


    }


    @Test
    public void findByUsernameAndAgeGreaterThan(){

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> findMembers = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        System.out.println("findMembers = " + findMembers);

        assertThat(findMembers.get(0).getUsername()).isEqualTo("AAA");
        assertThat(findMembers.get(0).getAge()).isEqualTo(20);

    }


    @Test
    public void testQuery(){

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> findMembers = memberRepository.findUser("AAA",10);

        System.out.println("findMembers = " + findMembers);

        assertThat(findMembers.get(0).getUsername()).isEqualTo("AAA");
        assertThat(findMembers.get(0).getAge()).isEqualTo(20);

    }

    @Test
    public void findUsernameList() {

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();

        System.out.println("findMembers = " + usernameList);

    }

    @Test
    public void findMemberDto() {

        Team team1 = new Team("Team1");
        teamRepository.save(team1);

        Member m1 = new Member("BBB", 37, team1);

        memberRepository.save(m1);

        List<MemberDto> members = memberRepository.findMemberDto();

        System.out.println("members = " + members);

    }


    @Test
    public void findByNames() {

        Team team1 = new Team("Team1");
        teamRepository.save(team1);

        Member m1 = new Member("BBB", 37, team1);
        Member m2 = new Member("CCC", 27, team1);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> byNames = memberRepository.findByNames(Arrays.asList("BBB", "CCC"));

        System.out.println("byNames = " + byNames);

    }


    @Test
    public void returnType() {

        Member m1 = new Member("BBB", 37);
        Member m2 = new Member("CCC", 27);

        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> bbb = memberRepository.findListByUsername("BBB");
        Member bbb2 = memberRepository.findMemberByUsername("BBB");
        Optional<Member> bbb3 = memberRepository.findOptionalByUsername("BBB");

        System.out.println("bbb = " + bbb);
        System.out.println("bbb2 = " + bbb2);
        System.out.println("bbb3 = " + bbb3);


    }


    @Test
    public void paging() {

        memberRepository.save(new Member("AAA", 10));
        memberRepository.save(new Member("BBB", 10));
        memberRepository.save(new Member("CCC", 10));
        memberRepository.save(new Member("DDD", 10));
        memberRepository.save(new Member("EEE", 10));

        int age = 10;

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findByAgeAsPage(age, pageRequest);

        Page<MemberDto> map = page.map(m -> new MemberDto(m.getId(), m.getUsername(), ""));

        List<Member> members = page.getContent();
        long totalElements = page.getTotalElements();

        System.out.println("members = " + members);
        System.out.println("totalElements = " + totalElements);


        assertThat(members.size()).isEqualTo(3);
        assertThat(totalElements).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); //now page
        assertThat(page.getTotalPages()).isEqualTo(2); //총 페이지 수
        assertThat(page.isFirst()).isTrue(); // 첫 페이지인가?
        assertThat(page.hasNext()).isTrue(); // 다음 페이지가 있는가?


        Slice<Member> slice = memberRepository.findByAgeAsSlice(age, pageRequest);
        List<Member> members1 = slice.getContent();

        System.out.println("members1 = " + members1);

        assertThat(members1.size()).isEqualTo(3);
        assertThat(slice.getNumber()).isEqualTo(0); //now page
        assertThat(slice.isFirst()).isTrue(); // 첫 페이지인가?
        assertThat(slice.hasNext()).isTrue(); // 다음 페이지가 있는가?


    }


    @Test
    public void blukUpdate() {

        memberRepository.save(new Member("AAA", 10));
        memberRepository.save(new Member("BBB", 20));
        memberRepository.save(new Member("CCC", 30));
        memberRepository.save(new Member("DDD", 40));
        memberRepository.save(new Member("EEE", 50));

        int i = memberRepository.blukAgePlus(20);
//        em.clear(); == (@clearAutomatically= true);

        List<Member> result = memberRepository.findByUsername("EEE");
        System.out.println("result.get(0) = " + result.get(0));

        assertThat(i).isEqualTo(4);

    }


    @Test
    public void findMemberLazy() {

        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 20, teamA);
        Member member2 = new Member("member2", 30, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

//        List<Member> members = memberRepository.findAll();
//        List<Member> members = memberRepository.findMemberFetchJoin();
        List<Member> members = memberRepository.findMemberEntityGraph();


        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }

    }


    @Test
    public void queryHint() {
        Member member1 = memberRepository.save(new Member("AAA", 10));
        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("AAA");
        findMember.setUsername("member1"); //@QueryHit를 활용하여 update query 사용 안함

        em.flush();
    }


    @Test
    public void lock() {
        Member member1 = memberRepository.save(new Member("AAA", 10));
        em.flush();
        em.clear();

        List<Member> members = memberRepository.findLockByUsername("AAB");

    }
    
    
    @Test
    public void callCustom() {
        List<Member> memberCustom = memberRepository.findMemberCustom();
    }


    @Test
    public void JpaEventBaseEntity() throws Exception{
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(member.getId()).get();

        System.out.println("member1.getCreatedDate = " + member1.getCreatedDate());
        System.out.println("member1.getUpdatedDate = " + member1.getLastModifiedDate());
        System.out.println("member1.getCreatedBy() = " + member1.getCreatedBy());
        System.out.println("member1.getLastModifiedBy() = " + member1.getLastModifiedBy());


    }
}