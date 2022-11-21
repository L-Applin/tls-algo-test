package ca.applin.tls;

import software.amazon.awssdk.http.*;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {
    static URI uri = URI.create("https://api.nobelprize.org/2.1/nobelPrize/phy/2021");

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String disabledAlgorithms = System.getProperty("jdk.tls.disabledAlgorithms");
        String protocols = System.getProperty("jdk.tls.client.protocols");
        System.out.printf("Disabled algo: %s%n", disabledAlgorithms);
        System.out.printf("protocols: %s%n", protocols);
        String[] supportedProtocols = SSLContext.getDefault().getDefaultSSLParameters().getProtocols();
        System.out.printf("Supported protocols: %s%n",
                Arrays.toString(supportedProtocols));
        try (SdkHttpClient client = UrlConnectionHttpClient.create()) {
            HttpExecuteResponse response = client.prepareRequest(
                            HttpExecuteRequest.builder()
                                    .request(SdkHttpRequest.builder()
                                            .method(SdkHttpMethod.GET)
                                            .uri(uri)
                                            .build())
                                    .build())
                    .call();
            AbortableInputStream stream = response.responseBody().orElseThrow();
            String json = new String(stream.readAllBytes());
            System.out.println(json);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }

    }
}
