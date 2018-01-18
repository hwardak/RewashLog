package hwardak.rewashlog;

/**
 * Created by HWardak on 2017-06-23.
 */

public class Crypto {


    /* TODO: The SecretKey should be kept secret, as of this build it is not fully secure. */
    public String encryption(String userText){
        String seedValue = "SecretKey";
        String userTextEncrypted="";
        try {
            userTextEncrypted = AESHelper.encrypt(seedValue, userText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userTextEncrypted;
    }


    /* TODO: The SecretKey should be kept secret, as of this build it is not fully secure. */
    public String decryption(String encryptedText){
        String seedValue = "SecretKey";
        String decryptedText="";
        try {
            decryptedText = AESHelper.decrypt(seedValue, encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }




}
