package arom_semo.server.domain.group.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.group.domain.GroupLocation;
import arom_semo.server.domain.group.dto.GroupResponseDto;
import arom_semo.server.domain.group.repository.GroupLocationRepository;
import arom_semo.server.domain.group.repository.GroupRepository;
import arom_semo.server.domain.joinrequest.domain.GroupJoinRequest;
import arom_semo.server.domain.joinrequest.repository.JoinRequestRepository;
import arom_semo.server.domain.member.domain.Member;
import arom_semo.server.domain.member.repository.MemberRepository;
import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupManagementService {
    private final GroupRepository groupRepository;
    private final GroupLocationRepository groupLocationRepository;
    private final MemberRepository memberRepository;
    private final JoinRequestRepository joinRequestRepository;

    public Group createGroup(PostCreateRequestDto.GroupRequestDto groupRequestDto,
                             PostCreateRequestDto.GroupRequestDto.GroupLocationRequestDto groupLocationRequestDto) {
        GroupLocation groupLocation = GroupLocation.builder()
                .county(groupLocationRequestDto.county())
                .city(groupLocationRequestDto.city())
                .streetName(groupLocationRequestDto.streetName())
                .buildingNumber(groupLocationRequestDto.buildingNumber())
                .detailedInfo(groupLocationRequestDto.detailedInfo())
                .build();

        groupLocationRepository.save(groupLocation);

        Group group = Group.builder()
                .groupName(groupRequestDto.name())
                .maxParticipants(groupRequestDto.maxParticipants())
                .currentParticipants(groupRequestDto.currentParticipants())
                .maxAge(groupRequestDto.maxAge())
                .minAge(groupRequestDto.minAge())
                .groupLocation(groupLocation)
                .build();

        return groupRepository.save(group);
    }

    public List<GroupResponseDto> fetchMyGroups(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자"));

        List<GroupJoinRequest> joinRequests = joinRequestRepository.findAllByMemberAndStatus(member, GroupJoinRequest.Status.approved);

        return joinRequests.stream()
                .map(GroupJoinRequest::getGroup)
                .map(group -> new GroupResponseDto(
                        group.getGroupName(),
                        group.getMaxParticipants(),
                        group.getCurrentParticipants(),
                        group.getMaxAge(),
                        group.getMinAge(),
                        new GroupResponseDto.GroupLocationResponseDto(
                                group.getGroupLocation().getCounty(),
                                group.getGroupLocation().getCity(),
                                group.getGroupLocation().getStreetName(),
                                group.getGroupLocation().getBuildingNumber(),
                                group.getGroupLocation().getDetailedInfo()
                        )
                ))
                .collect(Collectors.toList());
    }
}
