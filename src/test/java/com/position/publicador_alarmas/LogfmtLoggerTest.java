package com.position.publicador_alarmas;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class LogfmtLoggerTest {

    @Test
    void formateaCamposYEscapaTexto() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final LogfmtLogger logger = new LogfmtLogger(new PrintStream(out, true, StandardCharsets.UTF_8));

        logger.info(
                "test.event",
                InteractionContext.root("component").withWorkerId("7").withTag("scope", "poll"),
                "message", "hola mundo",
                "quote", "a\"b",
                "newline", "linea1\nlinea2");

        final String line = out.toString(StandardCharsets.UTF_8);

        assertThat(line).contains("level=info");
        assertThat(line).contains("event=test.event");
        assertThat(line).contains("component=component");
        assertThat(line).contains("worker_id=7");
        assertThat(line).contains("scope=poll");
        assertThat(line).contains("message=\"hola mundo\"");
        assertThat(line).contains("quote=\"a\\\"b\"");
        assertThat(line).contains("newline=\"linea1\\nlinea2\"");
    }
}
