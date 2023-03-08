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

    @Column(nullable = false)
    private String subId;

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
    private List<ArtizenHeart> artizenHeartList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<CommunityHeart> communityHeartList;

    public Member(String kakaoId, String nickname, String encodedPassword, MemberRoleEnum role, MemberGenderEnum gender, String ageRange, String profileImgUrl, String birthday, String subId) {
        this.id = kakaoId;
        this.nickname = nickname;
        this.subId = subId;
        this.password = encodedPassword;
        this.authority = role;
        this.gender = gender;
        this.ageRange = ageRange;
        this.profileImgUrl = profileImgUrl;
        this.birthday = birthday;
    }

    public void updateAuth (MemberRoleEnum role) {
        this.authority = role;
    }

}
