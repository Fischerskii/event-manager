package ru.trofimov.eventmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.trofimov.eventmanager.enums.Role;

import java.security.SecureRandom;

@AutoConfigureMockMvc
@SpringBootTest
public class AbstractTest {

    private static volatile boolean isSharedSetupDone = false;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserTestUtils userTestUtils;

    protected final SecureRandom secureRandom = new SecureRandom();

    public static PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("postgres");

    static {
        if (!isSharedSetupDone) {
            POSTGRESQL_CONTAINER.start();
            isSharedSetupDone = true;
        }
    }

    @DynamicPropertySource
    static void dataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("test.postgres.port", POSTGRESQL_CONTAINER::getFirstMappedPort);
    }

    @EventListener
    public void stopContainer() {
        POSTGRESQL_CONTAINER.stop();
    }

    public int getRandomInt() {
        return secureRandom.nextInt(1000000);
    }

    public String getAuthorizationHeader(Role role) {
        return "Bearer " + userTestUtils.getJwtTokenWithRole(role);
    }
 }
