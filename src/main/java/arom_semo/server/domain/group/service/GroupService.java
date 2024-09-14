package arom_semo.server.domain.group.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.group.domain.GroupLocation;
import arom_semo.server.domain.group.repository.GroupLocationRepository;
import arom_semo.server.domain.group.repository.GroupRepository;
import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupLocationRepository groupLocationRepository;

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
                .name(groupRequestDto.name())
                .maxParticipants(groupRequestDto.maxParticipants())
                .currentParticipants(groupRequestDto.currentParticipants())
                .maxAge(groupRequestDto.maxAge())
                .minAge(groupRequestDto.minAge())
                .groupLocation(groupLocation)
                .build();

        return groupRepository.save(group);
    }
}
