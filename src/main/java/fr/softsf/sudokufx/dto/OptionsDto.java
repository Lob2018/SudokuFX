/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing an' options entity.
 *
 * @param optionsid the unique identifier of the options
 * @param hexcolor the options color in hexadecimal format (max 8 characters), not null
 * @param imagepath the path to the options image (max 260 characters), not null
 * @param isimage flag indicating if the options uses an image (true) or a color (false)
 * @param isopaque flag indicating if the grid options is opaque (true) or transparent (false)
 */
public record OptionsDto(
        Long optionsid,
        @Nonnull @NotNull @Size(max = 8) String hexcolor,
        @Nonnull @NotNull @Size(max = 260) String imagepath,
        boolean isimage,
        boolean isopaque) {}
