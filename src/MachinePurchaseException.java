/**
 * Class for Machine purchase errors. Catches errors when the user attempts to perform invalid purchase actions.
 */
public class MachinePurchaseException extends RuntimeException {
    public MachinePurchaseException(String errorType) {
        if (errorType.equals("stock")) {
            System.out.println("ERROR: The item you attempted to purchase is out of stock!");
        } else if(errorType.equals("money")) {
            System.out.println("ERROR: You do not have the required funds!");
        } else if(errorType.equals("refund")){
            System.out.println("ERROR: You have not entered any money to be refunded!");
        } else {
            System.out.println("ERROR!");
        }
    }
}