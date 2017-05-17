package hwardak.rewashlog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RewashLogOptions extends AppCompatActivity {

    static final ArrayList<String>FRUITS = new ArrayList<String>();


    EmployeeDataAccess employeeDataAccess = new EmployeeDataAccess(this);

    RewashDataAccess rewashDataAccess = new RewashDataAccess(this);

    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> rewashList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewashlog_options);

        getEntireRewashList();
        updateListView();
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


    public void addDeleteUserButton(View view) {

    }


}

