package arom_semo.server.domain.post.dto;

import java.util.List;

public record PostCreateRequestDto (String title, String content, List<String> images) {
}