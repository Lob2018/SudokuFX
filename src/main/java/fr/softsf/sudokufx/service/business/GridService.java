/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IGridMapper;
import fr.softsf.sudokufx.dto.GridDto;
import fr.softsf.sudokufx.model.Grid;
import fr.softsf.sudokufx.repository.GridRepository;

/**
 * Service for managing Grid entities.
 *
 * <p>Provides duplication operations on grids, mapping entities to {@link GridDto} and validating
 * DTOs with {@link JakartaValidator} after persistence.
 *
 * <p>Methods:
 *
 * <ul>
 *   <li>{@link #duplicateGrid(Long)}: duplicates an existing Grid entity, validates the resulting
 *       DTO, fully transactional.
 * </ul>
 *
 * <p>Throws {@link NullPointerException} for null IDs, {@link IllegalArgumentException} if the
 * entity is missing, and {@link jakarta.validation.ConstraintViolationException} on validation
 * failures.
 */
@Service
public class GridService {

    private final GridRepository gridRepository;
    private final IGridMapper gridMapper;
    private final JakartaValidator jakartaValidator;

    public GridService(
            GridRepository gridRepository,
            IGridMapper gridMapper,
            JakartaValidator jakartaValidator) {
        this.gridRepository = gridRepository;
        this.gridMapper = gridMapper;
        this.jakartaValidator = jakartaValidator;
    }

    /**
     * Duplicates an existing Grid entity by creating a new record with a generated ID using the
     * fluent builder.
     *
     * <p>Fetches the source entity, copies its properties to a new instance via the builder API,
     * persists it, and validates the resulting DTO before returning.
     *
     * @param gridId the ID of the Grid entity to duplicate; must not be null
     * @return the newly created and validated {@link GridDto}
     * @throws NullPointerException if {@code gridId} is null
     * @throws IllegalArgumentException if the source Grid entity does not exist
     * @throws jakarta.validation.ConstraintViolationException if validation fails on the resulting
     *     DTO
     */
    @Transactional
    public GridDto duplicateGrid(Long gridId) {
        java.util.Objects.requireNonNull(gridId, "gridId must not be null");
        Grid source =
                gridRepository
                        .findById(gridId)
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "Grid not found: " + gridId));
        Grid duplicate =
                Grid.builder()
                        .defaultgridvalue(source.getDefaultgridvalue())
                        .gridvalue(source.getGridvalue())
                        .possibilities(source.getPossibilities())
                        .build();
        Grid saved = gridRepository.save(duplicate);
        GridDto result = gridMapper.mapGridToDto(saved);
        return jakartaValidator.validateOrThrow(result);
    }
}
