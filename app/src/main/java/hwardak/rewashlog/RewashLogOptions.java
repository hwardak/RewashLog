package hwardak.rewashlog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RewashLogOptions extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    EmployeeDataAccess employeeDataAccess = new EmployeeDataAccess(this);

    RewashDataAccess rewashDataAccess = new RewashDataAccess(this);

    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> rewashList;
    Spinner monthSpinner;
    Spinner yearSpinner;

    int monthNumber = 0;
    int year = 0;

    String monthString;

    File file;
    FileOutputStream fos;

    TextView luxuryCountTextView;
    TextView fullCountTextView;
    TextView quickCountTextView;
    TextView totalCountTextView;

    int luxuryCount;
    int fullCount;
    int quickCount;
    int totalRewashCount;
    EditText emailRecipientEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewashlog_options);

        luxuryCountTextView = (TextView) findViewById(R.id.luxuryCountTextView);
        fullCountTextView = (TextView) findViewById(R.id.fullCountTextView);
        quickCountTextView = (TextView) findViewById(R.id.quickCountTextView);
        totalCountTextView = (TextView) findViewById(R.id.totalCountTextView);
        emailRecipientEditText = (EditText) findViewById(R.id.emailRecipientEditText);

        instantiateMonthSpinner();
        instantiateYearSpinner();
        getEntireRewashList();
        updateListView();
        hideKeyboard();

    }


    private void instantiateYearSpinner() {
        yearSpinner = (Spinner) findViewById(R.id.rewashYearSpinner);

        ArrayList<String> yearList = rewashDataAccess.getYearList();
        yearList.add(0, "All Years");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, yearList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);

        yearSpinner.setOnItemSelectedListener(this);
    }

    private void instantiateMonthSpinner() {
        monthSpinner = (Spinner) findViewById(R.id.rewashMonthSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        monthSpinner.setOnItemSelectedListener(this);
    }


    private void updateListView() {
        listAdapter = new ArrayAdapter<>(this, R.layout.listview_row, R.id.listViewRow, rewashList);
        listView = (ListView) findViewById(R.id.rewashListView);
        listView.setAdapter(listAdapter);

    }


    private ArrayList<String> getEntireRewashList() {
        rewashList = rewashDataAccess.getRewashList();
        return rewashList;
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        luxuryCount =0 ;
        fullCount = 0;
        quickCount = 0;

        if (parent == monthSpinner) {
            if (position != 0) {
                monthNumber = position - 1;
            } else {
                monthNumber = -1;
            }
            monthString = parent.getItemAtPosition(position).toString();

        }

        if (parent == yearSpinner) {
            if (position != 0) {
                year = Integer.parseInt(parent.getItemAtPosition(position).toString());
            } else {
                year = 0;
            }
        }
        if (monthNumber >= 0 && year > 0) {
            rewashList = rewashDataAccess.getRewashListByMonthAndYear(monthNumber, year);

            luxuryCount = rewashDataAccess.getWashTypeCountByMonthAndYear("Luxury", monthNumber, year);
            fullCount = rewashDataAccess.getWashTypeCountByMonthAndYear("Full", monthNumber, year);
            quickCount = rewashDataAccess.getWashTypeCountByMonthAndYear("Quick", monthNumber, year);

            luxuryCountTextView.setText(String.valueOf(luxuryCount));
            fullCountTextView.setText(String.valueOf(fullCount));
            quickCountTextView.setText(String.valueOf(quickCount));

        } else if (monthNumber >= 0) {
            rewashList = rewashDataAccess.getRewashListByMonth(monthNumber);

            luxuryCount = rewashDataAccess.getWashTypeCountByMonth("Luxury", monthNumber);
            fullCount = rewashDataAccess.getWashTypeCountByMonth("Full", monthNumber);
            quickCount = rewashDataAccess.getWashTypeCountByMonth("Quick", monthNumber);

            luxuryCountTextView.setText(String.valueOf(luxuryCount));
            fullCountTextView.setText(String.valueOf(fullCount));
            quickCountTextView.setText(String.valueOf(quickCount));

        } else if (year > 0) {
            rewashList = rewashDataAccess.getRewashListByYear(year);

            luxuryCount =rewashDataAccess.getWashTypeCountByYear("Luxury" ,year);
            fullCount = rewashDataAccess.getWashTypeCountByYear("Full", year);
            quickCount = rewashDataAccess.getWashTypeCountByYear("Quick", year);

            luxuryCountTextView.setText(String.valueOf(luxuryCount));
            fullCountTextView.setText(String.valueOf(fullCount));
            quickCountTextView.setText(String.valueOf(quickCount));

        } else {
            rewashList = getEntireRewashList();

            luxuryCount =rewashDataAccess.getWashTypeCount("Luxury");
            fullCount = rewashDataAccess.getWashTypeCount("Full");
            quickCount = rewashDataAccess.getWashTypeCount("Quick");

            luxuryCountTextView.setText(String.valueOf(luxuryCount));
            fullCountTextView.setText(String.valueOf(fullCount));
            quickCountTextView.setText(String.valueOf(quickCount));
        }
        totalRewashCount = luxuryCount + fullCount + quickCount;
        totalCountTextView.setText(String.valueOf(luxuryCount + fullCount + quickCount));
        updateListView();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Creates a file and invokes to send the file in an email.
    public void emailOutOnClick(View view) throws IOException {

        String fileName = createFile();

        String subject = "Rewash List: " + monthString;

        if(year > 0){
            subject += " " + year;
        }


        String recipient;

        if (emailRecipientEditText.getText() != null){
            recipient = emailRecipientEditText.getText().toString();

            if(validateEmailAddress(recipient)){
                new SendMailTask(this).execute(subject, "Body",  fileName, recipient);

            } else {
                Toast.makeText(getApplicationContext(), "Invalid Email Address",
                        Toast.LENGTH_LONG).show();
            }
        }

    }


    private String createFile() throws IOException {
        String filename = "myfile.txt";

        file = getFilesDir();
        String path = file.getAbsolutePath();
        fos = openFileOutput(filename, MODE_PRIVATE);

        String fileContents = populateFileContents();

        fos.write(fileContents.getBytes());
        fos.close();

        return path + "/" + filename;
    }


    private String populateFileContents(){
        String fileContents = "";
        fileContents += "Rewash Counts \n";
        fileContents += "------------------------------------";
        fileContents += "\n";
        fileContents += "------------------------------------";
        fileContents += "\n";
        fileContents += "Luxury: " + luxuryCount;
        fileContents += "  ";
        fileContents += "Full: " +  fullCount;
        fileContents += "   ";
        fileContents += "Quick: " + quickCount;
        fileContents += "   ";
        fileContents += "Total: " + totalRewashCount;

        fileContents += "\n\n\n";

        fileContents += "Rewash List \n";
        fileContents += "------------------------------------";
        fileContents += "\n";
        fileContents += "------------------------------------";
        fileContents += "\n";


        for (int i = 0; i < rewashList.size(); i++) {
            fileContents += rewashList.get(i);
            fileContents += "\n";
            fileContents += "------------------------------------";
            fileContents += "\n";
        }
        return fileContents;
    }


    private boolean validateEmailAddress(String email) {
        boolean isValid = false;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            Log.d("Invalid email: ", email);
        }
        return isValid;
    }

}