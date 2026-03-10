/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.enums;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Internationalization (i18n) support. Manages language resources and locale synchronization
 * through JavaFX properties. Technical Note: Accessing INSTANCE triggers the initialization of
 * JavaFX properties, requiring an active JavaFX Toolkit.
 */
public enum I18n {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(I18n.class);

    /** Observable locale property. Requires JavaFX Toolkit for instantiation. */
    private final ReadOnlyObjectWrapper<Locale> locale =
            new ReadOnlyObjectWrapper<>(Locale.getDefault());

    private final ResourceBundle frenchBundle;
    private final ResourceBundle englishBundle;
    private ResourceBundle bundle;

    /**
     * Initializes bundles and binds the locale property to the system default. Triggers JavaFX
     * Toolkit check via ReadOnlyObjectWrapper.
     */
    I18n() {
        Locale localeFr = Locale.of("fr", "FR");
        String i18NPathPath = AppPaths.I18N_PATH.getPath();
        frenchBundle = ResourceBundle.getBundle(i18NPathPath, localeFr);
        Locale localeEn = Locale.of("en", "US");
        englishBundle = ResourceBundle.getBundle(i18NPathPath, localeEn);
        bundle = frenchBundle;
        locale.addListener((_, _, newLocale) -> Locale.setDefault(newLocale));
    }

    /**
     * Updates the current bundle and synchronizes the locale property.
     *
     * @param i18n Language code. "EN" for English, defaults to French otherwise.
     * @return The singleton instance.
     */
    public I18n setLocaleBundle(final String i18n) {
        bundle = "EN".equals(i18n) ? englishBundle : frenchBundle;
        locale.set(bundle.getLocale());
        return INSTANCE;
    }

    /**
     * @return Read-only observable locale property for UI binding.
     */
    public ReadOnlyObjectProperty<Locale> localeProperty() {
        return locale.getReadOnlyProperty();
    }

    /**
     * Retrieves a translated string from the active bundle.
     *
     * @param key Resource bundle key.
     * @return The localized value, or a formatted error string if not found.
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
     * @return Current ISO 639-1 language code ("en", "fr", etc.).
     */
    public String getLanguage() {
        return bundle.getLocale().getLanguage();
    }

    /**
     * @return Host system ISO 639-1 language code.
     */
    public String getHostEnvironmentLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * Matches the application language to the host environment. Defaults to English for any
     * language other than French.
     */
    public void setLanguageBasedOnTheHostEnvironment() {
        setLocaleBundle("fr".equals(getHostEnvironmentLanguageCode()) ? "" : "EN");
    }
}
