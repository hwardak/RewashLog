package hwardak.rewashlog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class RewashLogOptions extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    static final ArrayList<String>FRUITS = new ArrayList<String>();


    EmployeeDataAccess employeeDataAccess = new EmployeeDataAccess(this);

    RewashDataAccess rewashDataAccess = new RewashDataAccess(this);

    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> rewashList;
    Spinner monthSpinner;
    Spinner yearSpinner;
    int month = 0;
    int year = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewashlog_options);


        instantiateMonthSpinner();
        instantiateYearSpinner();
        getEntireRewashList();
        updateListView();
    }

    private void instantiateYearSpinner() {
        yearSpinner = (Spinner) findViewById(R.id.rewashYearSpinner);

        ArrayList<String> yearList = rewashDataAccess.getYearList();
        yearList.add(0, "All Years");

        ArrayAdapter adapter = new ArrayAdapter(this,
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


    public void updateListView(){
        listAdapter = new ArrayAdapter<>(this, R.layout.listview_row, R.id.listViewRow, rewashList);
        listView = (ListView) findViewById(R.id.rewashListView);
        listView.setAdapter(listAdapter);

    }

    public ArrayList<String> getEntireRewashList(){
        rewashList = rewashDataAccess.getRewashList();
        return rewashList;
    }

    public void getSpecifiedRewashList(String month, String year){

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == monthSpinner) {
            if (position != 0) {
                month = position;
            } else {
                month = 0;
            }

        }

        if (parent == yearSpinner) {
            if (position != 0) {
                year = Integer.parseInt(parent.getItemAtPosition(position).toString());
            } else {
                year = 0;
            }
        }
            if(month > 0 && year > 0){
                rewashList = rewashDataAccess.getRewashListByMonthAndYear(month, year);
            } else if(month > 0){
                rewashList = rewashDataAccess.getRewashListByMonth(month);
            } else if(year > 0){
                rewashList = rewashDataAccess.getRewashListByYear(year);
            } else {
                rewashList = getEntireRewashList();
            }


        updateListView();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

