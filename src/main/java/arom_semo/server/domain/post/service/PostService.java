package arom_semo.server.domain.post.service;

import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import arom_semo.server.domain.post.dto.PostModifyRequestDto;
import arom_semo.server.domain.post.dto.PostResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private PostManagementService postManagementService;

    @Transactional
    public String createPost(Long userId, PostCreateRequestDto dto) {
        return postManagementService.createPost(userId, dto);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        return postManagementService.findPost(postId);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postManagementService.findAllPosts();
    }

    @Transactional
    public void modifyPost(Long userId, PostModifyRequestDto dto) {
        postManagementService.modifyPost(userId, dto);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        postManagementService.deletePost(userId, postId);
    }
}
