package arom_semo.server.domain.post.controller;

import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import arom_semo.server.domain.post.dto.PostModifyRequestDto;
import arom_semo.server.domain.post.dto.PostResponseDto;
import arom_semo.server.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("posts")
    public ResponseEntity<String> createPost(@RequestParam Long userId,
                                             @RequestBody PostCreateRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(userId, dto));
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<PostResponseDto> fetchPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostResponseDto>> fetchAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PatchMapping("posts/{postId}")
    public ResponseEntity<Void> modifyPost(@RequestParam Long userId,
                                           @RequestBody PostModifyRequestDto dto) {
        postService.modifyPost(userId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<Void> deletePost(@RequestParam Long userId,
                                           @PathVariable Long postId) {
        postService.deletePost(userId, postId);
        return ResponseEntity.ok().build();
    }
}
