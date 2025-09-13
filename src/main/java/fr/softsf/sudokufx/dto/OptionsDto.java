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
 * @param opaque flag indicating if the grid's background is opaque (true) or transparent (false)
 * @param muted flag indicating if sound is muted (true) or enabled (false)
 */
public record OptionsDto(
        Long optionsid,
        @Nonnull @NotNull @Size(min = 8, max = 8) String hexcolor,
        @Nonnull @NotNull @Size(max = 260) String imagepath,
        @Nonnull @NotNull @Size(max = 260) String songpath,
        boolean opaque,
        boolean muted) {

    /**
     * Returns a copy of this DTO with a new {@code optionsid}.
     *
     * @param optionsid the new options ID
     * @return a new {@link OptionsDto} instance
     */
    public OptionsDto withOptionsid(Long optionsid) {
        return new OptionsDto(optionsid, hexcolor, imagepath, songpath, opaque, muted);
    }

    /**
     * Returns a copy of this DTO with a new {@code hexcolor}.
     *
     * @param hexcolor the new hex color
     * @return a new {@link OptionsDto} instance
     */
    public OptionsDto withHexcolor(String hexcolor) {
        return new OptionsDto(optionsid, hexcolor, imagepath, songpath, opaque, muted);
    }

    /**
     * Returns a copy of this DTO with a new {@code imagepath}.
     *
     * @param imagepath the new image path
     * @return a new {@link OptionsDto} instance
     */
    public OptionsDto withImagepath(String imagepath) {
        return new OptionsDto(optionsid, hexcolor, imagepath, songpath, opaque, muted);
    }

    /**
     * Returns a copy of this DTO with a new {@code songpath}.
     *
     * @param songpath the new song path
     * @return a new {@link OptionsDto} instance
     */
    public OptionsDto withSongpath(String songpath) {
        return new OptionsDto(optionsid, hexcolor, imagepath, songpath, opaque, muted);
    }

    /**
     * Returns a copy of this DTO with a new {@code opaque} flag.
     *
     * @param opaque the new opaque value
     * @return a new {@link OptionsDto} instance
     */
    public OptionsDto withOpaque(boolean opaque) {
        return new OptionsDto(optionsid, hexcolor, imagepath, songpath, opaque, muted);
    }

    /**
     * Returns a copy of this DTO with a new {@code muted} flag.
     *
     * @param muted the new muted value
     * @return a new {@link OptionsDto} instance
     */
    public OptionsDto withMuted(boolean muted) {
        return new OptionsDto(optionsid, hexcolor, imagepath, songpath, opaque, muted);
    }
}
