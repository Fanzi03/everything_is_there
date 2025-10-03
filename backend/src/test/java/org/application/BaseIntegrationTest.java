package org.application;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@DirtiesContext
public abstract class BaseIntegrationTest {

	@Container
	@ServiceConnection
	protected static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17");

}
