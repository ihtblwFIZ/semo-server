package arom_semo.server.domain.joinrequest.domain;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "group_join_request")
public class GroupJoinRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupJoinRequestId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    @Column(nullable = false)
    private LocalDateTime statusChangedAt;

    public enum Status {
        pending, approved, rejected
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
