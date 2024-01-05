/**
 * Class for admin privilege exceptions. Catches exceptions when the user does not enter
 * the correct login details, or when the user attempts to use a method without being in admin state.
 */
public class AdminPrivilegeException extends RuntimeException {
    public AdminPrivilegeException(String errorType) {
        if(errorType.equals("Login")){
            System.out.println("ERROR: The login details entered were incorrect!");
        } else {
            System.out.println("ERROR: You cannot perform this action without Admin privileges!");
        }
    }
}