package gr.uoa.di.android.helpers.test;

import android.annotation.TargetApi;
import android.os.Build;

import gr.uoa.di.android.helpers.AccessPreferences;

/**
 * This class tests the behavior of Prefs framework when I put a null **value**
 * (with or without a null key) and I ask it back. Resume : for Wrapper around
 * primitives (Boolean is tested but behavior should be similar for Float,
 * Integer and Long) a NPE is thrown **on put() invocation**. NPE also on get()
 * invocation if I supply a null default. For String/Set<String> null values are
 * OK - *but if I specify a non null default I get the default back - not null*.
 * {@link AccessPreferences} behave the same for Strings/Sets (allow null and
 * give default back) - but also allow for null boxed primitives (Booleans etc)
 * which leads to NullPointerExceptions on get() and assigning to primitives
 */
public final class AccessPreferencesNullValuesTest extends
		AccessPreferencesTest {

	// =========================================================================
	// Boolean - same should apply for Float, Integer and Long
	// =========================================================================
	/**
	 * Testing what happens when I put/retrieve Boolean {@code null} :
	 * NullPointerException. Same should happen with Float, Integer and Long :
	 * e.putFloat(FLOAT_KEY, (Float) null); e.putInt(INT_KEY, (Integer) null);
	 * e.putLong(LONG_KEY, (Long) null);
	 */
	@SuppressWarnings("null")
	public void testNullBoolean() {
		try {
			ed.putBoolean(BOOLEAN_KEY, NULL_BOOLEAN); // I GET NO WARNING WHEN I
			// USE A VARIABLE !
			fail("Expected NPE");
		} catch (NullPointerException e) {
			// CAUSE putBoolean(String arg0, ***boolean*** arg1) -->unboxes null
		}
		ed.commit();
		// Boolean GET : default value null
		try {
			// notice I need casts
			// Warning : Null pointer access: This expression of type Boolean is
			// null but requires auto-unboxing
			prefs.getBoolean(BOOLEAN_KEY, (Boolean) null);
			fail("Expected NPE");
		} catch (NullPointerException e) {
			// CAUSE getBoolean(String arg0, ***boolean*** arg1) -->unboxes null
		}
		// Boolean GET : default value NON NULL - will give back the
		// DEFAULT_BOOLEAN given // yes cause does not exist
		assertEquals(DEFAULT_BOOLEAN,
			prefs.getBoolean(BOOLEAN_KEY, DEFAULT_BOOLEAN));
		// try {
		// I get no CCE when I put null as Boolean and retrieve String
		// the key simply does not exist
		assertFalse(prefs.contains(BOOLEAN_KEY)); // NOT CONTAINED !
		assertEquals(DEFAULT_STRING,
			prefs.getString(BOOLEAN_KEY, DEFAULT_STRING));
		// I was getting the CCE cause I had a call :
		// AccessPreferences.put(ctx, BOOLEAN_KEY, DEFAULT_BOOLEAN);
		// in AccessPreferencesBooleanTest which was putting a boolean !
		// fail("Expected CCE");
		// } catch (ClassCastException e) {
		// java.lang.ClassCastException: java.lang.Boolean
		// at android.app.ContextImpl$SharedPreferencesImpl.
		// getString(ContextImpl.java:2699)
		// }
	}

	/**
	 * Testing what happens when I put/retrieve Boolean {@code null} with
	 * {@code null} key. Exactly same behavior as in {@link #testNullBoolean()}
	 */
	@SuppressWarnings("null")
	public void testNullKeyNullBoolean() {
		try {
			ed.putBoolean(null, (Boolean) null); // Warning: Null pointer
			// access: This expression of type Boolean is null but requires
			// auto-unboxing
			fail("Expected NPE");
		} catch (NullPointerException e) {
			// CAUSE putBoolean(String arg0, ***boolean*** arg1)
		}
		ed.commit();
		assertFalse(prefs.contains(null));
	}

	// =========================================================================
	// Tests for AccessPreferences Boolean
	// =========================================================================
	public void testPutNullBoolean() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, null);
		Boolean b = AccessPreferences.get(ctx, BOOLEAN_KEY, null);
		assertEquals(null, b);
	}

	public void testPutNullBooleanGetPrimitiveBoolean() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, null);
		try {
			// boolean b = AccessPreferences.get(this, nullKey,
			// "how about giving a string"); // won't compile
			boolean b = AccessPreferences.get(ctx, BOOLEAN_KEY, null);
			fail("Expected NPE: " + b);
		} catch (NullPointerException e) {
			// on unboxing in ASSIGNMENT
		}
		// I have put null and specify a default I get the default back not null
		boolean b = AccessPreferences.get(ctx, BOOLEAN_KEY, DEFAULT_BOOLEAN);
		assertEquals(DEFAULT_BOOLEAN, b);
		// CAN BE RETRIEVED AS ANYTHING !!!
		String s = AccessPreferences.get(ctx, BOOLEAN_KEY, DEFAULT_STRING);
		assertEquals(DEFAULT_STRING, s);
	}

	// TODO : test null keys in BOOLEAN
	// =========================================================================
	// String, Set<String>
	// =========================================================================
	/** Testing what happens when I put/retrieve Strings {@code null} */
	public void testNullString() {
		ed.putString(STRING_KEY, null);
		ed.commit();
		assertTrue("Null value for String should be quite normal",
			prefs.contains(STRING_KEY));
		assertEquals(null, prefs.getString(STRING_KEY, null));
		// I have put null and specify a default I get the default back not null
		assertEquals(DEFAULT_STRING,
			prefs.getString(STRING_KEY, DEFAULT_STRING));
		// or a boolean...
		assertEquals(DEFAULT_BOOLEAN,
			prefs.getBoolean(STRING_KEY, DEFAULT_BOOLEAN));
	}

	/** Testing what happens when I put/retrieve Set<String> {@code null} */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testNullStringSets() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ed.putStringSet(SET_KEY, null);
			ed.commit();
			assertTrue("Null value for string set is quite normal",
				prefs.contains(SET_KEY));
			assertEquals(null, prefs.getStringSet(SET_KEY, null));
			// $MATCHERS
			assertEquals(DEFAULT_STRING_SET,
				prefs.getStringSet(SET_KEY, DEFAULT_STRING_SET));
			// Anything goes...
			assertEquals(DEFAULT_STRING,
				prefs.getString(SET_KEY, DEFAULT_STRING));
		}
	}

	// =========================================================================
	// Tests for AccessPreferences String, Set<String>
	// =========================================================================
	public void testPutNullString() {
		AccessPreferences.put(ctx, STRING_KEY, null);
		assertTrue("Null value for String should be quite normal",
			prefs.contains(STRING_KEY));
		assertEquals(null, prefs.getString(STRING_KEY, null));
		// I have put null and specify a default I get the default back not null
		assertEquals(DEFAULT_STRING,
			AccessPreferences.get(ctx, STRING_KEY, DEFAULT_STRING));
		// or a boolean...
		assertEquals(DEFAULT_BOOLEAN,
			(boolean) AccessPreferences.get(ctx, STRING_KEY, DEFAULT_BOOLEAN));
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testPutNullStringSets() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			AccessPreferences.put(ctx, SET_KEY, null);
			assertTrue("Null value for string set is quite normal",
				prefs.contains(SET_KEY));
			assertEquals(null, AccessPreferences.get(ctx, SET_KEY, null));
			// $MATCHERS
			assertEquals(DEFAULT_STRING_SET,
				AccessPreferences.get(ctx, SET_KEY, DEFAULT_STRING_SET));
			// Anything goes...
			assertEquals(DEFAULT_STRING,
				AccessPreferences.get(ctx, SET_KEY, DEFAULT_STRING));
		}
	}
}
