package hwardak.rewashlog;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;

public class RewashLogMain extends AppCompatActivity {

    ScrollView mainScrollView;

    private LinearLayout nameLinearLayout;
    private LinearLayout timeLinearLayout;
    private LinearLayout dateLinearLayout;
    private LinearLayout washPackageLinearLayout;
    private LinearLayout reasonLinearLayout;
    private LinearLayout instructionsLinearLayout;

    private TextView instructionsTextView;
    private EditText employeeIdEditText;
    private EditText employeeNameEditText;
    private EditText timeEditText;
    private EditText dateEditText;

    private Button quickButton;
    private Button fullButton;
    private Button luxuryButton;

    private Button notCleanButton;
    private Button noSoapButton;
    private Button leftOverButton;
    private Button notDryButton;
    private Button expiredCodeButton;
    private Button customerSatisfactionButton;
    private Button testWashButton;

    private Button saveButton;
    private Button instructionsOkButton;

    EmployeeDataAccess employeeDataAccess;
    RewashDataAccess rewashDataAccess;

    private String washType ="";
    private String reason = "";
    private String name = "";
    private String time = "";
    private String date = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewash_log_main);

        employeeDataAccess = new EmployeeDataAccess(this);
        rewashDataAccess = new RewashDataAccess(this);

        employeeDataAccess.addEmployeeToTable(111, "Hasib Wardak");
        employeeDataAccess.addEmployeeToTable(222, "Ronald Yu");

        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);

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


        hideAllLayouts();
        applyTextChangeListener();
    }

    /**
     * Text change listener on the Employee id EditText.
     * As each character is entered, the SQLite database is queried to see if it that id belongs
     * to any existing employees.
     * Accessing additional Activities and options can also be achieved, provided the appropriate
     * id is given. 999 will start the ManagerOptions Activity.
     */
    private void applyTextChangeListener() {

        final Intent intent = new Intent(this, ManagerOptions.class);

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
                    startActivity(intent);
                }
            }


        });
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
     * Date and Time will also be
     * @param s
     */
    private void loadTimeDate(Editable s) {
        Calendar calender = Calendar.getInstance();
        time = calender.getTime().toString().substring(11,16);
        date = calender.getTime().toString().substring(0, 11);
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
        rewashDataAccess.addRewashToTable(name, time, date, washType, reason);

    }

    /**
     * Clears the employeeIdEditText, and prevents the app from exiting.
     */
    @Override
    public void onBackPressed(){
        employeeIdEditText.setText("");
    }
}
