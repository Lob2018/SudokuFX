/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
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

/**
 * Represents a Menu configuration with a unique ID and a mode.
 *
 * <p>Provides getters, setters, and a builder for fluent construction.
 */
@Entity
@Table(name = "menu")
public class Menu {

    /** Unique identifier of the menu (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuid", nullable = false)
    private Byte menuid;

    /** Mode of the menu (1–3). */
    @Min(1) @Max(3) @Column(name = "mode", nullable = false)
    private byte mode;

    /** Protected default constructor for JPA. */
    protected Menu() {}

    /**
     * Full constructor to initialize a menu with all fields.
     *
     * @param menuid the unique ID of the menu
     * @param mode the mode value (1–3)
     */
    public Menu(Byte menuid, byte mode) {
        this.menuid = menuid;
        this.mode = mode;
    }

    /** Returns the unique identifier of the menu. */
    public Byte getMenuid() {
        return menuid;
    }

    /** Returns the mode of the menu. */
    public byte getMode() {
        return mode;
    }

    /** Sets the mode of the menu. */
    public void setMode(byte mode) {
        this.mode = mode;
    }

    /** Creates a new {@link MenuBuilder} for fluent construction of Menu instances. */
    public static MenuBuilder builder() {
        return new MenuBuilder();
    }

    /**
     * Builder class for creating {@link Menu} instances.
     *
     * <p>Provides a fluent API to set all fields before constructing an instance of {@code Menu}.
     */
    public static class MenuBuilder {
        private Byte menuid;
        private byte mode;

        /** Sets the unique ID of the menu. */
        public MenuBuilder menuid(Byte menuid) {
            this.menuid = menuid;
            return this;
        }

        /** Sets the mode of the menu. */
        public MenuBuilder mode(byte mode) {
            this.mode = mode;
            return this;
        }

        /**
         * Builds a new {@link Menu} instance.
         *
         * @return a fully constructed Menu instance
         */
        public Menu build() {
            return new Menu(menuid, mode);
        }
    }

    /** Compares this menu with another object for equality based on all fields. */
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

    /** Computes the hash code based on all fields. */
    @Override
    public int hashCode() {
        return Objects.hash(menuid, mode);
    }

    /** Returns a string representation of the menu. */
    @Override
    public String toString() {
        return String.format("Menu{menuid=%s, mode=%d}", menuid, mode);
    }
}
