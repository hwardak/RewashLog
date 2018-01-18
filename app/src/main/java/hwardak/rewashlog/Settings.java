package hwardak.rewashlog;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Settings extends AppCompatActivity {

    EditText userEmailEditText;
    EditText recipientEmailEditText;
    EditText userEmailPasswordEditText;
    EditText storeNumberEditText;

    TextView infoMessageTextView;

    String userEmail;
    String recipientEmail;
    String userEmailPassword;
    int storeNumber;

    ArrayList settingsList;


    Crypto cryto;

    String encryptedUserEmail;
    String encryptedPassword;

    SettingsDataAccess settingsDataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userEmailEditText = (EditText) findViewById(R.id.userEmailEditText);
        userEmailPasswordEditText = (EditText) findViewById(R.id.userEmailPasswordEditText);
        recipientEmailEditText = (EditText) findViewById(R.id.recipientEmailEditText);
        storeNumberEditText = (EditText) findViewById(R.id.storeNumberEditText);

        infoMessageTextView = (TextView) findViewById(R.id.infoMessageTextView);
        settingsDataAccess = new SettingsDataAccess(this);

        cryto = new Crypto();


        loadSettings();

    }

    private void loadSettings() {
        if(settingsDataAccess.isSettingAvailable()){
            settingsList = settingsDataAccess.getSettings();
            userEmailEditText.setText(cryto.decryption(settingsList.get(0).toString()));
            recipientEmailEditText.setText(settingsList.get(2).toString());
            storeNumberEditText.setText(settingsList.get(3).toString());

            userEmailPasswordEditText.setHint("*************");
        }

    }


    public void saveButtonOnClick(View view) {

        if(checkFields()) {
            encryptValues();
            saveValues();
        }
    }


    private boolean checkFields() {
        String errorMessage = "";
        infoMessageTextView.setTextColor(Color.parseColor("RED"));
        boolean pass = true;

        if(!userEmailEditText.getText().toString().trim().equals("")) {
            userEmail = userEmailEditText.getText().toString().trim();
        } else {
            pass = false;
            errorMessage += "Please enter your GMail address.\n";
        }

        if(userEmail != null && !isEmailAddressValid(userEmail)){
            pass = false;
            errorMessage += "Invalid GMail address.\n";
        }

        if(!recipientEmailEditText.getText().toString().trim().equals("")) {
            recipientEmail = recipientEmailEditText.getText().toString().trim();
        } else {
            pass = false;
            errorMessage += "Please enter recipient email address.\n";

        }

        if (recipientEmail != null && !isEmailAddressValid(recipientEmail)) {
            pass = false;
            errorMessage += "Invalid GMail address\n";
        }

        if(!userEmailPasswordEditText.getText().toString().trim().equals("")){
            userEmailPassword = userEmailPasswordEditText.getText().toString().trim();
        } else {
            pass = false;
            errorMessage += "Please enter your GMail password\n";
        }

        if(!storeNumberEditText.getText().toString().trim().equals("")){
            storeNumber = Integer.parseInt(storeNumberEditText.getText().toString().trim());
        } else {
            pass = false;
            errorMessage += "Please enter your store number\n";
        }

        if(!pass){
            infoMessageTextView.setText(errorMessage);
        } else {
            errorMessage = "";
            infoMessageTextView.setText(errorMessage);
        }

        return pass;
    }

    private void encryptValues() {
        encryptedUserEmail = cryto.encryption(userEmail);
        encryptedPassword = cryto.encryption(userEmailPassword);


//        Log.d("preEncryption password", userEmailPassword);
//        Log.d("postEncryption password", encryptedPassword);
//        Log.d("preEncryption userEmail", userEmail);
//        Log.d("postEncr userEmail", encryptedUserEmail);

        userEmail = null;
        userEmailPassword = null;

    }


    private void saveValues() {
        settingsDataAccess.saveSettings(encryptedUserEmail, encryptedPassword, recipientEmail, storeNumber);
        infoMessageTextView.setTextColor(Color.parseColor("GREEN"));
        infoMessageTextView.setText("Settings saved succesfully!");
    }


    private boolean isEmailAddressValid(String email) {
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

    public void cancelButtonOnClick(View view) {
        super.onBackPressed();
    }

/////////////////////////////


}
