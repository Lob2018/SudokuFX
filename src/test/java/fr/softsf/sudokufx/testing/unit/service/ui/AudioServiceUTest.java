/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.service.ui;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.exception.ResourceLoadException;
import fr.softsf.sudokufx.service.ui.AudioService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class AudioServiceUTest {

    private AudioService audioService;
    private File validEffectFile;
    private File validSongFile;

    private static final String VALID_EFFECT_KEY = "button_click";
    private static final String ANOTHER_VALID_EFFECT_KEY = "case_click";
    private static final String INVALID_EFFECT_KEY = "invalid_key";

    @BeforeEach
    void setUp() {
        audioService = new AudioService();
        validEffectFile = new File("test_effect.mp3");
        validSongFile = new File("test_song.mp3");
    }

    @Nested
    @DisplayName("PlayEffect Tests")
    class PlayEffectTests {

        @Test
        @DisplayName("Should throw NullPointerException when effectFile is null")
        @SuppressWarnings("DataFlowIssue")
        void givenNullEffectFile_whenPlayEffect_thenThrowsNullPointerException() {
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> audioService.playEffect(null, VALID_EFFECT_KEY));
            assertThat(exception.getMessage()).contains("effectFile");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when key is null")
        @SuppressWarnings("DataFlowIssue")
        void givenNullKey_whenPlayEffect_thenThrowsIllegalArgumentException() {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> audioService.playEffect(validEffectFile, null));
            assertThat(exception.getMessage()).contains("Key must not be null or blank");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when key is blank")
        void givenBlankKey_whenPlayEffect_thenThrowsIllegalArgumentException() {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> audioService.playEffect(validEffectFile, "   "));
            assertThat(exception.getMessage()).contains("Key must not be null or blank");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when key is invalid")
        void givenInvalidKey_whenPlayEffect_thenThrowsIllegalArgumentException() {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> audioService.playEffect(validEffectFile, INVALID_EFFECT_KEY));
            assertThat(exception.getMessage()).contains("Unknown effect key");
        }

        @Test
        @DisplayName("Should create and play MediaPlayer for valid effect")
        void givenValidEffect_whenPlayEffect_thenMediaPlayerCreatedAndPlayed()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                assertThat(playerMocked.constructed()).hasSize(1);
                MediaPlayer player = playerMocked.constructed().getFirst();
                verify(player).setVolume(1.0);
                verify(player).setOnEndOfMedia(any(Runnable.class));
                verify(player).play();
            }
        }

        @Test
        @DisplayName("Should reuse existing MediaPlayer when same effect key is played twice")
        void givenSameEffectKeyPlayedTwice_whenPlayEffect_thenPlayerReusedAndRestarted()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                MediaPlayer firstPlayer = playerMocked.constructed().getFirst();
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                assertThat(playerMocked.constructed()).hasSize(1);
                verify(firstPlayer).stop();
                verify(firstPlayer).seek(Duration.ZERO);
                verify(firstPlayer, times(2)).play();
            }
        }

        @Test
        @DisplayName("Should set volume to zero when muted")
        void givenMutedState_whenPlayEffect_thenVolumeIsZero() throws ResourceLoadException {
            audioService.muteAll();
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                MediaPlayer player = playerMocked.constructed().getFirst();
                verify(player).setVolume(0.0);
                verify(player).play();
            }
        }
    }

    @Nested
    @DisplayName("PlaySong Tests")
    class PlaySongTests {

        @Test
        @DisplayName("Should throw NullPointerException when songFile is null")
        @SuppressWarnings("DataFlowIssue")
        void givenNullSongFile_whenPlaySong_thenThrowsNullPointerException() {
            NullPointerException exception =
                    assertThrows(NullPointerException.class, () -> audioService.playSong(null));
            assertThat(exception.getMessage()).contains("songFile");
        }

        @Test
        @DisplayName("Should create MediaPlayer with infinite loop for valid song")
        void givenValidSong_whenPlaySong_thenMediaPlayerCreatedAndLooped()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                assertThat(playerMocked.constructed()).hasSize(1);
                MediaPlayer player = playerMocked.constructed().getFirst();
                verify(player).setCycleCount(MediaPlayer.INDEFINITE);
                verify(player).setVolume(1.0);
                verify(player).play();
            }
        }

        @Test
        @DisplayName("Should stop and dispose previous song when playing new one")
        void givenNewSong_whenPlaySong_thenPreviousPlayerStoppedAndDisposed()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                MediaPlayer firstPlayer = playerMocked.constructed().getFirst();
                audioService.playSong(new File("another_song.mp3"));
                verify(firstPlayer).stop();
                verify(firstPlayer).dispose();
                assertThat(playerMocked.constructed()).hasSize(2);
            }
        }

        @Test
        @DisplayName("Should set volume to zero when muted")
        void givenMutedState_whenPlaySong_thenVolumeIsZero() throws ResourceLoadException {
            audioService.muteAll();
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                MediaPlayer player = playerMocked.constructed().getFirst();
                verify(player).setVolume(0.0);
                verify(player).setCycleCount(MediaPlayer.INDEFINITE);
                verify(player).play();
            }
        }
    }

    @Nested
    @DisplayName("Volume Control Tests")
    class VolumeControlTests {

        @Test
        @DisplayName("Should clamp song volume to valid range")
        void givenVolumeOutOfRange_whenSetSongVolume_thenVolumeClamped()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                MediaPlayer player = playerMocked.constructed().getFirst();
                InOrder inOrder = inOrder(player);
                inOrder.verify(player).setVolume(1.0);
                audioService.setSongVolume(1.5);
                inOrder.verify(player).setVolume(1.0);
                audioService.setSongVolume(-0.5);
                inOrder.verify(player).setVolume(0.0);
                audioService.setSongVolume(0.7);
                inOrder.verify(player).setVolume(0.7);
                inOrder.verifyNoMoreInteractions();
            }
        }

        @Test
        @DisplayName("Should clamp effect volume to valid range")
        void givenVolumeOutOfRange_whenSetEffectVolume_thenVolumeClamped()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                MediaPlayer player = playerMocked.constructed().getFirst();
                InOrder inOrder = inOrder(player);
                inOrder.verify(player).setVolume(1.0);
                audioService.setEffectVolume(VALID_EFFECT_KEY, 1.5);
                inOrder.verify(player).setVolume(1.0);
                audioService.setEffectVolume(VALID_EFFECT_KEY, -0.5);
                inOrder.verify(player).setVolume(0.0);
                audioService.setEffectVolume(VALID_EFFECT_KEY, 0.6);
                inOrder.verify(player).setVolume(0.6);
                inOrder.verifyNoMoreInteractions();
            }
        }

        @Test
        @DisplayName("Should throw exception for invalid effect key when setting volume")
        void givenInvalidKey_whenSetEffectVolume_thenThrowsIllegalArgumentException() {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> audioService.setEffectVolume(INVALID_EFFECT_KEY, 0.5));
            assertThat(exception.getMessage()).contains("Unknown effect key");
        }

        @Test
        @DisplayName("Should throw exception for null key when setting volume")
        @SuppressWarnings("DataFlowIssue")
        void givenNullKey_whenSetEffectVolume_thenThrowsIllegalArgumentException() {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> audioService.setEffectVolume(null, 0.5));
            assertThat(exception.getMessage()).contains("Key must not be null or blank");
        }
    }

    @Nested
    @DisplayName("Mute/Unmute Tests")
    class MuteUnmuteTests {

        @Test
        @DisplayName("Should mute all audio and set isMuted to true")
        void whenMuteAll_thenAllAudioMutedAndStateUpdated() throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                audioService.muteAll();
                assertThat(audioService.isMuted()).isTrue();
                for (MediaPlayer player : playerMocked.constructed()) {
                    verify(player, atLeastOnce()).setVolume(0.0);
                }
            }
        }

        @Test
        @DisplayName("Should unmute all audio and restore original volumes")
        void givenMutedAudio_whenUnmuteAll_thenOriginalVolumesRestored()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                audioService.setSongVolume(0.5);
                audioService.setEffectVolume(VALID_EFFECT_KEY, 0.3);
                audioService.muteAll();
                audioService.unmuteAll();
                assertThat(audioService.isMuted()).isFalse();
                MediaPlayer songPlayer = playerMocked.constructed().get(0);
                MediaPlayer effectPlayer = playerMocked.constructed().get(1);
                verify(songPlayer, atLeastOnce()).setVolume(0.5);
                verify(effectPlayer, atLeastOnce()).setVolume(0.3);
            }
        }

        @Test
        @DisplayName("Should set muted state correctly")
        void givenMutedState_whenSetMuted_thenStateUpdatedCorrectly() {
            audioService.setMuted(true);
            assertThat(audioService.isMuted()).isTrue();
            audioService.setMuted(false);
            assertThat(audioService.isMuted()).isFalse();
        }
    }

    @Nested
    @DisplayName("Stop Operations Tests")
    class StopOperationsTests {

        @Test
        @DisplayName("Should stop and dispose song player")
        void givenPlayingSong_whenStopSong_thenSongPlayerStoppedAndDisposed()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                MediaPlayer songPlayer = playerMocked.constructed().getFirst();
                audioService.stopSong();
                verify(songPlayer).stop();
                verify(songPlayer).dispose();
            }
        }

        @Test
        @DisplayName("Should stop and dispose all players")
        void givenPlayingAudio_whenStopAll_thenAllPlayersStoppedAndDisposed()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                audioService.playEffect(validEffectFile, ANOTHER_VALID_EFFECT_KEY);
                audioService.stopAll();
                for (MediaPlayer player : playerMocked.constructed()) {
                    verify(player).stop();
                    verify(player).dispose();
                }
            }
        }

        @Test
        @DisplayName("Should handle stopSong when no song is playing")
        void givenNoPlayingSong_whenStopSong_thenNoException() {
            assertDoesNotThrow(() -> audioService.stopSong());
        }

        @Test
        @DisplayName("Should handle stopAll when no audio is playing")
        void givenNoPlayingAudio_whenStopAll_thenNoException() {
            assertDoesNotThrow(() -> audioService.stopAll());
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw ResourceLoadException for invalid media file")
        void givenInvalidMediaFile_whenPlaySong_thenThrowsResourceLoadException() {
            try (MockedConstruction<Media> mediaMocked =
                            Mockito.mockConstruction(
                                    Media.class,
                                    (media, context) -> {
                                        throw new RuntimeException("media error");
                                    });
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                File fakeFile = new File("fake.mp3");
                ResourceLoadException exception =
                        assertThrows(
                                ResourceLoadException.class, () -> audioService.playSong(fakeFile));
                assertThat(exception.getMessage()).contains("Failed to play song");
            }
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should maintain consistent state through complex operations")
        void givenComplexOperations_whenExecuted_thenStateRemainsConsistent()
                throws ResourceLoadException {
            try (MockedConstruction<Media> mediaMocked = Mockito.mockConstruction(Media.class);
                    MockedConstruction<MediaPlayer> playerMocked =
                            Mockito.mockConstruction(MediaPlayer.class)) {
                audioService.playSong(validSongFile);
                audioService.playEffect(validEffectFile, VALID_EFFECT_KEY);
                audioService.playEffect(validEffectFile, ANOTHER_VALID_EFFECT_KEY);
                audioService.setSongVolume(0.7);
                audioService.setEffectVolume(VALID_EFFECT_KEY, 0.4);
                audioService.setEffectVolume(ANOTHER_VALID_EFFECT_KEY, 0.6);
                audioService.muteAll();
                assertThat(audioService.isMuted()).isTrue();
                audioService.unmuteAll();
                assertThat(audioService.isMuted()).isFalse();
                MediaPlayer songPlayer = playerMocked.constructed().getFirst();
                verify(songPlayer, atLeastOnce()).setVolume(0.7);
            }
        }
    }
}
