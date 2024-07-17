package arom_semo.server.domain.post.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.group.repository.GroupRepository;
import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.domain.member.repository.MemberRepository;
import arom_semo.server.domain.post.domain.Post;
import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import arom_semo.server.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostManagementService {
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;

    @Transactional
    public String createPost(String userName, String groupName, PostCreateRequestDto dto) {
        Member member = memberRepository.findByUsername(userName).orElseThrow();
        Group group = groupRepository.findByName(groupName).orElseThrow();

        Post post = Post.builder()
                .title(dto.title())
                .content(dto.content())
                .group(group)
                .member(member)
                .build();

        return postRepository.save(post).getPostId().toString();
    }
}
