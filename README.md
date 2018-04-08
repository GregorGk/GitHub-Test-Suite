## Motivation

Training purposes only.

## Prerequisites

Java 1.8+, Maven 3.3.9+.

## Tests

Simply execute the following:
```java
mvn clean test site
```
Optionally add a browser and username, password and token properties.
If any of: username, password and token properties is used, it implies that the 3 credential properties should be used together.

E.g. to prevent the default Chrome driver and use Firefox instead:
```java
mvn clean test site -Dbrowser=Firefox
```
## License

MIT.