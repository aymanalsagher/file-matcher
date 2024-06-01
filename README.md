# File matcher simple spring boot app

## Prerequisites

- JDK 21
- Maven 3.9.7

## Run tests

```sh
mvn clean install
```

## Start spring app using maven

```sh
mvn clean spring-boot:run
```

## Benchmark

To enable benchmark performance tests, remove `@Disabled` annotation in `src/test/java/com/github/aymanalsagher/filematcher/TestBenchmark.java`.