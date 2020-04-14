package sanitization;

import utilities.InputValidation;

import java.util.ArrayList;
import java.util.List;

public class InputControlAndSanitization {

    public List<String> validation(String email, String password){

        List<String> errors = new ArrayList<>();

        if(!InputValidation.validateEmail(email)){
            errors.add(InputValidation.getInvalidEmailError());
        }

//        if(!InputValidation.validateMaxInputLength(password, 8)){
//            errors.add(InputValidation.getMaxInputLengthError(password));
//        }
        if(errors.size() == 0){
            errors.add("cleaned");
            return errors;
        }
       return errors;
    }

    public List<String> validateRegInputs(String email,String name, String password, String confirmPassword){

        List<String> errors = new ArrayList<>();

        if(!InputValidation.validateMaxInputLength(name, 225)){
            errors.add(InputValidation.getMaxInputLengthError("name"));
        }

        if(!InputValidation.validateEmail(email)){
            errors.add(InputValidation.getInvalidEmailError());
        }
        if(!InputValidation.validateDomainEmail(email)){
            errors.add(InputValidation.getInvalidDomainError(email));
        }
        if(!InputValidation.validateMinInputLength(password, 8)){
            errors.add(InputValidation.getMinInputLengthError(password));
        }

        if(!InputValidation.validateStrongPassword(password)){
            errors.add(InputValidation.getWeakPasswordError());
        }

        if(!InputValidation.validateConfirmationPassword(password, confirmPassword)){
            errors.add(InputValidation.confirmPasswordsError());
        }
        if(errors.size() == 0){
            errors.add("cleaned");
            return errors;
        }
        return errors;
    }

    public int validateRole(String role){
        String hiddenRoleAS = "ACADEMIC STAFF";
        String hiddenRoleSF = "GCU AVS STAFF";
        int validRole;
//        if(role.length() != hiddenRoleAS.length() || role.length() != hiddenRoleSF.length()){
//            return 500;
//        }
        if(role.equals(hiddenRoleAS)){
            validRole = 3;
            return validRole;
        }else if(role.equals(hiddenRoleSF)){
            validRole = 2;
            return validRole;
        }else{
            validRole = Integer.MIN_VALUE;
            return validRole;
        }

    }

    public List<String> validateTicketInputs(String equipmentName, String description){
        List<String> errors = new ArrayList<>();

        if(!InputValidation.validateMaxInputLength(equipmentName, 225)){
            errors.add(InputValidation.getMaxInputLengthError("equipment name"));
        }

        if(!InputValidation.validateMaxInputLength(description, 500)){
            errors.add(InputValidation.getMaxInputLengthError("description"));
        }

        if(errors.size() == 0){
            errors.add("cleaned");
            return errors;
        }
        return errors;
    }

}
