package ca.applin.tls;

import software.amazon.awssdk.http.*;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static URI uri = URI.create("https://api.nobelprize.org/2.1/nobelPrize/phy/2021");

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("STARTED!");
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
            AbortableInputStream stream = response.responseBody().get();
            String json = new String(readAllBytes(stream));
            System.out.println(json);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }

    }

    // from JDK 9
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;
    public static byte[] readAllBytes(InputStream is) throws IOException {

        List<byte[]> bufs = null;
        byte[] result = null;
        int total = 0;
        int remaining = Integer.MAX_VALUE;
        int n;
        do {
            byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
            int nread = 0;

            // read to EOF which may read more or less than buffer size
            while ((n = is.read(buf, nread,
                    Math.min(buf.length - nread, remaining))) > 0) {
                nread += n;
                remaining -= n;
            }

            if (nread > 0) {
                if (MAX_BUFFER_SIZE - total < nread) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                total += nread;
                if (result == null) {
                    result = buf;
                } else {
                    if (bufs == null) {
                        bufs = new ArrayList<>();
                        bufs.add(result);
                    }
                    bufs.add(buf);
                }
            }
            // if the last call to read returned -1 or the number of bytes
            // requested have been read then break
        } while (n >= 0 && remaining > 0);

        if (bufs == null) {
            if (result == null) {
                return new byte[0];
            }
            return result.length == total ?
                    result : Arrays.copyOf(result, total);
        }

        result = new byte[total];
        int offset = 0;
        remaining = total;
        for (byte[] b : bufs) {
            int count = Math.min(b.length, remaining);
            System.arraycopy(b, 0, result, offset, count);
            offset += count;
            remaining -= count;
        }

        return result;
    }

}
