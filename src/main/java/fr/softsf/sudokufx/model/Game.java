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

    private boolean isselected = false;

    @Nonnull @NotNull private LocalDateTime createdat = LocalDateTime.now();

    @Nonnull @NotNull private LocalDateTime updatedat = LocalDateTime.now();

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
        this.isselected = isselected;
        this.gridid = Objects.requireNonNull(gridid, GRIDID_MUST_NOT_BE_NULL);
        this.playerid = Objects.requireNonNull(playerid, PLAYERID_MUST_NOT_BE_NULL);
        this.levelid = Objects.requireNonNull(levelid, LEVELID_MUST_NOT_BE_NULL);
        this.createdat = Objects.requireNonNull(createdat, CREATEDAT_MUST_NOT_BE_NULL);
        this.updatedat = Objects.requireNonNull(updatedat, UPDATEDAT_MUST_NOT_BE_NULL);
    }

    public Long getGameid() {
        return gameid;
    }

    @Nonnull
    public Grid getGridid() {
        return gridid;
    }

    @Nonnull
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

    public void setPlayerid(Player playerid) {
        this.playerid = playerid;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public void setUpdatedat(@Nonnull LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static class GameBuilder {
        private Long gameid;
        private Grid gridid;
        private Player playerid;
        private GameLevel levelid;
        private boolean isselected = false;
        @Nonnull @NotNull private LocalDateTime createdat = LocalDateTime.now();
        @Nonnull @NotNull private LocalDateTime updatedat = LocalDateTime.now();

        public GameBuilder gameid(Long gameid) {
            this.gameid = gameid;
            return this;
        }

        public GameBuilder gridid(Grid gridid) {
            this.gridid = Objects.requireNonNull(gridid, GRIDID_MUST_NOT_BE_NULL);
            return this;
        }

        public GameBuilder playerid(Player playerid) {
            this.playerid = Objects.requireNonNull(playerid, PLAYERID_MUST_NOT_BE_NULL);
            return this;
        }

        public GameBuilder levelid(GameLevel levelid) {
            this.levelid = Objects.requireNonNull(levelid, LEVELID_MUST_NOT_BE_NULL);
            return this;
        }

        public GameBuilder isselected(boolean isselected) {
            this.isselected = isselected;
            return this;
        }

        public GameBuilder createdat(LocalDateTime createdat) {
            this.createdat = Objects.requireNonNull(createdat, CREATEDAT_MUST_NOT_BE_NULL);
            return this;
        }

        public GameBuilder updatedat(LocalDateTime updatedat) {
            this.updatedat = Objects.requireNonNull(updatedat, UPDATEDAT_MUST_NOT_BE_NULL);
            return this;
        }

        public Game build() {
            return new Game(gameid, gridid, playerid, levelid, isselected, createdat, updatedat);
        }
    }
}
