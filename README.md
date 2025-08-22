# Java Collection Tester

This project is a **Java-based collection tester** designed to benchmark the performance of different collection types under various conditions.  
It measures the efficiency of operations such as insertion, lookup, removal, and iteration, while allowing developers to compare how different collections behave with various data types and workloads.  

------------------------------------------------------------------------

## User Configurable Options
The framework provides flexibility through configuration. Users can select:

- **Tested Class** – `Integer`, `Double`, `Person`, `Product`, `Point`, `MyColor`  
- **Collection Type** – various data structures such as `lists`, `sets`, or `maps`  
- **Test Type** – the operation benchmarked (e.g., `insertion`, `lookup`, `removal`, `iteration`)  
- **Output Format** – `CSV` or `CLI`  

------------------------------------------------------------------------

## Technical Overview

- **Custom Exception Handling**  
  A dedicated `IndexingNotSupportedException` is raised when a collection does not support indexing, ensuring precise error reporting.

- **Use of Java Streams**  
  Streams are used to generate and populate collections with test data in a concise and efficient manner.

- **Enum-Driven Configuration**  
  Enums (`collectionTypes`, `dataTypes`, `testTypes`, `resultOutputs`) define supported options, making the framework easy to extend without modifying core logic.

- **Separation of Concerns**  
  - `TestConfig` → manages configuration  
  - `Main` → handles execution  
  - `TestResult` → stores and processes results
