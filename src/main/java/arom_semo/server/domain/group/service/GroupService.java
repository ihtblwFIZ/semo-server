package arom_semo.server.domain.group.service;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.post.dto.PostCreateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GroupService {
    private final GroupManagementService groupManagementService;

    public Group createGroup(PostCreateRequestDto.GroupRequestDto groupRequestDto,
                             PostCreateRequestDto.GroupRequestDto.GroupLocationRequestDto groupLocationRequestDto) {
        return groupManagementService.createGroup(groupRequestDto, groupLocationRequestDto);
    }
}
