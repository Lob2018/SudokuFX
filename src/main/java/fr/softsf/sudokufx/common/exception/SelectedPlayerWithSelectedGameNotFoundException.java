package fr.softsf.sudokufx.common.exception;

/**
 * Exception thrown when no selected Player with a selected Game is found.
 */
public class SelectedPlayerWithSelectedGameNotFoundException  extends RuntimeException {
    public SelectedPlayerWithSelectedGameNotFoundException(String message) {
        super(message);
    }
}
