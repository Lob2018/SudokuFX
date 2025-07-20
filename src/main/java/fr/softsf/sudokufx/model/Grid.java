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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "grid")
public class Grid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gridid;

    @NotNull @Size(max = 81) private String defaultgridvalue;

    @NotNull @Size(max = 810) private String gridvalue;

    @NotNull @Min(0) @Max(100) private Byte possibilities;

    public Grid() {}

    public Grid(Long gridid, String defaultgridvalue, String gridvalue, Byte possibilities) {
        this.gridid = gridid;
        this.defaultgridvalue = defaultgridvalue;
        this.gridvalue = gridvalue;
        this.possibilities = possibilities;
    }

    public Long getGridid() {
        return gridid;
    }

    public String getDefaultgridvalue() {
        return defaultgridvalue;
    }

    public String getGridvalue() {
        return gridvalue;
    }

    public Byte getPossibilities() {
        return possibilities;
    }

    public void setDefaultgridvalue(String defaultgridvalue) {
        this.defaultgridvalue = defaultgridvalue;
    }

    public void setGridvalue(String gridvalue) {
        this.gridvalue = gridvalue;
    }

    public void setPossibilities(Byte possibilities) {
        this.possibilities = possibilities;
    }

    public static GridBuilder builder() {
        return new GridBuilder();
    }

    public static class GridBuilder {
        private Long gridid;
        private String defaultgridvalue;
        private String gridvalue;
        private Byte possibilities;

        public GridBuilder gridid(Long gridid) {
            this.gridid = gridid;
            return this;
        }

        public GridBuilder defaultgridvalue(String defaultgridvalue) {
            this.defaultgridvalue = defaultgridvalue;
            return this;
        }

        public GridBuilder gridvalue(String gridvalue) {
            this.gridvalue = gridvalue;
            return this;
        }

        public GridBuilder possibilities(Byte possibilities) {
            this.possibilities = possibilities;
            return this;
        }

        public Grid build() {
            return new Grid(gridid, defaultgridvalue, gridvalue, possibilities);
        }
    }
}
