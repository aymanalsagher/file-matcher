package com.github.aymanalsagher.filematcher;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@SpringBootApplication
@Configuration
public class FileMatcherApplication {

  private final FileComparisonService service;

  public static void main(String[] args) {
    SpringApplication.run(FileMatcherApplication.class, args);
  }

  @Bean
  public ApplicationRunner runAfterStart() {
    return args -> service.compareFiles();
  }
}
