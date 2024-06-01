package com.github.aymanalsagher.filematcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileComparisonServiceTest {

  @Test
  public void whenCompareFilesThenScore100() throws IOException {
    // Given
    FileComparisonService fileComparisonService =
        new FileComparisonService("classpath:normal/fileA.txt", "classpath:normal/pool");

    // When
    Optional<String> bestMatchFilePath = fileComparisonService.compareFiles();

    // Then
    Assertions.assertTrue(bestMatchFilePath.isPresent());
    Assertions.assertEquals("file1.txt", bestMatchFilePath.get());
  }

  @Test
  public void whenCompareFilesThenNoneMatch() throws IOException {
    // Given
    FileComparisonService fileComparisonService =
        new FileComparisonService("classpath:nomatch/fileA.txt", "classpath:nomatch/pool");

    // When
    Optional<String> bestMatchFilePath = fileComparisonService.compareFiles();

    // Then
    Assertions.assertTrue(bestMatchFilePath.isEmpty());
  }

  @Test
  public void whenCompareFilesThenIllegalArgumentException() {
    // Given
    FileComparisonService fileComparisonService =
        new FileComparisonService("classpath:nomatch/fileA.txt", "classpath:empty");

    // When
    // Then
    Assertions.assertThrows(FileNotFoundException.class, fileComparisonService::compareFiles);
  }
}
