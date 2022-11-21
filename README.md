Dummy project for testing behavior of `jdk.tls.disabledAlgorithms` and `jdk.tls.client.protocols` system properties.

## Run default
```bash
mvn clean package
java -Djavax.net.debug=ssl \
    -jar target/tls-algo-test-1.0-SNAPSHOT-jar-with-dependencies.jar \

```

## Test disable TLS 1.3
```bash
mvn clean package
java -Djavax.net.debug=ssl \
    -Djdk.tls.disabledAlgorithms="TLSv1.2,TLSv1.3" \
    -jar target/tls-algo-test-1.0-SNAPSHOT-jar-with-dependencies.jar \
```
