package it.capozxvii.clankchampionship.util.exception;

import it.capozxvii.clankchampionship.util.Message;

public class ClankChampionshipException extends RuntimeException {
    public ClankChampionshipException(final String message, final Object... arguments) {
        super(Message.formatMessage(message, arguments));
    }
}
