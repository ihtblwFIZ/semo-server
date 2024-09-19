package arom_semo.server.domain.group.dto;

public record GroupResponseDto(
        String groupName,
        int maxParticipants,
        int currentParticipants,
        int maxAge,
        int minAge,
        GroupLocationResponseDto groupLocationResponseDto
        ) {
    public record GroupLocationResponseDto(
            String county,
            String city,
            String streetName,
            String buildingNumber,
            String detailedInfo
    ) {
    }
}
