/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "playerlanguage")
public class PlayerLanguage {

    private static final String DEFAULT_ISO = "FR";
    private static final String ISO_MUST_NOT_BE_NULL = "iso must not be null";
    private static final String ISO_INVALID_VALUE = "iso must be either 'FR' or 'EN'";
    private static final java.util.regex.Pattern VALID_ISO_REGEX =
            java.util.regex.Pattern.compile("^(FR|EN)$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerlanguageid;

    @Nonnull
    @NotNull @Column(nullable = false, length = 2)
    @Pattern(regexp = "^(FR|EN)$", message = ISO_INVALID_VALUE)
    private String iso = DEFAULT_ISO;

    protected PlayerLanguage() {}

    public PlayerLanguage(Long playerlanguageid, @Nonnull @NotNull String iso) {
        this.playerlanguageid = playerlanguageid;
        this.iso = validateIso(iso);
    }

    /**
     * Validates that the iso is not null and matches allowed values (FR or EN).
     *
     * @param iso the iso to validate
     * @return the validated iso
     * @throws NullPointerException if the iso is null
     * @throws IllegalArgumentException if the iso is not FR or EN
     */
    private static String validateIso(String iso) {
        Objects.requireNonNull(iso, ISO_MUST_NOT_BE_NULL);
        if (!VALID_ISO_REGEX.matcher(iso).matches()) {
            throw new IllegalArgumentException(ISO_INVALID_VALUE);
        }
        return iso;
    }

    public Long getPlayerlanguageid() {
        return playerlanguageid;
    }

    @Nonnull
    public String getIso() {
        return iso;
    }

    public void setIso(@Nonnull String iso) {
        this.iso = validateIso(iso);
    }

    public static PlayerLanguageBuilder builder() {
        return new PlayerLanguageBuilder();
    }

    /**
     * Builder class for creating instances of {@link PlayerLanguage}.
     *
     * <p>Provides a fluent API to set all fields before constructing an instance of {@code
     * PlayerLanguage}. Validation occurs at build() to avoid exceptions during construction.
     */
    public static class PlayerLanguageBuilder {
        private Long playerlanguageid;
        private String iso = DEFAULT_ISO;

        public PlayerLanguageBuilder playerlanguageid(Long playerlanguageid) {
            this.playerlanguageid = playerlanguageid;
            return this;
        }

        public PlayerLanguageBuilder iso(@Nonnull String iso) {
            this.iso = iso;
            return this;
        }

        /**
         * Creates PlayerLanguage instance with parameter validation.
         *
         * @return new validated PlayerLanguage instance
         * @throws NullPointerException if required parameters are null
         * @throws IllegalArgumentException if iso is not FR or EN
         */
        public PlayerLanguage build() {
            return new PlayerLanguage(playerlanguageid, iso);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlayerLanguage other)) {
            return false;
        }
        return Objects.equals(playerlanguageid, other.playerlanguageid)
                && Objects.equals(iso, other.iso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerlanguageid, iso);
    }

    @Override
    public String toString() {
        return String.format(
                "PlayerLanguage{playerlanguageid=%s, iso='%s'}", playerlanguageid, iso);
    }
}
