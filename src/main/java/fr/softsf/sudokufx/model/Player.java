/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a player in the SudokuFX application.
 *
 * <p>Includes the player's language, options, menu, games, name, selection status, and timestamps.
 * Provides fluent builder, validation, and standard object methods.
 */
@Entity
@Table(name = "player")
public class Player {

    private static final String PLAYERLANGUAGEID_MUST_NOT_BE_NULL =
            "playerlanguageid must not be null";
    private static final String OPTIONSID_MUST_NOT_BE_NULL = "optionsid must not be null";
    private static final String MENU_MUST_NOT_BE_NULL = "menu must not be null";
    private static final String NAME_MUST_NOT_BE_NULL = "name must not be null";
    private static final String CREATEDAT_MUST_NOT_BE_NULL = "createdat must not be null";
    private static final String UPDATEDAT_MUST_NOT_BE_NULL = "updatedat must not be null";

    /** Unique identifier of the player (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerid", nullable = false)
    private Long playerid;

    /** Language of the player. */
    @NotNull @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerlanguageplayerlanguageid", nullable = false)
    private PlayerLanguage playerlanguageid;

    /** Options associated with the player. */
    @NotNull @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "optionsoptionsid", nullable = false)
    private Options optionsid;

    /** Menu associated with the player. */
    @NotNull @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "menumenuid", nullable = false)
    private Menu menuid;

    /** Games played by the player. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerid", orphanRemoval = true)
    private Set<Game> games = new LinkedHashSet<>();

    /** Name of the player. */
    @Nonnull
    @NotNull @Size(max = 256) @Column(name = "name", nullable = false, unique = true, length = 256)
    private String name = "";

    /** Flag indicating whether this player is selected. */
    @Column(name = "selected", nullable = false)
    private boolean selected = false;

    /** Creation timestamp of the player. */
    @Nonnull
    @NotNull @Column(name = "createdat", nullable = false)
    private Instant createdat = Instant.now();

    /** Last update timestamp of the player. */
    @Nonnull
    @NotNull @Column(name = "updatedat", nullable = false)
    private Instant updatedat = Instant.now();

    /** Protected default constructor for JPA. */
    protected Player() {}

    /**
     * Full constructor to initialize all fields of Player.
     *
     * @param playerid unique ID
     * @param playerlanguageid language of the player
     * @param optionsid player's options
     * @param menuid player's menu
     * @param games games associated with the player
     * @param name name of the player
     * @param selected whether the player is selected
     * @param createdat creation timestamp
     * @param updatedat update timestamp
     */
    public Player(
            Long playerid,
            @Nonnull @NotNull PlayerLanguage playerlanguageid,
            @Nonnull @NotNull Options optionsid,
            @Nonnull @NotNull Menu menuid,
            Set<Game> games,
            @Nonnull @NotNull String name,
            boolean selected,
            @Nonnull @NotNull Instant createdat,
            @Nonnull @NotNull Instant updatedat) {
        this.playerid = playerid;
        this.playerlanguageid = validatePlayerLanguage(playerlanguageid);
        this.optionsid = validateOptions(optionsid);
        this.menuid = validateMenu(menuid);
        this.games = (games != null) ? games : new LinkedHashSet<>();
        this.name = validateName(name);
        this.selected = selected;
        this.createdat = validateCreatedAt(createdat);
        this.updatedat = validateUpdatedAt(updatedat);
    }

    /** Validates that the player language is not null. */
    private static PlayerLanguage validatePlayerLanguage(PlayerLanguage playerlanguageid) {
        return Objects.requireNonNull(playerlanguageid, PLAYERLANGUAGEID_MUST_NOT_BE_NULL);
    }

    /** Validates that the options are not null. */
    private static Options validateOptions(Options optionsid) {
        return Objects.requireNonNull(optionsid, OPTIONSID_MUST_NOT_BE_NULL);
    }

    /** Validates that the menu is not null. */
    private static Menu validateMenu(Menu menuid) {
        return Objects.requireNonNull(menuid, MENU_MUST_NOT_BE_NULL);
    }

    /** Validates that the name is not null. */
    private static String validateName(String name) {
        return Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);
    }

    /** Validates that the creation date is not null. */
    private static Instant validateCreatedAt(Instant createdat) {
        return Objects.requireNonNull(createdat, CREATEDAT_MUST_NOT_BE_NULL);
    }

    /** Validates that the update date is not null. */
    private static Instant validateUpdatedAt(Instant updatedat) {
        return Objects.requireNonNull(updatedat, UPDATEDAT_MUST_NOT_BE_NULL);
    }

    /** Returns the unique ID of the player. */
    public Long getPlayerid() {
        return playerid;
    }

    /** Returns the language of the player. */
    public PlayerLanguage getPlayerlanguageid() {
        return playerlanguageid;
    }

    /** Returns the options associated with the player. */
    public Options getOptionsid() {
        return optionsid;
    }

    /** Returns the menu associated with the player. */
    public Menu getMenuid() {
        return menuid;
    }

    /** Returns the games played by the player. */
    public Set<Game> getGames() {
        return games;
    }

    /** Returns the name of the player. */
    public String getName() {
        return name;
    }

    /** Returns whether the player is selected. */
    public boolean getSelected() {
        return selected;
    }

    /** Returns the creation timestamp. */
    public Instant getCreatedat() {
        return createdat;
    }

    /** Returns the update timestamp. */
    public Instant getUpdatedat() {
        return updatedat;
    }

    /** Sets the language after validation. */
    public void setPlayerlanguageid(@Nonnull PlayerLanguage playerlanguageid) {
        this.playerlanguageid = validatePlayerLanguage(playerlanguageid);
    }

    /** Sets the options after validation. */
    public void setOptionsid(@Nonnull Options optionsid) {
        this.optionsid = validateOptions(optionsid);
    }

    /** Sets the menu after validation. */
    public void setMenuid(@Nonnull Menu menuid) {
        this.menuid = validateMenu(menuid);
    }

    /** Sets the games. */
    public void setGames(Set<Game> games) {
        this.games = (games != null) ? games : new LinkedHashSet<>();
    }

    /** Sets the name after validation. */
    public void setName(@Nonnull String name) {
        this.name = validateName(name);
    }

    /** Sets the selection flag. */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /** Sets the update timestamp after validation. */
    public void setUpdatedat(@Nonnull Instant updatedat) {
        this.updatedat = validateUpdatedAt(updatedat);
    }

    /** Returns a new {@link PlayerBuilder} instance for fluent construction. */
    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }

    /**
     * Builder class for creating {@link Player} instances with fluent API.
     *
     * <p>Allows setting all fields before constructing a validated Player object.
     */
    public static class PlayerBuilder {
        private Long playerid;
        private PlayerLanguage playerlanguageid;
        private Options optionsid;
        private Menu menuid;
        private Set<Game> games = new LinkedHashSet<>();
        private String name;
        private boolean selected = false;
        private Instant createdat = Instant.now();
        private Instant updatedat = Instant.now();

        /** Sets the unique ID of the player. */
        public PlayerBuilder playerid(Long playerid) {
            this.playerid = playerid;
            return this;
        }

        /** Sets the language of the player. */
        public PlayerBuilder playerlanguageid(@Nonnull PlayerLanguage playerlanguageid) {
            this.playerlanguageid = validatePlayerLanguage(playerlanguageid);
            return this;
        }

        /** Sets the options of the player. */
        public PlayerBuilder optionsid(@Nonnull Options optionsid) {
            this.optionsid = validateOptions(optionsid);
            return this;
        }

        /** Sets the menu of the player. */
        public PlayerBuilder menuid(@Nonnull Menu menuid) {
            this.menuid = validateMenu(menuid);
            return this;
        }

        /** Sets the games played by the player. */
        public PlayerBuilder games(Set<Game> games) {
            this.games = (games != null) ? games : new LinkedHashSet<>();
            return this;
        }

        /** Sets the name of the player. */
        public PlayerBuilder name(@Nonnull String name) {
            this.name = validateName(name);
            return this;
        }

        /** Sets the selection flag. */
        public PlayerBuilder selected(boolean selected) {
            this.selected = selected;
            return this;
        }

        /** Sets the creation timestamp. */
        public PlayerBuilder createdat(@Nonnull Instant createdat) {
            this.createdat = validateCreatedAt(createdat);
            return this;
        }

        /** Sets the update timestamp. */
        public PlayerBuilder updatedat(@Nonnull Instant updatedat) {
            this.updatedat = validateUpdatedAt(updatedat);
            return this;
        }

        /**
         * Builds a validated {@link Player} instance.
         *
         * @return a fully constructed Player instance
         * @throws NullPointerException if required fields are null
         */
        public Player build() {
            return new Player(
                    playerid,
                    playerlanguageid,
                    optionsid,
                    menuid,
                    games,
                    name,
                    selected,
                    createdat,
                    updatedat);
        }
    }

    /** Compares two Player objects for equality based on all fields. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Player other) {
            return Objects.equals(playerid, other.playerid)
                    && Objects.equals(playerlanguageid, other.playerlanguageid)
                    && Objects.equals(optionsid, other.optionsid)
                    && Objects.equals(menuid, other.menuid)
                    && Objects.equals(name, other.name)
                    && Objects.equals(selected, other.selected)
                    && Objects.equals(createdat, other.createdat)
                    && Objects.equals(updatedat, other.updatedat);
        }
        return false;
    }

    /** Computes the hash code based on all fields. */
    @Override
    public int hashCode() {
        return Objects.hash(
                playerid,
                playerlanguageid,
                optionsid,
                menuid,
                name,
                selected,
                createdat,
                updatedat);
    }

    /** Returns a string representation of the Player object. */
    @Override
    public String toString() {
        return String.format(
                "Player{playerid=%s, playerlanguageid=%s, optionsid=%s, menuid=%s, name=%s,"
                        + " selected=%b, createdat=%s, updatedat=%s}",
                playerid,
                playerlanguageid.getPlayerlanguageid(),
                optionsid.getOptionsid(),
                menuid.getMenuid(),
                name,
                selected,
                createdat,
                updatedat);
    }
}
