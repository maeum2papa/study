package study.querydsl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name="team_id") //fk
    private Team team;

    public Member(String username) {
        this.username = username;
        this.age = 0;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
        this.team = null;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }

    }

    private void changeTeam(Team team) {
        this.team = team;
    }
}
