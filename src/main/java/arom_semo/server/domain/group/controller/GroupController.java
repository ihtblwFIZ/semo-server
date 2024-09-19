package arom_semo.server.domain.group.controller;

import arom_semo.server.domain.group.dto.GroupResponseDto;
import arom_semo.server.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/my-groups")
    public ResponseEntity<List<GroupResponseDto>> fetchMyGroups(@RequestParam(value = "memberId") Long memberId) {
        return ResponseEntity.ok(groupService.fetchMyGroups(memberId));
    }
}
