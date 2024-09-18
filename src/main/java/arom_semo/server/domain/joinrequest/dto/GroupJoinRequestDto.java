package arom_semo.server.domain.joinrequest.dto;

public record GroupJoinRequestDto(
        Long groupJoinRequestDto,
        String groupName,
        String memberName,
        String status
) {
}
