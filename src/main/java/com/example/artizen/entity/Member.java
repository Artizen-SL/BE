package com.example.artizen.entity;

import com.example.artizen.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends TimeStamped {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String profileImgUrl;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private MemberGenderEnum gender;

    @Column(nullable = false)
    private String ageRange;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private MemberRoleEnum authority;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Heart> heartList;

    public Member(String kakaoId, String nickname, String encodedPassword, MemberRoleEnum role, MemberGenderEnum gender, String ageRange, String profileImgUrl, String birthday) {
        this.id = kakaoId;
        this.nickname = nickname;
        this.password = encodedPassword;
        this.authority = role;
        this.gender = gender;
        this.ageRange = ageRange;
        this.profileImgUrl = profileImgUrl;
        this.birthday = birthday;
    }

}
