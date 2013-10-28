package gr.uoa.di.android.helpers.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;

import gr.uoa.di.android.helpers.AccessPreferences;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

abstract class AccessPreferencesTest extends AndroidTestCase {

	Context ctx;
	SharedPreferences prefs;
	Editor ed;
	static final String STRING_KEY = "STRING_KEY";
	static final String BOOLEAN_KEY = "BOOLEAN_KEY";
	static final boolean DEFAULT_BOOLEAN = true;
	static final String INT_KEY = "INT_KEY";
	static final String LONG_KEY = "LONG_KEY";
	static final String FLOAT_KEY = "FLOAT_KEY";
	static final String UNKNOWN_KEY = "UNKNOWN_KEY";
	static final String SET_KEY = "SET_KEY";
	static final String DEFAULT_STRING = "DEFAULT_STRING";
	static final Set<String> DEFAULT_STRING_SET = Collections.emptySet();
	static final Boolean NULL_BOOLEAN = null;
	static final long DEFAULT_LONG = 87687609876097L;
	static final int DEFAULT_INTEGER = Integer.MAX_VALUE;
	static final float DEFAULT_FLOAT = Float.MAX_VALUE;
	// logging
	final String TAG = this.getClass().getSimpleName();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ctx = getContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		ed = prefs.edit();
	}

	@Override
	protected void tearDown() throws Exception {
		// ugly but so so true
		Field f = AccessPreferences.class.getDeclaredField("prefs");
		f.setAccessible(true);
		// f.set(null, null); // no use
		final SharedPreferences sp = (SharedPreferences) f.get(null);
		if (sp != null) sp.edit().clear().commit(); // TODO : how does this
		// behave with null keys ?
		if (ed != null) ed.clear().commit();
		super.tearDown();
	}

	static void assertEquals(float expected, float actual) {
		assertEquals(expected, actual, Float.MIN_VALUE);
	}
}
