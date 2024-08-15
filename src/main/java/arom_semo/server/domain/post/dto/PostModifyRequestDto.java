package arom_semo.server.domain.post.dto;

import arom_semo.server.domain.member.domain.Member;

import java.util.List;

public record PostModifyRequestDto(
        Long postId,
        String title,
        String content,
        Member member,
        List<String> images) {
}
