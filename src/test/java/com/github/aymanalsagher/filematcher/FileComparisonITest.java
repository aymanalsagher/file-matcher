package com.github.aymanalsagher.filematcher;

import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class FileComparisonITest {

  @Autowired private FileComparisonService fileComparisonService;

  @Test
  public void whenCompareFilesThenScore100() throws IOException {
    // Given
    // When
    Optional<String> bestMatchFilePath = fileComparisonService.compareFiles();

    // Then
    Assertions.assertTrue(bestMatchFilePath.isPresent());
    Assertions.assertEquals("file1.txt", bestMatchFilePath.get());
  }
}
