/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.ResourceLoadException;

/**
 * Service managing audio playback for the application.
 *
 * <p>Supports song and short sound effects, with automatic disposal of MediaPlayer instances to
 * prevent memory leaks.
 */
@Service
public class AudioService {

    private static final Logger LOG = LoggerFactory.getLogger(AudioService.class);

    private MediaPlayer songPlayer;
    private final Map<String, MediaPlayer> effectsPlayers = new HashMap<>();
    private final Map<String, Double> originalEffectVolumes = new HashMap<>();
    private static final Set<String> VALID_EFFECT_KEYS = Set.of("button_click", "case_click");
    private boolean isMuted = false;
    private double originalSongVolume = 1.0;

    /**
     * Plays a short sound effect. Reuses an existing MediaPlayer for the given key if present.
     * Automatically disposes the MediaPlayer after playback.
     *
     * <p>The provided {@code effectFile} must not be null. The {@code key} must be one of the
     * {@link #VALID_EFFECT_KEYS}. The method throws a {@link ResourceLoadException} if the media
     * file cannot be loaded or played.
     *
     * <p>{@code effectFile} is required and cannot be null. {@code key} cannot be null, blank, or
     * invalid; otherwise an {@link IllegalArgumentException} is thrown.
     *
     * @param effectFile the sound effect file; must not be null
     * @param key a unique identifier for the effect; must be in {@link #VALID_EFFECT_KEYS},
     *     non-blank
     * @throws NullPointerException if {@code effectFile} is null
     * @throws IllegalArgumentException if {@code key} is null, blank, or not in {@link
     *     #VALID_EFFECT_KEYS}
     * @throws ResourceLoadException if the media file cannot be loaded or played
     */
    public synchronized void playEffect(File effectFile, String key) throws ResourceLoadException {
        Objects.requireNonNull(effectFile, "The effectFile must not be null");
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                key, "Key must not be null or blank");
        if (VALID_EFFECT_KEYS.contains(key)) {
            MediaPlayer existingPlayer = effectsPlayers.get(key);
            if (existingPlayer == null) {
                try {
                    Media media = new Media(effectFile.toURI().toString());
                    MediaPlayer player = new MediaPlayer(media);
                    double volume = originalEffectVolumes.getOrDefault(key, 1.0);
                    originalEffectVolumes.put(key, volume);
                    player.setVolume(isMuted ? 0 : volume);
                    player.setOnEndOfMedia(
                            () -> {
                                player.dispose();
                                effectsPlayers.remove(key);
                                originalEffectVolumes.remove(key);
                            });
                    effectsPlayers.put(key, player);
                    player.play();
                } catch (Exception e) {
                    throw ExceptionTools.INSTANCE.logAndInstantiateResourceLoad(
                            "Failed to play effect " + effectFile.getName(), e);
                }
            } else {
                existingPlayer.stop();
                existingPlayer.seek(Duration.ZERO);
                existingPlayer.play();
            }
            return;
        }
        throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                "Unknown effect key: " + key);
    }

    /**
     * Plays a song in a loop. Stops and disposes any existing song player.
     *
     * <p>The provided {@code songFile} must not be null. If the file cannot be loaded or played, a
     * {@link ResourceLoadException} is thrown.
     *
     * @param songFile the song audio file; must not be null
     * @throws NullPointerException if {@code songFile} is null
     * @throws ResourceLoadException if the media file cannot be loaded or played
     */
    public synchronized void playSong(File songFile) throws ResourceLoadException {
        Objects.requireNonNull(songFile, "The songFile must not be null");
        if (songPlayer != null) {
            songPlayer.stop();
            songPlayer.dispose();
        }
        try {
            Media media = new Media(songFile.toURI().toString());
            songPlayer = new MediaPlayer(media);
            songPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            songPlayer.setVolume(isMuted ? 0 : originalSongVolume);
            songPlayer.play();
        } catch (Exception e) {
            throw ExceptionTools.INSTANCE.logAndInstantiateResourceLoad(
                    "Failed to play song " + songFile.getName(), e);
        }
    }

    /**
     * Checks if audio is currently muted.
     *
     * @return true if all audio is muted
     */
    public synchronized boolean isMuted() {
        return isMuted;
    }

    /** Mutes all audio (song and effects). */
    public synchronized void muteAll() {
        isMuted = true;
        if (songPlayer != null) {
            songPlayer.setVolume(0);
        }
        for (MediaPlayer effectPlayer : effectsPlayers.values()) {
            effectPlayer.setVolume(0);
        }
    }

    /** Unmutes all audio and restores original volumes. */
    public synchronized void unmuteAll() {
        isMuted = false;
        if (songPlayer != null) {
            songPlayer.setVolume(originalSongVolume);
        }
        for (Map.Entry<String, MediaPlayer> entry : effectsPlayers.entrySet()) {
            String key = entry.getKey();
            MediaPlayer effectPlayer = entry.getValue();
            Double originalVolume = originalEffectVolumes.get(key);
            effectPlayer.setVolume(originalVolume != null ? originalVolume : 1.0);
        }
    }

    /**
     * Sets the muted state. Used for initialization from database.
     *
     * @param isMuted true to mute all audio, false to unmute
     */
    public synchronized void setMuted(boolean isMuted) {
        if (isMuted) {
            muteAll();
        } else {
            unmuteAll();
        }
    }

    /**
     * Sets the volume for the song (when not muted).
     *
     * @param volume volume level (0.0 to 1.0)
     */
    public synchronized void setSongVolume(double volume) {
        originalSongVolume = Math.clamp(volume, 0.0, 1.0);
        if (songPlayer != null && !isMuted) {
            songPlayer.setVolume(originalSongVolume);
        }
    }

    /**
     * Sets the volume for a specific effect (when not muted).
     *
     * @param key the effect key
     * @param volume volume level (0.0 to 1.0)
     * @throws IllegalArgumentException if key is null, empty, or blank
     * @throws IllegalArgumentException if key is not in the valid effect keys set
     */
    public synchronized void setEffectVolume(String key, double volume) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                key, "Key must not be null or blank, but was " + key);
        if (VALID_EFFECT_KEYS.contains(key)) {
            double clampedVolume = Math.clamp(volume, 0.0, 1.0);
            originalEffectVolumes.put(key, clampedVolume);
            MediaPlayer effectPlayer = effectsPlayers.get(key);
            if (effectPlayer != null && !isMuted) {
                effectPlayer.setVolume(clampedVolume);
            }
            return;
        }
        throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                "Unknown effect key: " + key);
    }

    /** Stops and disposes the song player. */
    public synchronized void stopSong() {
        if (songPlayer != null) {
            songPlayer.stop();
            songPlayer.dispose();
            songPlayer = null;
        }
    }

    /** Stops and disposes all audio resources. */
    public synchronized void stopAll() {
        LOG.info("\n▓▓ Stops and disposes all audio resources");
        stopSong();
        for (MediaPlayer effectPlayer : effectsPlayers.values()) {
            effectPlayer.stop();
            effectPlayer.dispose();
        }
        effectsPlayers.clear();
        originalEffectVolumes.clear();
    }
}
