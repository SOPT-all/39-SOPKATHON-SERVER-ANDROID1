package sopt._39sopkathonserverandroid1.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

public record MissionBasketCreateRequest(
        @Schema(description = "담을 미션 ID", example = "3", minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "미션 ID는 1 이상이어야 합니다.")
        @Positive(message = "미션 ID는 1 이상이어야 합니다.")
        @JsonDeserialize(using = MissionBasketCreateRequest.MissionIdDeserializer.class)
        Long missionId
) {

    static class MissionIdDeserializer extends ValueDeserializer<Long> {

        @Override
        public Long deserialize(JsonParser parser, DeserializationContext context) {
            if (parser.currentToken() != JsonToken.VALUE_NUMBER_INT) {
                return context.reportInputMismatch(Long.class, "미션 ID는 정수여야 합니다.");
            }
            return parser.getLongValue();
        }
    }
}
