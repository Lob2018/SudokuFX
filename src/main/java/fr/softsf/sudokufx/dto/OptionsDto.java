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
 * Data Transfer Object representing an Options entity.
 *
 * @param optionsid the unique identifier of the options
 * @param hexcolor the color in hexadecimal format (e.g., FFFFFFFF), 8 characters, not null
 * @param imagepath the file path to the image associated with the options, max 260 characters, not
 *     null
 * @param songpath the file path to the song associated with the options, max 260 characters, not
 *     null
 * @param image flag indicating if the options uses an image (true) or a color (false)
 * @param opaque flag indicating if the grid's background is opaque (true) or transparent (false)
 * @param muted flag indicating if sound is muted (true) or enabled (false)
 */
public record OptionsDto(
        Long optionsid,
        @Nonnull @NotNull @Size(min = 8, max = 8) String hexcolor,
        @Nonnull @NotNull @Size(max = 260) String imagepath,
        @Nonnull @NotNull @Size(max = 260) String songpath,
        boolean image,
        boolean opaque,
        boolean muted) {}
