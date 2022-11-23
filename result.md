
## OPEN JDK 11 & 19
Default supported protocols: `[TLSv1.3, TLSv1.2]`

| setup                                                                         | result                                                       |
|-------------------------------------------------------------------------------|--------------------------------------------------------------|
| `nothing`                                                                     | Negotiated protocol version: TLSv1.3                         |
| `jdk.tls.disabledAlgorithms="TLSv1.3"`                                        | Negotiated protocol version: TLSv1.3                         |
| `jdk.tls.client.protocols="TLSv1.3"` + `jdk.tls.disabledAlgorithms="TLSv1.3"` | Negotiated protocol version: TLSv1.3                         |
| `jdk.tls.client.protocols="TLSv1.1"`                                          | javax.net.ssl.SSLHandshakeException: No appropriate protocol |
| `jdk.tls.client.protocols="TLSv1.2"`                                          | Negotiated protocol version: TLSv1.2                         |
| `jdk.tls.client.protocols="TLSv1.2"` + `jdk.tls.disabledAlgorithms="TLSv1.2"` | Negotiated protocol version: TLSv1.2                         |


## Coretto JDK 8
Default supported protocols: `[TLSv1.2, TLSv1.1, TLSv1]`

| setup                                  | result                                                                      |
|----------------------------------------|-----------------------------------------------------------------------------|
| `nothing`                              | Negotiated protocol version: TLSv1.2                                        |
| `jdk.tls.client.protocols="TLSv1.1"`   | javax.net.ssl.SSLHandshakeException: Received fatal alert: protocol_version |
| `jdk.tls.client.protocols="TLSv1.3"`   | SSL error 1010                                                              |
| `jdk.tls.disabledAlgorithms="TLSv1.2"` | Negotiated protocol version: TLSv1.2                                        |
| `jdk.tls.client.protocols="TLSv1.1"`   | javax.net.ssl.SSLHandshakeException: No appropriate protocol                |



## Coretto JDK 11



## Coretto JDK 17
