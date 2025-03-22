package fr.softsf.sudokufx.configuration.database;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.MyRegex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class GenerateSecretUTest {
    private final GenerateSecret generateSecret;

    @Autowired
    public GenerateSecretUTest(GenerateSecret generateSecret) {
        this.generateSecret = generateSecret;
    }

    @Test
    void givenPassayGenerator_whenGeneratePassaySecret_thenSecretIsValid() {
        String secret = generateSecret.generatePassaySecret();
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        assertTrue(MyRegex.INSTANCE.isValidatedByRegex(secret, secretPattern));
    }

    @Test
    void givenInvalidValues_whenIsValidatedByRegex_thenFailsRegex() {
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex(null, null));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex(null, secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("012345678910111213141516", null));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("LLLLLLLLLLLLLLLLLLLLLLLL", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("llllllllllllllllllllllll", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("@@@@@@@@@@@@@@@@@@@@@@@@", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("111111111111111111111111", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("                        ", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("LLLLLLLLLLLLLLLLLLLLLLLL", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("llllllllllllllllllllllll", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("@@@@@@@@@@@@@@@@@@@@@@@@", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("111111111111111111111111", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("uCQD1x$^UeWfn#OAb!YjYFHo", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("-CQD1x$^UeWfn#OAb!Y1YFH1", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("9uCQD1xi^UeWfntOAbmY1YFH", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("9uCQD1x$^UeWfn#OAb!Y1YFH1", secretPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("9uCQD1x$^UeWfn#OAb!Y1YF", secretPattern));
    }
}
