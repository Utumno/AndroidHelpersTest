package gr.uoa.di.android.helpers.test;

import gr.uoa.di.android.helpers.AccessPreferences;

public final class AccessPreferencesStringTest extends AccessPreferencesTest {

	public void testPutString() {
		AccessPreferences.put(ctx, STRING_KEY, DEFAULT_STRING);
		String s = AccessPreferences.get(ctx, STRING_KEY, DEFAULT_STRING
			+ "change the default");
		assertEquals(DEFAULT_STRING, s);
	}
}
