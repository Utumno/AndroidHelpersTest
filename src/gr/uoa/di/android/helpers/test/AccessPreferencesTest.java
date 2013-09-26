package gr.uoa.di.android.helpers.test;

import java.util.Collections;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;

public class AccessPreferencesTest extends AndroidTestCase {

	private static Context ctx;
	private static SharedPreferences prefs;
	private Editor e;
	private static final String STRING_KEY = "STRING_KEY";
	private static final String INT_KEY = "INT_KEY";
	private static final String LONG_KEY = "LONG_KEY";
	private static final String FLOAT_KEY = "FLOAT_KEY";
	private static final String SET_KEY = "SET_KEY";
	private static final String BOOLEAN_KEY = "BOOLEAN_KEY";
	private static final String DEFAULT_STRING = "DEFAULT_STRING";
	private static final Set<String> DEFAULT_STRING_SET = Collections
			.emptySet();
	private static final boolean DEFAULT_BOOLEAN = true;

	// =========================================================================
	// Tests for android's SharedPreferences behavior
	// =========================================================================
	/** Testing what happens when I put/retrieve Strings {@code null} */
	public void testNullString() {
		e.putString(STRING_KEY, null);
		e.commit();
		assertEquals(prefs.getString(STRING_KEY, null), null);
		assertEquals(prefs.getString(STRING_KEY, DEFAULT_STRING),
			DEFAULT_STRING);
	}

	/** Testing what happens when I put/retrieve Set<String> {@code null} */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void testNullStringSets() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			e.putStringSet(SET_KEY, null);
		e.commit();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			assertEquals(prefs.getStringSet(SET_KEY, null), null);
			assertEquals(prefs.getStringSet(SET_KEY, DEFAULT_STRING_SET),
				DEFAULT_STRING_SET);
		}
	}

	/** Testing what happens when I put/retrieve Boolean {@code null} */
	public void testNullBollean() {
		// notice I need casts
		try {
			e.putBoolean(BOOLEAN_KEY, (Boolean) null);
			// e.putFloat(FLOAT_KEY, (Float) null);
			// e.putInt(INT_KEY, (Integer) null);
			// e.putLong(LONG_KEY, (Long) null);
			fail("Expected NPE");
		} catch (NullPointerException e1) {
			// on unboxing null
		}
		e.commit();
		assertEquals(prefs.getString(STRING_KEY, null), null);
		try {
			assertEquals(prefs.getBoolean(BOOLEAN_KEY, (Boolean) null), null);
			// assertEquals(prefs.getFloat(FLOAT_KEY, (Float) null), null);
			// assertEquals(prefs.getInt(INT_KEY, (Integer) null), null);
			// assertEquals(prefs.getLong(LONG_KEY, (Long) null), null);
		} catch (NullPointerException e1) {
			// on unboxing null
		}
	}

	// NULL KEYS
	/** Testing what happens when I have a null key {@code null} */
	public void testNullKeyString() {
		e.putString(null, null);
		e.commit();
		assertEquals(prefs.getString(null, null), null);
	}

	/** Testing what happens when I have a null key {@code null} */
	public void testNullKeyStringDefault() {
		e.putString(null, null);
		e.commit();
		assertEquals(prefs.getString(null, DEFAULT_STRING), DEFAULT_STRING);
	}

	/** Testing what happens when I have a null key {@code null} */
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
		} catch (ClassCastException e1) {
			// key : null --> value : Boolean
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ctx = getContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		e = prefs.edit();
	}
}
