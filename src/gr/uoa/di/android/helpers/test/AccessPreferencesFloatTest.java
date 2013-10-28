package gr.uoa.di.android.helpers.test;

import gr.uoa.di.android.helpers.AccessPreferences;

public final class AccessPreferencesFloatTest extends AccessPreferencesTest {

	public void testPutFloat() {
		AccessPreferences.put(ctx, FLOAT_KEY, DEFAULT_FLOAT);
		Float f = AccessPreferences.get(ctx, FLOAT_KEY, 0F);
		assertEquals(DEFAULT_FLOAT, (float) f);
	}

	public void testPutFloatSeenAsDouble() {
		try {
			AccessPreferences.put(ctx, FLOAT_KEY, 0.0); // thinks it's a Double
			fail("Expected IAE");
		} catch (IllegalArgumentException e) {
			// IllegalArgumentException: The given value : 0.0 cannot be
			// persisted
			// at ****AccessPreferences***.put(AccessPreferences.java:71)
		}
	}

	public void testPutFloatSeenAsInteger() {
		AccessPreferences.put(ctx, FLOAT_KEY, 0); // thinks it's Integer
		try {
			AccessPreferences.get(ctx, FLOAT_KEY, 0.0F);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// ClassCastException: java.lang.Integer
			// at android.app.ContextImpl$SharedPreferencesImpl.getFloat(
			// ContextImpl.java:2718)
			// at ***AccessPreferences***.get(AccessPreferences.java:137)
		}
	}

	public void testPutFloatSeenAsInteger2() {
		AccessPreferences.put(ctx, FLOAT_KEY, 0); // thinks it's Integer
		try {
			// the CCE in NOT thrown at the assignment
			@SuppressWarnings("unused")
			// I assign f to verify the CCE is not thrown here
			Float f = AccessPreferences.get(ctx, FLOAT_KEY, 0F);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// ClassCastException: java.lang.Integer
			// at android.app.ContextImpl$SharedPreferencesImpl.getFloat(
			// ContextImpl.java:2718)
			// at ***AccessPreferences***.get(AccessPreferences.java:137)
		}
	}

	public void testPutFloatSeenAsInteger3() {
		AccessPreferences.put(ctx, FLOAT_KEY, 0); // thinks it's Integer
		try {
			@SuppressWarnings("unused")
			float f = AccessPreferences.get(ctx, FLOAT_KEY, 0F);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// java.lang.ClassCastException: java.lang.Integer cannot be cast to
			// java.lang.Float at
			// android.app.SharedPreferencesImpl.getFloat(
			// SharedPreferencesImpl.java:254) at
			// gr.uoa.di.android.helpers.AccessPreferences.get(
			// AccessPreferences.java:260)
		}
	}
}
