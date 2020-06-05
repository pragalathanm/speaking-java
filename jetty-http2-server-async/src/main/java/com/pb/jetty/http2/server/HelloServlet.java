package com.pb.jetty.http2.server;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Pragalathan M
 */
@Slf4j
public class HelloServlet extends HttpServlet {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final AsyncContext asyncContext = request.startAsync();
        executor.schedule(() -> {
            try {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("text/html");
                response.setCharacterEncoding("utf-8");
                response.getWriter().println("<h1>Hello from HelloServlet</h1>");
            } catch (Exception ex) {
                log.error(null, ex);
            }
            asyncContext.complete();
        }, 5, TimeUnit.SECONDS);
    }
}
