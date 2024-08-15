package arom_semo.server.domain.image.domain;

import arom_semo.server.domain.post.domain.Post;
import arom_semo.server.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post-id", nullable = false)
    private Post post;
}
