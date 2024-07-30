package arom_semo.server.domain.post.dto;

import arom_semo.server.domain.member.domain.Member;

public record PostModifyRequestDto(
        Long postId,
        String title,
        String content,
        Member member) {
}
