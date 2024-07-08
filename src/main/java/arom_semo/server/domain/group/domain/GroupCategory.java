package arom_semo.server.domain.group.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
public class GroupCategory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long groupCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public enum Category {
        CATEGORY
    }
}
