/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.enums;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility enum for internationalization (i18n) support. This enum manages language resources and
 * provides methods to switch between languages and retrieve localized strings.
 */
public enum I18n {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(I18n.class);

    private static final Locale LOCALE_FR = Locale.of("fr", "FR");
    private static final Locale LOCALE_EN = Locale.of("en", "US");
    private static final String I_18_N_PATH = Paths.I18N_PATH.getPath(); // Static field

    private static final ResourceBundle FRENCH_BUNDLE;
    private static final ResourceBundle ENGLISH_BUNDLE;
    private static ResourceBundle bundle;

    static {
        FRENCH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_FR);
        ENGLISH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_EN);
        bundle = FRENCH_BUNDLE;
    }

    /**
     * Sets the application's locale based on the provided language code. This method updates the
     * resource bundle used for translations and sets the default locale for the application. If the
     * provided language code is "EN", the locale is set to English (United States). Any other value
     * defaults to French (France).
     *
     * @param i18n The language code ("EN" for English, any other value defaults to French).
     * @return The singleton instance of {@code I18n}.
     */
    public synchronized I18n setLocaleBundle(final String i18n) {
        I18n.bundle = "EN".equals(i18n) ? ENGLISH_BUNDLE : FRENCH_BUNDLE;
        Locale.setDefault(I18n.bundle.getLocale());
        return INSTANCE;
    }

    /**
     * Retrieves the localized string value for a given key.
     *
     * @param key The String key representing the desired localized string.
     * @return The localized String value or a placeholder if an error occurs.
     */
    public String getValue(final String key) {
        if (key == null) {
            log.error("██ Localized string : Null String key");
            return "???null???";
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            log.error("██ Localized string : Missing String key : {}", e.getMessage(), e);
            return "???missing:" + key + "???";
        } catch (ClassCastException e) {
            log.error("██ Localized string : Invalid String key : {}", e.getMessage(), e);
            return "???invalid:" + key + "???";
        }
    }

    /**
     * Gets the current language of the application.
     *
     * @return The language code as a String (e.g., "fr" for French, "en" for English)
     */
    public String getLanguage() {
        Locale locale = bundle.getLocale();
        return locale.getLanguage();
    }

    /**
     * Gets the language code of the host environment. Uses the default locale to return the
     * language code (e.g., "en" for English, "fr" for French).
     *
     * @return The language code as a String.
     */
    public String getHostEnvironmentLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * Sets the locale bundle based on the host environment's language. If the host environment's
     * language is French ("fr"), uses the default locale bundle. Otherwise, uses the English locale
     * bundle ("EN").
     */
    public void setLanguageBasedOnTheHostEnvironment() {
        setLocaleBundle("fr".equals(getHostEnvironmentLanguageCode()) ? "" : "EN");
    }
}
