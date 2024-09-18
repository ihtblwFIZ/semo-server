package arom_semo.server.domain.joinrequest.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.group.repository.GroupRepository;
import arom_semo.server.domain.joinrequest.domain.GroupJoinRequest;
import arom_semo.server.domain.joinrequest.repository.JoinRequestRepository;
import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinRequestService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final JoinRequestRepository joinRequestRepository;

    public void applyToJoinGroup(Long memberId, Long groupId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 그룹"));

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

    // 마저 작성 혹은 apply 수정 후 커밋
}
