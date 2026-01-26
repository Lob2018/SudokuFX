/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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

/**
 * Represents a player's language in the SudokuFX application.
 *
 * <p>Each language is identified by a unique ID and a two-letter ISO code ("FR" or "EN"). Provides
 * fluent builder, validation, and standard object methods.
 */
@Entity
@Table(name = "playerlanguage")
public class PlayerLanguage {

    private static final String DEFAULT_ISO = "FR";
    private static final String ISO_MUST_NOT_BE_NULL = "iso must not be null";
    private static final String ISO_INVALID_VALUE = "iso must be either 'FR' or 'EN'";
    private static final java.util.regex.Pattern VALID_ISO_REGEX =
            java.util.regex.Pattern.compile("^(FR|EN)$");

    /** Unique identifier of the player language (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerlanguageid", nullable = false)
    private Long playerlanguageid;

    /** ISO code for the language ("FR" or "EN"). */
    @Nonnull
    @NotNull @Column(nullable = false, length = 2)
    @Pattern(regexp = "^(FR|EN)$", message = ISO_INVALID_VALUE)
    private String iso = DEFAULT_ISO;

    /** Protected default constructor for JPA. */
    protected PlayerLanguage() {}

    /**
     * Full constructor to initialize all fields of PlayerLanguage.
     *
     * @param playerlanguageid unique ID of the language
     * @param iso two-letter ISO code ("FR" or "EN")
     */
    public PlayerLanguage(Long playerlanguageid, @Nonnull @NotNull String iso) {
        this.playerlanguageid = playerlanguageid;
        this.iso = validateIso(iso);
    }

    /**
     * Validates that the iso is not null and matches allowed values (FR or EN).
     *
     * @param iso the iso to validate
     * @return validated iso
     * @throws NullPointerException if iso is null
     * @throws IllegalArgumentException if iso is not FR or EN
     */
    private static String validateIso(String iso) {
        Objects.requireNonNull(iso, ISO_MUST_NOT_BE_NULL);
        if (VALID_ISO_REGEX.matcher(iso).matches()) {
            return iso;
        }
        throw new IllegalArgumentException(ISO_INVALID_VALUE);
    }

    /** Returns the unique ID of this PlayerLanguage instance. */
    public Long getPlayerlanguageid() {
        return playerlanguageid;
    }

    /** Returns the ISO code of this PlayerLanguage. */
    @Nonnull
    public String getIso() {
        return iso;
    }

    /** Sets the ISO code after validation. */
    public void setIso(@Nonnull String iso) {
        this.iso = validateIso(iso);
    }

    /** Returns a new {@link PlayerLanguageBuilder} instance for fluent construction. */
    public static PlayerLanguageBuilder builder() {
        return new PlayerLanguageBuilder();
    }

    /**
     * Builder class for creating {@link PlayerLanguage} instances with fluent API.
     *
     * <p>Allows setting all fields before constructing a validated PlayerLanguage object.
     */
    public static class PlayerLanguageBuilder {
        private Long playerlanguageid;
        private String iso = DEFAULT_ISO;

        /** Sets the unique ID of the PlayerLanguage instance. */
        public PlayerLanguageBuilder playerlanguageid(Long playerlanguageid) {
            this.playerlanguageid = playerlanguageid;
            return this;
        }

        /** Sets the ISO code of the PlayerLanguage. */
        public PlayerLanguageBuilder iso(@Nonnull String iso) {
            this.iso = iso;
            return this;
        }

        /**
         * Builds a validated {@link PlayerLanguage} instance.
         *
         * @return a fully constructed PlayerLanguage instance
         * @throws NullPointerException if iso is null
         * @throws IllegalArgumentException if iso is not FR or EN
         */
        public PlayerLanguage build() {
            return new PlayerLanguage(playerlanguageid, iso);
        }
    }

    /** Compares two PlayerLanguage objects for equality based on all fields. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PlayerLanguage other) {
            return Objects.equals(playerlanguageid, other.playerlanguageid)
                    && Objects.equals(iso, other.iso);
        }
        return false;
    }

    /** Computes the hash code based on all fields. */
    @Override
    public int hashCode() {
        return Objects.hash(playerlanguageid, iso);
    }

    /** Returns a string representation of the PlayerLanguage object. */
    @Override
    public String toString() {
        return String.format(
                "PlayerLanguage{playerlanguageid=%s, iso='%s'}", playerlanguageid, iso);
    }
}
