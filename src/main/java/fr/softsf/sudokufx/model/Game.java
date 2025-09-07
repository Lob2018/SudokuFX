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

@Entity
@Table(name = "game")
public class Game {

    private static final String GRIDID_MUST_NOT_BE_NULL = "gridid must not be null";
    private static final String PLAYERID_MUST_NOT_BE_NULL = "playerid must not be null";
    private static final String LEVELID_MUST_NOT_BE_NULL = "levelid must not be null";
    private static final String CREATEDAT_MUST_NOT_BE_NULL = "createdat must not be null";
    private static final String UPDATEDAT_MUST_NOT_BE_NULL = "updatedat must not be null";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameid", nullable = false)
    private Long gameid;

    @Valid @OneToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "gridgridid")
    private Grid gridid;

    @Valid @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerplayerid")
    private Player playerid;

    @Valid @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "levellevelid")
    private GameLevel levelid;

    @Column(name = "isselected", nullable = false)
    private boolean isselected = false;

    @Nonnull
    @NotNull @Column(name = "createdat", nullable = false)
    private LocalDateTime createdat = LocalDateTime.now();

    @Nonnull
    @NotNull @Column(name = "updatedat", nullable = false)
    private LocalDateTime updatedat = LocalDateTime.now();

    protected Game() {}

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

    /**
     * Validates that the given {@link Grid} is not null.
     *
     * @param grid the grid to validate
     * @return the validated grid (not null)
     * @throws NullPointerException if the grid is null
     */
    private static Grid validateGrid(Grid grid) {
        return Objects.requireNonNull(grid, GRIDID_MUST_NOT_BE_NULL);
    }

    /**
     * Validates that the given {@link Player} is not null.
     *
     * @param player the player to validate
     * @return the validated player (not null)
     * @throws NullPointerException if the player is null
     */
    private static Player validatePlayer(Player player) {
        return Objects.requireNonNull(player, PLAYERID_MUST_NOT_BE_NULL);
    }

    /**
     * Validates that the given {@link GameLevel} is not null.
     *
     * @param level the game level to validate
     * @return the validated level (not null)
     * @throws NullPointerException if the level is null
     */
    private static GameLevel validateLevel(GameLevel level) {
        return Objects.requireNonNull(level, LEVELID_MUST_NOT_BE_NULL);
    }

    /**
     * Validates that the given creation date is not null.
     *
     * @param createdat the creation date to validate
     * @return the validated creation date (not null)
     * @throws NullPointerException if the creation date is null
     */
    private static LocalDateTime validateCreatedAt(LocalDateTime createdat) {
        return Objects.requireNonNull(createdat, CREATEDAT_MUST_NOT_BE_NULL);
    }

    /**
     * Validates that the given update date is not null.
     *
     * @param updatedat the update date to validate
     * @return the validated update date (not null)
     * @throws NullPointerException if the update date is null
     */
    private static LocalDateTime validateUpdatedAt(LocalDateTime updatedat) {
        return Objects.requireNonNull(updatedat, UPDATEDAT_MUST_NOT_BE_NULL);
    }

    public Long getGameid() {
        return gameid;
    }

    @Nonnull
    public Grid getGridid() {
        return gridid;
    }

    /**
     * Gets the player associated with this game.
     *
     * @return the player, or null if detached
     */
    public Player getPlayerid() {
        return playerid;
    }

    @Nonnull
    public GameLevel getLevelid() {
        return levelid;
    }

    public boolean getIsselected() {
        return isselected;
    }

    @Nonnull
    public LocalDateTime getCreatedat() {
        return createdat;
    }

    @Nonnull
    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setPlayerid(@Nonnull Player playerid) {
        this.playerid = validatePlayer(playerid);
    }

    /**
     * Detaches this game from its current player. Used when removing game from player's collection.
     */
    public void detachFromPlayer() {
        this.playerid = null;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public void setUpdatedat(@Nonnull LocalDateTime updatedat) {
        this.updatedat = validateUpdatedAt(updatedat);
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    /**
     * Builder class for creating instances of {@link Game}.
     *
     * <p>Provides a fluent API to set all fields before constructing an instance of {@code Game}.
     * All non-nullable fields are validated.
     */
    public static class GameBuilder {
        private Long gameid;
        private Grid gridid;
        private Player playerid;
        private GameLevel levelid;
        private boolean isselected = false;
        private LocalDateTime createdat = LocalDateTime.now();
        private LocalDateTime updatedat = LocalDateTime.now();

        public GameBuilder gameid(Long gameid) {
            this.gameid = gameid;
            return this;
        }

        public GameBuilder gridid(@Nonnull Grid gridid) {
            this.gridid = validateGrid(gridid);
            return this;
        }

        public GameBuilder playerid(@Nonnull Player playerid) {
            this.playerid = validatePlayer(playerid);
            return this;
        }

        public GameBuilder levelid(@Nonnull GameLevel levelid) {
            this.levelid = validateLevel(levelid);
            return this;
        }

        public GameBuilder isselected(boolean isselected) {
            this.isselected = isselected;
            return this;
        }

        public GameBuilder createdat(@Nonnull LocalDateTime createdat) {
            this.createdat = validateCreatedAt(createdat);
            return this;
        }

        public GameBuilder updatedat(@Nonnull LocalDateTime updatedat) {
            this.updatedat = validateUpdatedAt(updatedat);
            return this;
        }

        /**
         * Builds a new {@link Game} instance using the values previously set in the builder.
         *
         * <p>All required fields are validated and must not be null.
         *
         * @return a fully constructed {@code Game} instance
         * @throws NullPointerException if any required field is null
         */
        public Game build() {
            return new Game(gameid, gridid, playerid, levelid, isselected, createdat, updatedat);
        }
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(gameid, gridid, playerid, levelid, isselected, createdat, updatedat);
    }

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
