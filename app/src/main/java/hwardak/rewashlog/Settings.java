package hwardak.rewashlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Settings extends AppCompatActivity {

    EditText userEmailEditText;
    EditText recipientEmailEditText;
    EditText userEmailPasswordEditText;
    EditText storeNumberEditText;
    TextView errorTextView;

    String userEmail;
    String recipientEmail;
    String userEmailPassword;
    int storeNumber;

    Cryto cryto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userEmailEditText = (EditText) findViewById(R.id.userEmailEditText);
        userEmailPasswordEditText = (EditText) findViewById(R.id.userEmailPasswordEditText);
        recipientEmailEditText = (EditText) findViewById(R.id.recipientEmailEditText);
        storeNumberEditText = (EditText) findViewById(R.id.storeNumberEditText);
        errorTextView = (TextView) findViewById(R.id.errorTextView);

        cryto = new Cryto();

    }

    public void saveButtonOnClick(View view) {

        checkFields();
        saveFieldValues();

    }


    private boolean checkFields() {
        String errorMessage = "";
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
            errorTextView.setText(errorMessage);
        } else {
            errorMessage = "";
            errorTextView.setText(errorMessage);
        }

        return pass;
    }


    private void saveFieldValues() {
        Log.d("preEncryption", userEmailPassword);
        String postEncryption = cryto.encryption(userEmailPassword);
        Log.d("postEncryption", postEncryption);
        String postDecryption = cryto.decryption(postEncryption);
        Log.d("postDecryption", postDecryption);






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

/////////////////////////////


}
