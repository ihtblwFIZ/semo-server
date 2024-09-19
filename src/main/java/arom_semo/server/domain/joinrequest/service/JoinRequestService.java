package arom_semo.server.domain.joinrequest.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.group.repository.GroupRepository;
import arom_semo.server.domain.joinrequest.domain.GroupJoinRequest;
import arom_semo.server.domain.joinrequest.dto.GroupJoinRequestDto;
import arom_semo.server.domain.joinrequest.repository.JoinRequestRepository;
import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinRequestService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final JoinRequestRepository joinRequestRepository;

    @Transactional
    public void applyToJoinGroup(Long memberId, Long groupId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 모임"));

        if (joinRequestRepository.existsByGroupAndMemberAndStatus(group, member, GroupJoinRequest.Status.pending)
            || joinRequestRepository.existsByGroupAndMemberAndStatus(group, member, GroupJoinRequest.Status.approved)) {
            throw new IllegalStateException("이미 지원됨");
        }

        GroupJoinRequest groupJoinRequest = GroupJoinRequest.builder()
                .status(GroupJoinRequest.Status.pending)
                .member(member)
                .group(group)
                .build();

        joinRequestRepository.save(groupJoinRequest);
    }

    @Transactional
    public void approveJoinRequest(Long joinRequestId) {
        GroupJoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 가입 요청"));

        if (joinRequest.getStatus() != GroupJoinRequest.Status.pending) {
            throw new IllegalStateException("이미 처리된 가입 요청");
        }

        joinRequest.updateStatus(GroupJoinRequest.Status.approved);
        joinRequestRepository.save(joinRequest);

        Group group = joinRequest.getGroup();
        group.increaseCurrentParticipants();
    }

    @Transactional
    public void rejectJoinRequest(Long joinRequestId) {
        GroupJoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 가입 요청"));

        if (joinRequest.getStatus() != GroupJoinRequest.Status.pending) {
            throw  new IllegalStateException("이미 처리된 가입 요청");
        }

        joinRequest.updateStatus(GroupJoinRequest.Status.rejected);
        joinRequestRepository.save(joinRequest);
    }

    public List<GroupJoinRequestDto> getPendingJoinRequests(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 모임"));

        List<GroupJoinRequest> pendingRequests =
                joinRequestRepository.findAllByGroupAndStatus(group, GroupJoinRequest.Status.pending);

        return pendingRequests.stream()
                .map(request -> new GroupJoinRequestDto(
                        request.getGroupJoinRequestId(),
                        request.getGroup().getGroupName(),
                        request.getMember().getUsername(),
                        request.getStatus().name()))
                .collect(Collectors.toList());
    }
}