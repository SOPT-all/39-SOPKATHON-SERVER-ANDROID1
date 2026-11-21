package sopt._39sopkathonserverandroid1.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MissionCompletionResponse(
        @Schema(description = "Gemini 이미지 검증 결과", example = "PASS")
        VerificationResult verificationResult
) {

    public static MissionCompletionResponse of(boolean passed) {
        return new MissionCompletionResponse(passed ? VerificationResult.PASS : VerificationResult.FAIL);
    }

    public enum VerificationResult {
        PASS,
        FAIL
    }
}
