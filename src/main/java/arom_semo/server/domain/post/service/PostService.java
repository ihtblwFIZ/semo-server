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
    public String createPost(String userName, PostCreateRequestDto dto) {
        return postManagementService.createPost(userName, dto);
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
    public void modifyPost(String userName, PostModifyRequestDto dto) {
        postManagementService.modifyPost(userName, dto);
    }

    @Transactional
    public void deletePost(String userName, Long postId) {
        postManagementService.deletePost(userName, postId);
    }
}
