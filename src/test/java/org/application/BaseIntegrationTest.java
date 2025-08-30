package org.application;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class BaseIntegrationTest {

	@Container
	@ServiceConnection
	protected static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17");
}
