package gr.uoa.di.android.helpers.test;

import gr.uoa.di.android.helpers.AccessPreferences;

public final class AccessPreferencesBooleanTest extends AccessPreferencesTest {

	public void testPutBoolean() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, DEFAULT_BOOLEAN);
		// notice I retrieve a primitive
		boolean b = AccessPreferences.get(ctx, BOOLEAN_KEY, null);
		assertEquals(DEFAULT_BOOLEAN, b);
	}
}
