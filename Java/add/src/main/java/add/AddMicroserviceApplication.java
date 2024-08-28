package add;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AddMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AddMicroserviceApplication.class, args);
    }
}
