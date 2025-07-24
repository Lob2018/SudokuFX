/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Nonnull;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backgroundid;

    @Nonnull
    @NotNull @Size(max = 8) private String hexcolor = DEFAULT_HEX_COLOR;

    @Nonnull
    @NotNull @Size(max = 260) private String imagepath = EMPTY_PATH;

    private boolean isimage = false;

    protected Background() {}

    public Background(
            Long backgroundid,
            @Nonnull @NotNull String hexcolor,
            @Nonnull @NotNull String imagepath,
            boolean isimage) {
        if (StringUtils.isBlank(hexcolor)) {
            throw new IllegalArgumentException(HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK);
        }
        Objects.requireNonNull(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
        this.backgroundid = backgroundid;
        this.hexcolor = hexcolor;
        this.imagepath = imagepath;
        this.isimage = isimage;
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
        if (StringUtils.isBlank(hexcolor)) {
            throw new IllegalArgumentException(HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK);
        }
        this.hexcolor = hexcolor;
    }

    public void setImagepath(@Nonnull String imagepath) {
        Objects.requireNonNull(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
        this.imagepath = imagepath;
    }

    public void setIsimage(boolean isimage) {
        this.isimage = isimage;
    }

    public static BackgroundBuilder builder() {
        return new BackgroundBuilder();
    }

    public static class BackgroundBuilder {
        private Long backgroundid;

        @Nonnull
        @NotNull @Size(max = 8) private String hexcolor = DEFAULT_HEX_COLOR;

        @Nonnull
        @NotNull @Size(max = 260) private String imagepath = EMPTY_PATH;

        private boolean isimage = false;

        public BackgroundBuilder backgroundid(Long backgroundid) {
            this.backgroundid = backgroundid;
            return this;
        }

        public BackgroundBuilder hexcolor(@Nonnull String hexcolor) {
            if (StringUtils.isBlank(hexcolor)) {
                throw new IllegalArgumentException(HEXCOLOR_MUST_NOT_BE_NULL_OR_BLANK);
            }
            this.hexcolor = hexcolor;
            return this;
        }

        public BackgroundBuilder imagepath(@Nonnull String imagepath) {
            Objects.requireNonNull(imagepath, IMAGEPATH_MUST_NOT_BE_NULL);
            this.imagepath = imagepath;
            return this;
        }

        public BackgroundBuilder isimage(boolean isimage) {
            this.isimage = isimage;
            return this;
        }

        public Background build() {
            return new Background(backgroundid, hexcolor, imagepath, isimage);
        }
    }
}
