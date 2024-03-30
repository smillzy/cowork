package tw.appworks.school.example.stylish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import tw.appworks.school.example.stylish.storage.StorageProperties;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(StorageProperties.class)
public class StylishApplication {

    public static void main(String[] args) {
        SpringApplication.run(StylishApplication.class, args);
    }

}