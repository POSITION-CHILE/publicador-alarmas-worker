package com.position.publicador_alarmas;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

public final class LogfmtLogger {
    private final PrintStream out;

    public LogfmtLogger() {
        this(System.out);
    }

    public LogfmtLogger(final PrintStream out) {
        this.out = out;
    }

    public void info(final String event, final InteractionContext context, final Object... keyValues) {
        log("info", event, context, keyValues);
    }

    public void warn(final String event, final InteractionContext context, final Object... keyValues) {
        log("warn", event, context, keyValues);
    }

    public void error(final String event, final InteractionContext context, final Object... keyValues) {
        log("error", event, context, keyValues);
    }

    public void log(final String level, final String event, final InteractionContext context, final Object... keyValues) {
        final LinkedHashMap<String, Object> fields = new LinkedHashMap<String, Object>();
        fields.put("level", level);
        fields.put("event", event);
        if (context != null) {
            for (final Map.Entry<String, String> entry : context.fields().entrySet()) {
                fields.put(entry.getKey(), entry.getValue());
            }
        }
        if (keyValues != null) {
            for (int i = 0; i + 1 < keyValues.length; i += 2) {
                final Object key = keyValues[i];
                final Object value = keyValues[i + 1];
                if (key != null && value != null) {
                    fields.put(String.valueOf(key), value);
                }
            }
        }
        final StringBuilder line = new StringBuilder();
        boolean first = true;
        for (final Map.Entry<String, Object> entry : fields.entrySet()) {
            if (!first) {
                line.append(' ');
            }
            first = false;
            line.append(entry.getKey()).append('=').append(formatValue(entry.getValue()));
        }
        this.out.println(line.toString());
    }

    private String formatValue(final Object value) {
        final String text = String.valueOf(value);
        if (text.isEmpty()) {
            return "\"\"";
        }
        if (text.matches("[A-Za-z0-9_\\-./:@]+")) {
            return text;
        }
        return "\"" + text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r") + "\"";
    }
}
