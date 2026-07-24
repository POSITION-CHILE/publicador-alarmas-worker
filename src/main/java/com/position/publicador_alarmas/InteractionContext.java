package com.position.publicador_alarmas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class InteractionContext {
    private final String component;
    private final String workerId;
    private final String interactionId;
    private final String operation;
    private final String phase;
    private final String table;
    private final String target;
    private final LinkedHashMap<String, String> tags;

    private InteractionContext(
            final String component,
            final String workerId,
            final String interactionId,
            final String operation,
            final String phase,
            final String table,
            final String target,
            final LinkedHashMap<String, String> tags) {
        this.component = component;
        this.workerId = workerId;
        this.interactionId = interactionId;
        this.operation = operation;
        this.phase = phase;
        this.table = table;
        this.target = target;
        this.tags = tags;
    }

    public static InteractionContext root(final String component) {
        return new InteractionContext(component, null, UUID.randomUUID().toString(), null, null, null, null, new LinkedHashMap<String, String>());
    }

    public InteractionContext withComponent(final String value) {
        return copy(value, this.workerId, this.interactionId, this.operation, this.phase, this.table, this.target);
    }

    public InteractionContext withWorkerId(final String value) {
        return copy(this.component, value, this.interactionId, this.operation, this.phase, this.table, this.target);
    }

    public InteractionContext withInteractionId(final String value) {
        return copy(this.component, this.workerId, value, this.operation, this.phase, this.table, this.target);
    }

    public InteractionContext withOperation(final String value) {
        return copy(this.component, this.workerId, this.interactionId, value, this.phase, this.table, this.target);
    }

    public InteractionContext withPhase(final String value) {
        return copy(this.component, this.workerId, this.interactionId, this.operation, value, this.table, this.target);
    }

    public InteractionContext withTable(final String value) {
        return copy(this.component, this.workerId, this.interactionId, this.operation, this.phase, value, this.target);
    }

    public InteractionContext withTarget(final String value) {
        return copy(this.component, this.workerId, this.interactionId, this.operation, this.phase, this.table, value);
    }

    public InteractionContext withTag(final String key, final String value) {
        final LinkedHashMap<String, String> nextTags = new LinkedHashMap<String, String>(this.tags);
        if (value != null && !value.trim().isEmpty()) {
            nextTags.put(key, value);
        }
        return new InteractionContext(this.component, this.workerId, this.interactionId, this.operation, this.phase, this.table, this.target, nextTags);
    }

    public Map<String, String> fields() {
        final LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
        if (this.component != null) {
            fields.put("component", this.component);
        }
        if (this.workerId != null) {
            fields.put("worker_id", this.workerId);
        }
        if (this.interactionId != null) {
            fields.put("interaction_id", this.interactionId);
        }
        if (this.operation != null) {
            fields.put("operation", this.operation);
        }
        if (this.phase != null) {
            fields.put("phase", this.phase);
        }
        if (this.table != null) {
            fields.put("table", this.table);
        }
        if (this.target != null) {
            fields.put("target", this.target);
        }
        fields.putAll(this.tags);
        return fields;
    }

    private InteractionContext copy(
            final String component,
            final String workerId,
            final String interactionId,
            final String operation,
            final String phase,
            final String table,
            final String target) {
        return new InteractionContext(component, workerId, interactionId, operation, phase, table, target, new LinkedHashMap<String, String>(this.tags));
    }
}
