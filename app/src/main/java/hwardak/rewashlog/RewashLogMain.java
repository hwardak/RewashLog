package hwardak.rewashlog;

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

    private void applyTextChangeListener() {
        employeeIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("beforeTextChangeEF:", s.toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("onTextChangeEF:", s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("afterTextChangeEF:", s.toString());
                // Create methods in the dataAccess class to check if this employeeId belongs to
                //anyone.
                if(s.length() > 0 && employeeDataAccess.doesEmployeeExist(Integer.parseInt(s.toString()))){
                    loadNameTimeDate(s);
                    loadWashPackageOptions();


                } else {
                    hideAllLayouts();
                }

            }


        });
    }

    private void loadWashPackageOptions() {
        fullButton.setBackgroundResource(android.R.drawable.btn_default);
        quickButton.setBackgroundResource(android.R.drawable.btn_default);
        luxuryButton.setBackgroundResource(android.R.drawable.btn_default);
        washPackageLinearLayout.setVisibility(View.VISIBLE);
    }


    /*
       Retrieves the name employee name, current date and time, sets the Views to visible, and
       fill the form three fields with the oppropriate info.
     */
    private void loadNameTimeDate(Editable s) {
        Calendar calender = Calendar.getInstance();

        name = employeeDataAccess.getEmployeeName(Integer.parseInt(s.toString()));
        time = calender.getTime().toString().substring(11,16);
        date = calender.getTime().toString().substring(0, 11);
        employeeNameEditText.setText(name);
        timeEditText.setText(time);
        dateEditText.setText(date);
        nameLinearLayout.setVisibility(View.VISIBLE);
        timeLinearLayout.setVisibility(View.VISIBLE);
        dateLinearLayout.setVisibility(View.VISIBLE);

    }

    /*
     * Sets the entire form to invisble, except the employee ID EditText.
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

    public void washTypeButtonOnClick(View view) {
        loadWashPackageOptions();
        Button button = (Button) view;
        washType = button.getText().toString();
        button.setBackgroundColor(Color.parseColor("Green"));
        clearReasons();
        reasonLinearLayout.setVisibility(View.VISIBLE);
        mainScrollView.scrollTo(0,10000);

    }

    public void reasonButtonOnClick(View view){
        clearReasons();
        Button button = (Button) view;
        reason = button.getText().toString();
        button.setBackgroundColor(Color.parseColor("Green"));
        saveButton.setVisibility(View.VISIBLE);

    }

    private void clearReasons() {
        notCleanButton.setBackgroundResource(android.R.drawable.btn_default);
        noSoapButton.setBackgroundResource(android.R.drawable.btn_default);
        leftOverButton.setBackgroundResource(android.R.drawable.btn_default);
        notDryButton.setBackgroundResource(android.R.drawable.btn_default);
        expiredCodeButton.setBackgroundResource(android.R.drawable.btn_default);
        customerSatisfactionButton.setBackgroundResource(android.R.drawable.btn_default);
        testWashButton.setBackgroundResource(android.R.drawable.btn_default);
    }


    public void saveButtonOnClick(View view) {
        rewashDataAccess.addRewashToTable(name, time, date, washType, reason);

    }
}
