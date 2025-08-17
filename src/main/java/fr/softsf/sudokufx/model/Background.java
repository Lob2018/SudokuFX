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
@Table(name = "background")
public class Background {

    private static final String DEFAULT_HEX_COLOR = "#000000";
    private static final String EMPTY_PATH = "";
    private static final String HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK =
            "hexcolor must not be null or blank";
    private static final String IMAGEPATH_MUST_NOT_BE_NULL = "imagepath must not be null";
    private static final String INVALID_HEX_COLOR_FORMAT =
            "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)";
    private static final Pattern HEX_COLOR_PATTERN =
            Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backgroundid;

    @Nonnull
    @NotNull @Size(max = 8) @Column(name = "hexcolor", nullable = false, length = 8)
    private String hexcolor = DEFAULT_HEX_COLOR;

    @Nonnull
    @NotNull @Size(max = 260) @Column(name = "imagepath", nullable = false, length = 1024)
    private String imagepath = EMPTY_PATH;

    private boolean isimage = false;

    protected Background() {}

    public Background(
            Long backgroundid,
            @Nonnull @NotNull String hexcolor,
            @Nonnull @NotNull String imagepath,
            boolean isimage) {
        this.backgroundid = backgroundid;
        this.hexcolor = validateHexcolor(hexcolor);
        this.imagepath = validateImagepath(imagepath);
        this.isimage = isimage;
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
     * Validates image path is not null.
     *
     * @param imagepath image path to validate
     * @return validated image path
     * @throws NullPointerException if null
     */
    private static String validateImagepath(String imagepath) {
        return Objects.requireNonNull(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
    }

    public Long getBackgroundid() {
        return backgroundid;
    }

    @Nonnull
    public String getHexcolor() {
        return hexcolor;
    }

    @Nonnull
    public String getImagepath() {
        return imagepath;
    }

    public boolean getIsimage() {
        return isimage;
    }

    public void setHexcolor(@Nonnull String hexcolor) {
        this.hexcolor = validateHexcolor(hexcolor);
    }

    public void setImagepath(@Nonnull String imagepath) {
        this.imagepath = validateImagepath(imagepath);
    }

    public void setIsimage(boolean isimage) {
        this.isimage = isimage;
    }

    public static BackgroundBuilder builder() {
        return new BackgroundBuilder();
    }

    /**
     * Builder for creating Background instances with fluent API. Validation occurs at build() to
     * avoid exceptions during construction.
     */
    public static class BackgroundBuilder {
        private Long backgroundid;
        private String hexcolor = DEFAULT_HEX_COLOR;
        private String imagepath = EMPTY_PATH;
        private boolean isimage = false;

        public BackgroundBuilder backgroundid(Long backgroundid) {
            this.backgroundid = backgroundid;
            return this;
        }

        public BackgroundBuilder hexcolor(@Nonnull String hexcolor) {
            this.hexcolor = hexcolor;
            return this;
        }

        public BackgroundBuilder imagepath(@Nonnull String imagepath) {
            this.imagepath = imagepath;
            return this;
        }

        public BackgroundBuilder isimage(boolean isimage) {
            this.isimage = isimage;
            return this;
        }

        /**
         * Creates Background instance with parameter validation.
         *
         * @return new validated Background instance
         * @throws IllegalArgumentException if parameters are invalid
         */
        public Background build() {
            return new Background(backgroundid, hexcolor, imagepath, isimage);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Background other)) {
            return false;
        }
        return isimage == other.isimage
                && Objects.equals(backgroundid, other.backgroundid)
                && Objects.equals(hexcolor, other.hexcolor)
                && Objects.equals(imagepath, other.imagepath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(backgroundid, hexcolor, imagepath, isimage);
    }

    @Override
    public String toString() {
        return String.format(
                "Background{backgroundid=%s, hexcolor='%s', imagepath='%s', isimage=%b}",
                backgroundid, hexcolor, imagepath, isimage);
    }
}
