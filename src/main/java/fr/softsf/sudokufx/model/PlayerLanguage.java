package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "playerlanguage")
public class PlayerLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerlanguageid;

    @NotNull
    @Size(max = 2)
    private String iso;

    public PlayerLanguage() {
    }

    public PlayerLanguage(Long playerlanguageid, String iso) {
        this.playerlanguageid = playerlanguageid;
        this.iso = iso;
    }

    public Long getPlayerlanguageid() {
        return playerlanguageid;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public static PlayerLanguageBuilder builder() {
        return new PlayerLanguageBuilder();
    }

    public static class PlayerLanguageBuilder {
        private Long playerlanguageid;
        private String iso;

        public PlayerLanguageBuilder playerlanguageid(Long playerlanguageid) {
            this.playerlanguageid = playerlanguageid;
            return this;
        }

        public PlayerLanguageBuilder iso(String iso) {
            this.iso = iso;
            return this;
        }

        public PlayerLanguage build() {
            return new PlayerLanguage(playerlanguageid, iso);
        }
    }
}

