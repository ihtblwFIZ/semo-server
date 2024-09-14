package arom_semo.server.domain.group.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class GroupLocation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long groupLocationId;

    @Column(length = 32)
    private String county;

    @Column(length = 32)
    private String city;

    @Column(length = 32)
    private String streetName;

    @Column(length = 32)
    private String buildingNumber;

    @Column(length = 32)
    private String detailedInfo;
}
