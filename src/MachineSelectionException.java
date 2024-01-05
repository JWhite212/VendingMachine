/**
 * Class for machine selection exceptions. Catches errors when the user enters an invalid product code, or
 * tries to perform actions without having an item selected.
 */
public class MachineSelectionException extends RuntimeException {

    public MachineSelectionException(String errorType) {
        if(errorType.equals("InvalidCode")){
            System.out.println("ERROR: Code entered is invalid!");
        } else if(errorType.equals("noItemSelected")){
            System.out.println("ERROR: No item has been selected! Please select an item you wish purchase!");
        } else {
            System.out.println("ERROR: Selection error!");
        }
    }
}
