/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton enum for internationalization (i18n) support. Manages language resources and locale
 * synchronization.
 */
public enum I18n {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(I18n.class);

    private static final ObjectProperty<Locale> LOCALE =
            new SimpleObjectProperty<>(Locale.getDefault());

    private static final Locale LOCALE_FR = Locale.of("fr", "FR");
    private static final Locale LOCALE_EN = Locale.of("en", "US");
    private static final String I_18_N_PATH = AppPaths.I18N_PATH.getPath();

    private static final ResourceBundle FRENCH_BUNDLE;
    private static final ResourceBundle ENGLISH_BUNDLE;
    private static ResourceBundle bundle;

    static {
        FRENCH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_FR);
        ENGLISH_BUNDLE = ResourceBundle.getBundle(I_18_N_PATH, LOCALE_EN);
        bundle = FRENCH_BUNDLE;
        LOCALE.addListener((obs, oldLocale, newLocale) -> Locale.setDefault(newLocale));
    }

    /**
     * Updates the current locale and bundle based on language code. "EN" sets English; any other
     * value defaults to French.
     *
     * @param i18n Language code.
     * @return Singleton instance.
     */
    public I18n setLocaleBundle(final String i18n) {
        bundle = "EN".equals(i18n) ? ENGLISH_BUNDLE : FRENCH_BUNDLE;
        LOCALE.set(bundle.getLocale());
        return INSTANCE;
    }

    /**
     * @return Observable locale property.
     */
    public ObjectProperty<Locale> localeProperty() {
        return LOCALE;
    }

    /**
     * Returns the localized string for the given key.
     *
     * @param key Resource key.
     * @return Translated value or fallback.
     */
    public String getValue(final String key) {
        if (StringUtils.isBlank(key)) {
            LOG.error("██ Localized string: Null or blank key");
            return "???null???";
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            LOG.error("██ Localized string: Missing key: {}", e.getMessage(), e);
            return "???missing:" + key + "???";
        } catch (ClassCastException e) {
            LOG.error("██ Localized string: Invalid key: {}", e.getMessage(), e);
            return "???invalid:" + key + "???";
        }
    }

    /**
     * @return Current language code ("en", "fr", etc.).
     */
    public String getLanguage() {
        return bundle.getLocale().getLanguage();
    }

    /**
     * @return Host system language code.
     */
    public String getHostEnvironmentLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    /** Sets the language to match host system (defaulting to English if not French). */
    public void setLanguageBasedOnTheHostEnvironment() {
        setLocaleBundle("fr".equals(getHostEnvironmentLanguageCode()) ? "" : "EN");
    }
}
