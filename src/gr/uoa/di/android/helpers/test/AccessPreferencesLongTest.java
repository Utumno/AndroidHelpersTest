package gr.uoa.di.android.helpers.test;

import gr.uoa.di.android.helpers.AccessPreferences;

public final class AccessPreferencesLongTest extends AccessPreferencesTest {

	public void testPutLong() {
		AccessPreferences.put(ctx, LONG_KEY, 0L); // must say it's Long...
		// AccessPreferences.put(ctx, LONG_KEY, 65876587765); //... won't
		// compile : The literal 65876587765 of type int is out of range
		long actual = AccessPreferences.get(ctx, LONG_KEY, 0L);
		assertEquals(0, actual);
	}

	public void testPutLongDefault() {
		AccessPreferences.put(ctx, LONG_KEY, DEFAULT_LONG);
		long actual = AccessPreferences.get(ctx, LONG_KEY, 0L); // primitive !
		assertEquals(DEFAULT_LONG, actual);
	}

	public void testPutLongAsInteger() {
		AccessPreferences.put(ctx, LONG_KEY, 0); // must say it's Long...
		try {
			AccessPreferences.get(ctx, LONG_KEY, Long.MAX_VALUE);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// ClassCastException: java.lang.Integer
			// at android.app.ContextImpl$SharedPreferencesImpl.getLong(
			// ContextImpl.java:2712)
			// at ***AccessPreferences***.get(AccessPreferences.java:135)
		}
	}

	// public void testPutLongAsIntegerGetInteger() {
	// AccessPreferences.put(ctx, LONG_KEY, 0); // must say it's Long...
	// try {
	// // Long lo = AccessPreferences.get(ctx, LONG_KEY, 0); // WON'T COMPILE!
	// fail("Expected CCE");
	// } catch (ClassCastException e) {
	// // ClassCastException: java.lang.Integer
	// // at android.app.ContextImpl$SharedPreferencesImpl.getLong(
	// // ContextImpl.java:2712)
	// // at at ***AccessPreferences***.get(AccessPreferences.java:135)
	// }
	// }
}
