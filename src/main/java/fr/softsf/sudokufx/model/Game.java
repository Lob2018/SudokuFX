/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a Sudoku game instance stored in the database.
 *
 * <p>Contains references to the {@link Grid}, {@link Player}, and {@link GameLevel}. Tracks
 * creation and update timestamps, selection state, and provides builders and validation methods.
 */
@Entity
@Table(name = "game")
public class Game {

    private static final String GRIDID_MUST_NOT_BE_NULL = "gridid must not be null";
    private static final String PLAYERID_MUST_NOT_BE_NULL = "playerid must not be null";
    private static final String LEVELID_MUST_NOT_BE_NULL = "levelid must not be null";
    private static final String CREATEDAT_MUST_NOT_BE_NULL = "createdat must not be null";
    private static final String UPDATEDAT_MUST_NOT_BE_NULL = "updatedat must not be null";

    /** Unique identifier of the game (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameid", nullable = false)
    private Long gameid;

    /** The grid associated with this game. */
    @Valid @OneToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "gridgridid")
    private Grid gridid;

    /** The player who owns this game. */
    @Valid @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerplayerid")
    private Player playerid;

    /** The difficulty level of this game. */
    @Valid @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "levellevelid")
    private GameLevel levelid;

    /** Whether this game is currently selected. */
    @Column(name = "isselected", nullable = false)
    private boolean isselected = false;

    /** Timestamp when this game was created. */
    @Nonnull
    @NotNull @Column(name = "createdat", nullable = false)
    private LocalDateTime createdat = LocalDateTime.now();

    /** Timestamp when this game was last updated. */
    @Nonnull
    @NotNull @Column(name = "updatedat", nullable = false)
    private LocalDateTime updatedat = LocalDateTime.now();

    /** Protected default constructor for JPA. */
    protected Game() {}

    /**
     * Full constructor initializing all fields of the game.
     *
     * @param gameid the unique game ID
     * @param gridid the grid associated with this game
     * @param playerid the player associated with this game
     * @param levelid the game level
     * @param isselected whether the game is selected
     * @param createdat creation timestamp
     * @param updatedat last update timestamp
     */
    public Game(
            Long gameid,
            @Nonnull @NotNull Grid gridid,
            @Nonnull @NotNull Player playerid,
            @Nonnull @NotNull GameLevel levelid,
            boolean isselected,
            @Nonnull @NotNull LocalDateTime createdat,
            @Nonnull @NotNull LocalDateTime updatedat) {
        this.gameid = gameid;
        this.gridid = validateGrid(gridid);
        this.playerid = validatePlayer(playerid);
        this.levelid = validateLevel(levelid);
        this.isselected = isselected;
        this.createdat = validateCreatedAt(createdat);
        this.updatedat = validateUpdatedAt(updatedat);
    }

    /** Ensures that the {@link Grid} is not null. */
    private static Grid validateGrid(Grid grid) {
        return Objects.requireNonNull(grid, GRIDID_MUST_NOT_BE_NULL);
    }

    /** Ensures that the {@link Player} is not null. */
    private static Player validatePlayer(Player player) {
        return Objects.requireNonNull(player, PLAYERID_MUST_NOT_BE_NULL);
    }

    /** Ensures that the {@link GameLevel} is not null. */
    private static GameLevel validateLevel(GameLevel level) {
        return Objects.requireNonNull(level, LEVELID_MUST_NOT_BE_NULL);
    }

    /** Ensures that the creation timestamp is not null. */
    private static LocalDateTime validateCreatedAt(LocalDateTime createdat) {
        return Objects.requireNonNull(createdat, CREATEDAT_MUST_NOT_BE_NULL);
    }

    /** Ensures that the update timestamp is not null. */
    private static LocalDateTime validateUpdatedAt(LocalDateTime updatedat) {
        return Objects.requireNonNull(updatedat, UPDATEDAT_MUST_NOT_BE_NULL);
    }

    /** Returns the unique ID of the game. */
    public Long getGameid() {
        return gameid;
    }

    /** Returns the {@link Grid} associated with this game. */
    @Nonnull
    public Grid getGridid() {
        return gridid;
    }

    /** Returns the {@link Player} associated with this game. */
    public Player getPlayerid() {
        return playerid;
    }

    /** Returns the {@link GameLevel} of this game. */
    @Nonnull
    public GameLevel getLevelid() {
        return levelid;
    }

    /** Returns whether the game is currently selected. */
    public boolean getIsselected() {
        return isselected;
    }

    /** Returns the creation timestamp. */
    @Nonnull
    public LocalDateTime getCreatedat() {
        return createdat;
    }

    /** Returns the last update timestamp. */
    @Nonnull
    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    /** Sets the player associated with this game. */
    public void setPlayerid(@Nonnull Player playerid) {
        this.playerid = validatePlayer(playerid);
    }

    /** Detaches this game from its current player. */
    public void detachFromPlayer() {
        this.playerid = null;
    }

    /** Sets the selection state of this game. */
    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    /** Updates the last modified timestamp. */
    public void setUpdatedat(@Nonnull LocalDateTime updatedat) {
        this.updatedat = validateUpdatedAt(updatedat);
    }

    /** Creates a new {@link GameBuilder} for fluent construction. */
    public static GameBuilder builder() {
        return new GameBuilder();
    }

    /** Builder class to create {@link Game} instances in a fluent style. */
    public static class GameBuilder {
        private Long gameid;
        private Grid gridid;
        private Player playerid;
        private GameLevel levelid;
        private boolean isselected = false;
        private LocalDateTime createdat = LocalDateTime.now();
        private LocalDateTime updatedat = LocalDateTime.now();

        /** Sets the game ID. */
        public GameBuilder gameid(Long gameid) {
            this.gameid = gameid;
            return this;
        }

        /** Sets the grid. */
        public GameBuilder gridid(@Nonnull Grid gridid) {
            this.gridid = validateGrid(gridid);
            return this;
        }

        /** Sets the player. */
        public GameBuilder playerid(@Nonnull Player playerid) {
            this.playerid = validatePlayer(playerid);
            return this;
        }

        /** Sets the game level. */
        public GameBuilder levelid(@Nonnull GameLevel levelid) {
            this.levelid = validateLevel(levelid);
            return this;
        }

        /** Sets whether the game is selected. */
        public GameBuilder isselected(boolean isselected) {
            this.isselected = isselected;
            return this;
        }

        /** Sets the creation timestamp. */
        public GameBuilder createdat(@Nonnull LocalDateTime createdat) {
            this.createdat = validateCreatedAt(createdat);
            return this;
        }

        /** Sets the last update timestamp. */
        public GameBuilder updatedat(@Nonnull LocalDateTime updatedat) {
            this.updatedat = validateUpdatedAt(updatedat);
            return this;
        }

        /** Builds a validated {@link Game} instance. */
        public Game build() {
            return new Game(gameid, gridid, playerid, levelid, isselected, createdat, updatedat);
        }
    }

    /** Compares this game with another object for equality based on all fields. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Game other) {
            return Objects.equals(gameid, other.gameid)
                    && Objects.equals(gridid, other.gridid)
                    && Objects.equals(playerid, other.playerid)
                    && Objects.equals(levelid, other.levelid)
                    && Objects.equals(isselected, other.isselected)
                    && Objects.equals(createdat, other.createdat)
                    && Objects.equals(updatedat, other.updatedat);
        }
        return false;
    }

    /** Computes the hash code based on all fields. */
    @Override
    public int hashCode() {
        return Objects.hash(gameid, gridid, playerid, levelid, isselected, createdat, updatedat);
    }

    /** Returns a string representation of the game. */
    @Override
    public String toString() {
        return String.format(
                "Game{gameid=%s, gridid=%s, playerid=%s, levelid=%s, isselected=%b, createdat=%s,"
                        + " updatedat=%s}",
                gameid,
                gridid.getGridid(),
                playerid.getPlayerid(),
                levelid.getLevelid(),
                isselected,
                createdat,
                updatedat);
    }
}
