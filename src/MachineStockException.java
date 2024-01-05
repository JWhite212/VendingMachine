/**
 * Class for machine stock errors. Catches errors when the user attempts to perform actions, without providing a valid
 * inserted coin amount, or when the machine does not contain enough change to provide the user with a refund.
 */
public class MachineStockException extends RuntimeException {
    public MachineStockException(String errorType) {
        if (errorType.equals("Coin amount")) {
            System.out.println("ERROR: You cannot enter negative amounts of coins, please enter a positive value!");
        } else if (errorType.equals("CoinStock")) {
            System.out.println("ERROR: The machine does not contain enough change to provide the refunded amount!");
        } else {
            System.out.println("ERROR!");
        }
    }
}
