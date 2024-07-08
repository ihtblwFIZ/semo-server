package arom_semo.server.domain.bookmark.domain;

import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.domain.post.domain.Post;
import arom_semo.server.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
public class BookMark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long bookMarkId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
