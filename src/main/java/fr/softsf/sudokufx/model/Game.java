/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameid;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "gridgridid")
    private Grid gridid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerplayerid")
    private Player playerid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "levellevelid")
    private GameLevel levelid;

    @NotNull private Boolean isselected = false;

    @NotNull private LocalDateTime createdat;

    @NotNull private LocalDateTime updatedat;

    public Game() {
        this.isselected = false;
    }

    public Game(
            Long gameid,
            Grid gridid,
            Player playerid,
            GameLevel levelid,
            Boolean isselected,
            LocalDateTime createdat,
            LocalDateTime updatedat) {
        this.gameid = gameid;
        this.gridid = gridid;
        this.playerid = playerid;
        this.levelid = levelid;
        this.isselected = isselected != null ? isselected : false;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public Long getGameid() {
        return gameid;
    }

    public Grid getGridid() {
        return gridid;
    }

    public Player getPlayerid() {
        return playerid;
    }

    public GameLevel getLevelid() {
        return levelid;
    }

    public Boolean getIsselected() {
        return isselected;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setPlayerid(Player playerid) {
        this.playerid = playerid;
    }

    public void setIsselected(Boolean isselected) {
        this.isselected = isselected;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
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
        private Boolean isselected = false;
        private LocalDateTime createdat;
        private LocalDateTime updatedat;

        public GameBuilder gameid(Long gameid) {
            this.gameid = gameid;
            return this;
        }

        public GameBuilder gridid(Grid gridid) {
            this.gridid = gridid;
            return this;
        }

        public GameBuilder playerid(Player playerid) {
            this.playerid = playerid;
            return this;
        }

        public GameBuilder levelid(GameLevel levelid) {
            this.levelid = levelid;
            return this;
        }

        public GameBuilder isselected(Boolean isselected) {
            this.isselected = isselected != null ? isselected : false;
            return this;
        }

        public GameBuilder createdat(LocalDateTime createdat) {
            this.createdat = createdat;
            return this;
        }

        public GameBuilder updatedat(LocalDateTime updatedat) {
            this.updatedat = updatedat;
            return this;
        }

        public Game build() {
            return new Game(gameid, gridid, playerid, levelid, isselected, createdat, updatedat);
        }
    }
}
