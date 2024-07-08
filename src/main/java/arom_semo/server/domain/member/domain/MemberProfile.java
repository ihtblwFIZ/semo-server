package arom_semo.server.domain.member.domain;

import arom_semo.server.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@Table(name = "member_profile")
@NoArgsConstructor(access = PROTECTED)
public class MemberProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberProfileId;

    @Column(nullable = false, length = 8)
    private String nickname;

    private String profileImageUrl;

    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    public enum Gender {
        M, F
    }
}
