package arom_semo.server.domain.post.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.group.repository.GroupRepository;
import arom_semo.server.domain.image.domain.Image;
import arom_semo.server.domain.image.repository.ImageRepository;
import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.domain.member.repository.MemberRepository;
import arom_semo.server.domain.post.domain.Post;
import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import arom_semo.server.domain.post.dto.PostResponseDto;
import arom_semo.server.domain.post.dto.PostModifyRequestDto;
import arom_semo.server.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostManagementService {
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

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

        postRepository.save(post);

        dto.images().forEach(images -> {
            Image image = new Image(images, post);
            post.addImage(image);
            imageRepository.save(image);
        });

        return post.getPostId().toString();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        List<String> images = post.getImages().stream()
                .map(Image::getImageUrl).toList();

        return PostResponseDto.of(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getGroup(),
                post.getMember(),
                images);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> {
                    List<String> images = post.getImages().stream()
                            .map(Image::getImageUrl).toList();

                    return PostResponseDto.of(
                            post.getPostId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getGroup(),
                            post.getMember(),
                            images);
                })
                .toList();
    }

    @Transactional
    public void modifyPost(String userName, PostModifyRequestDto dto) {
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

        if (dto.images() != null && !dto.images().isEmpty()) {
            post.getImages().clear();
            dto.images().forEach(imageUrl -> {
                Image image = new Image(imageUrl, post);
                post.addImage(image);
                imageRepository.save(image);
            });
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
