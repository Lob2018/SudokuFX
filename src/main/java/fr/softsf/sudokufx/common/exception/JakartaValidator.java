/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.exception;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

/**
 * Spring component for validating objects using Jakarta Bean Validation. Throws
 * IllegalArgumentException if the object is null, and ConstraintViolationException if any
 * validation constraints are violated.
 *
 * <p>This component requires Spring context initialization to inject the {@link Validator}.
 *
 * <p>Example usage with a record:
 *
 * <pre>{@code
 * public record UserDto(
 *     @NotNull @Size(min = 2, max = 50) String username,
 *     @NotNull @Email String email,
 *     @Min(18) int age
 * ) {}
 *
 * UserDto user = new UserDto("JohnDoe", "john@example.com", 25);
 * jakartaValidator.validateOrThrow(user);
 * }</pre>
 */
@Component
public class JakartaValidator {

    private static final Logger LOG = LoggerFactory.getLogger(JakartaValidator.class);
    private final Validator validator;

    public JakartaValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates the given object.
     *
     * @param obj the object to validate, must not be null
     * @param <T> the object type
     * @return the validated object if valid
     * @throws IllegalArgumentException if the object is null
     * @throws ConstraintViolationException if validation constraints are violated
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
