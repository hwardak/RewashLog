package hwardak.rewashlog;

/**
 * Created by HWardak on 16-08-25.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SendMailTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity sendMailActivity;


    public SendMailTask(Activity activity) {
        sendMailActivity = activity;

    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sendMailActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        String senderEmail = "hwardak001@gmail.com"; // XXXXXXXXXX
        String senderPassword = "Python1!"; // XXXXXXXXX
        String subject = args[0].toString();
        String body = args[1].toString();
        List emailRecipients = new ArrayList();
        emailRecipients.add(args[3].toString());

        List<String> attachments = new LinkedList<>();

        if (args.length > 2) {
            attachments.add(args[2].toString());
        }

        try {
            Log.i("SendMailTask", "About to instantiate GMail...");
            publishProgress("Processing input....");

            GMail gMail = new GMail(
                    senderEmail,
                    senderPassword,
                    emailRecipients,
                    subject,
                    body,
                    attachments);

            publishProgress("Preparing mail message....");
            gMail.createEmailMessage();
            publishProgress("Sending email....");
            gMail.sendEmail();
            publishProgress("Email Sent.");
            Log.i("SendMailTask", "Mail Sent.");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);

        }
            return null;
    }
    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
        statusDialog.dismiss();
    }

}