package com.github.aymanalsagher.filematcher;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Slf4j
@Service
public class FileComparisonService {

  private final String fileAPath;

  private final String filesDirectoryPath;

  public FileComparisonService(
      @Value("${fileA.path}") String fileAPath,
      @Value("${files.directory.path}") String filesDirectoryPath) {
    this.fileAPath = fileAPath;
    this.filesDirectoryPath = filesDirectoryPath;
  }

  /**
   * compare files pool to file A configured in configuration with
   * best matching score
   *
   * @return {@link Optional} of file name of best matching score
   * @throws IOException in case of error reading files
   */
  public Optional<String> compareFiles() throws IOException {
    File fileA = ResourceUtils.getFile(fileAPath);
    File[] poolFiles =  Objects.requireNonNull(ResourceUtils.getFile(filesDirectoryPath).listFiles());

    Set<String> wordsInA = getWordsFromFile(fileA);

    Map<String, Double> filesScore = new HashMap<>();

    for (File poolFile : poolFiles) {
      Set<String> wordsInPoolFile = getWordsFromFile(poolFile);
      double similarityScore = calculateSimilarityScore(wordsInA, wordsInPoolFile);
      filesScore.put(poolFile.getName(), similarityScore);
      log.info(
          "File: [{}], Similarity Score: [{}]",
          poolFile.getName(),
          String.format("%.2f%%", similarityScore * 100));
    }

    return findBestMatch(filesScore);
  }

  private Set<String> getWordsFromFile(File file) throws IOException {
    String content = FileUtils.readFileToString(file, "UTF-8");
    String[] words = content.split("\\W+");
    Set<String> wordSet = new HashSet<>();
    for (String word : words) {
      if (word.matches("[a-zA-Z]+")) {
        wordSet.add(word.toLowerCase());
      }
    }
    return wordSet;
  }

  private double calculateSimilarityScore(Set<String> wordsInA, Set<String> wordsInPoolFile) {
    long commonWordsCount = wordsInPoolFile.stream().filter(wordsInA::contains).count();
    return (double) commonWordsCount / wordsInA.size();
  }

  private Optional<String> findBestMatch(Map<String, Double> scores) {
    return scores.entrySet().stream()
        .filter(stringDoubleEntry -> stringDoubleEntry.getValue() > 0)
        .max(Comparator.comparingDouble(Map.Entry::getValue))
        .map(Map.Entry::getKey);
  }
}
