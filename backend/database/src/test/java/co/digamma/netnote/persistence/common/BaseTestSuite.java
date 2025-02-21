package co.digamma.netnote.persistence.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class BaseTestSuite {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:16-alpine");
}
