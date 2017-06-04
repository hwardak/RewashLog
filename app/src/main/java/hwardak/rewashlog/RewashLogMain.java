package hwardak.rewashlog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Calendar;



public class RewashLogMain extends AppCompatActivity {

    /*
     * Reference to main and only scroll view.
     */
    ScrollView mainScrollView;

    /*
     * References to data access objects.
     */
    EmployeeDataAccess employeeDataAccess;
    RewashDataAccess rewashDataAccess;

    /*
     * All Linear Layouts.
     */
    private LinearLayout nameLinearLayout;
    private LinearLayout timeLinearLayout;
    private LinearLayout dateLinearLayout;
    private LinearLayout washPackageLinearLayout;
    private LinearLayout reasonLinearLayout;
    private LinearLayout instructionsLinearLayout;

    /*
     * All EditText fields.
     */
    private EditText employeeIdEditText;
    private EditText employeeNameEditText;
    private EditText timeEditText;
    private EditText dateEditText;

    /*
     * All carwash type buttons.
     */
    private Button quickButton;
    private Button fullButton;
    private Button luxuryButton;

    /*
     * All reason buttons.
     */
    private Button notCleanButton;
    private Button noSoapButton;
    private Button leftOverButton;
    private Button notDryButton;
    private Button expiredCodeButton;
    private Button customerSatisfactionButton;
    private Button testWashButton;

    /*
     * Save button.
     */
    private Button saveButton;


    /*
     * Strings to hold and pass all rewash information.
     */
    private String washType ="";
    private String reason = "";
    private String name = "";
    private String time = "";
    private String date = "";
    private int year = 0;
    private int month = 0;  // Jan = 0, dec = 11
    private int dayOfMonth =0;


    private Button instructionsOkButton;
    private TextView instructionsTextView;

    /**
     * Calls to instantiate all references.
     * Clears and hides all linear layouts, except employee id.
     * Applies text change listener to employee id EditText.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewash_log_main);


        this.instantiateAllVariable();
        this.hideAllLayouts();
        this.applyTextChangeListener();
//
//        employeeDataAccess.addEmployeeToTable(111, "Hasib Wardak");
//        employeeDataAccess.addEmployeeToTable(222, "Ronald Yu");


    }


    /**
     * Instantiates all references.
     */
    private void instantiateAllVariable() {
        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);

        employeeDataAccess = new EmployeeDataAccess(this);
        rewashDataAccess = new RewashDataAccess(this);

        nameLinearLayout = (LinearLayout) findViewById(R.id.nameLinearLayout);
        timeLinearLayout = (LinearLayout) findViewById(R.id.timeLinearLayout);
        dateLinearLayout = (LinearLayout) findViewById(R.id.dateLinearLayout);
        washPackageLinearLayout = (LinearLayout) findViewById(R.id.washPackageLinearLayout);
        reasonLinearLayout = (LinearLayout) findViewById(R.id.reasonLinearLayout);
        instructionsLinearLayout = (LinearLayout) findViewById(R.id.instructionsLinearLayout);

        instructionsTextView = (TextView) findViewById(R.id.instructionsTextView);
        employeeIdEditText = (EditText) findViewById(R.id.employeeIdEditText);
        employeeNameEditText = (EditText) findViewById(R.id.employeeNameEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        quickButton = (Button) findViewById(R.id.quickButton);
        fullButton = (Button) findViewById(R.id.fullButton);
        luxuryButton = (Button) findViewById(R.id.luxuryButton);

        notCleanButton = (Button) findViewById(R.id.notCleanCheckbox);
        noSoapButton = (Button) findViewById(R.id.noSoapCheckbox);
        leftOverButton = (Button) findViewById(R.id.leftOverSoapCheckbox);
        notDryButton = (Button) findViewById(R.id.notDryCheckbox);
        expiredCodeButton = (Button) findViewById(R.id.expiredCodeCheckbox);
        customerSatisfactionButton = (Button) findViewById(R.id.customerSatisfactionCheckbox);
        testWashButton = (Button) findViewById(R.id.testWashCheckbox);

        saveButton = (Button) findViewById(R.id.saveButton);
        instructionsOkButton = (Button) findViewById(R.id.instructionsOkButton);

    }

    /**
     * Text change listener on the Employee id EditText.
     * As each character is entered, the SQLite database is queried to see if it that id belongs
     * to any existing employees.
     * Accessing additional Activities and options can also be achieved, provided the appropriate
     * id is given. 999 will start the RewashLogOptions Activity.
     */
    private void applyTextChangeListener() {
        /*
         * Intent to start RewashLogOptions activity provided the user enters a valid id.
         */
        final Intent rewashLogIntent = new Intent(this, RewashLogOptions.class);
        final Intent employeeLogIntent = new Intent(this, EmployeeOptions.class);

        employeeIdEditText.addTextChangedListener(new TextWatcher() {
            @Override

            /*
             * Not in use.
             */
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("beforeTextChangeEF:", s.toString());

            }

            /*
             * Not in use.
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("onTextChangeEF:", s.toString());

            }

            /*
             * After a new char is added to the employee id EditText, this method is invoked.
             */
            @Override
            public void afterTextChanged(Editable s) {
                //    Log.i("afterTextChangeEF:", s.toString());

                /*
                 * If the employee exists, load his/her name, load current time and data, and
                 * load wash package options/
                 */
                if(s.length() > 0 && employeeDataAccess.doesEmployeeExist(Integer.parseInt(s.toString()))){

                    hideKeyboard();
                    loadEmployeeName(s);
                    loadTimeDate(s);
                    loadWashPackageOptions();

                    /*
                     * Else, hide all linear layouts.
                     */
                } else {
                    hideAllLayouts();
                }

                /*
                 * If the user enters 999 in the employee id EditText, it will start the manager
                 * options activity.
                 */
                if(s.length() > 0 && Integer.parseInt(s.toString()) == 999){
                    startActivity(rewashLogIntent);
                }

                if(s.length() > 0 && Integer.parseInt(s.toString()) == 998){
                    startActivity(employeeLogIntent);
                }

                if(s.length() > 0 && Integer.parseInt(s.toString()) == 997){
                    for(int i = 0; i <3; i++) {
                        rewashDataAccess.addRewashToTable("Jim", "1200", "date", 2015, 1, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("joe", "1200", "date", 2015, 1, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("bob", "1200", "date", 2015, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tim", "1200", "date", 2015, 3, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tom", "1200", "date", 2016, 4, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Bill", "1200", "date", 2016, 4, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Jill", "1200", "date", 2016, 4, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Jim", "1200", "date", 2016, 4, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("joe", "1200", "date", 2016, 1, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("bob", "1200", "date", 2014, 1, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tim", "1200", "date", 2014, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tom", "1200", "date", 2014, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Bill", "1200", "date", 2014, 3, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Jill", "1200", "date", 2014, 4, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Jim", "1200", "date", 2013, 1, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("joe", "1200", "date", 2013, 1, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("bob", "1200", "date", 2013, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tim", "1200", "date", 2013, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tom", "1200", "date", 2013, 3, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Bill", "1200", "date", 2013, 3, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Jill", "1200", "date", 2012, 3, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Jim", "1200", "date", 2012, 1, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("joe", "1200", "date", 2012, 3, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("bob", "1200", "date", 2012, 3, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tim", "1200", "date", 2012, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Tom", "1200", "date", 2012, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Bill", "1200", "date", 2012, 2, 14, "washtype", "reason");
                        rewashDataAccess.addRewashToTable("Jill", "1200", "date", 2012, 1, 14, "washtype", "reason");
                    }
                }


            }


        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    /**
     * Sets default background colors for wash type buttons, and their layout visible.
     */
    private void loadWashPackageOptions() {
        fullButton.setBackgroundResource(android.R.drawable.btn_default);
        quickButton.setBackgroundResource(android.R.drawable.btn_default);
        luxuryButton.setBackgroundResource(android.R.drawable.btn_default);
        washPackageLinearLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Using the employee id, his/her name will be retrieved, set, and its layout made visible.
     * @param s
     */
    private void loadEmployeeName(Editable s){
        name = employeeDataAccess.getEmployeeName(Integer.parseInt(s.toString()));
        employeeNameEditText.setText(name);
        nameLinearLayout.setVisibility(View.VISIBLE);

    }

    /**
     * Date and Time... !@#$%^
     * @param s
     */
    private void loadTimeDate(Editable s) {
        Calendar calendar = Calendar.getInstance();
        time = calendar.getTime().toString().substring(11,16);
        date = calendar.getTime().toString().substring(0, 11);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        timeEditText.setText(time);
        dateEditText.setText(date);
        timeLinearLayout.setVisibility(View.VISIBLE);
        dateLinearLayout.setVisibility(View.VISIBLE);

    }

    /**
     * Resets the entire form by setting all linear layouts to invisible, except the employee id
     * EditText.
     */
    private void hideAllLayouts() {
        nameLinearLayout.setVisibility(View.INVISIBLE);
        timeLinearLayout.setVisibility(View.INVISIBLE);
        dateLinearLayout.setVisibility(View.INVISIBLE);
        washPackageLinearLayout.setVisibility(View.INVISIBLE);
        reasonLinearLayout.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        instructionsLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Depending on which wash type button was clicked, its label will be read in as stored as the
     * 'wash type.'
     * @param view
     */
    public void washTypeButtonOnClick(View view) {
        loadWashPackageOptions();
        Button button = (Button) view;
        washType = button.getText().toString();
        button.setBackgroundColor(Color.parseColor("Green"));
        clearReasons();
        reasonLinearLayout.setVisibility(View.VISIBLE);
        mainScrollView.scrollTo(0,10000);

    }

    /**
     * Depending on which reason button was clicked, its label will be read in as stored as the
     * 'reason.'
     * @param view
     */
    public void reasonButtonOnClick(View view){
        clearReasons();
        Button button = (Button) view;
        reason = button.getText().toString();
        button.setBackgroundColor(Color.parseColor("Green"));
        saveButton.setVisibility(View.VISIBLE);

    }

    /**
     * Clears the reason buttons, and string variable.
     */
    private void clearReasons() {
        notCleanButton.setBackgroundResource(android.R.drawable.btn_default);
        noSoapButton.setBackgroundResource(android.R.drawable.btn_default);
        leftOverButton.setBackgroundResource(android.R.drawable.btn_default);
        notDryButton.setBackgroundResource(android.R.drawable.btn_default);
        expiredCodeButton.setBackgroundResource(android.R.drawable.btn_default);
        customerSatisfactionButton.setBackgroundResource(android.R.drawable.btn_default);
        testWashButton.setBackgroundResource(android.R.drawable.btn_default);
        reason = "";
    }

    /**
     * Passes the five string values and passes them to rewashDataAccess for persistence handling.
     * @param view
     */
    public void saveButtonOnClick(View view) {
        rewashDataAccess.addRewashToTable(name, time, date, year, month, dayOfMonth, washType, reason);
        employeeIdEditText.setText("");
        // Should make toast here.

    }

    /**
     * Clears the employeeIdEditText, and prevents the app from exiting.
     */
    @Override
    public void onBackPressed(){
        employeeIdEditText.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideAllLayouts();
        employeeIdEditText.setText("");
    }
}

