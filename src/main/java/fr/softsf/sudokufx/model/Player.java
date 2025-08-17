/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerlanguageplayerlanguageid")
    private PlayerLanguage playerlanguageid;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "backgroundbackgroundid")
    private Background backgroundid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "menumenuid")
    private Menu menuid;

    @OneToMany(mappedBy = "playerid", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private Set<Game> games;

    @NotNull @Size(max = 256) @Column(nullable = false, unique = true)
    private String name;

    @NotNull private Boolean isselected = false;

    @NotNull private LocalDateTime createdat;

    @NotNull private LocalDateTime updatedat;

    public Player() {
        this.games = new LinkedHashSet<>();
        this.isselected = false;
    }

    public Player(
            Long playerid,
            PlayerLanguage playerlanguageid,
            Background backgroundid,
            Menu menuid,
            Set<Game> games,
            String name,
            Boolean isselected,
            LocalDateTime createdat,
            LocalDateTime updatedat) {
        this.playerid = playerid;
        this.playerlanguageid = playerlanguageid;
        this.backgroundid = backgroundid;
        this.menuid = menuid;
        this.games = (games != null) ? games : new LinkedHashSet<>();
        this.name = name;
        this.isselected = isselected;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public Long getPlayerid() {
        return playerid;
    }

    public PlayerLanguage getPlayerlanguageid() {
        return playerlanguageid;
    }

    public Background getBackgroundid() {
        return backgroundid;
    }

    public Menu getMenuid() {
        return menuid;
    }

    public Set<Game> getGames() {
        return games;
    }

    public String getName() {
        return name;
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

    public void setPlayerlanguageid(PlayerLanguage playerlanguageid) {
        this.playerlanguageid = playerlanguageid;
    }

    public void setBackgroundid(Background backgroundid) {
        this.backgroundid = backgroundid;
    }

    public void setMenuid(Menu menuid) {
        this.menuid = menuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsselected(Boolean isselected) {
        this.isselected = isselected;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    public void addGame(Game game) {
        if (game != null) {
            if (games == null) {
                games = new LinkedHashSet<>();
            }
            games.add(game);
            game.setPlayerid(this);
        }
    }

    public void removeGame(Game game) {
        if (game != null && games != null) {
            games.remove(game);
            game.detachFromPlayer();
        }
    }

    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }

    public static class PlayerBuilder {
        private Long playerid;
        private PlayerLanguage playerlanguageid;
        private Background backgroundid;
        private Menu menuid;
        private Set<Game> games = new LinkedHashSet<>();
        private String name;
        private Boolean isselected = false;
        private LocalDateTime createdat;
        private LocalDateTime updatedat;

        public PlayerBuilder playerid(Long playerid) {
            this.playerid = playerid;
            return this;
        }

        public PlayerBuilder playerlanguageid(PlayerLanguage playerlanguageid) {
            this.playerlanguageid = playerlanguageid;
            return this;
        }

        public PlayerBuilder backgroundid(Background backgroundid) {
            this.backgroundid = backgroundid;
            return this;
        }

        public PlayerBuilder menuid(Menu menuid) {
            this.menuid = menuid;
            return this;
        }

        public PlayerBuilder games(Set<Game> games) {
            this.games = (games != null) ? games : new LinkedHashSet<>();
            return this;
        }

        public PlayerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlayerBuilder isselected(Boolean isselected) {
            this.isselected = isselected;
            return this;
        }

        public PlayerBuilder createdat(LocalDateTime createdat) {
            this.createdat = createdat;
            return this;
        }

        public PlayerBuilder updatedat(LocalDateTime updatedat) {
            this.updatedat = updatedat;
            return this;
        }

        public Player build() {
            return new Player(
                    playerid,
                    playerlanguageid,
                    backgroundid,
                    menuid,
                    games,
                    name,
                    isselected,
                    createdat,
                    updatedat);
        }
    }
}
