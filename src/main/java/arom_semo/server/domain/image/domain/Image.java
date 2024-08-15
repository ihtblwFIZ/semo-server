package arom_semo.server.domain.image.domain;

import arom_semo.server.domain.post.domain.Post;
import arom_semo.server.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post-id", nullable = false)
    private Post post;

    public Image(String imageUrl, Post post) {
        this.imageUrl = imageUrl;
        this.post = post;
    }

    public void updatePost(Post post) {
        this.post = post;
    }
}
