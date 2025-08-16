/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "gamelevel")
public class GameLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte levelid;

    @Min(1) @Max(3) private byte level;

    protected GameLevel() {}

    public GameLevel(Byte levelid, byte level) {
        this.levelid = levelid;
        this.level = level;
    }

    public Byte getLevelid() {
        return levelid;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public static GameLevelBuilder builder() {
        return new GameLevelBuilder();
    }

    /**
     * Builder class for creating instances of {@link GameLevel}.
     *
     * <p>Provides a fluent API to set all fields before constructing an instance of {@code
     * GameLevel}.
     */
    public static class GameLevelBuilder {
        private Byte levelid;
        private byte level;

        public GameLevelBuilder levelid(Byte levelid) {
            this.levelid = levelid;
            return this;
        }

        public GameLevelBuilder level(byte level) {
            this.level = level;
            return this;
        }

        /**
         * Builds a new {@link GameLevel} instance using the values previously set in the builder.
         *
         * @return a fully constructed {@code GameLevel} instance
         */
        public GameLevel build() {
            return new GameLevel(levelid, level);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameLevel other)) {
            return false;
        }
        return Objects.equals(levelid, other.levelid) && level == other.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelid, level);
    }

    @Override
    public String toString() {
        return String.format("GameLevel{levelid=%s, level=%d}", levelid, level);
    }
}
