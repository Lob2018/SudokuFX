package fr.softsf.sudokufx.common.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Spring component for validating objects using Jakarta Bean Validation.
 * Throws IllegalArgumentException if the object is null,
 * and ConstraintViolationException if validation constraints are violated.
 *
 * <p>This component requires Spring context initialization to inject the Validator.
 *
 * <p>Example usage with a record:
 * <pre>{@code
 * public record UserDto(
 *     @NotNull @Size(min = 2, max = 50) String username,
 *     @NotNull @Email String email,
 *     @Min(18) int age
 * ) {}
 *
 * UserDto user = new UserDto("JohnDoe", "john@example.com", 25);
 * validationUtils.validateOrThrow(user);
 * }</pre>
 */
@Component
public class ValidationUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtils.class);
    private final Validator validator;

    public ValidationUtils(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates the given object.
     * @param obj the object to validate, must not be null
     * @param <T> the object type
     * @return the validated object if valid
     * @throws IllegalArgumentException if the object is null
     * @throws ConstraintViolationException if validation fails
     */
    public <T> T validateOrThrow(T obj) {
        if (Objects.isNull(obj)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The object to validate mustn't be null");
        }
        var violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            ConstraintViolationException exception = new ConstraintViolationException(violations);
            LOG.error("██ Exception validation failed for object: {}", obj, exception);
            throw exception;
        }
        return obj;
    }
}