package arom_semo.server.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@Table(name = "member_tag")
@NoArgsConstructor(access = PROTECTED)
public class MemberTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberTagId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
