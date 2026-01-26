/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.common.util;

import java.io.File;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.util.AudioUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioUtilsUTest {

    private final AudioUtils audioUtils = new AudioUtils();

    @Test
    void givenValidAudioFile_whenIsValidAudio_thenReturnsTrue() {
        assertTrue(audioUtils.isValidAudio(new File("song.mp3")));
        assertTrue(audioUtils.isValidAudio(new File("sound.WAV")));
        assertTrue(audioUtils.isValidAudio(new File("music.aac")));
        assertTrue(audioUtils.isValidAudio(new File("track.M4A")));
        assertTrue(audioUtils.isValidAudio(new File("audio.AIF")));
        assertTrue(audioUtils.isValidAudio(new File("voice.aiff")));
    }

    @Test
    void givenInvalidAudioFile_whenIsValidAudio_thenReturnsFalse() {
        assertFalse(audioUtils.isValidAudio(new File("document.pdf")));
        assertFalse(audioUtils.isValidAudio(new File("image.jpg")));
        assertFalse(audioUtils.isValidAudio(new File("archive.zip")));
        assertFalse(audioUtils.isValidAudio(new File("file.")));
    }

    @Test
    void givenNullFile_whenIsValidAudio_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> audioUtils.isValidAudio(null));
        assertTrue(ex.getMessage().contains("The audio file mustn't be null"));
    }
}
