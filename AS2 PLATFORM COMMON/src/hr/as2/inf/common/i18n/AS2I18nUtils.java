package hr.as2.inf.common.i18n;

import java.util.Locale;

public final class AS2I18nUtils {

	/**
	 * Utility class for internationalization. This class provides a central
	 * location to do specialized formatting in both a default and a locale
	 * specfic manner
	 */

	private AS2I18nUtils() {
		// protects from instantiation
	}

	/**
	 * Convert a string based locale into a Locale Object. Assumes the string
	 * has form "{language}_{country}_{variant}". Examples: "en", "de_DE",
	 * "_GB", "en_US_WIN", "de__POSIX", "fr_MAC"
	 *
	 * @param localeString
	 *            The String
	 * @return the Locale
	 */
	public static Locale getLocaleFromString(String localeString) {
		if (localeString == null) {
			return null;
		}
		localeString = localeString.trim();
		if (localeString.toLowerCase().equals("default")) {
			return Locale.getDefault();
		}

		// Extract language
		int languageIndex = localeString.indexOf('_');
		String language = null;
		if (languageIndex == -1) {
			// No further "_" so is "{language}" only
			return new Locale(localeString, "");
		}

		language = localeString.substring(0, languageIndex);

		// Extract country
		int countryIndex = localeString.indexOf('_', languageIndex + 1);
		String country = null;
		if (countryIndex == -1) {
			// No further "_" so is "{language}_{country}"
			country = localeString.substring(languageIndex + 1);
			return new Locale(language, country);
		}

		// Assume all remaining is the variant so is
		// "{language}_{country}_{variant}"
		country = localeString.substring(languageIndex + 1, countryIndex);
		String variant = localeString.substring(countryIndex + 1);
		return new Locale(language, country, variant);
	}
}
