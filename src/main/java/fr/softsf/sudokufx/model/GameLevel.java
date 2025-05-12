/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "gamelevel")
public class GameLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte levelid;

    @NotNull @Min(1) @Max(3) private Byte level;

    public GameLevel() {}

    public GameLevel(Byte levelid, Byte level) {
        this.levelid = levelid;
        this.level = level;
    }

    public Byte getLevelid() {
        return levelid;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public static GameLevelBuilder builder() {
        return new GameLevelBuilder();
    }

    public static class GameLevelBuilder {
        private Byte levelid;
        private Byte level;

        public GameLevelBuilder levelid(Byte levelid) {
            this.levelid = levelid;
            return this;
        }

        public GameLevelBuilder level(Byte level) {
            this.level = level;
            return this;
        }

        public GameLevel build() {
            return new GameLevel(levelid, level);
        }
    }
}
