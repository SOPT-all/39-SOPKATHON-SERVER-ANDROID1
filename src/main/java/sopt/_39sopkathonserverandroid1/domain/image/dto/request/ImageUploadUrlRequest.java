package sopt._39sopkathonserverandroid1.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ImageUploadUrlRequest(
        @NotBlank(message = "이미지 Content-Type은 필수입니다.")
        @Pattern(
                regexp = "image/(jpeg|png|webp)",
                message = "JPEG, PNG, WebP 이미지만 업로드할 수 있습니다."
        )
        @Schema(description = "업로드할 이미지의 Content-Type", example = "image/jpeg")
        String contentType
) {
}
