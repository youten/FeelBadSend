
package youten.redo.feelbadsend;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Settings extends AppCompatActivity {
    // key
    public static final String KEY_MAILTO1 = "pref_key_mailto1";
    public static final String KEY_MAILTO2 = "pref_key_mailto2";
    public static final String KEY_MAILTO3 = "pref_key_mailto3";
    public static final String KEY_MAILCC1 = "pref_key_mailcc1";
    public static final String KEY_MAILCC2 = "pref_key_mailcc2";
    public static final String KEY_MAILCC3 = "pref_key_mailcc3";
    public static final String KEY_MAILBCC1 = "pref_key_mailbcc1";
    public static final String KEY_MAILBCC2 = "pref_key_mailbcc2";
    public static final String KEY_MAILBCC3 = "pref_key_mailbcc3";
    public static final String KEY_SUBJECT1 = "pref_key_subject1";
    public static final String KEY_SUBJECT2 = "pref_key_subject2";
    public static final String KEY_SUBJECT3 = "pref_key_subject3";
    public static final String KEY_PREFIX = "pref_key_prefix";
    public static final String KEY_PREFIX2 = "pref_key_prefix2";
    public static final String KEY_PREFIX3 = "pref_key_prefix3";
    public static final String KEY_BODY1 = "pref_key_body1";
    public static final String KEY_BODY2 = "pref_key_body2";
    public static final String KEY_BODY3 = "pref_key_body3";
    public static final String KEY_SUFFIX = "pref_key_suffix";
    public static final String KEY_SUFFIX2 = "pref_key_suffix2";
    public static final String KEY_SUFFIX3 = "pref_key_suffix3";
    public static final String KEY_LINKEDTO = "pref_key_linkedto";
    public static final boolean DEFVALUE_LINKEDTO = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_white);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

            // set Summary
            updateSummary(getPreferenceScreen().getSharedPreferences());
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                    this);
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updateSummary(sharedPreferences);
        }

        /**
         * サマリ更新
         *
         * @param sharedPreferences SharedPreferences
         */
        private void updateSummary(SharedPreferences sharedPreferences) {
            findPreference(KEY_MAILTO1).setSummary(
                    sharedPreferences.getString(KEY_MAILTO1,
                            getString(R.string.pref_mailto1_defvalue)));
            findPreference(KEY_MAILTO2).setSummary(
                    sharedPreferences.getString(KEY_MAILTO2,
                            getString(R.string.pref_mailto2_defvalue)));
            findPreference(KEY_MAILTO3).setSummary(
                    sharedPreferences.getString(KEY_MAILTO3,
                            getString(R.string.pref_mailto3_defvalue)));

            findPreference(KEY_MAILCC1).setSummary(
                    sharedPreferences.getString(KEY_MAILCC1,
                            getString(R.string.pref_mailcc1_defvalue)));
            findPreference(KEY_MAILCC2).setSummary(
                    sharedPreferences.getString(KEY_MAILCC2,
                            getString(R.string.pref_mailcc2_defvalue)));
            findPreference(KEY_MAILCC3).setSummary(
                    sharedPreferences.getString(KEY_MAILCC3,
                            getString(R.string.pref_mailcc3_defvalue)));

            findPreference(KEY_MAILBCC1).setSummary(
                    sharedPreferences.getString(KEY_MAILBCC1,
                            getString(R.string.pref_mailbcc1_defvalue)));
            findPreference(KEY_MAILBCC2).setSummary(
                    sharedPreferences.getString(KEY_MAILBCC2,
                            getString(R.string.pref_mailbcc2_defvalue)));
            findPreference(KEY_MAILBCC3).setSummary(
                    sharedPreferences.getString(KEY_MAILBCC3,
                            getString(R.string.pref_mailbcc3_defvalue)));

            findPreference(KEY_SUBJECT1).setSummary(
                    sharedPreferences
                            .getString(KEY_SUBJECT1, getString(R.string.pref_subject1_defvalue)));
            findPreference(KEY_SUBJECT2).setSummary(
                    sharedPreferences
                            .getString(KEY_SUBJECT2, getString(R.string.pref_subject2_defvalue)));
            findPreference(KEY_SUBJECT3).setSummary(
                    sharedPreferences
                            .getString(KEY_SUBJECT3, getString(R.string.pref_subject3_defvalue)));

            findPreference(KEY_PREFIX).setSummary(
                    sharedPreferences.getString(KEY_PREFIX, getString(R.string.pref_prefix_defvalue)));
            findPreference(KEY_PREFIX2).setSummary(
                    sharedPreferences.getString(KEY_PREFIX2,
                            getString(R.string.pref_prefix2_defvalue)));
            findPreference(KEY_PREFIX3).setSummary(
                    sharedPreferences.getString(KEY_PREFIX3,
                            getString(R.string.pref_prefix3_defvalue)));

            findPreference(KEY_BODY1).setSummary(
                    sharedPreferences.getString(KEY_BODY1, getString(R.string.pref_body1_defvalue)));
            findPreference(KEY_BODY2).setSummary(
                    sharedPreferences.getString(KEY_BODY2, getString(R.string.pref_body2_defvalue)));
            findPreference(KEY_BODY3).setSummary(
                    sharedPreferences.getString(KEY_BODY3, getString(R.string.pref_body3_defvalue)));

            findPreference(KEY_SUFFIX).setSummary(
                    sharedPreferences.getString(KEY_SUFFIX, getString(R.string.pref_suffix_defvalue)));
            findPreference(KEY_SUFFIX2).setSummary(
                    sharedPreferences.getString(KEY_SUFFIX2,
                            getString(R.string.pref_suffix2_defvalue)));
            findPreference(KEY_SUFFIX3).setSummary(
                    sharedPreferences.getString(KEY_SUFFIX3,
                            getString(R.string.pref_suffix3_defvalue)));

            // linked To: モード
            if (sharedPreferences.getBoolean(KEY_LINKEDTO, DEFVALUE_LINKEDTO)) {
                findPreference(KEY_MAILCC1).setTitle(getString(R.string.pref_mailcc1_text_depend));
                findPreference(KEY_MAILCC2).setTitle(getString(R.string.pref_mailcc2_text_depend));
                findPreference(KEY_MAILCC3).setTitle(getString(R.string.pref_mailcc3_text_depend));
                findPreference(KEY_MAILBCC1).setTitle(getString(R.string.pref_mailbcc1_text_depend));
                findPreference(KEY_MAILBCC2).setTitle(getString(R.string.pref_mailbcc2_text_depend));
                findPreference(KEY_MAILBCC3).setTitle(getString(R.string.pref_mailbcc3_text_depend));

                findPreference(KEY_SUBJECT1).setTitle(getString(R.string.pref_subject1_text_depend));
                findPreference(KEY_SUBJECT2).setTitle(getString(R.string.pref_subject2_text_depend));
                findPreference(KEY_SUBJECT3).setTitle(getString(R.string.pref_subject3_text_depend));

                findPreference(KEY_PREFIX).setTitle(getString(R.string.pref_prefix_text_depend));

                findPreference(KEY_BODY1).setTitle(getString(R.string.pref_body1_text_depend));
                findPreference(KEY_BODY2).setTitle(getString(R.string.pref_body2_text_depend));
                findPreference(KEY_BODY3).setTitle(getString(R.string.pref_body3_text_depend));

                findPreference(KEY_SUFFIX).setTitle(getString(R.string.pref_suffix_text_depend));

            } else {
                findPreference(KEY_MAILCC1).setTitle(getString(R.string.pref_mailcc1_text));
                findPreference(KEY_MAILCC2).setTitle(getString(R.string.pref_mailcc2_text));
                findPreference(KEY_MAILCC3).setTitle(getString(R.string.pref_mailcc3_text));
                findPreference(KEY_MAILBCC1).setTitle(getString(R.string.pref_mailbcc1_text));
                findPreference(KEY_MAILBCC2).setTitle(getString(R.string.pref_mailbcc2_text));
                findPreference(KEY_MAILBCC3).setTitle(getString(R.string.pref_mailbcc3_text));

                findPreference(KEY_SUBJECT1).setTitle(getString(R.string.pref_subject1_text));
                findPreference(KEY_SUBJECT2).setTitle(getString(R.string.pref_subject2_text));
                findPreference(KEY_SUBJECT3).setTitle(getString(R.string.pref_subject3_text));

                findPreference(KEY_PREFIX).setTitle(getString(R.string.pref_prefix_text));

                findPreference(KEY_BODY1).setTitle(getString(R.string.pref_body1_text));
                findPreference(KEY_BODY2).setTitle(getString(R.string.pref_body2_text));
                findPreference(KEY_BODY3).setTitle(getString(R.string.pref_body3_text));

                findPreference(KEY_SUFFIX).setTitle(getString(R.string.pref_suffix_text));
            }
        }
    }
}
