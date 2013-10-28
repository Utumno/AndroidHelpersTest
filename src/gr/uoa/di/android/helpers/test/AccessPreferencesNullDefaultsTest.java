package gr.uoa.di.android.helpers.test;

import android.annotation.TargetApi;
import android.os.Build;

import gr.uoa.di.android.helpers.AccessPreferences;

import java.util.Set;

public final class AccessPreferencesNullDefaultsTest extends
		AccessPreferencesTest {

	// TODO : docs renames etc - it is not simply about null defaults
	// =========================================================================
	// Null defaults - CCE on assignment (or immediately)
	// =========================================================================
	public void testNullDefaultCCE() {
		ed.putBoolean(BOOLEAN_KEY, DEFAULT_BOOLEAN);
		ed.commit();
		try {
			// Immediate CCE ! - no need to assign
			prefs.getString(BOOLEAN_KEY, null);
			// Log.e(TAG, nullString + ""); // null
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// java.lang.ClassCastException: java.lang.Boolean cannot be cast to
			// java.lang.String
		}
	}

	public void testNonNullDefaultCCE() {
		ed.putBoolean(BOOLEAN_KEY, DEFAULT_BOOLEAN);
		ed.commit();
		try {
			// Immediate CCE ! - no need to assign
			prefs.getString(BOOLEAN_KEY, DEFAULT_STRING);
			// Log.e(TAG, string + ""); // DEFAULT_STRING
			// final int int1 = prefs.getInt(BOOLEAN_KEY, 0);
			// Log.e(TAG, int1 + ""); // 0
			fail("Expected CCE");
		} catch (ClassCastException e) {}
	}

	// strings, sets and null - NO CCE !
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testNullDefaultNullValueCCE() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ed.putString(STRING_KEY, null);
			ed.commit();
			prefs.getStringSet(STRING_KEY, null); // NO CCE !
			@SuppressWarnings("unused")
			Set<String> stringSet = prefs.getStringSet(STRING_KEY, null);
		}
	}

	// =========================================================================
	// AccessPreferences
	// =========================================================================
	public void testAPNullDefault() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, DEFAULT_BOOLEAN);
		AccessPreferences.get(ctx, BOOLEAN_KEY, null); // NO CCE !!!!!!!!!!!!!!!
		try {
			@SuppressWarnings("unused")
			String dummy = AccessPreferences.get(ctx, BOOLEAN_KEY, null);
			fail("Expected CCE");
		} catch (ClassCastException e) {}
	}

	public void testAPNonNullDefault() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, DEFAULT_BOOLEAN);
		try {
			// Immediate CCE ! - no need to assign
			AccessPreferences.get(ctx, BOOLEAN_KEY, DEFAULT_STRING);
			fail("Expected CCE");
		} catch (ClassCastException e) {}
	}

	// strings and null - TODO test with Booleans etc
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testAPNullDefaultNullValueCCE() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			AccessPreferences.put(ctx, STRING_KEY, null);
			AccessPreferences.get(ctx, STRING_KEY, null); // NO CCE !
			@SuppressWarnings("unused")
			Set<String> stringSet = AccessPreferences
				.get(ctx, STRING_KEY, null);
		}
	}

	// tricky unboxing
	public void testAPNullDefaultUnboxingLong() {
		AccessPreferences.put(ctx, LONG_KEY, 0L); // could look like an integer
		// <Long> Long get(Context ctx, String key, Long defaultValue)
		@SuppressWarnings("unused")
		Long lo = AccessPreferences.get(ctx, LONG_KEY, null); // ALL FINE
	}
}
