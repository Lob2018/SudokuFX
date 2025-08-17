/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "player")
public class Player {

    private static final String PLAYERLANGUAGEID_MUST_NOT_BE_NULL =
            "playerlanguageid must not be null";
    private static final String BACKGROUNID_MUST_NOT_BE_NULL = "backgroundid must not be null";
    private static final String MENU_MUST_NOT_BE_NULL = "menu must not be null";
    private static final String NAME_MUST_NOT_BE_NULL = "name must not be null";
    private static final String CREATEDAT_MUST_NOT_BE_NULL = "createdat must not be null";
    private static final String UPDATEDAT_MUST_NOT_BE_NULL = "updatedat must not be null";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerid", nullable = false)
    private Long playerid;

    @NotNull @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerlanguageplayerlanguageid", nullable = false)
    private PlayerLanguage playerlanguageid;

    @NotNull @OneToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "backgroundbackgroundid", nullable = false)
    private Background backgroundid;

    @NotNull @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "menumenuid", nullable = false)
    private Menu menuid;

    @OneToMany(mappedBy = "playerid", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private Set<Game> games = new LinkedHashSet<>();

    @Nonnull
    @NotNull @Size(max = 256) @Column(name = "name", nullable = false, unique = true, length = 256)
    private String name = "";

    @Column(name = "isselected", nullable = false)
    private boolean isselected = false;

    @Nonnull
    @NotNull @Column(name = "createdat", nullable = false)
    private LocalDateTime createdat = LocalDateTime.now();

    @Nonnull
    @NotNull @Column(name = "updatedat", nullable = false)
    private LocalDateTime updatedat = LocalDateTime.now();

    protected Player() {}

    public Player(
            Long playerid,
            @Nonnull @NotNull PlayerLanguage playerlanguageid,
            @Nonnull @NotNull Background backgroundid,
            @Nonnull @NotNull Menu menuid,
            Set<Game> games,
            @Nonnull @NotNull String name,
            boolean isselected,
            @Nonnull @NotNull LocalDateTime createdat,
            @Nonnull @NotNull LocalDateTime updatedat) {
        this.playerid = playerid;
        this.playerlanguageid = validatePlayerLanguage(playerlanguageid);
        this.backgroundid = validateBackground(backgroundid);
        this.menuid = validateMenu(menuid);
        this.games = (games != null) ? games : new LinkedHashSet<>();
        this.name = validateName(name);
        this.isselected = isselected;
        this.createdat = validateCreatedAt(createdat);
        this.updatedat = validateUpdatedAt(updatedat);
    }

    private static PlayerLanguage validatePlayerLanguage(PlayerLanguage playerlanguageid) {
        return Objects.requireNonNull(playerlanguageid, PLAYERLANGUAGEID_MUST_NOT_BE_NULL);
    }

    private static Background validateBackground(Background backgroundid) {
        return Objects.requireNonNull(backgroundid, BACKGROUNID_MUST_NOT_BE_NULL);
    }

    private static Menu validateMenu(Menu menuid) {
        return Objects.requireNonNull(menuid, MENU_MUST_NOT_BE_NULL);
    }

    private static String validateName(String name) {
        return Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);
    }

    private static LocalDateTime validateCreatedAt(LocalDateTime createdat) {
        return Objects.requireNonNull(createdat, CREATEDAT_MUST_NOT_BE_NULL);
    }

    private static LocalDateTime validateUpdatedAt(LocalDateTime updatedat) {
        return Objects.requireNonNull(updatedat, UPDATEDAT_MUST_NOT_BE_NULL);
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

    public boolean getIsselected() {
        return isselected;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setPlayerlanguageid(@Nonnull PlayerLanguage playerlanguageid) {
        this.playerlanguageid = validatePlayerLanguage(playerlanguageid);
    }

    public void setBackgroundid(@Nonnull Background backgroundid) {
        this.backgroundid = validateBackground(backgroundid);
    }

    public void setMenuid(@Nonnull Menu menuid) {
        this.menuid = validateMenu(menuid);
    }

    public void setGames(Set<Game> games) {
        this.games = (games != null) ? games : new LinkedHashSet<>();
    }

    public void setName(@Nonnull String name) {
        this.name = validateName(name);
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public void setUpdatedat(@Nonnull LocalDateTime updatedat) {
        this.updatedat = validateUpdatedAt(updatedat);
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
        private boolean isselected = false;
        private LocalDateTime createdat = LocalDateTime.now();
        private LocalDateTime updatedat = LocalDateTime.now();

        public PlayerBuilder playerid(Long playerid) {
            this.playerid = playerid;
            return this;
        }

        public PlayerBuilder playerlanguageid(@Nonnull PlayerLanguage playerlanguageid) {
            this.playerlanguageid = validatePlayerLanguage(playerlanguageid);
            return this;
        }

        public PlayerBuilder backgroundid(@Nonnull Background backgroundid) {
            this.backgroundid = validateBackground(backgroundid);
            return this;
        }

        public PlayerBuilder menuid(@Nonnull Menu menuid) {
            this.menuid = validateMenu(menuid);
            return this;
        }

        public PlayerBuilder games(Set<Game> games) {
            this.games = (games != null) ? games : new LinkedHashSet<>();
            return this;
        }

        public PlayerBuilder name(@Nonnull String name) {
            this.name = validateName(name);
            return this;
        }

        public PlayerBuilder isselected(boolean isselected) {
            this.isselected = isselected;
            return this;
        }

        public PlayerBuilder createdat(@Nonnull LocalDateTime createdat) {
            this.createdat = validateCreatedAt(createdat);
            return this;
        }

        public PlayerBuilder updatedat(@Nonnull LocalDateTime updatedat) {
            this.updatedat = validateUpdatedAt(updatedat);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player other)) {
            return false;
        }
        return Objects.equals(playerid, other.playerid)
                && Objects.equals(playerlanguageid, other.playerlanguageid)
                && Objects.equals(backgroundid, other.backgroundid)
                && Objects.equals(menuid, other.menuid)
                && Objects.equals(name, other.name)
                && Objects.equals(isselected, other.isselected)
                && Objects.equals(createdat, other.createdat)
                && Objects.equals(updatedat, other.updatedat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                playerid,
                playerlanguageid,
                backgroundid,
                menuid,
                name,
                isselected,
                createdat,
                updatedat);
    }

    @Override
    public String toString() {
        return String.format(
                "Player{playerid=%s, playerlanguageid=%s, backgroundid=%s, menuid=%s, name=%s,"
                        + " isselected=%b, createdat=%s, updatedat=%s}",
                playerid,
                playerlanguageid.getPlayerlanguageid(),
                backgroundid.getBackgroundid(),
                menuid.getMenuid(),
                name,
                isselected,
                createdat,
                updatedat);
    }
}
