package youten.redo.feelbadsend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import youten.redo.feelbadsend.util.Util;


public class MainActivity extends AppCompatActivity {
    /** log tag */
    private static final String TAG = "FeelBadSend";

    /** デフォルトデリミタ */
    private static final String DELIM = "$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Preferenceから設定をload
        loadPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_white);

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Spinner mailccSpinner = (Spinner) findViewById(R.id.mailcc_spinner);
                Spinner mailbccSpinner = (Spinner) findViewById(R.id.mailbcc_spinner);
                Spinner mailtoSpinner = (Spinner) findViewById(R.id.mailto_spinner);
                Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
                TextView prefixText = (TextView) findViewById(R.id.prefix_text);
                Spinner bodySpinner = (Spinner) findViewById(R.id.body_spinner);
                TextView suffixText = (TextView) findViewById(R.id.suffix_text);

                StringBuffer sb = new StringBuffer("");
                sb.append(prefixText.getText()).append("\n");
                if (bodySpinner.getSelectedItem() != null) {
                    sb.append(bodySpinner.getSelectedItem().toString()).append("\n");
                }
                sb.append(suffixText.getText());

                // Intentを変更
                // 参考：http://tomokey.blogspot.com/2011/03/android.html
                // Uri uri = Uri.parse("mailto:" + mailtoSpinner.getSelectedItem().toString());
                // Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                if (mailtoSpinner.getSelectedItem() != null) {
                    intent.putExtra(Intent.EXTRA_EMAIL, Util.parseCSV(mailtoSpinner.getSelectedItem().toString()));
                }
                if (mailccSpinner.getSelectedItem() != null) {
                    intent.putExtra(Intent.EXTRA_CC, Util.parseCSV(mailccSpinner.getSelectedItem().toString()));
                }
                if (mailbccSpinner.getSelectedItem() != null) {
                    intent.putExtra(Intent.EXTRA_BCC, Util.parseCSV(mailbccSpinner.getSelectedItem().toString()));
                }
                if (subjectSpinner.getSelectedItem() != null) {
                    intent.putExtra(Intent.EXTRA_SUBJECT, subjectSpinner.getSelectedItem().toString());
                }
                intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    /**
     * PreferenceをロードしてUIに反映
     */
    private void loadPreference() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        Spinner mailtoSpinner = (Spinner) findViewById(R.id.mailto_spinner);
        Spinner mailccSpinner = (Spinner) findViewById(R.id.mailcc_spinner);
        Spinner mailbccSpinner = (Spinner) findViewById(R.id.mailbcc_spinner);
        Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
        TextView prefixText = (TextView) findViewById(R.id.prefix_text);
        Spinner bodySpinner = (Spinner) findViewById(R.id.body_spinner);
        TextView suffixText = (TextView) findViewById(R.id.suffix_text);

        { // To:
            String mailto1 = p.getString(Settings.KEY_MAILTO1,
                    getString(R.string.pref_mailto1_defvalue));
            String mailto2 = p.getString(Settings.KEY_MAILTO2,
                    getString(R.string.pref_mailto2_defvalue));
            String mailto3 = p.getString(Settings.KEY_MAILTO3,
                    getString(R.string.pref_mailto3_defvalue));
            ArrayList<String> mailtoList = new ArrayList<String>();
            if (StringUtils.isNotBlank(mailto1)) {
                mailtoList.add(mailto1);
            }
            if (StringUtils.isNotBlank(mailto2)) {
                mailtoList.add(mailto2);
            }
            if (StringUtils.isNotBlank(mailto3)) {
                mailtoList.add(mailto3);
            }

            ArrayAdapter<String> mailtoSpinnerAdaper = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mailtoList);
            mailtoSpinnerAdaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mailtoSpinner.setAdapter(mailtoSpinnerAdaper);
        }

        { // Cc:
            String mailcc1 = p.getString(Settings.KEY_MAILCC1,
                    getString(R.string.pref_mailcc1_defvalue));
            String mailcc2 = p.getString(Settings.KEY_MAILCC2,
                    getString(R.string.pref_mailcc2_defvalue));
            String mailcc3 = p.getString(Settings.KEY_MAILCC3,
                    getString(R.string.pref_mailcc3_defvalue));
            ArrayList<String> mailccList = new ArrayList<String>();
            if (StringUtils.isNotBlank(mailcc1)) {
                mailccList.add(mailcc1);
            }
            if (StringUtils.isNotBlank(mailcc2)) {
                mailccList.add(mailcc2);
            }
            if (StringUtils.isNotBlank(mailcc3)) {
                mailccList.add(mailcc3);
            }

            ArrayAdapter<String> mailccSpinnerAdaper = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mailccList);
            mailccSpinnerAdaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mailccSpinner.setAdapter(mailccSpinnerAdaper);
        }

        { // Bcc:
            String mailbcc1 = p.getString(Settings.KEY_MAILBCC1,
                    getString(R.string.pref_mailbcc1_defvalue));
            String mailbcc2 = p.getString(Settings.KEY_MAILBCC2,
                    getString(R.string.pref_mailbcc2_defvalue));
            String mailbcc3 = p.getString(Settings.KEY_MAILBCC3,
                    getString(R.string.pref_mailbcc3_defvalue));
            ArrayList<String> mailbccList = new ArrayList<String>();
            if (StringUtils.isNotBlank(mailbcc1)) {
                mailbccList.add(mailbcc1);
            }
            if (StringUtils.isNotBlank(mailbcc2)) {
                mailbccList.add(mailbcc2);
            }
            if (StringUtils.isNotBlank(mailbcc3)) {
                mailbccList.add(mailbcc3);
            }

            ArrayAdapter<String> mailbccSpinnerAdaper = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mailbccList);
            mailbccSpinnerAdaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mailbccSpinner.setAdapter(mailbccSpinnerAdaper);
        }

        { // Subject:
            String subject1 = p.getString(Settings.KEY_SUBJECT1,
                    getString(R.string.pref_subject1_defvalue));
            String subject2 = p.getString(Settings.KEY_SUBJECT2,
                    getString(R.string.pref_subject2_defvalue));
            String subject3 = p.getString(Settings.KEY_SUBJECT3,
                    getString(R.string.pref_subject3_defvalue));
            ArrayList<String> subjectList = new ArrayList<String>();
            if (StringUtils.isNotBlank(subject1)) {
                subjectList.add(Util.valueSDF(subject1, DELIM));
            }
            if (StringUtils.isNotBlank(subject2)) {
                subjectList.add(Util.valueSDF(subject2, DELIM));
            }
            if (StringUtils.isNotBlank(subject3)) {
                subjectList.add(Util.valueSDF(subject3, DELIM));
            }

            ArrayAdapter<String> subjectSpinnerAdaper = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, subjectList);
            subjectSpinnerAdaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subjectSpinner.setAdapter(subjectSpinnerAdaper);
        }

        {
            String prefix = p.getString(Settings.KEY_PREFIX, getString(R.string.pref_prefix_defvalue));
            prefixText.setText(Util.valueSDF(prefix, DELIM));
        }

        {
            String body1 = p.getString(Settings.KEY_BODY1, getString(R.string.pref_body1_defvalue));
            String body2 = p.getString(Settings.KEY_BODY2, getString(R.string.pref_body2_defvalue));
            String body3 = p.getString(Settings.KEY_BODY3, getString(R.string.pref_body3_defvalue));
            ArrayList<String> bodyList = new ArrayList<String>();
            if (StringUtils.isNotBlank(body1)) {
                bodyList.add(Util.valueSDF(body1, DELIM));
            }
            if (StringUtils.isNotBlank(body2)) {
                bodyList.add(Util.valueSDF(body2, DELIM));
            }
            if (StringUtils.isNotBlank(body3)) {
                bodyList.add(Util.valueSDF(body3, DELIM));
            }

            ArrayAdapter<String> bodySpinnerAdaper = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, bodyList);
            bodySpinnerAdaper.setDropDownViewResource(R.layout.spinner_row_multiline);
            bodySpinner.setAdapter(bodySpinnerAdaper);
        }

        {
            String suffix = p.getString(Settings.KEY_SUFFIX, getString(R.string.pref_suffix_defvalue));
            suffixText.setText(Util.valueSDF(suffix, DELIM));
        }

        // linked To: モード
        if (p.getBoolean(Settings.KEY_LINKEDTO, Settings.DEFVALUE_LINKEDTO)) {
            // Cc, Bcc, Subject, BodyのSpinnerは無効にする
            mailccSpinner.setEnabled(false);
            mailbccSpinner.setEnabled(false);
            subjectSpinner.setEnabled(false);
            bodySpinner.setEnabled(false);

            // Toを選択したら全ての項目が連動する様にする
            mailtoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(view
                            .getContext());

                    Spinner mailccSpinner = (Spinner) findViewById(R.id.mailcc_spinner);
                    mailccSpinner.setSelection(position);
                    Spinner mailbccSpinner = (Spinner) findViewById(R.id.mailbcc_spinner);
                    mailbccSpinner.setSelection(position);
                    Spinner subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
                    subjectSpinner.setSelection(position);
                    Spinner bodySpinner = (Spinner) findViewById(R.id.body_spinner);
                    bodySpinner.setSelection(position);
                    TextView prefixText = (TextView) findViewById(R.id.prefix_text);
                    TextView suffixText = (TextView) findViewById(R.id.suffix_text);
                    switch (position) {
                        case 0:
                            String prefix = p.getString(Settings.KEY_PREFIX,
                                    getString(R.string.pref_prefix_defvalue));
                            prefixText.setText(Util.valueSDF(prefix, DELIM));
                            String suffix = p.getString(Settings.KEY_SUFFIX,
                                    getString(R.string.pref_suffix_defvalue));
                            suffixText.setText(Util.valueSDF(suffix, DELIM));
                            break;
                        case 1:
                            String prefix2 = p.getString(Settings.KEY_PREFIX2,
                                    getString(R.string.pref_prefix2_defvalue));
                            prefixText.setText(Util.valueSDF(prefix2, DELIM));
                            String suffix2 = p.getString(Settings.KEY_SUFFIX2,
                                    getString(R.string.pref_suffix2_defvalue));
                            suffixText.setText(Util.valueSDF(suffix2, DELIM));
                            break;
                        case 2:
                            String prefix3 = p.getString(Settings.KEY_PREFIX3,
                                    getString(R.string.pref_prefix3_defvalue));
                            prefixText.setText(Util.valueSDF(prefix3, DELIM));
                            String suffix3 = p.getString(Settings.KEY_SUFFIX3,
                                    getString(R.string.pref_suffix3_defvalue));
                            suffixText.setText(Util.valueSDF(suffix3, DELIM));
                            break;
                        default:
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else {
            // Cc, Bcc, Subject, BodyのSpinnerは通常時には有効に
            mailccSpinner.setEnabled(true);
            mailbccSpinner.setEnabled(true);
            subjectSpinner.setEnabled(true);
            bodySpinner.setEnabled(true);
        }
    }

}
