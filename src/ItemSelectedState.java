import java.util.List;

/**
 * Class for the item selected state. This is the state of the machine once a user has
 * selected an item. To proceed, the user must insert money.
 */
public class ItemSelectedState implements State{
    private final VendingMachine vendingMachine;
    public ItemSelectedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * This method, changes implementation based on machines current state.
     * In this state the user inserts the chosen coin, with the inputted amount of coins.
     * Then transitions the state of the machine to the coin inserted state.
     *
     * @param coin Coin to be inserted.
     * @param coinAmount Amount of the chosen coin to be inserted.
     * @throws MachineStockException Throws exception if coin amount is not a number greater than 0.
     */
    @Override
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        if (coinAmount < 1){
            throw new MachineStockException("Coin amount");
        } else {
            vendingMachine.getCoinSlot().coinInserted(coin,coinAmount);
            vendingMachine.setCurrentState(vendingMachine.getCoinInsertedState());
        }
    }

    /**
     * This method is used by the user to select the item they wish to purchase using the code of the item.
     * In this state changes the currently selected item to the entered code's item.
     * Throws an error if the code entered does not match any codes of the products in the machine.
     *
     * @param code product code of item to be selected by the user.
     * @throws MachineSelectionException Throws error if the code entered by the user is an incorrect code.
     */
    @Override
    public void selectItem(String code) throws MachineSelectionException {
        try {
            vendingMachine.getKeypad().enterItemCode(code);
        } catch (Exception e) {
            throw new MachineSelectionException("InvalidCode");
        }
    }

    /**
     * This method is used to return the currently selected item to the user.
     * It prints the currently selected item, and its price.
     *
     * @return The currently selected item by the user.
     */
    @Override
    public Product currentItem() {
        String currentItem = vendingMachine.getSelectedItem().toString();
        System.out.println("The currently selected item is: " + currentItem);
        System.out.println("The currently selected item: " + currentItem + "'s price is £" + vendingMachine.getSelectedItemPrice());
        return vendingMachine.getSelectedItem();
    }

    /**
     * This method is used to purchase the currently selected item.
     * In this state it prints to the user that no money has been inserted, to purchase the selected item.
     * Throws an error if the user has not entered enough money to purchase the item, or if the currently selected item is out of stock.
     *
     * @throws MachinePurchaseException Throws an error if the user has not entered enough money to purchase the item, or the item is out of stock.
     */
    @Override
    public void purchaseItem() throws MachinePurchaseException {
        System.out.println("No money has been inserted. Please insert the money required to purchase the selected item!");
    }

    /**
     * This method is used to collect the products purchased by the user from the bucket.
     * In this state no item has been purchased, so it prints to the user to please purchase an item.
     *
     * @return A list containing all the products collected from the bucket.
     */
    @Override
    public List<Product> getBucketProducts() {
        System.out.println("No item has been purchased! To proceed, please insert the money required to purchase the selected item!");
        return null;
    }

    /**
     * This method is used to collect the refunded coins from the bucket.
     * In this state no item has been purchased, so it prints to the user to please purchase an item.
     *
     * @return A list containing all the coins collected from the bucket.
     */
    @Override
    public List<Coin> getBucketCoins() {
        System.out.println("No coins are in the bucket! To proceed, please insert the money required to purchase the selected item!");
        return null;
    }

    /**
     * This method is used by the admin to log in to the admin mode of the machine to access admin methods.
     * Requires the correct username and password to be entered.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @throws AdminPrivilegeException Throws an error if the username or password are not correct.
     */
    @Override
    public void login(String username, String password) throws AdminPrivilegeException{
        if(username.equals("OwnerUsername") && password.equals("P4ssw0rd")){
            vendingMachine.setSelectedItem(null);
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }
}
