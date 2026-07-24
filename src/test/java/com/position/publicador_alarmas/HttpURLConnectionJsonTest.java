package com.position.publicador_alarmas;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HttpURLConnectionJsonTest {
    private static final AtomicBoolean FACTORY_INSTALLED = new AtomicBoolean(false);

    @BeforeAll
    static void installFactory() {
        if (FACTORY_INSTALLED.compareAndSet(false, true)) {
            try {
                URL.setURLStreamHandlerFactory(new MockFactory());
            }
            catch (Error ignored) {
                // Another test already installed it. Fine.
            }
        }
    }

    @Test
    void get_emiteLogsEstructurados() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final LogfmtLogger logger = new LogfmtLogger(new PrintStream(out, true, StandardCharsets.UTF_8));

        final HttpURLConnection_json client = new HttpURLConnection_json(logger);
        client.setInteractionContext(InteractionContext.root("test").withWorkerId("9"));

        final String response = client.get("http://mock.local/ok", 2000);

        assertThat(response).contains("\"status\":\"ok\"");
        final String logs = out.toString(StandardCharsets.UTF_8);
        assertThat(logs).contains("event=http.request_started");
        assertThat(logs).contains("event=http.request_completed");
        assertThat(logs).contains("method=GET");
        assertThat(logs).contains("status=200");
        assertThat(logs).contains("component=http");
    }

    @Test
    void post_event_esp_emiteLogsEstructurados() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final LogfmtLogger logger = new LogfmtLogger(new PrintStream(out, true, StandardCharsets.UTF_8));

        final HttpURLConnection_json client = new HttpURLConnection_json(logger);
        client.setInteractionContext(InteractionContext.root("test").withWorkerId("11"));

        final String response = client.post_event_esp("http://mock.local/post", "k=v");

        assertThat(response).contains("ok");
        final String logs = out.toString(StandardCharsets.UTF_8);
        assertThat(logs).contains("event=http.request_started");
        assertThat(logs).contains("event=http.request_completed");
        assertThat(logs).contains("method=POST");
        assertThat(logs).contains("component=http");
    }

    private static final class MockFactory implements URLStreamHandlerFactory {
        @Override
        public URLStreamHandler createURLStreamHandler(final String protocol) {
            if ("http".equals(protocol) || "https".equals(protocol)) {
                return new MockHandler();
            }
            return null;
        }
    }

    private static final class MockHandler extends URLStreamHandler {
        @Override
        protected URLConnection openConnection(final URL url) {
            return new MockConnection(url);
        }
    }

    private static final class MockConnection extends HttpURLConnection {
        private final ByteArrayOutputStream requestBody = new ByteArrayOutputStream();

        protected MockConnection(final URL url) {
            super(url);
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() {
        }

        @Override
        public OutputStream getOutputStream() {
            return this.requestBody;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(responseBody().getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public InputStream getErrorStream() {
            if (getResponseCode() >= 400) {
                return new ByteArrayInputStream(responseBody().getBytes(StandardCharsets.UTF_8));
            }
            return null;
        }

        @Override
        public int getResponseCode() {
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("post")) {
                return 200;
            }
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("ok")) {
                return 200;
            }
            return 500;
        }

        private String responseBody() {
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("post")) {
                return "ok";
            }
            return "{\"status\":\"ok\"}";
        }
    }
}
