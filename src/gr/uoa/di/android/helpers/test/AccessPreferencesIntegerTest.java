package gr.uoa.di.android.helpers.test;

import gr.uoa.di.android.helpers.AccessPreferences;

public final class AccessPreferencesIntegerTest extends AccessPreferencesTest {

	public void testPutInteger() {
		AccessPreferences.put(ctx, INT_KEY, DEFAULT_INTEGER);
		int i = AccessPreferences.get(ctx, INT_KEY, 0);
		assertEquals(DEFAULT_INTEGER, i);
	}
}
