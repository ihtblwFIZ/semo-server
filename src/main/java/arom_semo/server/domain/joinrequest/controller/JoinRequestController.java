package arom_semo.server.domain.joinrequest.controller;

import arom_semo.server.domain.joinrequest.service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    @PostMapping("groups/{groupId}/join")
    public ResponseEntity<String> applyToJoinGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        try {
            joinRequestService.applyToJoinGroup(groupId, userId);
            return ResponseEntity.ok("지원 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
