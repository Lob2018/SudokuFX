package fr.softsf.sudokufx.common.exception;

/**
 * Checked exception thrown when a resource fails to load.
 * <p>
 * Can be used for audio, image, or any other resource type that cannot be loaded or processed.
 */
public class ResourceLoadException extends Exception {

    /**
     * Constructs a new ResourceLoadException with the specified detail message.
     *
     * @param message descriptive message of the failure; must not be null
     */
    public ResourceLoadException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceLoadException with the specified detail message and cause.
     *
     * @param message descriptive message of the failure; must not be null
     * @param cause   the underlying cause of the exception; may be null
     */
    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
