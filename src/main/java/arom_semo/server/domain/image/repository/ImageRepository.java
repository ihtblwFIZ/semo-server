package arom_semo.server.domain.image.repository;

import arom_semo.server.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
