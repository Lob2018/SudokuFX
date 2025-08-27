/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.service;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.service.AudioService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class AudioServiceUTest {

    private AudioService audioService;

    @BeforeEach
    void setUp() {
        audioService = new AudioService();
    }

    @Test
    void givenNullEffectFile_whenPlayEffect_thenThrowsException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> audioService.playEffect(null, "button_click"));
        assertTrue(ex.getMessage().contains("effectFile"));
    }

    @Test
    void givenNullSongFile_whenPlaySong_thenThrowsException() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> audioService.playSong(null));
        assertTrue(ex.getMessage().contains("songFile"));
    }

    @Test
    void givenInvalidKey_whenPlayEffect_thenThrowsException() {
        File dummyFile = new File("dummy.mp3");
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> audioService.playEffect(dummyFile, "invalid_key"));
        assertTrue(ex.getMessage().contains("Unknown effect key"));
    }

    @Test
    void givenValidEffect_whenPlayEffect_thenMediaPlayerCreatedAndPlayed() {
        File dummyFile = new File("dummy.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playEffect(dummyFile, "button_click");
            assertEquals(1, playerMocked.constructed().size());
            MediaPlayer player = playerMocked.constructed().getFirst();
            verify(player).play();
        }
    }

    @Test
    void givenSong_whenPlaySong_thenMediaPlayerCreatedAndLooped() {
        File dummyFile = new File("song.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playSong(dummyFile);
            assertEquals(1, playerMocked.constructed().size());
            MediaPlayer player = playerMocked.constructed().getFirst();
            verify(player).setCycleCount(MediaPlayer.INDEFINITE);
            verify(player).play();
        }
    }

    @Test
    void givenMuted_whenMuteAll_thenVolumesZero() {
        File dummyFile = new File("song.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playSong(dummyFile);
            MediaPlayer player = playerMocked.constructed().getFirst();
            audioService.muteAll();
            assertTrue(audioService.isMuted());
            verify(player).setVolume(0);
        }
    }

    @Test
    void givenMuted_thenUnmuteRestoresVolume() {
        File dummyFile = new File("song.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playSong(dummyFile);
            MediaPlayer player = playerMocked.constructed().getFirst();
            audioService.muteAll();
            audioService.unmuteAll();
            assertFalse(audioService.isMuted());
            verify(player, atLeastOnce()).setVolume(1.0);
        }
    }

    @Test
    void givenStopAll_whenCalled_thenMediaPlayersStoppedAndDisposed() {
        File dummyFile = new File("song.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playSong(dummyFile);
            audioService.stopAll();
            MediaPlayer player = playerMocked.constructed().getFirst();
            verify(player).stop();
            verify(player).dispose();
        }
    }

    @Test
    void givenSetSongVolume_whenCalled_thenVolumeClamped() {
        assertDoesNotThrow(() -> audioService.setSongVolume(1.5));
        assertDoesNotThrow(() -> audioService.setSongVolume(-0.5));
        assertFalse(audioService.isMuted(), "Audio should not be muted after setting song volume");
    }

    @Test
    void givenSetEffectVolume_whenCalled_thenVolumeClamped() {
        File dummyFile = new File("dummy.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playEffect(dummyFile, "button_click");
            assertDoesNotThrow(() -> audioService.setEffectVolume("button_click", 1.5)); // >1.0
            assertDoesNotThrow(() -> audioService.setEffectVolume("button_click", -0.5)); // <0.0
            MediaPlayer player = playerMocked.constructed().getFirst();
            ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
            verify(player, atLeastOnce()).setVolume(captor.capture());
            for (Double vol : captor.getAllValues()) {
                assertNotNull(vol);
                assertTrue(vol >= 0.0 && vol <= 1.0, "Volume should be clamped between 0 and 1");
            }
        }
    }

    @Test
    void givenSameEffectKeyPlayedTwice_whenPlayEffect_thenPlayerReusedAndRestarted() {
        File dummyFile = new File("dummy.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playEffect(dummyFile, "button_click");
            MediaPlayer player = playerMocked.constructed().getFirst();
            audioService.playEffect(dummyFile, "button_click");
            assertEquals(1, playerMocked.constructed().size());
            verify(player, atLeastOnce()).stop();
            verify(player, atLeastOnce()).play();
        }
    }

    @Test
    void givenSongPlayedTwice_whenPlaySong_thenPreviousPlayerStoppedAndDisposed() {
        File dummyFile = new File("song.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playSong(dummyFile);
            MediaPlayer firstSongPlayer = playerMocked.constructed().getFirst();
            audioService.playSong(dummyFile);
            MediaPlayer secondSongPlayer = playerMocked.constructed().get(1);
            verify(firstSongPlayer).stop();
            verify(firstSongPlayer).dispose();
            assertNotSame(firstSongPlayer, secondSongPlayer);
        }
    }

    @Test
    void givenEffectWithCustomVolume_whenMuteAndUnmute_thenVolumeRestored() {
        File dummyFile = new File("dummy.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playEffect(dummyFile, "button_click");
            MediaPlayer player = playerMocked.constructed().getFirst();
            audioService.setEffectVolume("button_click", 0.3);
            audioService.muteAll();
            audioService.unmuteAll();
            verify(player, atLeastOnce()).setVolume(0.3);
        }
    }

    @Test
    void givenSongWithCustomVolume_whenMuteAndUnmute_thenVolumeRestored() {
        File dummyFile = new File("song.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playSong(dummyFile);
            MediaPlayer player = playerMocked.constructed().getFirst();
            audioService.setSongVolume(0.5);
            audioService.muteAll();
            audioService.unmuteAll();
            verify(player, atLeastOnce()).setVolume(0.5);
        }
    }

    @Test
    void givenMultipleEffects_whenStopAll_thenAllPlayersStoppedAndDisposed() {
        File dummyFile = new File("dummy.mp3");
        try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMocked =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playEffect(dummyFile, "button_click");
            audioService.playEffect(dummyFile, "case_click");
            audioService.stopAll();
            for (MediaPlayer player : playerMocked.constructed()) {
                verify(player, atLeastOnce()).stop();
                verify(player).dispose();
            }
        }
    }

    @Test
    void givenInvalidFile_whenPlaySong_thenMediaExceptionThrown() {
        File invalidFile = new File("not_existing.mp3");
        assertThrows(
                javafx.scene.media.MediaException.class, () -> audioService.playSong(invalidFile));
    }

    @Test
    void givenMuted_whenPlayEffect_thenVolumeIsZero() {
        audioService.muteAll();
        File fakeFile = new File("dummy.mp3");
        String key = "button_click";
        try (MockedConstruction<Media> mediaMock = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMock =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playEffect(fakeFile, key);
            MediaPlayer createdPlayer = playerMock.constructed().getFirst();
            verify(createdPlayer).setVolume(0);
            verify(createdPlayer).play();
        }
    }

    @Test
    void givenMuted_whenPlaySong_thenVolumeIsZero() {
        audioService.muteAll();
        File fakeSong = new File("dummy_song.mp3");
        try (MockedConstruction<Media> mediaMock = Mockito.mockConstruction(Media.class);
                MockedConstruction<MediaPlayer> playerMock =
                        Mockito.mockConstruction(MediaPlayer.class)) {
            audioService.playSong(fakeSong);
            MediaPlayer createdPlayer = playerMock.constructed().getFirst();
            verify(createdPlayer).setVolume(0);
            verify(createdPlayer).play();
            verify(createdPlayer).setCycleCount(MediaPlayer.INDEFINITE);
        }
    }

    @Test
    void givenUnknownEffectKey_whenSetEffectVolume_thenIllegalArgumentException() {
        String invalidKey = "invalid_key";
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            audioService.setEffectVolume(invalidKey, 0.5);
                        });
        assertTrue(exception.getMessage().contains("Unknown effect key"));
    }
}
