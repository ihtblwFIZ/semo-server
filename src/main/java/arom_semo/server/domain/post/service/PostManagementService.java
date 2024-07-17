package arom_semo.server.domain.post.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.group.repository.GroupRepository;
import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.domain.member.repository.MemberRepository;
import arom_semo.server.domain.post.domain.Post;
import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import arom_semo.server.domain.post.dto.PostResponseDto;
import arom_semo.server.domain.post.dto.PostUpdateRequestDto;
import arom_semo.server.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return PostResponseDto.of(
                post.getPostId(), post.getTitle(), post.getContent(), post.getGroup(), post.getMember());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostResponseDto.of(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getGroup(),
                        post.getMember()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void modifyPost(String userName, PostUpdateRequestDto dto) {
        Post post = postRepository.findById(dto.postId()).orElseThrow();
        Member member = memberRepository.findByUsername(userName).orElseThrow();

        if (!post.getMember().equals(member)) {
            throw new IllegalArgumentException("Error");
        }

        if (dto.title() != null) {
            post.updateTitle(dto.title());
        }
        if (dto.content() != null) {
            post.updateContent(dto.content());
        }
    }

    @Transactional
    public void deletePost(String userName, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Member member = memberRepository.findByUsername(userName).orElseThrow();

        if (!post.getMember().equals(member)) {
            throw new IllegalArgumentException("Error");
        }

        postRepository.delete(post);
    }
}
