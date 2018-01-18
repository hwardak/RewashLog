package hwardak.rewashlog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

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
    private LinearLayout activityOptionsLinearLayout;

    /*
     * All EditText fields.
     */
    private TextView employeeIdEditText;
    private TextView employeeNameEditText;
    private EditText timeEditText;
    private EditText dateEditText;

    /*
     * All carwash type buttons.
     */
    private ToggleButton quickButton;
    private ToggleButton fullButton;
    private ToggleButton luxuryButton;

    /*
     * All reason buttons.
     */
    private ToggleButton notCleanButton;
    private ToggleButton noSoapButton;
    private ToggleButton leftOverButton;
    private ToggleButton notDryButton;
    private ToggleButton expiredCodeButton;
    private ToggleButton customerSatisfactionButton;
    private ToggleButton testWashButton;
    private ToggleButton administrativeButton;

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



    /**
     * Calls to instantiate all references.
     * Clears and hides all linear layouts, except employee id.
     * Applies text change listener to employee id EditText.
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewash_log_main);

        this.instantiateAllVariable();
        this.resetLayouts();
        this.applyTextChangeListener();


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
        activityOptionsLinearLayout = (LinearLayout) findViewById(R.id.activityOptionsLinearLayout);

        employeeIdEditText = (TextView) findViewById(R.id.employeeIdEditText);
        employeeNameEditText = (TextView) findViewById(R.id.employeeNameEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        quickButton = (ToggleButton) findViewById(R.id.quickButton);
        fullButton = (ToggleButton) findViewById(R.id.fullButton);
        luxuryButton = (ToggleButton) findViewById(R.id.luxuryButton);

        notCleanButton = (ToggleButton) findViewById(R.id.notCleanCheckbox);
        noSoapButton = (ToggleButton) findViewById(R.id.noSoapCheckbox);
        leftOverButton = (ToggleButton) findViewById(R.id.leftOverSoapCheckbox);
        notDryButton = (ToggleButton) findViewById(R.id.notDryCheckbox);
        expiredCodeButton = (ToggleButton) findViewById(R.id.expiredCodeCheckbox);
        customerSatisfactionButton = (ToggleButton) findViewById(R.id.customerSatisfactionCheckbox);
        testWashButton = (ToggleButton) findViewById(R.id.testWashCheckbox);
        administrativeButton = (ToggleButton) findViewById(R.id.administrativeCheckbox);

        saveButton = (Button) findViewById(R.id.saveButton);



    }

    /**
     * Text change listener on the Employee id EditText.
     * As each character is entered, the SQLite database is queried to see if it that id belongs
     * to any existing employees.
     * Accessing additional Activities and options can also be achieved, provided the appropriate
     */
    private void applyTextChangeListener() {
        /*
         * Intent to start RewashLogOptions activity provided the user enters a valid id.
         */

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
            public void afterTextChanged(Editable userID) {
                //    Log.i("afterTextChangeEF:", userID.toString());

                /*
                 * If the employee exists, load his/her name, load current time and data, and
                 * load wash package options/
                 */

                if(userID.length() > 0 ) {
                    activityOptionsLinearLayout.setVisibility(View.GONE);

                    if (employeeDataAccess.doesEmployeeExist(Integer.parseInt(userID.toString()))) {
                        activityOptionsLinearLayout.setVisibility(View.GONE);
                        hideKeyboard();
                        getEmployeeName(userID);
                        getTimeDate();
                        setTimeDate();
                        setWashTypeToggleButtonListeners();
                        setReasonToggleButtonListeners();
                        showWashTypeOptions();

                    }
                } else {
                    /*
                     * Reset the form.
                     */
                    resetLayouts();
                }

            }


        });
    }

    private void washTypeButtonToggled(CompoundButton toggleButton){
        setCheckedWashTypeButtons(false);
        toggleButton.setChecked(true);
        washType = toggleButton.getText().toString();
        reasonLinearLayout.setVisibility(View.VISIBLE);
        Log.d("WashType B:", washType);

    }

    private void reasonButtonToggled(CompoundButton toggleButton){
        setCheckedReasonButtons(false);
        toggleButton.setChecked(true);
        reason = toggleButton.getText().toString();
        mainScrollView.scrollTo(0,10000);

        saveButton.setVisibility(View.VISIBLE);
        Log.d("Reason :", reason);
    }


    private void setReasonToggleButtonListeners() {

        notCleanButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });


        noSoapButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });


        leftOverButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });


        notDryButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });


        expiredCodeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });

        customerSatisfactionButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });


        testWashButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });

        administrativeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reasonButtonToggled(buttonView);
                }
            }
        });
    }

    private void setWashTypeToggleButtonListeners() {

        quickButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    washTypeButtonToggled(buttonView);
                }
            }
        });


        fullButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    washTypeButtonToggled(buttonView);
                }
            }
        });


        luxuryButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    washTypeButtonToggled(buttonView);
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


    private void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    /**
     * Sets default background colors for wash type buttons, and their layout visible.
     */
    private void showWashTypeOptions() {
        washPackageLinearLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Using the employee id, his/her name will be retrieved, set, and its layout made visible.
     * @param userID an int representing the number enterted into the EditText.
     */
    private void getEmployeeName(Editable userID){
        name = employeeDataAccess.getEmployeeName(Integer.parseInt(userID.toString()));
        employeeNameEditText.setText(name);
        nameLinearLayout.setVisibility(View.VISIBLE);

    }

    /**
     * Date and Time...
     *
     */
    private void getTimeDate() {
        Calendar calendar = Calendar.getInstance();
        time = calendar.getTime().toString().substring(11,16);
        date = calendar.getTime().toString().substring(0, 11);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void setTimeDate(){
        timeEditText.setText(time);
        dateEditText.setText(date);
        timeLinearLayout.setVisibility(View.VISIBLE);
        dateLinearLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Resets the entire form by setting all linear layouts to invisible, except the employee id
     * EditText, and the activity options layout.
     */
    private void resetLayouts() {
        setCheckedReasonButtons(false);
        nameLinearLayout.setVisibility(View.INVISIBLE);
        timeLinearLayout.setVisibility(View.INVISIBLE);
        dateLinearLayout.setVisibility(View.INVISIBLE);
        washPackageLinearLayout.setVisibility(View.INVISIBLE);
        reasonLinearLayout.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        instructionsLinearLayout.setVisibility(View.GONE);
        activityOptionsLinearLayout.setVisibility(View.VISIBLE);
    }

    private void setCheckedWashTypeButtons(boolean isChecked) {
        quickButton.setChecked(isChecked);
        fullButton.setChecked(isChecked);
        luxuryButton.setChecked(isChecked);
    }


    private void setCheckedReasonButtons(boolean isChecked) {
        notCleanButton.setChecked(isChecked);
        noSoapButton.setChecked(isChecked);
        leftOverButton.setChecked(isChecked);
        notDryButton.setChecked(isChecked);
        expiredCodeButton.setChecked(isChecked);
        customerSatisfactionButton.setChecked(isChecked);
        testWashButton.setChecked(isChecked);
        administrativeButton.setChecked(isChecked);
    }



    /**
     * Passes the five string values and passes them to rewashDataAccess for persistence handling.
     * @param view The save button.
     */
    public void saveButtonOnClick(View view) {
        rewashDataAccess
                .addRewashToTable(name, time, date, year, month, dayOfMonth, washType, reason);
        setCheckedWashTypeButtons(false);
        setCheckedReasonButtons(false);
        employeeIdEditText.setText("");
        Toaster.toastUp(this, "Rewash Saved Succesfully");
        showKeyboard();
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
        resetLayouts();
        setCheckedWashTypeButtons(false);
        setCheckedReasonButtons(false);
        washType = "";
        reason = "";
        employeeIdEditText.setText("");
    }



    public void employeesButtonOnClick(View view) {
        final Intent employeeLogIntent = new Intent(this, EmployeeOptions.class);
        startActivity(employeeLogIntent);
    }

    public void RewashLogButtonOnClick(View view) {
        final Intent rewashLogIntent = new Intent(this, RewashLogOptions.class);
        startActivity(rewashLogIntent);
    }

    public void SettingsButtonOnClick(View view) {
        final Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }
}

/*
TODO: The sets of toggle buttons should always have on enabled.

TODO: Toggle buttons don't reset if the toggle(s) are ON and the back btn is clicked instead of save.
 */

