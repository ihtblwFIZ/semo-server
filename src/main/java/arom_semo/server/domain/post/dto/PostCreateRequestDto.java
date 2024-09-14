package arom_semo.server.domain.post.dto;

public record PostCreateRequestDto (
        String title,
        String content,
        GroupRequestDto group) {
    public record GroupRequestDto(
            String name,
            int maxParticipants,
            int currentParticipants,
            int maxAge,
            int minAge,
            GroupLocationRequestDto groupLocation
    ) {
        public record GroupLocationRequestDto(
                String county,
                String city,
                String streetName,
                String buildingNumber,
                String detailedInfo
        ) {
        }
    }
}