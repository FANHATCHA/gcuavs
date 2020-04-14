package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Helper {

    public static String generateCSRFToken() {
        RandomString randomString = new RandomString();
        return randomString.nextString();
    }

    public static int generateTicketNumber() {
        // Generate random integers in range 0 to 999
        int number = ThreadLocalRandom.current().nextInt();
        return Math.abs(number);
    }

    public static String getCurrentTimestamp(){
        Date date = new Date(); // This object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

//    public byte[] encryptID(String id) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
//
//    }
}
