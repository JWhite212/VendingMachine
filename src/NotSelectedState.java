import java.util.List;

/**
 * Class for the not selected state. this is the default state of the vending machine if it is not
 * empty of products. To progress the user must select an item to purchase.
 */
public class NotSelectedState implements State{
    private final VendingMachine vendingMachine;

    /**
     * The constructor for the not selected state.
     *
     * @param vendingMachine passes the current vending machine into the constructor.
     */
    public NotSelectedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        vendingMachine.setSelectedItem(null);
    }

    /**
     * This method, changes implementation based on machines current state.
     * In this state the method prints to the user. To be able to insert a coin, the user needs to
     * first select an item.
     *
     * @param coin Coin to be inserted.
     * @param coinAmount Amount of the chosen coin to be inserted.
     * @throws MachineStockException Throws exception if coin amount is not a number greater than 0.
     */
    @Override
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        System.out.println("Please Select an item before inserting coins!");
    }


    /**
     * This method is used by the user to select the item they wish to purchase using the entered code of the item.
     * In this state sets the selected item to the entered code's item, and then transitions the state of the machine.
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
        vendingMachine.setCurrentState(vendingMachine.getItemSelectedState());
    }

    /**
     * This method prints to the user that no item has been selected.
     */
    private void printNotSelectedMessage(){
        System.out.println("No item has been selected! Please select an item you wish purchase!");
    }

    /**
     * This method is used to return the currently selected item.
     * In this state there is no currently selected item, it returns null, and prints to the user no item has been selected.
     *
     * @return null.
     */
    @Override
    public Product currentItem() {
        printNotSelectedMessage();
        return null;
    }

    /**
     * This method is used to purchase the currently selected item.
     * In this state it prints to the user that no item has currently been selected.
     * Throws an error if the user has not entered enough money to purchase the item, or if the currently selected item is out of stock.
     *
     * @throws MachinePurchaseException Throws an error if the user has not entered enough money to purchase the item, or the item is out of stock.
     */
    @Override
    public void purchaseItem() throws MachinePurchaseException {
        printNotSelectedMessage();
    }

    /**
     * This method is used to collect the products purchased by the user from the bucket.
     * In this state no item has been purchased, so it prints to the user to please select an item.
     *
     * @return A list containing all the products collected from the bucket.
     */
    @Override
    public List<Product> getBucketProducts() {
        System.out.println("No item has been purchased! Please select an item you wish purchase!");
        return null;
    }

    /**
     * This method is used to collect the refunded coins from the bucket.
     * In this state no item has been purchased, so it prints to the user to please select an item.
     *
     * @return A list containing all the coins collected from the bucket.
     */
    @Override
    public List<Coin> getBucketCoins() {
        System.out.println("No coins are in the bucket! To proceed, please select an item you wish purchase!");
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
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }

}
