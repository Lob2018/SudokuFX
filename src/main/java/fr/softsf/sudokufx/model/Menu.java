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

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte menuid;

    @NotNull @Min(1) @Max(3) private Byte mode;

    public Menu() {}

    public Menu(Byte menuid, Byte mode) {
        this.menuid = menuid;
        this.mode = mode;
    }

    public Byte getMenuid() {
        return menuid;
    }

    public Byte getMode() {
        return mode;
    }

    public void setMode(Byte mode) {
        this.mode = mode;
    }

    public static MenuBuilder builder() {
        return new MenuBuilder();
    }

    public static class MenuBuilder {
        private Byte menuid;
        private Byte mode;

        public MenuBuilder menuid(Byte menuid) {
            this.menuid = menuid;
            return this;
        }

        public MenuBuilder mode(Byte mode) {
            this.mode = mode;
            return this;
        }

        public Menu build() {
            return new Menu(menuid, mode);
        }
    }
}
