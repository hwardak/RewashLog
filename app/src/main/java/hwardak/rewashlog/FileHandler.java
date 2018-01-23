package hwardak.rewashlog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;



import static android.content.Context.MODE_PRIVATE;

/**
 * Created by HWardak on 2018-01-22.
 */

public class FileHandler extends AppCompatActivity  {

    File file;
    FileOutputStream fos;



    public String createFile(ArrayList<String> rewashList, int luxuryCount, int fullCount, int quickCount, int totalRewashCount) throws IOException {
        String filename = "myfile.txt";

        file = getFilesDir();
        String path = file.getAbsolutePath();
        fos = openFileOutput(filename, MODE_PRIVATE);

        String fileContents = populateFileContents(rewashList, luxuryCount, fullCount, quickCount, totalRewashCount);

        fos.write(fileContents.getBytes());
        fos.close();

        return path + "/" + filename;
    }

    private String populateFileContents(ArrayList<String> rewashList, int luxuryCount, int fullCount, int quickCount, int totalRewashCount){
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


}
