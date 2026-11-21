package sopt._39sopkathonserverandroid1.domain.mission.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAiUnavailableException;
import tools.jackson.databind.json.JsonMapper;

class GeminiMissionImageVerifierTest {

    private GeminiMissionImageVerifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new GeminiMissionImageVerifier(
                JsonMapper.builder().build(),
                "test-api-key",
                "gemini-test-model",
                Duration.ofSeconds(1)
        );
    }

    @Test
    void parsesPassResult() {
        String response = """
                {"candidates":[{"content":{"parts":[{"text":"PASS"}]}}]}
                """;

        assertTrue(verifier.parseVerificationResult(response));
    }

    @Test
    void parsesFailResult() {
        String response = """
                {"candidates":[{"content":{"parts":[{"text":"FAIL"}]}}]}
                """;

        assertFalse(verifier.parseVerificationResult(response));
    }

    @Test
    void rejectsUnexpectedResult() {
        String response = """
                {"candidates":[{"content":{"parts":[{"text":"UNKNOWN"}]}}]}
                """;

        assertThrows(MissionAiUnavailableException.class,
                () -> verifier.parseVerificationResult(response));
    }
}
