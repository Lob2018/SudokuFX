/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerLanguageMapper;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.model.PlayerLanguage;
import fr.softsf.sudokufx.repository.PlayerLanguageRepository;

/**
 * Service for managing {@link PlayerLanguage} entities.
 *
 * <p>Provides retrieval operations for player languages and mapping to {@link PlayerLanguageDto}.
 * Validates DTOs using {@link JakartaValidator} after mapping. Intended for use by higher-level
 * components such as coordinators or controllers to obtain language data.
 *
 * <p>Throws {@link IllegalArgumentException} if a requested language is not found in the
 * repository.
 */
@Service
public class PlayerLanguageService {

    private final PlayerLanguageRepository playerLanguageRepository;
    private final JakartaValidator jakartaValidator;
    private final IPlayerLanguageMapper playerLanguageMapper;

    public PlayerLanguageService(
            PlayerLanguageRepository playerLanguageRepository,
            JakartaValidator jakartaValidator,
            IPlayerLanguageMapper playerLanguageMapper) {
        this.playerLanguageRepository = playerLanguageRepository;
        this.jakartaValidator = jakartaValidator;
        this.playerLanguageMapper = playerLanguageMapper;
    }

    /**
     * Retrieves a {@link PlayerLanguageDto} by ISO code.
     *
     * <p>The search is performed via the repository. If no language matches the provided ISO code,
     * an {@link IllegalArgumentException} is thrown.
     *
     * @param iso the ISO code, must be "FR" or "EN"
     * @return the validated {@link PlayerLanguageDto}
     * @throws IllegalArgumentException if the language is not found
     */
    @Transactional(readOnly = true)
    public PlayerLanguageDto getByIso(String iso) {
        PlayerLanguage entity =
                playerLanguageRepository
                        .findByIso(iso)
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "PlayerLanguage not found for ISO: " + iso));
        PlayerLanguageDto dto = playerLanguageMapper.mapPlayerLanguageToDto(entity);
        return jakartaValidator.validateOrThrow(dto);
    }
}
