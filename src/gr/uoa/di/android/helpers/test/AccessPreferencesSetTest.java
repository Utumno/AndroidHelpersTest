package gr.uoa.di.android.helpers.test;

import android.annotation.TargetApi;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.util.Log;

import gr.uoa.di.android.helpers.AccessPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class AccessPreferencesSetTest extends AccessPreferencesTest {

	private static final Set<String> STRING_SET = new HashSet<String>(
		Arrays.asList(new String[] { DEFAULT_STRING }));
	@SuppressWarnings({ "rawtypes", "unchecked" })
	// if the set is parameterized as <Integer> won't let me put it into prefs
	// the unchecked is : The constructor HashSet(Collection) belongs to the raw
	// type HashSet. References to generic type HashSet<E> should be
	// parameterized
	private static final Set RAW_INTEGERS_SET = new HashSet(
		Arrays.asList(new int[] { 1 }));

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testNullStringSetsRaw() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			@SuppressWarnings("unchecked")
			// use dummy variable to focus the warning
			Editor dummy = ed.putStringSet(SET_KEY, RAW_INTEGERS_SET);
			dummy.commit();
			final Set<String> actual = prefs.getStringSet(SET_KEY, STRING_SET);
			// $MATCHERS (although works ! :
			// junit.framework.AssertionFailedError: expected:<[1]> but was:<[]>
			// in assertEquals(integerHashSet, s);) // this error was cause I
			// forgot to call ed.commit()
			final String msg = "The set I put in: " + RAW_INTEGERS_SET
				+ " and what I got out :" + actual;
			Log.e(TAG, msg);
			assertTrue(msg, RAW_INTEGERS_SET.equals(actual));
			assertTrue(actual.contains(1)); // !
			assertEquals(new String[] { "1" }, actual.toArray(new String[1]));
		}
	}

	// @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	// public void testNullStringSetsRaw() {
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	// final Set<Integer> integerHashSet = new HashSet<Integer>();
	// integerHashSet.add(1);
	// // e.putStringSet(SET_KEY, integerHashSet); // won't compile !!!
	// Set<String> s = prefs.getStringSet(SET_KEY, new HashSet<String>());
	// assertEquals(integerHashSet, s);
	// }
	// }
	// =========================================================================
	// Access Preferences
	// =========================================================================
	public void testPutSet() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			AccessPreferences.put(ctx, SET_KEY, DEFAULT_STRING_SET);
			Set<String> s = AccessPreferences.get(ctx, SET_KEY,
				new HashSet<String>());
			assertEquals(DEFAULT_STRING_SET, s);
		}
	}

	public void testPutSetRaw() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			AccessPreferences.put(ctx, SET_KEY, RAW_INTEGERS_SET);
			Set<String> s = AccessPreferences.get(ctx, SET_KEY,
				new HashSet<String>());
			Log.e(TAG, "Set print: " + Arrays.toString(s.toArray()));
			assertEquals(RAW_INTEGERS_SET, s); // $MATCHERS
		}
	}

	public void testPutSetBeforeHONEYCOMB() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			try {
				AccessPreferences.put(ctx, SET_KEY, DEFAULT_STRING_SET);
				fail("Expected IAE");
			} catch (IllegalArgumentException e) {
				// You can add sets in the preferences only after API 11
			}
			try {
				AccessPreferences.get(ctx, SET_KEY, DEFAULT_STRING_SET);
				fail("Expected IAE");
			} catch (IllegalArgumentException e) {
				// You can add sets in the preferences only after API 11
			}
		}
	}
}
