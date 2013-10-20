package gr.uoa.di.android.helpers.test;

import gr.uoa.di.android.helpers.AccessPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.util.Log;

public class AccessPreferencesTest extends AndroidTestCase {

	private static Context ctx;
	private static SharedPreferences prefs;
	private static final String STRING_KEY = "STRING_KEY";
	Editor e;
	private static final String INT_KEY = "INT_KEY";
	private static final String LONG_KEY = "LONG_KEY";
	private static final String FLOAT_KEY = "FLOAT_KEY";
	private static final String SET_KEY = "SET_KEY";
	private static final String BOOLEAN_KEY = "BOOLEAN_KEY";
	private static final String DEFAULT_STRING = "DEFAULT_STRING";
	private static final Set<String> DEFAULT_STRING_SET = Collections
			.emptySet();
	private static final boolean DEFAULT_BOOLEAN = true;
	private static final Boolean NULL_BOOLEAN = null;
	private static final long DEFAULT_LONG = 87687609876097L;
	private static final int DEFAULT_INTEGER = Integer.MAX_VALUE;
	private static final float DEFAULT_FLOAT = Float.MAX_VALUE;

	// =========================================================================
	// Tests for android's SharedPreferences behavior
	// =========================================================================
	/** Testing what happens when I put/retrieve Strings {@code null} */
	public void testNullString() {
		e.putString(STRING_KEY, null);
		e.commit();
		assertEquals(prefs.getString(STRING_KEY, null), null);
		// I have put null and specify a default I get the default back not null
		assertEquals(prefs.getString(STRING_KEY, DEFAULT_STRING),
			DEFAULT_STRING);
	}

	/** Testing what happens when I put/retrieve Set<String> {@code null} */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testNullStringSets() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			e.putStringSet(SET_KEY, null);
			e.commit();
			assertEquals(prefs.getStringSet(SET_KEY, null), null);
			assertEquals(prefs.getStringSet(SET_KEY, DEFAULT_STRING_SET),
				DEFAULT_STRING_SET);
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
	/**
	 * Testing what happens when I put/retrieve Boolean {@code null}. Same
	 * should happen with Float, Integer and Long : e.putFloat(FLOAT_KEY,
	 * (Float) null); e.putInt(INT_KEY, (Integer) null); e.putLong(LONG_KEY,
	 * (Long) null);
	 */
	// @SuppressWarnings("null")
	public void testNullBollean() {
		// notice I need casts
		try {
			e.putBoolean(BOOLEAN_KEY, NULL_BOOLEAN); // I GET NO WARNING WHEN I
			// USE A VARIABLE !
			fail("Expected NPE");
		} catch (NullPointerException e1) {
			// CAUSE putBoolean(String arg0, ***boolean*** arg1) -->unboxes null
		}
		e.commit();
		assertEquals(prefs.getString(STRING_KEY, null), null);
		try {
			assertEquals(prefs.getBoolean(BOOLEAN_KEY, (Boolean) null), null);
			fail("Expected NPE");
		} catch (NullPointerException e1) {
			// CAUSE getBoolean(String arg0, ***boolean*** arg1) -->unboxes null
		}
	}

	// NULL KEYS
	/**
	 * Testing what happens when I have a {@code null} key and I retrieve the
	 * value giving a {@code null} default - I get null back
	 */
	public void testNullKeyString() {
		e.putString(null, null);
		e.commit();
		assertEquals(prefs.getString(null, null), null);
	}

	/**
	 * Testing what happens when I have a {@code null} key and I retrieve the
	 * value giving a DEFAULT_STRING default - I get the DEFAULT_STRING back NOT
	 * {@code null}
	 */
	public void testNullKeyStringDefault() {
		e.putString(null, null);
		e.commit();
		assertEquals(prefs.getString(null, DEFAULT_STRING), DEFAULT_STRING);
	}

	/** Testing what happens when I have a {@code null} key */
	public void testNullKeyBoolean() {
		e.putBoolean(null, DEFAULT_BOOLEAN);
		e.commit();
		assertEquals(prefs.getBoolean(null, DEFAULT_BOOLEAN), DEFAULT_BOOLEAN);
	}

	/**
	 * null is a key - CCE when I put (null,boolean) and ask for (null,String)
	 */
	public void testNullKeyBooleanGetString() {
		e.putBoolean(null, DEFAULT_BOOLEAN);
		e.commit();
		try {
			prefs.getString(null, DEFAULT_STRING);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// key : null --> value : Boolean
		}
	}

	/**
	 * NPE when I put null for Boolean value due to unboxing null BUT THE
	 * PREFERENCE IS SET TO BOOLEAN - so when I ask for a String I get CCE (?!).
	 * When I try to retrieve a Boolean or boolean all fine
	 */
	@SuppressWarnings("null")
	public void testNullKeyBooleanPutNullBoolean() {
		try {
			e.putBoolean(null, (Boolean) null); // Warning: Null pointer access:
			// This expression of type Boolean is null but requires
			// auto-unboxing
			fail("Expected NPE");
		} catch (NullPointerException e) {
			// CAUSE putBoolean(String arg0, ***boolean*** arg1)
		}
		e.commit();
		try {
			prefs.getString(null, DEFAULT_STRING);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// key : null --> value : Boolean
		}
		Boolean b = prefs.getBoolean(null, DEFAULT_BOOLEAN);
		assertEquals((Boolean) DEFAULT_BOOLEAN, b);
		boolean b1 = prefs.getBoolean(null, DEFAULT_BOOLEAN);
		assertEquals(DEFAULT_BOOLEAN, b1);
	}

	/**
	 * null is a key - when I put (null,String) and ask for (null,Boolean) *NO
	 * CCE* - I just get the DEFAULT_BOOLEAN I ask for
	 */
	public void testNullKeyStringGetNullBoolean() {
		e.putString(null, null);
		e.commit();
		boolean b = prefs.getBoolean(null, DEFAULT_BOOLEAN);
		assertEquals(DEFAULT_BOOLEAN, b);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ctx = getContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		e = prefs.edit();
	}

	public void testScenariosMoveMeToJunit() {
		String nullKey = null;
		String nullValue = null;
		e.putString(nullKey, nullValue).commit();
		assertTrue("Contains null", prefs.contains(null));
		d("prefs.getString(nullKey, \"do you see me or null\")"
			+ prefs.getString(nullKey, "do you see me or null"));
		d("prefs.getString(nullKey, nullValue)"
			+ prefs.getString(nullKey, nullValue));
		d("prefs.getBoolean(nullKey, true)" + prefs.getBoolean(nullKey, true));
		String s = AccessPreferences.get(ctx, nullKey, null);
		e.putBoolean("boolean_key", true).commit();
		try {
			/* String sFromBool = */AccessPreferences.get(ctx, "boolean_key",
				null);
			fail("Expected CCE");
		} catch (ClassCastException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// boolean b = AccessPreferences.retrieve(this, nullKey, null); // NPE
		// boolean b = AccessPreferences.retrieve(this, nullKey,
		// "how about giving a string"); // wont compile
		d("s : " + s + " b: " + "");
		e.putString(nullKey, "I put a sting with null key").commit();
		d(prefs.getString(nullKey, "do you see me or the string"));
		d(prefs.getString(nullKey, nullValue) + "");
		e.putString("non null key null value", null).commit();
		assertTrue("Contains non null key null value",
			prefs.contains("non null key null value"));
		d(prefs.getString("non null key null value", "DO YOU SEE ME OR NULL"));
		d(prefs.getString("UNKNOWN KEY", "DO YOU SEE ME OR NULL"));
		// d(prefs.getBoolean(nullKey, true) + ""); //ClassCastException
		AccessPreferences.put(ctx, nullKey, nullValue);
		AccessPreferences.get(ctx, nullKey, nullValue);
	}

	private static void d(String string) {
		Log.d(AccessPreferencesTest.class.getSimpleName(), string);
	}

	// =========================================================================
	// Tests for AccessPreferences
	// =========================================================================
	public void testPutNullBoolean() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, null);
		Boolean b = AccessPreferences.get(ctx, BOOLEAN_KEY, null);
		assertEquals(null, b);
	}

	public void testPutNullBooleanGetPrimitiveBoolean() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, null);
		try {
			boolean b = AccessPreferences.get(ctx, BOOLEAN_KEY, null);
			fail("Expected NPE: " + b);
		} catch (NullPointerException e) {
			// on unboxing
		}
	}

	public void testPutBoolean() {
		AccessPreferences.put(ctx, BOOLEAN_KEY, DEFAULT_BOOLEAN);
		// notice I retrieve a primitive
		boolean b = AccessPreferences.get(ctx, BOOLEAN_KEY, null);
		assertEquals(DEFAULT_BOOLEAN, b);
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

	/**
	 * **NO CCE** - it actually returns an integer and promotes it to float
	 */
	public void testPutFloatSeenAsInteger3() {
		AccessPreferences.put(ctx, FLOAT_KEY, 0); // thinks it's Integer
		try {
			float f = AccessPreferences.get(ctx, FLOAT_KEY, 0F);
			assertEquals(0, f, 0);
			fail("Expected CCE");
		} catch (ClassCastException e) {}
	}

	public void testPutInteger() {
		AccessPreferences.put(ctx, INT_KEY, 0);
		Integer i = AccessPreferences.get(ctx, INT_KEY, 0);
		System.out.println(i);
	}

	public void testPutLong() {
		AccessPreferences.put(ctx, LONG_KEY, 0L); // must say it's Long...
		// AccessPreferences.put(ctx, LONG_KEY, 65876587765); //... won't
		// compile : The literal 65876587765 of type int is out of range
		AccessPreferences.get(ctx, LONG_KEY, 0L);
	}

	public void testPutLongDefault() {
		AccessPreferences.put(ctx, LONG_KEY, DEFAULT_LONG);
		long lo = AccessPreferences.get(ctx, LONG_KEY, 0L); // notice primitive
		assertEquals(DEFAULT_LONG, lo);
	}

	public void testPutLongAsInteger() {
		AccessPreferences.put(ctx, LONG_KEY, 0); // must say it's Long...
		try {
			AccessPreferences.get(ctx, LONG_KEY, 0L);
			fail("Expected CCE");
		} catch (ClassCastException e) {
			// ClassCastException: java.lang.Integer
			// at android.app.ContextImpl$SharedPreferencesImpl.getLong(
			// ContextImpl.java:2712)
			// at at ***AccessPreferences***.get(AccessPreferences.java:135)
		}
	}

	public void testPutSet() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			AccessPreferences.put(ctx, SET_KEY, DEFAULT_STRING_SET);
			Set<String> s = AccessPreferences.get(ctx, SET_KEY,
				new HashSet<String>());
			assertEquals(DEFAULT_STRING_SET, s);
		} else {
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

	public void testPutSetRaw() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			final Set<Integer> integerHashSet = new HashSet<Integer>();
			integerHashSet.add(1);
			AccessPreferences.put(ctx, SET_KEY, integerHashSet);
			Set<String> s = AccessPreferences.get(ctx, SET_KEY,
				new HashSet<String>());
			assertEquals(integerHashSet, s);
		} else {
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

	public void testPutString() {
		AccessPreferences.put(ctx, STRING_KEY, DEFAULT_STRING);
		String s = AccessPreferences.get(ctx, STRING_KEY, DEFAULT_STRING
			+ "change the default");
		assertEquals(DEFAULT_STRING, s);
	}
}
