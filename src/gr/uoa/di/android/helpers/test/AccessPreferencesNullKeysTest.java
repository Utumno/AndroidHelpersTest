package gr.uoa.di.android.helpers.test;

public final class AccessPreferencesNullKeysTest extends AccessPreferencesTest {

	private static final String NULL_KEY = null;

	// =========================================================================
	// Tests for android's SharedPreferences behavior
	// =========================================================================
	// Strings (same should go for Sets)
	/**
	 * Testing what happens when I have a {@code null} key and I put a
	 * {@code null} value - I CAN RETRIEVE IT AS ANYTHING - I just get the
	 * default back
	 */
	public void testNullKeyNullString() {
		ed.putString(NULL_KEY, null);
		ed.commit();
		assertTrue("Contains null key", prefs.contains(NULL_KEY));
		// I had the assert calls below in different methods but too much hassle
		assertEquals(
			"Retrieve the value giving null default - I get null back", null,
			prefs.getString(NULL_KEY, null));
		assertEquals("Retrieve the value giving default - I get default back",
			DEFAULT_STRING, prefs.getString(NULL_KEY, DEFAULT_STRING));
		// ANYTHING GOES !!!!
		assertEquals("Retrieve the value AS BOOLEAN", true,
			prefs.getBoolean(NULL_KEY, true));
	}

	/**
	 * Testing what happens when I have a {@code null} key and I put a NON
	 * {@code null} value - the value is stored normally !
	 */
	public void testNullKeyNonNullString() {
		ed.putString(NULL_KEY, DEFAULT_STRING);
		ed.commit();
		assertTrue("Contains null key", prefs.contains(NULL_KEY));
		assertEquals("Retrieve the value giving null default", DEFAULT_STRING,
			prefs.getString(NULL_KEY, null));
		assertEquals("Retrieve the value giving default", DEFAULT_STRING,
			prefs.getString(NULL_KEY, DEFAULT_STRING + "blah"));
		assertEquals("Retrieve the value giving default - I get default back",
			DEFAULT_STRING, prefs.getString(NULL_KEY, DEFAULT_STRING));
		// ANYTHING DOES NOT GO !!!!
		try {
			prefs.getBoolean(NULL_KEY, true);
			fail("Expected CCE");
		} catch (ClassCastException e) {}
	}

	// Booleans (same goes for Float, Integer and Long)
	/** Testing what happens when I have a {@code null} key & a boolean value */
	public void testNullKeyNonNullBoolean() {
		ed.putBoolean(NULL_KEY, DEFAULT_BOOLEAN);
		ed.commit();
		assertEquals(DEFAULT_BOOLEAN,
			prefs.getBoolean(NULL_KEY, DEFAULT_BOOLEAN));
		assertEquals(DEFAULT_BOOLEAN,
			prefs.getBoolean(NULL_KEY, !DEFAULT_BOOLEAN));
		try {
			prefs.getString(NULL_KEY, DEFAULT_STRING);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// key : null --> value : Boolean
		}
	}
}
