/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuid", nullable = false)
    private Byte menuid;

    @Min(1) @Max(3) @Column(name = "mode", nullable = false)
    private byte mode;

    protected Menu() {}

    public Menu(Byte menuid, byte mode) {
        this.menuid = menuid;
        this.mode = mode;
    }

    public Byte getMenuid() {
        return menuid;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public static MenuBuilder builder() {
        return new MenuBuilder();
    }

    /**
     * Builder class for creating instances of {@link Menu}.
     *
     * <p>Provides a fluent API to set all fields before constructing an instance of {@code Menu}.
     * Validation occurs at build() to avoid exceptions during construction.
     */
    public static class MenuBuilder {
        private Byte menuid;
        private byte mode;

        public MenuBuilder menuid(Byte menuid) {
            this.menuid = menuid;
            return this;
        }

        public MenuBuilder mode(byte mode) {
            this.mode = mode;
            return this;
        }

        /**
         * Creates Menu instance with parameter validation.
         *
         * @return new validated Menu instance
         * @throws NullPointerException if required parameters are null
         */
        public Menu build() {
            return new Menu(menuid, mode);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Menu other) {
            return Objects.equals(menuid, other.menuid) && mode == other.mode;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuid, mode);
    }

    @Override
    public String toString() {
        return String.format("Menu{menuid=%s, mode=%d}", menuid, mode);
    }
}
