package arom_semo.server.domain.group.domain;

import arom_semo.server.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(unique = true, nullable = false, length = 32)
    private String name;

    @Column(nullable = false)
    private int maxParticipants = 1;

    @Column(nullable = false)
    private int currentParticipants;

    @Column(nullable = false)
    private int maxAge = 100;

    @Column(nullable = false)
    private int minAge;

    @OneToOne
    @JoinColumn(name = "group_location_id", nullable = false)
    private GroupLocation groupLocation;
}