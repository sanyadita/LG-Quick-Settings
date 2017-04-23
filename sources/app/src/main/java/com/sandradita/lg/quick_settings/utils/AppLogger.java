package com.sandradita.lg.quick_settings.utils;

import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author aditkovskaya
 */

public final class AppLogger {

    private static final String APP_TAG = "LGQuickSettings";

    private AppLogger() {
    }

    /**
     * Send a {@link Log#DEBUG DEBUG} message. As a message it uses all selected objects. They will
     * be format into a string separated by whitespace. If objects are null or its amount is 0,
     * shows "(null)" as message. Tag will be shown as "{@link AppLogger#APP_TAG APP_TAG} tag message".
     */
    public static <T> void debug(@Nullable String tag, @Nullable T... objects) {
        String fullTag = getFullTag(tag);
        String fullMessage = getFullMessage(objects);

        Log.d(fullTag, fullMessage);
    }

    /**
     * Send a {@link Log#DEBUG DEBUG} message. As a message it uses all selected objects. They will
     * be format into a string separated by whitespace. If objects are null or its amount is 0,
     * shows "(null)" as message. Tag will be {@link AppLogger#APP_TAG APP_TAG}.
     */
    public static <T> void debug(@Nullable T... objects) {
        debug(null, objects);
    }

    /**
     * Send a {@link Log#INFO INFO} message. As a message it uses all selected objects. They will
     * be format into a string separated by whitespace. If objects are null or its amount is 0,
     * shows "(null)" as message. Tag will be shown as "{@link AppLogger#APP_TAG APP_TAG} tag message".
     */
    public static <T> void info(@Nullable String tag, @Nullable T... objects) {
        String fullTag = getFullTag(tag);
        String fullMessage = getFullMessage(objects);

        Log.i(fullTag, fullMessage);
    }

    /**
     * Send a {@link Log#INFO INFO} message. As a message it uses all selected objects. They will
     * be format into a string separated by whitespace. If objects are null or its amount is 0,
     * shows "(null)" as message. Tag will be {@link AppLogger#APP_TAG APP_TAG}.
     */
    public static <T> void info(@Nullable T... objects) {
        info(null, objects);
    }

    /**
     * Send a {@link Log#ERROR ERROR} message.
     */
    public static void error(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        String fullTag = getFullTag(tag);
        String fullMessage = message != null ? message : "";
        if (throwable != null) {
            fullMessage += " ::: " + throwable.getMessage();
        }

        Log.e(fullTag, fullMessage);
    }

    /**
     * Send a {@link Log#ERROR ERROR} message.
     */
    public static void error(@Nullable String tag, @Nullable Throwable throwable) {
        error(tag, null, throwable);
    }

    private static String getFullTag(@Nullable String tag) {
        if (tag == null) return APP_TAG;
        return String.format("%s <%s>", APP_TAG, tag);
    }

    private static String getFullMessage(@Nullable Object... objects) {
        if (objects != null && objects.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < objects.length; i++) {
                Object o = objects[i];
                if (o != null) {
                    stringBuilder.append(o);
                    if (i != objects.length - 1) stringBuilder.append(' ');
                }
            }
            return stringBuilder.toString();
        }
        return "(null)";
    }

}
