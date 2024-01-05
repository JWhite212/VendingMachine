import java.util.List;

/**
 * Class for the product empty state. This is the state of the machine when the machine has no remaining products stocked.
 * To leave this state the admin must log in and refill the machine.
 */
public class ProductEmptyState implements State{
    private final VendingMachine vendingMachine;
    public ProductEmptyState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }


    /**
     * This method prints to the user that the machine is empty, and needs to be restocked by an administrator.
     */
    private void printMachineEmptyMessage(){
        System.out.println("Machine is out of all stock. Please contact the Administrator of the machine!");
    }

    /**
     * This method, changes implementation based on machines current state.
     * In this state the method prints to the user the machine is empty of all stock.
     *
     * @param coin Coin to be inserted.
     * @param coinAmount Amount of the chosen coin to be inserted.
     * @throws MachineStockException Throws exception if coin amount is not a number greater than 0.
     */
    @Override
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException {
        printMachineEmptyMessage();
    }

    /**
     * Method used to tell the user the amount of coins currently deposited by them.
     * In this state the method prints to the user the machine is empty of all stock.
     *
     * @return The current amount of coins deposited by the user.
     */
    @Override
    public double amountDeposited() {
        printMachineEmptyMessage();
        return 0;
    }

    @Override
    public void requestRefund() {
        printMachineEmptyMessage();
    }

    /**
     * This method is used by the user to select the item they wish to purchase using the entered code of the item.
     * In this state the method prints to the user the machine is empty of all stock.
     * Throws an error if the code entered does not match any codes of the products in the machine.
     *
     * @param code product code of item to be selected by the user.
     * @throws MachineSelectionException Throws error if the code entered by the user is an incorrect code.
     */
    @Override
    public void selectItem(String code) throws MachineSelectionException {
        printMachineEmptyMessage();
    }

    /**
     * This method is used to return the currently selected item to the user.
     * In this state the method prints to the user the machine is empty of all stock.
     * It prints the currently selected item, and its price.
     *
     * @return The currently selected item by the user.
     */
    @Override
    public Product currentItem() {
        printMachineEmptyMessage();
        return null;
    }

    /**
     * This method is used to purchase the currently selected item.
     * In this state the method prints to the user the machine is empty of all stock.
     * Throws an error if the user has not entered enough money to purchase the item, or if the currently selected item is out of stock.
     *
     * @throws MachinePurchaseException Throws an error if the user has not entered enough money to purchase the item, or the item is out of stock.
     */
    @Override
    public void purchaseItem() throws MachinePurchaseException {
        printMachineEmptyMessage();
    }

    /**
     * This method is used to collect the products purchased by the user from the bucket.
     * In this state the method prints to the user the machine is empty of all stock.
     * Prints a message to the user describing that they have collected their item.
     *
     * @return A list containing all the products collected from the bucket.
     */
    @Override
    public List<Product> getBucketProducts() {
        printMachineEmptyMessage();
        return null;
    }

    /**
     * This method is used to collect the refunded coins from the bucket.
     * In this state the method prints to the user the machine is empty of all stock.
     *
     * @return A list containing all the coins collected from the bucket.
     */
    @Override
    public List<Coin> getBucketCoins() {
        printMachineEmptyMessage();
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
        if("OwnerUsername".equals(username) && "P4ssw0rd".equals(password)){
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }

}
