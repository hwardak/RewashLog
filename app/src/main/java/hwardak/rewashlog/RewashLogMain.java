package hwardak.rewashlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private RadioGroup washPackageRadioGroup;
    private RadioButton quickRadioButton;
    private RadioButton fullRadioButton;
    private RadioButton luxuryRadioButton;

    private CheckBox notCleanCheckbox;
    private CheckBox noSoapCheckbox;
    private CheckBox leftOverSoapCheckbox;
    private CheckBox notDryCheckbox;
    private CheckBox expiredCodeCheckbox;
    private CheckBox customerSatisfactionCheckbox;
    private CheckBox testWashCheckbox;

    private Button saveButton;
    private Button instructionsOkButton;

    EmployeeDataAccess employeeDataAccess;

    private String washType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewash_log_main);

        employeeDataAccess = new EmployeeDataAccess(this);

//        employeeDataAccess.addEmployeeToTable(111, "Hasib Wardak");
//        employeeDataAccess.addEmployeeToTable(222, "Ronald Yu");

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

        washPackageRadioGroup = (RadioGroup) findViewById(R.id.washPackageRadioGroup);
        quickRadioButton = (RadioButton) findViewById(R.id.quickRadioButton);
        fullRadioButton = (RadioButton) findViewById(R.id.fullRadioButton);
        luxuryRadioButton = (RadioButton) findViewById(R.id.luxuryRadioButton);

        notCleanCheckbox = (CheckBox) findViewById(R.id.notCleanCheckbox);
        noSoapCheckbox = (CheckBox) findViewById(R.id.noSoapCheckbox);
        leftOverSoapCheckbox = (CheckBox) findViewById(R.id.leftOverSoapCheckbox);
        notDryCheckbox = (CheckBox) findViewById(R.id.notDryCheckbox);
        expiredCodeCheckbox = (CheckBox) findViewById(R.id.expiredCodeCheckbox);
        customerSatisfactionCheckbox = (CheckBox) findViewById(R.id.customerSatisfactionCheckbox);
        testWashCheckbox = (CheckBox) findViewById(R.id.testWashCheckbox);

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
                    setWashPackage();


                } else {
                    hideAllLayouts();
                }

                }


        });
    }

    private void setWashPackage() {
        washPackageLinearLayout.setVisibility(View.VISIBLE);
        washPackageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(washPackageRadioGroup.getCheckedRadioButtonId() == fullRadioButton.getId()){
                    washType = "Full";
                }
                else if (washPackageRadioGroup.getCheckedRadioButtonId() == luxuryRadioButton.getId()){
                    washType = " Luxury";
                }
                else {
                    washType = "Quick";
                }
            }
        });

    }


    /*
       Retrieves the name employee name, current date and time, sets the Views to visible, and
       fill the form three fields with the oppropriate info.
     */
    private void loadNameTimeDate(Editable s) {
        Calendar calender = Calendar.getInstance();

        String name = employeeDataAccess.getEmployeeName(Integer.parseInt(s.toString()));
        String time = calender.getTime().toString().substring(11,16);
        String date = calender.getTime().toString().substring(0, 11);
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
        instructionsLinearLayout.setVisibility(View.INVISIBLE);
    }

}
