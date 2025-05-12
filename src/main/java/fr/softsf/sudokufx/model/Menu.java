/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
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
