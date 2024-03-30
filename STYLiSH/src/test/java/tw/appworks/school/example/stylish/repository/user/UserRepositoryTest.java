package tw.appworks.school.example.stylish.repository.user;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import tw.appworks.school.example.stylish.model.user.Role;
import tw.appworks.school.example.stylish.model.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class UserRepositoryTest {

    @Container
    static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
    @Container
    static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");

        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User fakeUser;

    @BeforeEach
    @Transactional
    public void before() {
        Role fakeRole = new Role();
        fakeRole.setId(1);
        fakeRole.setName("fakeRoleName");

        fakeUser = new User();
        fakeUser.setId(100000L);
        fakeUser.setName("fake name");
        fakeUser.setEmail("fake@email");
        fakeUser.setPassword("fakePassword");
        fakeUser.setProvider("fakeProvider");
        fakeUser.setAccessToken("fakeToken");
        fakeUser.setPicture("fakePicture");
        fakeUser.setAccessExpired(123456L);
        fakeUser.setRole(fakeRole);

        roleRepository.save(fakeRole);
    }

    @AfterEach
    @Transactional
    public void after() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        MY_SQL_CONTAINER.stop();
        REDIS_CONTAINER.stop();
    }

    @Test
    public void when_findUserByEmail_then_return_user() {
        userRepository.save(fakeUser);
        User user = userRepository.findUserByEmail("fake@email");
        assertEquals("fake name", user.getName());
    }
}
