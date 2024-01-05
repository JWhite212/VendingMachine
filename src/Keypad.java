/**
 * Class for the keypad component of the vending machine.
 * Contains methods for the functionality of selecting an item to purchase by entering its code.
 */
public class Keypad {

    private final VendingMachine vendingMachine;
    public Keypad(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * This method is used to enter the item code of the product to be selected by the user.
     *
     * @param code The code of the item to be selected by the user.
     */
    public void enterItemCode(String code){
        vendingMachine.setSelectedItem(Product.getProducts(code));
        vendingMachine.setSelectedItemCode(code);
        String selectedItemString = Product.getProducts(code).toString();
        System.out.println("You Selected: " + selectedItemString);
        System.out.println("The currently selected item: " + selectedItemString + "'s price is £" + vendingMachine.getSelectedItemPrice());
    }
}
