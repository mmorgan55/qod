package edu.cnm.deepdive.qod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableEntityLinks;

@EnableEntityLinks
@SpringBootApplication
public class QodApplication {

  public static void main(String[] args) {
    SpringApplication.run(QodApplication.class, args);
  }
}
