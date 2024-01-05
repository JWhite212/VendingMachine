/**
 * Class for machine setup errors. Catches errors when the machine is not setup with valid values.
 */
public class MachineInitialisationError extends RuntimeException {
    public MachineInitialisationError(String errorType) {
        if (errorType.equals("Size")) {
            System.out.println("ERROR: Vending Machine MAX_SIZE must be greater than 0!");
        } else if (errorType.equals("Product")) {
            System.out.println("ERROR: Vending Machine Product Level must be a positive number!");
        } else if (errorType.equals("Change")) {
            System.out.println("ERROR: Vending Machine Change Level must be a positive number!");
        } else if (errorType.equals("ProductMax")) {
            System.out.println("ERROR: Vending Machine Product Level must be less than the max size of the Vending Machine!");
        }  else {
            System.out.println("ERROR: Vending Machine could not be initialised!");
        }
    }

}
