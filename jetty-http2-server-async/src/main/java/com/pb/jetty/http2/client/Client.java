package com.pb.jetty.http2.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;

/**
 *
 * @author Pragalathan M
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.setConnectTimeout(60_000);
        httpClient.setIdleTimeout(60_000);
        httpClient.start();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                long t1 = System.currentTimeMillis();
                Request req = httpClient.newRequest("http://localhost:8080/hello-servlet");
                req.idleTimeout(1, TimeUnit.MINUTES);
                ContentResponse response = req.send();
                long t2 = System.currentTimeMillis();
                log.info("response time:{} ms", t2 - t1);
                return true;
            });
        }
        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);
        httpClient.stop();
    }
}
