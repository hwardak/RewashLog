package hwardak.rewashlog;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class ManagerOptions extends AppCompatActivity {

    static final ArrayList<String>FRUITS = new ArrayList<String>();



    RewashDataAccess rewashDataAccess = new RewashDataAccess(this);

    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> rewashList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_options);

        getEntireRewashList();
        updateListView();
    }



    public void updateListView(){


        listAdapter = new ArrayAdapter<>(this, R.layout.rewash_listview_row, R.id.rewashRow, rewashList);
        listView = (ListView) findViewById(R.id.rewashListView);
        listView.setAdapter(listAdapter);

    }

    public ArrayList<String> getEntireRewashList(){
        rewashList = rewashDataAccess.getRewashList();
        return rewashList;
    }


}

