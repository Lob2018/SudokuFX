/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents user-configurable options for the SudokuFX application.
 *
 * <p>Includes background color, image and song paths, and flags for image usage, opacity, and
 * muting. Provides fluent builder, validation, and standard object methods.
 */
@Entity
@Table(name = "playeroptions")
public class Options {

    private static final String DEFAULT_HEX_COLOR = "FFFFFFFF";
    private static final String EMPTY_PATH = "";
    private static final String HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK =
            "hexcolor must not be null or blank";
    private static final String IMAGEPATH_MUST_NOT_BE_NULL = "imagepath must not be null";
    private static final String SONGPATH_MUST_NOT_BE_NULL = "songpath must not be null";
    private static final int MAX_PATH_LENGTH = 260;
    private static final String INVALID_HEX_COLOR_FORMAT =
            "hexcolor must be a valid hex color format (e.g., FFFFFFFF)";
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^([A-Fa-f0-9]{8})$");

    /** Unique identifier of the options (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionsid", nullable = false)
    private Long optionsid;

    /** Background color in ARGB hex format (e.g., FFFFFFFF). */
    @Nonnull
    @NotNull @Size(max = 8) @Column(name = "hexcolor", nullable = false, length = 8)
    private String hexcolor = DEFAULT_HEX_COLOR;

    /** Path to the background image file. */
    @Nonnull
    @NotNull @Size(max = MAX_PATH_LENGTH) @Column(name = "imagepath", nullable = false, length = MAX_PATH_LENGTH)
    private String imagepath = EMPTY_PATH;

    /** Path to the background song file. */
    @Nonnull
    @NotNull @Size(max = MAX_PATH_LENGTH) @Column(name = "songpath", nullable = false, length = MAX_PATH_LENGTH)
    private String songpath = EMPTY_PATH;

    /** Flag indicating if a background image is used. */
    @Column(name = "image", nullable = false)
    private boolean image = false;

    /** Flag indicating if the background image is opaque. */
    @Column(name = "opaque", nullable = false)
    private boolean opaque = true;

    /** Flag indicating if the background music is muted. */
    @Column(name = "muted", nullable = false)
    private boolean muted = true;

    /** Protected default constructor for JPA. */
    protected Options() {}

    /**
     * Full constructor to initialize all fields of options.
     *
     * @param optionsid unique ID of the options
     * @param hexcolor background color in ARGB hex format
     * @param imagepath path to background image
     * @param songpath path to background song
     * @param image true if using an image
     * @param opaque true if the image is opaque
     * @param muted true if the song is muted
     */
    public Options(
            Long optionsid,
            @Nonnull @NotNull String hexcolor,
            @Nonnull @NotNull String imagepath,
            @Nonnull @NotNull String songpath,
            boolean image,
            boolean opaque,
            boolean muted) {
        this.optionsid = optionsid;
        this.hexcolor = validateHexcolor(hexcolor);
        this.imagepath = validatePath(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
        this.songpath = validatePath(songpath, SONGPATH_MUST_NOT_BE_NULL);
        this.image = image;
        this.opaque = opaque;
        this.muted = muted;
    }

    /**
     * Validates that the hex color is non-blank and conforms to ARGB 8-character hex format.
     *
     * @param hexcolor the color string to validate
     * @return validated hex color
     * @throws IllegalArgumentException if hexcolor is blank or invalid
     */
    private static String validateHexcolor(String hexcolor) {
        if (StringUtils.isBlank(hexcolor)) {
            throw new IllegalArgumentException(HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK);
        }
        if (HEX_COLOR_PATTERN.matcher(hexcolor).matches()) {
            return hexcolor;
        }
        throw new IllegalArgumentException(INVALID_HEX_COLOR_FORMAT);
    }

    /**
     * Validates a file path for non-null and maximum length.
     *
     * @param path path string to validate
     * @param nullMsg exception message if path is null
     * @return validated path
     * @throws NullPointerException if path is null
     * @throws IllegalArgumentException if path exceeds MAX_PATH_LENGTH
     */
    private static String validatePath(String path, String nullMsg) {
        Objects.requireNonNull(path, nullMsg);
        if (path.length() > MAX_PATH_LENGTH) {
            throw new IllegalArgumentException(
                    "Path must not exceed " + MAX_PATH_LENGTH + " characters");
        }
        return path;
    }

    /** Returns the unique ID of this Options instance. */
    public Long getOptionsid() {
        return optionsid;
    }

    /** Returns the ARGB hex color string. */
    @Nonnull
    public String getHexcolor() {
        return hexcolor;
    }

    /** Returns the background image path. */
    @Nonnull
    public String getImagepath() {
        return imagepath;
    }

    /** Returns the background song path. */
    @Nonnull
    public String getSongpath() {
        return songpath;
    }

    /** Returns true if a background image is used. */
    public boolean getImage() {
        return image;
    }

    /** Returns true if the background image is opaque. */
    public boolean getOpaque() {
        return opaque;
    }

    /** Returns true if the background song is muted. */
    public boolean getMuted() {
        return muted;
    }

    /** Sets the ARGB hex color after validation. */
    public void setHexcolor(@Nonnull String hexcolor) {
        this.hexcolor = validateHexcolor(hexcolor);
    }

    /** Sets the background image path after validation. */
    public void setImagepath(@Nonnull String imagepath) {
        this.imagepath = validatePath(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
    }

    /** Sets the background song path after validation. */
    public void setSongpath(@Nonnull String songpath) {
        this.songpath = validatePath(songpath, SONGPATH_MUST_NOT_BE_NULL);
    }

    /** Sets whether a background image is used. */
    public void setImage(boolean image) {
        this.image = image;
    }

    /** Sets whether the background image is opaque. */
    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }

    /** Sets whether the background song is muted. */
    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    /** Returns a new {@link OptionsBuilder} instance for fluent construction. */
    public static OptionsBuilder builder() {
        return new OptionsBuilder();
    }

    /**
     * Builder class for creating {@link Options} instances with fluent API.
     *
     * <p>Allows setting all fields before constructing a validated Options object.
     */
    public static class OptionsBuilder {
        private Long optionsid;
        private String hexcolor = DEFAULT_HEX_COLOR;
        private String imagepath = EMPTY_PATH;
        private String songpath = EMPTY_PATH;
        private boolean image = false;
        private boolean opaque = true;
        private boolean muted = true;

        /** Sets the unique ID of the Options instance. */
        public OptionsBuilder optionsid(Long optionsid) {
            this.optionsid = optionsid;
            return this;
        }

        /** Sets the ARGB hex color. */
        public OptionsBuilder hexcolor(@Nonnull String hexcolor) {
            this.hexcolor = hexcolor;
            return this;
        }

        /** Sets the background image path. */
        public OptionsBuilder imagepath(@Nonnull String imagepath) {
            this.imagepath = imagepath;
            return this;
        }

        /** Sets the background song path. */
        public OptionsBuilder songpath(@Nonnull String songpath) {
            this.songpath = songpath;
            return this;
        }

        /** Sets whether a background image is used. */
        public OptionsBuilder image(boolean image) {
            this.image = image;
            return this;
        }

        /** Sets whether the background image is opaque. */
        public OptionsBuilder opaque(boolean opaque) {
            this.opaque = opaque;
            return this;
        }

        /** Sets whether the background song is muted. */
        public OptionsBuilder muted(boolean muted) {
            this.muted = muted;
            return this;
        }

        /**
         * Builds a validated {@link Options} instance.
         *
         * @return a fully constructed Options instance
         * @throws IllegalArgumentException if parameters are invalid
         */
        public Options build() {
            return new Options(optionsid, hexcolor, imagepath, songpath, image, opaque, muted);
        }
    }

    /** Compares two Options objects for equality based on all fields. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Options other) {
            return image == other.image
                    && opaque == other.opaque
                    && muted == other.muted
                    && Objects.equals(optionsid, other.optionsid)
                    && Objects.equals(hexcolor, other.hexcolor)
                    && Objects.equals(imagepath, other.imagepath)
                    && Objects.equals(songpath, other.songpath);
        }
        return false;
    }

    /** Computes the hash code based on all fields. */
    @Override
    public int hashCode() {
        return Objects.hash(optionsid, hexcolor, imagepath, songpath, image, opaque, muted);
    }

    /** Returns a string representation of the Options object. */
    @Override
    public String toString() {
        return String.format(
                "Options{optionsid=%s, hexcolor='%s', imagepath='%s', songpath='%s', image=%b,"
                        + " opaque=%b, muted=%b}",
                optionsid, hexcolor, imagepath, songpath, image, opaque, muted);
    }
}
