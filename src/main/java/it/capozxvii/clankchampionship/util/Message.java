package it.capozxvii.clankchampionship.util;

public final class Message {

    public static final String NOT_FOUND = "{} with id [{}] not found";

    public static final String SUCCESSFULLY_SAVED = "{} with id [{}] has been saved";

    public static final String SUCCESSFULLY_UPDATED = "{} with id [{}] has been updated";

    public static final String SUCCESSFULLY_DELETED = "{} with id [{}] has been saved";

    public static final String POINTS_SUCCESSFULLY_SAVED = "Points have been saved";

    public static final String ERROR_WHILE_CONVERTING_TO_JSON = "Error while converting [{}] to JSON";
    public static final String ERROR_WHILE_CONVERTING_TO_OBJECT = "Error while converting [{}] to Object";

    private Message() {
    }

    public static String formatMessage(final String message, final Object... arguments) {
        String finalMessage = message;
        for (final Object argument : arguments) {
            finalMessage = finalMessage.replaceFirst("\\{\\}", argument.toString());
        }
        return finalMessage;
    }
}
