Dummy project for testing behavior of `jdk.tls.disabledAlgorithms` and `jdk.tls.client.protocols` system properties.

## Run default
```bash
mvn clean package
java -Djavax.net.debug=all \
    -jar target/tls-algo-test-1.0-SNAPSHOT-jar-with-dependencies.jar \

```

## Test use TLS1.1
```bash
java -Djavax.net.debug=all \
    -Djdk.tls.client.protocols="TLSv1.1" \
    -jar target/tls-algo-test-1.0-SNAPSHOT-jar-with-dependencies.jar \
    > out.txt 2>&1
```

## Test use TLS1.2
```bash
java -Djavax.net.debug=all \
    -Djdk.tls.client.protocols="TLSv1.2" \
    -jar target/tls-algo-test-1.0-SNAPSHOT-jar-with-dependencies.jar \
    > out.txt 2>&1
```

## Test use TLS1.2 and disable it
```bash
java -Djavax.net.debug=all \
    -Djdk.tls.client.protocols="TLSv1.2" \
    -Djdk.tls.disabledAlgorithms="TLSv1.2" \
    -jar target/tls-algo-test-1.0-SNAPSHOT-jar-with-dependencies.jar \
    > out.txt 2>&1
```

## Test use TLS1.3 and disable it
```bash
java -Djavax.net.debug=all \
    -Djdk.tls.client.protocols="TLSv1.3" \
    -Djdk.tls.disabledAlgorithms="TLSv1.3" \
    -jar target/tls-algo-test-1.0-SNAPSHOT-jar-with-dependencies.jar \
    > out.txt 2>&1
```
