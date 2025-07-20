/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backgroundid;

    @NotNull @Size(max = 8) private String hexcolor;

    @Size(max = 260) private String imagepath;

    @NotNull private Boolean isimage = false;

    public Background() {
        this.isimage = false;
    }

    public Background(Long backgroundid, String hexcolor, String imagepath, Boolean isimage) {
        this.backgroundid = backgroundid;
        this.hexcolor = hexcolor;
        this.imagepath = imagepath;
        this.isimage = isimage != null ? isimage : false;
    }

    public Long getBackgroundid() {
        return backgroundid;
    }

    public String getHexcolor() {
        return hexcolor;
    }

    public String getImagepath() {
        return imagepath;
    }

    public Boolean getIsimage() {
        return isimage;
    }

    public void setHexcolor(String hexcolor) {
        this.hexcolor = hexcolor;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setIsimage(Boolean isimage) {
        this.isimage = isimage;
    }

    public static BackgroundBuilder builder() {
        return new BackgroundBuilder();
    }

    public static class BackgroundBuilder {
        private Long backgroundid;
        private String hexcolor;
        private String imagepath;
        private Boolean isimage = false;

        public BackgroundBuilder backgroundid(Long backgroundid) {
            this.backgroundid = backgroundid;
            return this;
        }

        public BackgroundBuilder hexcolor(String hexcolor) {
            this.hexcolor = hexcolor;
            return this;
        }

        public BackgroundBuilder imagepath(String imagepath) {
            this.imagepath = imagepath;
            return this;
        }

        public BackgroundBuilder isimage(Boolean isimage) {
            this.isimage = isimage != null ? isimage : false;
            return this;
        }

        public Background build() {
            return new Background(backgroundid, hexcolor, imagepath, isimage);
        }
    }
}
