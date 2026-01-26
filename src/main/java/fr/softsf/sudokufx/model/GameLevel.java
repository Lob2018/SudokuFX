/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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
 * Represents a difficulty level for a Sudoku game.
 *
 * <p>Levels are constrained to values 1 to 3. Provides builders, getters, and setters.
 */
@Entity
@Table(name = "gamelevel")
public class GameLevel {

    /** Unique identifier of the game level (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "levelid", nullable = false)
    private Byte levelid;

    /** Difficulty level of the game, between 1 and 3. */
    @Min(1) @Max(3) @Column(name = "level", nullable = false)
    private byte level;

    /** Protected default constructor for JPA. */
    protected GameLevel() {}

    /**
     * Full constructor to initialize a game level.
     *
     * @param levelid the unique ID of the level
     * @param level the difficulty value of the level (1–3)
     */
    public GameLevel(Byte levelid, byte level) {
        this.levelid = levelid;
        this.level = level;
    }

    /** Returns the unique identifier of this game level. */
    public Byte getLevelid() {
        return levelid;
    }

    /** Returns the difficulty value of this game level. */
    public byte getLevel() {
        return level;
    }

    /** Sets the difficulty value of this game level. */
    public void setLevel(byte level) {
        this.level = level;
    }

    /** Creates a new {@link GameLevelBuilder} for fluent construction. */
    public static GameLevelBuilder builder() {
        return new GameLevelBuilder();
    }

    /**
     * Builder class for creating {@link GameLevel} instances.
     *
     * <p>Provides a fluent API to set fields before constructing a {@code GameLevel}.
     */
    public static class GameLevelBuilder {
        private Byte levelid;
        private byte level;

        /** Sets the unique ID for the game level. */
        public GameLevelBuilder levelid(Byte levelid) {
            this.levelid = levelid;
            return this;
        }

        /** Sets the difficulty value for the game level. */
        public GameLevelBuilder level(byte level) {
            this.level = level;
            return this;
        }

        /** Builds a validated {@link GameLevel} instance. */
        public GameLevel build() {
            return new GameLevel(levelid, level);
        }
    }

    /** Compares this game level with another object for equality based on all fields. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GameLevel other) {
            return Objects.equals(levelid, other.levelid) && level == other.level;
        }
        return false;
    }

    /** Computes the hash code based on all fields. */
    @Override
    public int hashCode() {
        return Objects.hash(levelid, level);
    }

    /** Returns a string representation of the game level. */
    @Override
    public String toString() {
        return String.format("GameLevel{levelid=%d, level=%d}", levelid, level);
    }
}
