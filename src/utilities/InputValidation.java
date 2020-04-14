package utilities;

public class InputValidation {

    public static boolean validateMaxInputLength(String input, int maxLength){
        return input.length() <= maxLength;
    }

    public static String getMaxInputLengthError(String input){
        return "The name is too long. Max 225 characters";
    }

    public static boolean validateMinInputLength(String input, int minLength){
        return input.length() >= minLength;
    }

    public static String getMinInputLengthError(String input){
        return "The name is too short. Min 8 characters";
    }

    public static boolean validateDomainEmail(String email){
        System.out.println("Passed email: " + email);
        if(validateEmail(email) == true){
            String[] emailSplices = email.split("@");
            System.out.println("Splited email: " + emailSplices[1]);
             if(emailSplices[1].equals("gcu.ac.uk")){
                return true;
             }
             return false;
        }
        System.out.println("First validation : false");
        return false;

    }

    public static String getInvalidDomainError(String email){
        return "The " + email + "  is not a GCU email.";
    }

    /**
     * Email regex: https://emailregex.com/
     * General Email Regex (RFC 5322 Official Standard)
     * */
    public static boolean validateEmail(String email){
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    public static String getInvalidEmailError(){
        return "The email address is invalid.";
    }

    /**
     * Strong password with at least 8 characters, at least one digit, one uppercase letter, one lowercase letter, and one symbol.
     * */
    public static boolean validateStrongPassword(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public static String getWeakPasswordError(){
        return "Password must contain: at least 8 characters, 1 digit, " +
                "1 uppercase letter, 1 lowercase letter, and 1 symbol.";
    }

    public static boolean validateConfirmationPassword(String initialPassword, String confirmPassword){
        return initialPassword.equals(confirmPassword);
    }

    public static String confirmPasswordsError(){
        return "The passwords do not match.";
    }
}
