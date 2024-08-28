package arom_semo.server.domain.image.service;

import arom_semo.server.domain.image.domain.Image;
import arom_semo.server.domain.image.repository.ImageRepository;
import arom_semo.server.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public Image addImage(String imageUrl, Post post) {
        Image image = Image.builder()
                .imageUrl(imageUrl)
                .post(post)
                .build();

        return imageRepository.save(image);
    }

    @Transactional
    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }
}
