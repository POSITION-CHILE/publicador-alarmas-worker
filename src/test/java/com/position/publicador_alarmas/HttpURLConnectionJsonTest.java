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

    @Test
    void delete_emiteLogsEstructurados() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final LogfmtLogger logger = new LogfmtLogger(new PrintStream(out, true, StandardCharsets.UTF_8));

        final HttpURLConnection_json client = new HttpURLConnection_json(logger);
        client.setInteractionContext(InteractionContext.root("test").withWorkerId("13"));

        final String response = client.detete("http://mock.local/delete", "k=v");

        assertThat(response).isEqualTo("ok");
        final String logs = out.toString(StandardCharsets.UTF_8);
        assertThat(logs).contains("event=http.request_started");
        assertThat(logs).contains("event=http.request_completed");
        assertThat(logs).contains("method=DELETE");
        assertThat(logs).contains("status=200");
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
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("delete")) {
                return 200;
            }
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("moviles")) {
                return 200;
            }
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("nombre")) {
                return 200;
            }
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("post")) {
                return 200;
            }
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("ok")) {
                return 200;
            }
            return 500;
        }

        private String responseBody() {
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("moviles")) {
                return "{\"response\":[{\"user1\":\"u1\",\"l2\":1001,\"plate\":\"ABC123\",\"r12\":1,\"tipo\":2,\"modem\":3,\"numero\":\"987654321\",\"rendimiento\":1.1,\"factor_correcion\":1.2,\"costo_combustible\":1.3,\"tipo_combustible\":1.4,\"marca\":\"marca\",\"modelo\":\"modelo\",\"anno\":2020,\"km_inicial\":100,\"id_equipo\":10,\"estado\":1,\"tele\":1,\"color_stop\":2,\"color_mov\":3,\"tipo_ib\":4,\"l2f\":5,\"disponible\":6,\"volt_min\":7.1,\"volt_max\":8.2,\"observacion\":\"obs\",\"capacidad\":9.3,\"altura\":10.4,\"ancho\":11.5,\"largo\":12.6,\"color_ico\":\"blue\",\"corte_motor\":7}]}";
            }
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("nombre")) {
                return "<xml><response><item><id_poligono>77</id_poligono><nombre>poligono-1</nombre><user1>u1</user1><vel>50.5</vel><res_1>r1</res_1><res_2>r2</res_2><res_3>r3</res_3><res_4>r4</res_4><res_5>r5</res_5><id_capa>8</id_capa><planta>9</planta></item></response></xml>";
            }
            if (this.url.getPath().toLowerCase(Locale.ROOT).contains("post")) {
                return "ok";
            }
            return "{\"status\":\"ok\"}";
        }
    }
}
