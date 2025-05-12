/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "software")
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long softwareid;

    @NotNull @Size(max = 128) private String currentversion;

    @NotNull @Size(max = 128) private String lastversion;

    @NotNull private LocalDateTime createdat;

    @NotNull private LocalDateTime updatedat;

    public Software() {}

    public Software(
            Long softwareid,
            String currentversion,
            String lastversion,
            LocalDateTime createdat,
            LocalDateTime updatedat) {
        this.softwareid = softwareid;
        this.currentversion = currentversion;
        this.lastversion = lastversion;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public Long getSoftwareid() {
        return softwareid;
    }

    public String getCurrentversion() {
        return currentversion;
    }

    public String getLastversion() {
        return lastversion;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setCurrentversion(String currentversion) {
        this.currentversion = currentversion;
    }

    public void setLastversion(String lastversion) {
        this.lastversion = lastversion;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public static SoftwareBuilder builder() {
        return new SoftwareBuilder();
    }

    public static class SoftwareBuilder {
        private Long softwareid;
        private String currentversion;
        private String lastversion;
        private LocalDateTime createdat;
        private LocalDateTime updatedat;

        public SoftwareBuilder softwareid(Long softwareid) {
            this.softwareid = softwareid;
            return this;
        }

        public SoftwareBuilder currentversion(String currentversion) {
            this.currentversion = currentversion;
            return this;
        }

        public SoftwareBuilder lastversion(String lastversion) {
            this.lastversion = lastversion;
            return this;
        }

        public SoftwareBuilder createdat(LocalDateTime createdat) {
            this.createdat = createdat;
            return this;
        }

        public SoftwareBuilder updatedat(LocalDateTime updatedat) {
            this.updatedat = updatedat;
            return this;
        }

        public Software build() {
            return new Software(softwareid, currentversion, lastversion, createdat, updatedat);
        }
    }
}
