/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service;

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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

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
    private static final Set<String> VALID_EFFECT_KEYS = Set.of("button_click");
    private boolean isMuted = false;
    private double originalSongVolume = 1.0;

    /**
     * Plays a short sound effect. Reuses an existing MediaPlayer for the given key if present.
     * Automatically disposes the MediaPlayer after playback.
     *
     * @param effectFile the sound effect file
     * @param key a unique identifier for the effect
     * @throws IllegalArgumentException if effectFile is null
     * @throws IllegalArgumentException if key is null, empty, or blank
     * @throws IllegalArgumentException if key is not in the valid effect keys set
     * @throws IllegalArgumentException if the file URI is invalid
     * @throws RuntimeException if the media file cannot be loaded or played
     */
    public synchronized void playEffect(@Nullable File effectFile, String key) {
        if (Objects.isNull(effectFile)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The effectFile mustn't be null");
        }
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                key, "Key must not be null or blank, but was " + key);
        if (!VALID_EFFECT_KEYS.contains(key)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "Unknown effect key: " + key);
        }
        MediaPlayer existingPlayer = effectsPlayers.get(key);
        if (existingPlayer == null) {
            final Media media = new Media(effectFile.toURI().toString());
            final MediaPlayer player = new MediaPlayer(media);
            Double storedVolume = originalEffectVolumes.get(key);
            double volumeToApply = storedVolume != null ? storedVolume : 1.0;
            originalEffectVolumes.put(key, volumeToApply);
            if (isMuted) {
                player.setVolume(0);
            } else {
                player.setVolume(volumeToApply);
            }
            player.setOnEndOfMedia(
                    () -> {
                        player.dispose();
                        effectsPlayers.remove(key);
                        originalEffectVolumes.remove(key);
                    });
            effectsPlayers.put(key, player);
            player.play();
        } else {
            existingPlayer.stop();
            existingPlayer.seek(Duration.ZERO);
            existingPlayer.play();
        }
    }

    /**
     * Plays a song in a loop. Stops and disposes any existing song player.
     *
     * @param songFile the song audio file
     * @throws IllegalArgumentException if songFile is null
     * @throws IllegalArgumentException if the file URI is invalid
     * @throws RuntimeException if the media file cannot be loaded or played
     */
    public synchronized void playSong(@Nullable File songFile) {
        if (Objects.isNull(songFile)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The songFile mustn't be null");
        }
        if (songPlayer != null) {
            songPlayer.stop();
            songPlayer.dispose();
        }
        Media media = new Media(songFile.toURI().toString());
        songPlayer = new MediaPlayer(media);
        songPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        if (isMuted) {
            songPlayer.setVolume(0);
        } else {
            songPlayer.setVolume(originalSongVolume);
        }
        songPlayer.play();
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
     * Checks if audio is currently muted.
     *
     * @return true if all audio is muted
     */
    public synchronized boolean isMuted() {
        return isMuted;
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
        if (!VALID_EFFECT_KEYS.contains(key)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "Unknown effect key: " + key);
        }
        double clampedVolume = Math.clamp(volume, 0.0, 1.0);
        originalEffectVolumes.put(key, clampedVolume);
        MediaPlayer effectPlayer = effectsPlayers.get(key);
        if (effectPlayer != null && !isMuted) {
            effectPlayer.setVolume(clampedVolume);
        }
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
