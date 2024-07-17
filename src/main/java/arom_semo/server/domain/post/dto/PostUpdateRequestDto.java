package arom_semo.server.domain.post.dto;

import arom_semo.server.domain.member.domain.Member;

public record PostUpdateRequestDto(
        Long postId,
        String title,
        String content,
        Member member) {
    public static PostUpdateRequestDto of(Long postId, String title, String content, Member member) {
        return new PostUpdateRequestDto(postId, title, content, member);
    }
}
