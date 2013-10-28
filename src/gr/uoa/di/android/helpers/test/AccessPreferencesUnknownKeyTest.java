package gr.uoa.di.android.helpers.test;

import android.annotation.TargetApi;
import android.os.Build;

import gr.uoa.di.android.helpers.AccessPreferences;

/**
 * This class tests the behavior of Prefs framework and
 * {@link AccessPreferences} when I retrieve an unknown key. I get the given
 * default back
 */
public final class AccessPreferencesUnknownKeyTest extends
		AccessPreferencesTest {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testUnknownKey() {
		assertEquals(DEFAULT_STRING,
			prefs.getString(UNKNOWN_KEY, DEFAULT_STRING));
		assertEquals(DEFAULT_BOOLEAN,
			prefs.getBoolean(UNKNOWN_KEY, DEFAULT_BOOLEAN));
		assertEquals(DEFAULT_INTEGER,
			prefs.getInt(UNKNOWN_KEY, DEFAULT_INTEGER));
		assertEquals(DEFAULT_LONG, prefs.getLong(UNKNOWN_KEY, DEFAULT_LONG));
		assertEquals(DEFAULT_FLOAT, prefs.getFloat(UNKNOWN_KEY, DEFAULT_FLOAT));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			assertEquals(DEFAULT_STRING_SET,
				prefs.getStringSet(UNKNOWN_KEY, DEFAULT_STRING_SET));
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testUnknownKeyNullDefault() {
		assertEquals(null, prefs.getString(UNKNOWN_KEY, null));
		// assertEquals(null, prefs.getBoolean(UNKNOWN_KEY, null));
		// assertEquals(null, prefs.getInt(UNKNOWN_KEY, null));
		// assertEquals(null, prefs.getLong(UNKNOWN_KEY, null));
		// assertEquals(null, prefs.getFloat(UNKNOWN_KEY, null));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			assertEquals(null, prefs.getStringSet(UNKNOWN_KEY, null));
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testAPUnknownKey() {
		assertEquals(DEFAULT_STRING,
			AccessPreferences.get(ctx, UNKNOWN_KEY, DEFAULT_STRING));
		// I need casts cause there is both an assertEquals(Object, Object) and
		// assertEquals(boolean, boolean) ...
		assertEquals(DEFAULT_BOOLEAN,
			(boolean) AccessPreferences.get(ctx, UNKNOWN_KEY, DEFAULT_BOOLEAN));
		assertEquals(DEFAULT_INTEGER,
			(int) AccessPreferences.get(ctx, UNKNOWN_KEY, DEFAULT_INTEGER));
		assertEquals(DEFAULT_LONG,
			(long) AccessPreferences.get(ctx, UNKNOWN_KEY, DEFAULT_LONG));
		// ...but there was no assertEquals(float, float) - now there is
		assertEquals(DEFAULT_FLOAT,
			(float) AccessPreferences.get(ctx, UNKNOWN_KEY, DEFAULT_FLOAT));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			assertEquals(DEFAULT_STRING_SET,
				AccessPreferences.get(ctx, UNKNOWN_KEY, DEFAULT_STRING_SET));
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testAPUnknownKeyNullDefault() {
		assertEquals(null, AccessPreferences.get(ctx, UNKNOWN_KEY, null));
		assertEquals(null, AccessPreferences.get(ctx, UNKNOWN_KEY, null));
		assertEquals(null, AccessPreferences.get(ctx, UNKNOWN_KEY, null));
		assertEquals(null, AccessPreferences.get(ctx, UNKNOWN_KEY, null));
		assertEquals(null, AccessPreferences.get(ctx, UNKNOWN_KEY, null));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			assertEquals(null, AccessPreferences.get(ctx, UNKNOWN_KEY, null));
		}
	}
}
