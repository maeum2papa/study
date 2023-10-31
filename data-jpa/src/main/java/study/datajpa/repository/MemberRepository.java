package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;


import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsername(String username);

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age > :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);


    List<Member> findListByUsername(String username); //컬렉션, 결과가 없으면 .size() = 0
    Member findMemberByUsername(String username); //단건, 결과가 없으면 NULL
    Optional<Member> findOptionalByUsername(String username); //단건 Optional

    @Query("select m from Member m where m.age = :age")
    Page<Member> findByAgeAsPage(@Param("age") int age, Pageable pageable);

//    @Query(value = "select m from Member m where m.age = :age",countQuery = "select count(m) from Member m where m.age = :age")
//    Page<Member> findByAgeAsPage(@Param("age") int age, Pageable pageable);

    @Query("select m from Member m where m.age = :age")
    Slice<Member> findByAgeAsSlice(@Param("age") int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int blukAgePlus(@Param("age") int age);


    @Query("select  m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // fetch join, findMemberFetchJoin 참조
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value="true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 내가 하는 동안 데이터베이스 건들지마! <- 실시간 서비스에서는 별로 쓸일이 없네
    List<Member> findLockByUsername(String username);
}
