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

@Entity
@Table(name = "playeroptions")
public class Options {

    private static final String DEFAULT_HEX_COLOR = "#000000";
    private static final String EMPTY_PATH = "";
    private static final String HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK =
            "hexcolor must not be null or blank";
    private static final String IMAGEPATH_MUST_NOT_BE_NULL = "imagepath must not be null";
    private static final String SONGPATH_MUST_NOT_BE_NULL = "songpath must not be null";
    private static final int MAX_PATH_LENGTH = 260;
    private static final String INVALID_HEX_COLOR_FORMAT =
            "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)";
    private static final Pattern HEX_COLOR_PATTERN =
            Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionsid", nullable = false)
    private Long optionsid;

    @Nonnull
    @NotNull
    @Size(max = 8)
    @Column(name = "hexcolor", nullable = false, length = 8)
    private String hexcolor = DEFAULT_HEX_COLOR;

    @Nonnull
    @NotNull
    @Size(max = MAX_PATH_LENGTH)
    @Column(name = "imagepath", nullable = false, length = MAX_PATH_LENGTH)
    private String imagepath = EMPTY_PATH;

    @Nonnull
    @NotNull
    @Size(max = MAX_PATH_LENGTH)
    @Column(name = "songpath", nullable = false, length = MAX_PATH_LENGTH)
    private String songpath = EMPTY_PATH;

    @Column(name = "isimage", nullable = false)
    private boolean isimage = false;

    @Column(name = "isopaque", nullable = false)
    private boolean isopaque = true;

    @Column(name = "ismuted", nullable = false)
    private boolean ismuted = true;

    protected Options() {
    }

    public Options(
            Long optionsid,
            @Nonnull @NotNull String hexcolor,
            @Nonnull @NotNull String imagepath,
            @Nonnull @NotNull String songpath,
            boolean isimage,
            boolean isopaque,
            boolean ismuted) {
        this.optionsid = optionsid;
        this.hexcolor = validateHexcolor(hexcolor);
        this.imagepath = validatePath(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
        this.songpath = validatePath(songpath, SONGPATH_MUST_NOT_BE_NULL);
        this.isimage = isimage;
        this.isopaque = isopaque;
        this.ismuted = ismuted;
    }

    /**
     * Validates hex color format (#RGB or #RRGGBB).
     *
     * @param hexcolor hex color to validate
     * @return validated hex color
     * @throws IllegalArgumentException if invalid format
     */
    private static String validateHexcolor(String hexcolor) {
        if (StringUtils.isBlank(hexcolor)) {
            throw new IllegalArgumentException(HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK);
        }
        if (!HEX_COLOR_PATTERN.matcher(hexcolor).matches()) {
            throw new IllegalArgumentException(INVALID_HEX_COLOR_FORMAT);
        }
        return hexcolor;
    }

    /**
     * Validates that a path is not null and does not exceed the allowed length.
     *
     * @param path    the path to validate
     * @param nullMsg the message if path is null
     * @return validated path
     * @throws NullPointerException     if the path is null
     * @throws IllegalArgumentException if the path length exceeds MAX_PATH_LENGTH
     */
    private static String validatePath(String path, String nullMsg) {
        Objects.requireNonNull(path, nullMsg);
        if (path.length() > MAX_PATH_LENGTH) {
            throw new IllegalArgumentException(
                    "Path must not exceed " + MAX_PATH_LENGTH + " characters");
        }
        return path;
    }

    public Long getOptionsid() {
        return optionsid;
    }

    @Nonnull
    public String getHexcolor() {
        return hexcolor;
    }

    @Nonnull
    public String getImagepath() {
        return imagepath;
    }

    @Nonnull
    public String getSongpath() {
        return songpath;
    }

    public boolean isImage() {
        return isimage;
    }

    public boolean isOpaque() {
        return isopaque;
    }

    public boolean isMuted() {
        return ismuted;
    }

    public void setHexcolor(@Nonnull String hexcolor) {
        this.hexcolor = validateHexcolor(hexcolor);
    }

    public void setImagepath(@Nonnull String imagepath) {
        this.imagepath = validatePath(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
    }

    public void setSongpath(@Nonnull String songpath) {
        this.songpath = validatePath(songpath, SONGPATH_MUST_NOT_BE_NULL);
    }

    public void setIsimage(boolean isimage) {
        this.isimage = isimage;
    }

    public void setIsopaque(boolean isopaque) {
        this.isopaque = isopaque;
    }

    public void setIsmuted(boolean ismuted) {
        this.ismuted = ismuted;
    }

    public static OptionsBuilder builder() {
        return new OptionsBuilder();
    }

    /**
     * Builder for creating Options instances with fluent API. Validation occurs at build() to avoid
     * exceptions during construction.
     */
    public static class OptionsBuilder {
        private Long optionsid;
        private String hexcolor = DEFAULT_HEX_COLOR;
        private String imagepath = EMPTY_PATH;
        private String songpath = EMPTY_PATH;
        private boolean isimage = false;
        private boolean isopaque = true;
        private boolean ismuted = true;

        public OptionsBuilder optionsid(Long optionsid) {
            this.optionsid = optionsid;
            return this;
        }

        public OptionsBuilder hexcolor(@Nonnull String hexcolor) {
            this.hexcolor = hexcolor;
            return this;
        }

        public OptionsBuilder imagepath(@Nonnull String imagepath) {
            this.imagepath = imagepath;
            return this;
        }

        public OptionsBuilder songpath(@Nonnull String songpath) {
            this.songpath = songpath;
            return this;
        }

        public OptionsBuilder isimage(boolean isimage) {
            this.isimage = isimage;
            return this;
        }

        public OptionsBuilder isopaque(boolean isopaque) {
            this.isopaque = isopaque;
            return this;
        }

        public OptionsBuilder ismuted(boolean ismuted) {
            this.ismuted = ismuted;
            return this;
        }

        /**
         * Creates Options instance with parameter validation.
         *
         * @return new validated Options instance
         * @throws IllegalArgumentException if parameters are invalid
         */
        public Options build() {
            return new Options(optionsid, hexcolor, imagepath, songpath, isimage, isopaque, ismuted);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Options other)) {
            return false;
        }
        return isimage == other.isimage
                && isopaque == other.isopaque
                && ismuted == other.ismuted
                && Objects.equals(optionsid, other.optionsid)
                && Objects.equals(hexcolor, other.hexcolor)
                && Objects.equals(imagepath, other.imagepath)
                && Objects.equals(songpath, other.songpath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionsid, hexcolor, imagepath, songpath, isimage, isopaque, ismuted);
    }

    @Override
    public String toString() {
        return String.format(
                "Options{optionsid=%s, hexcolor='%s', imagepath='%s', songpath='%s', isimage=%b,"
                        + " isopaque=%b, ismuted=%b}",
                optionsid, hexcolor, imagepath, songpath, isimage, isopaque, ismuted);
    }
}
