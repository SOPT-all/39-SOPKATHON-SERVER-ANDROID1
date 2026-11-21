package sopt._39sopkathonserverandroid1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "aws.s3.bucket=test-bucket",
        "aws.s3.region=ap-southeast-2"
})
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
