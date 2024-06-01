package com.github.aymanalsagher.filematcher;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.*;

public class TestBenchmark {

  @Test
  @Disabled
  public void launchBenchmark() throws Exception {

    Options opt =
        new OptionsBuilder()
            .include(this.getClass().getName() + ".*")
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.MICROSECONDS)
            .warmupTime(TimeValue.seconds(1))
            .warmupIterations(2)
            .measurementTime(TimeValue.seconds(1))
            .measurementIterations(2)
            .threads(2)
            .forks(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .build();

    new Runner(opt).run();
  }

  // http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
  @State(Scope.Thread)
  public static class BenchmarkState {

    @Setup(Level.Trial)
    public void initialize() {}
  }

  @Benchmark
  public void benchmark1(BenchmarkState state, Blackhole bh) throws IOException {

    // Given
    FileComparisonService fileComparisonService =
        new FileComparisonService("classpath:normal/fileA.txt", "classpath:normal/pool");

    // When
    Optional<String> bestMatchFilePath = fileComparisonService.compareFiles();

    // Then
    Assertions.assertTrue(bestMatchFilePath.isPresent());
    Assertions.assertEquals("file1.txt", bestMatchFilePath.get());
  }
}
