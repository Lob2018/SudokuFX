/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IOptionsMapper;
import fr.softsf.sudokufx.dto.OptionsDto;
import fr.softsf.sudokufx.model.Options;
import fr.softsf.sudokufx.repository.OptionsRepository;

/**
 * Service for managing Options entities.
 *
 * <p>Provides retrieval and update operations on options, mapping entities to {@link OptionsDto}
 * and validating DTOs with {@link JakartaValidator} before and after persistence.
 *
 * <p>Methods:
 *
 * <ul>
 *   <li>{@link #updateOptions(OptionsDto)}: updates an existing Options entity, validates input and
 *       output DTOs, fully transactional.
 * </ul>
 *
 * <p>Throws {@link NullPointerException} for null DTOs, {@link IllegalArgumentException} if the
 * entity is missing, and {@link jakarta.validation.ConstraintViolationException} on validation
 * failures.
 */
@Service
public class OptionsService {

    private final OptionsRepository optionsRepository;
    private final IOptionsMapper optionsMapper;
    private final JakartaValidator jakartaValidator;

    public OptionsService(
            OptionsRepository optionsRepository,
            IOptionsMapper optionsMapper,
            JakartaValidator jakartaValidator) {
        this.optionsRepository = optionsRepository;
        this.optionsMapper = optionsMapper;
        this.jakartaValidator = jakartaValidator;
    }

    /**
     * Updates an existing Options entity with the values from the given {@link OptionsDto}.
     *
     * <p>Validates the DTO before applying changes and validates the resulting DTO after saving.
     *
     * @param dto the options data to update; must not be null
     * @return the updated and validated {@link OptionsDto}
     * @throws NullPointerException if {@code dto} is null
     * @throws IllegalArgumentException if the Options entity does not exist
     * @throws jakarta.validation.ConstraintViolationException if validation fails on the DTO
     */
    @Transactional
    public OptionsDto updateOptions(OptionsDto dto) {
        OptionsDto validatedDto = jakartaValidator.validateOrThrow(dto);
        Options existing =
                optionsRepository
                        .findById(validatedDto.optionsid())
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "Options not found: " + validatedDto.optionsid()));
        existing.setHexcolor(validatedDto.hexcolor());
        existing.setImagepath(validatedDto.imagepath());
        existing.setSongpath(validatedDto.songpath());
        existing.setOpaque(validatedDto.opaque());
        existing.setMuted(validatedDto.muted());
        Options saved = optionsRepository.save(existing);
        OptionsDto result = optionsMapper.mapOptionsToDto(saved);
        return jakartaValidator.validateOrThrow(result);
    }
}
