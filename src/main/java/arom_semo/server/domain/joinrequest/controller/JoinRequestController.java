package arom_semo.server.domain.joinrequest.controller;

import arom_semo.server.domain.joinrequest.service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    @PostMapping("/{groupId}/join")
    public ResponseEntity<String> applyToJoinGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        joinRequestService.applyToJoinGroup(groupId, userId);
        return ResponseEntity.ok("지원 완료");
    }

    @PostMapping("/join-requests/{joinRequestId}/approve")
    public ResponseEntity<String> approveJoinRequest(@PathVariable Long joinRequestId) {
        joinRequestService.approveJoinRequest(joinRequestId);
        return ResponseEntity.ok("승인 완료");
    }

    @PostMapping("/join-requests/{joinRequestId}/reject")
    public ResponseEntity<String> rejectJoinRequest(@PathVariable Long joinRequestId) {
        joinRequestService.rejectJoinRequest(joinRequestId);
        return ResponseEntity.ok("거절 완료");
    }
}
