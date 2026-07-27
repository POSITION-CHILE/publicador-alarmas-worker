package com.position.publicador_alarmas;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class InteractionContextTest {

    @Test
    void root_incluyeComponenteYInteractionId() {
        final InteractionContext context = InteractionContext.root("worker")
                .withWorkerId("42")
                .withOperation("poll")
                .withPhase("http")
                .withTable("publicador_alarmas")
                .withTarget("api")
                .withTag("scope", "batch")
                .withTag("ignored", " ");

        final Map<String, String> fields = context.fields();

        assertThat(fields).containsEntry("component", "worker");
        assertThat(fields).containsEntry("worker_id", "42");
        assertThat(fields).containsEntry("operation", "poll");
        assertThat(fields).containsEntry("phase", "http");
        assertThat(fields).containsEntry("table", "publicador_alarmas");
        assertThat(fields).containsEntry("target", "api");
        assertThat(fields).containsEntry("scope", "batch");
        assertThat(fields).doesNotContainKey("ignored");
        assertThat(fields).containsKey("interaction_id");
    }

    @Test
    void withTag_noSobrescribeCamposSinValor() {
        final InteractionContext context = InteractionContext.root("component")
                .withTag("scope", "poll")
                .withTag("scope", null);

        assertThat(context.fields()).containsEntry("scope", "poll");
    }
}
