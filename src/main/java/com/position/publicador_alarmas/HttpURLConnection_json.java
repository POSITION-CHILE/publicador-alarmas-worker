package com.position.publicador_alarmas;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpURLConnection_json {
    private final LogfmtLogger log;
    private InteractionContext interactionContext;

    public HttpURLConnection_json() {
        this(new LogfmtLogger());
    }

    HttpURLConnection_json(final LogfmtLogger log) {
        this.log = log;
        this.interactionContext = InteractionContext.root("http");
    }

    public void setInteractionContext(final InteractionContext context) {
        if (context != null) {
            this.interactionContext = context.withComponent("http");
        }
    }

    private InteractionContext ctx(final String operation) {
        return this.interactionContext.withOperation(operation).withPhase("http");
    }

    private void started(final String operation, final String method) {
        this.log.info("http.request_started", ctx(operation), "method", method);
    }

    private void completed(final String operation, final String method, final int status, final long elapsedMs, final String outcome, final Integer bytes) {
        this.log.info("http.request_completed", ctx(operation), "method", method, "status", status, "elapsed_ms", elapsedMs, "outcome", outcome, "bytes", bytes == null ? 0 : bytes);
    }

    private void failed(final String operation, final String method, final Exception ex, final long elapsedMs) {
        this.log.error("http.request_failed", ctx(operation), "method", method, "elapsed_ms", elapsedMs, "error", ex.getClass().getSimpleName(), "message", ex.getMessage());
    }

    public String detete(final String url, final String parameters) {
        final long startedAt = System.nanoTime();
        started("delete", "DELETE");
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("DELETE");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    completed("delete", "DELETE", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", bytes.length);
                    return "ok";
                }
                case 201 -> {
                    completed("delete", "DELETE", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", bytes.length);
                    return "error";
                }
            }
        } catch (IOException ex) {
            failed("delete", "DELETE", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("delete", "DELETE", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String put(final String url, final String parameters) {
        final long startedAt = System.nanoTime();
        started("put", "PUT");
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("PUT");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    completed("put", "PUT", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", bytes.length);
                    return "ok";
                }
                case 201 -> {
                    completed("put", "PUT", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", bytes.length);
                    return "error";
                }
                case 500 -> {
                    return "error";
                }
            }
        } catch (IOException ex) {
            failed("put", "PUT", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("put", "PUT", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String post_event_esp(final String url, final String parameters) {
        final long startedAt = System.nanoTime();
        started("post_event_esp", "POST");
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final StringBuilder sb;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                    }
                    completed("post_event_esp", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", bytes.length);
                    return sb.toString();
                }
                case 201 -> {
                    completed("post_event_esp", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", bytes.length);
                    return "error";
                }
            }
        } catch (IOException ex) {
            failed("post_event_esp", "POST", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("post_event_esp", "POST", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String post(final String url, final Registros_insert parameters) {
        final long startedAt = System.nanoTime();
        started("post", "POST");
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_insert.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    completed("post", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", null);
                    return "ok";
                }
                case 201 -> {
                    completed("post", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", null);
                    return "error";
                }
                case 500 -> {
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            failed("post", "POST", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("post", "POST", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String post_alm_gps(final String url, final Registros_alm_gps_insert parameters) {
        final long startedAt = System.nanoTime();
        started("post_alm_gps", "POST");
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_alm_gps_insert.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final InputStream errorstream = c.getErrorStream();
                    BufferedReader br;
                    if (errorstream == null) {
                        final InputStream inputstream = c.getInputStream();
                        br = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        br = new BufferedReader(new InputStreamReader(errorstream));
                    }
                    String response = "";
                    String nachricht;
                    while ((nachricht = br.readLine()) != null) {
                        response += nachricht;
                    }
                    completed("post_alm_gps", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", null);
                    return response;
                }
                case 201 -> {
                    completed("post_alm_gps", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", null);
                    return "error";
                }
                case 500 -> {
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            failed("post_alm_gps", "POST", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("post_alm_gps", "POST", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String post_alm(final String url, final Registros_alm_insert parameters) {
        final long startedAt = System.nanoTime();
        started("post_alm", "POST");
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_alm_insert.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    completed("post_alm", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", null);
                    return "ok";
                }
                case 201 -> {
                    completed("post_alm", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", null);
                    return "error";
                }
                case 500 -> {
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            failed("post_alm", "POST", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("post_alm", "POST", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String post_alm_vel(final String url, final Registros_alm_insert_vel parameters) {
        final long startedAt = System.nanoTime();
        started("post_alm_vel", "POST");
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_alm_insert_vel.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    completed("post_alm_vel", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", null);
                    return "ok";
                }
                case 201 -> {
                    completed("post_alm_vel", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", null);
                    return "error";
                }
                case 500 -> {
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            failed("post_alm_vel", "POST", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("post_alm_vel", "POST", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String post_event_esp_alm(final String url, final Registros_eventos_especiales_alm parameters) {
        final long startedAt = System.nanoTime();
        started("post_event_esp_alm", "POST");
        try {
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setChunkedStreamingMode(0);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestMethod("POST");
            c.connect();
            final OutputStream outputStream = new BufferedOutputStream(c.getOutputStream());
            try (JsonWriter writer = new JsonWriter((Writer)new OutputStreamWriter(outputStream, "UTF-8"))) {
                final Gson gson = new Gson();
                gson.toJson((Object)parameters, (Type)Registros_eventos_especiales_alm.class, writer);
                writer.flush();
            }
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final InputStream okstream = c.getErrorStream();
                    BufferedReader okbr;
                    if (okstream == null) {
                        final InputStream inputstream = c.getInputStream();
                        okbr = new BufferedReader(new InputStreamReader(inputstream));
                    }
                    else {
                        okbr = new BufferedReader(new InputStreamReader(okstream));
                    }
                    String okresponse = "";
                    String oknachricht;
                    while ((oknachricht = okbr.readLine()) != null) {
                        okresponse += oknachricht;
                    }
                    completed("post_event_esp_alm", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", null);
                    return okresponse.replaceAll("\"", "");
                }
                case 201 -> {
                    completed("post_event_esp_alm", "POST", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", null);
                    return "error";
                }
                case 500 -> {
                    return "error";
                }
            }
        } catch (JsonIOException | IOException ex) {
            failed("post_event_esp_alm", "POST", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        completed("post_event_esp_alm", "POST", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }

    public String get(final String url, final int timeout) {
        HttpURLConnection c = null;
        final long startedAt = System.nanoTime();
        started("get", "GET");
        try {
            final URL u = new URL(url);
            c = (HttpURLConnection)u.openConnection();
            c.setRequestProperty("Content-Type", "application/json;odata=verbose");
            c.setRequestProperty("Accept", "application/json;odata=verbose");
            c.setRequestMethod("GET");
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            final int status = c.getResponseCode();
            switch (status) {
                case 200 -> {
                    final StringBuilder sb;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                    }
                    completed("get", "GET", status, (System.nanoTime() - startedAt) / 1_000_000L, "ok", sb.length());
                    return sb.toString();
                }
                case 201 -> {
                    completed("get", "GET", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", 0);
                    return null;
                }
                case 500 -> {
                    completed("get", "GET", status, (System.nanoTime() - startedAt) / 1_000_000L, "error", 0);
                    return "error";
                }
                default -> {
                }
            }
        } catch (IOException ex) {
            failed("get", "GET", ex, (System.nanoTime() - startedAt) / 1_000_000L);
            return "error";
        }
        finally {
            if (c != null) {
                try {
                    c.disconnect();
                }
                catch (Exception ex3) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex3);
                }
            }
        }
        completed("get", "GET", -1, (System.nanoTime() - startedAt) / 1_000_000L, "unknown", null);
        return null;
    }
}
