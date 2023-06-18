package kr.inhatc.pip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing                  // JPA Auditing 활성화
public class PipApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipApplication.class, args);
    }

}
