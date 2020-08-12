package com.masterdevskills.cha1.ext5;

import java.time.Instant;
import java.util.function.Supplier;

//TODO: implement info, trace, debug, warn
public class Logger implements Log {
    private static final String DELIM = "{}";
    private volatile boolean enabled;

    private static final String INFO = "INFO";
    private static final String TRACE = "TRACE";
    private static final String DEBUG = "DEBUG";
    private static final String WARNING = "WARNING";

    enum Color {
        //Color end string, color reset
        RESET("\033[0m"),

        // Regular Colors. Normal color, no bold, background color etc.
        GREEN("\033[0;32m"),
        RED("\033[0;31m"),
        BLUE("\033[0;34m"),
        YELLOW("\033[0;33m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    private Logger() {
    }

    public static Log getLogger() {
        return new Logger();
    }

    @Override
    public boolean isLoggable() {
        return enabled;
    }

    @Override
    public void enableLogging() {
        this.enabled = true;
    }

    @Override
    public void info(final String message, final Object... params) {
        if (isLoggable()) {
            System.out.println(formatMessage(INFO, message, params));
        }
    }

    private String formatMessage(final String type, final String message,
                                 final Object[] params) {
        if (type != null && message != null && params != null) {
            String time = Instant.now().toString();
            StringBuilder sbMessage = new StringBuilder(time);
            sbMessage.append(" | ").append(type).append(" | ").append(message);

            for (Object arg : params) {
                int index = sbMessage.indexOf(DELIM);
                if (index == -1) break;
                sbMessage.replace(index, index + DELIM.length(),
                        arg == null ? "null" : arg.toString());
            }

            return sbMessage.toString();
        }
        return message;
    }

    /**
     * @param message INFO log message
     * @param params log parameters
     */
    @Override
    public void info(final String message, final Supplier<Object[]> params) {
        printLogMessage(INFO, message, params, Color.GREEN);
    }

    /**
     * @param message TRACE log message
     * @param params log parameters
     */
    @Override
    public void trace(String message, Supplier<Object[]> params) {
        printLogMessage(TRACE, message, params, Color.RED);
    }

    /**
     * @param message DEBUG log message
     * @param params log parameters
     */
    @Override
    public void debug(String message, Supplier<Object[]> params) {
        printLogMessage(DEBUG, message, params, Color.BLUE);
    }

    /**
     * @param message WARNING log message
     * @param params log parameters
     */
    @Override
    public void warn(String message, Supplier<Object[]> params) {
        printLogMessage(WARNING, message, params, Color.YELLOW);
    }

    /**
     * This method will print log message with date-time, type and message
     *
     * @param type log type
     * @param message log message
     * @param params log parameters
     */
    private void printLogMessage(final String type, final String message,
                                 final Supplier<Object[]> params, Color color) {
        if (isLoggable()) {
            System.out.print(color);
            System.out.println(formatMessage(type, message, params.get()));
            System.out.print(Color.RESET);
        }
    }

    public Logger setEnabled(final boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}


