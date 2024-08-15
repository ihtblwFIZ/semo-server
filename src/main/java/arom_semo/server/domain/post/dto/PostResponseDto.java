package arom_semo.server.domain.post.dto;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.member.domain.Member;

import java.util.List;

public record PostResponseDto(
        Long postId,
        String title,
        String content,
        Group group,
        Member member,
        List<String> images) {
    public static PostResponseDto of(Long postId, String title, String content, Group group, Member member, List<String> images) {
        return new PostResponseDto(postId, title, content, group, member, images);
    }
}
