package de.cokuss.chhe.pinmoney;import android.app.DatePickerDialog;import android.support.v7.app.ActionBar;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.support.v7.widget.Toolbar;import android.text.Editable;import android.text.TextWatcher;import android.util.Log;import android.view.Menu;import android.view.View;import android.widget.Button;import android.widget.DatePicker;import android.widget.EditText;import android.widget.RadioButton;import android.widget.RadioGroup;import android.widget.TextView;import java.text.DecimalFormat;import java.text.NumberFormat;import java.text.ParseException;import java.util.Calendar;import java.util.Locale;public class ChangePinInfoActivity extends AppCompatActivity {    private static final String LOG_TAG = ChangePinInfoActivity.class.getSimpleName();    private final Calendar c = Calendar.getInstance();    DateHelper dateHelper = new DateHelper();    RadioGroup itemtypeGroup;    private String inhaber;    private PinMoneyEntry pinMoneyEntry;    private DAOImplSQLight daoImplSQLight = DAOImplSQLight.getInstance(ChangePinInfoActivity.this);    private EditText valueField, dateField;    private RadioButton radioDay;    private RadioButton radioWeek;    private RadioButton radioMonth;    private Button changeButton;    private Cycle cycle;    private float value;    private int mYear = c.get(Calendar.YEAR);    private int mMonth = c.get(Calendar.MONTH);    private int mDay = c.get(Calendar.DAY_OF_MONTH);    private boolean clickbar = false;    //private DecimalFormat df = new DecimalFormat( "###,##0.00" );    private void log(String string) {        Log.d(LOG_TAG, string);    }    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_change_pin_info);        init();    }    private void init() {        valueField = (EditText) findViewById(R.id.betragEdit);        dateField = (EditText) findViewById(R.id.input_changeDate);        radioDay = (RadioButton) findViewById(R.id.rbDay);        radioWeek = (RadioButton) findViewById(R.id.rbWeek);        radioMonth = (RadioButton) findViewById(R.id.rbMonth);        changeButton = (Button) findViewById(R.id.variButton);        itemtypeGroup = (RadioGroup) findViewById(R.id.cycle);        inhaber = getIntent().getStringExtra("inhaber");        TextView tvInhaber = (TextView) findViewById(R.id.inhaber);        Button changeStartButton = (Button) findViewById(R.id.changeStartButton);        tvInhaber.setText(inhaber);        showCurrentState();        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);        setSupportActionBar(toolbar);        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        ActionBar actionBar = getSupportActionBar();        actionBar.setDisplayShowHomeEnabled(true);        actionBar.setIcon(R.mipmap.ic_launcher_change);        //listen to the changes in cycle        itemtypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                showActiveButton();            }        });        //listen to the changes in valueField        valueField.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }            @Override            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }            @Override            public void afterTextChanged(Editable editable) {                showActiveButton();            }        });        //listen to the changes in dateField        dateField.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }            @Override            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }            @Override            public void afterTextChanged(Editable editable) {                showActiveButton();            }        });        changeStartButton.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),                        new DatePickerDialog.OnDateSetListener() {                            @Override                            public void onDateSet(DatePicker view, int year,                                                  int monthOfYear, int dayOfMonth) {                                dateField.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);                            }                        }, mYear, mMonth, mDay);                datePickerDialog.show();            }        });        changeButton.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                if (clickbar) {                    //get cycle                    switch (itemtypeGroup.getCheckedRadioButtonId()) {                        case R.id.rbDay:                            cycle = Cycle.TAEGLICH;                            break;                        case R.id.rbWeek:                            cycle = Cycle.WOECHENTLICH;                            break;                        case R.id.rbMonth:                            cycle = Cycle.MONATLICH;                            break;                    }                    //get value                    //Todo Immer das alte Thema , oder .                    value = Float.valueOf(valueField.getText().toString());                    //create Payment                    //NullPointer                    log("Date " + dateField.getText().toString() + " Value " + value);                    Payments newPayments = new Payments(dateHelper.string2Date(dateField.getText().toString()), cycle, value);                    //Todo abschliessende Buchung ??? Letzte Chance ???                    //add the Entry                    daoImplSQLight.addEntryToPinMoney(inhaber, pinMoneyEntry.getBirthDate(), newPayments, "changed");                    finish();                }            }        });    }    private void showCurrentState() {        pinMoneyEntry = daoImplSQLight.getEntryFromPinMoney(getApplicationContext(), inhaber);        Payments payments = pinMoneyEntry.getPayments();        valueField.setText(String.valueOf(payments.getBetrag()));        dateField.setText(dateHelper.sdfShort.format(payments.getDate()));        switch (payments.getTurnusStrShort()) {            case "t":                radioDay.setChecked(true);                break;            case "w":                radioWeek.setChecked(true);                break;            case "m":                radioMonth.setChecked(true);                break;        }    }    private void showActiveButton() {        changeButton.setBackgroundResource(R.drawable.button_change);        changeButton.isClickable();        clickbar = true;    }    @Override    public boolean onCreateOptionsMenu(Menu menu) {        // Inflate the menu; this adds items to the action bar if it is present.        getMenuInflater().inflate(R.menu.main_menu_change_pin, menu);        return true;    }}