import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for the admin mode state. This is the state of the machine when a user has logged into
 * admin mode. From this state the admin can access a range of admin methods to manage the contents of
 * the machine.
 */
public class AdminModeState implements AdminState{
    private final VendingMachine vendingMachine;

    public AdminModeState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * This method, changes implementation based on machines current state.
     * In this state the admin can insert a set amount of the chosen coin.
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
            int coinStockLevel = vendingMachine.getCoinStock().get(coin);
            coinStockLevel = coinStockLevel + coinAmount;
            vendingMachine.getCoinStock().put(coin, coinStockLevel);
            System.out.println("Admin inserted " + coinAmount + " " + coin.toString() + " coins into the machine!");
        }
    }

    /**
     * Method used to tell the user the amount of coins currently deposited by them.
     * In this state, it prints the machine's coin stock information, and returns the total
     * value of all machine coins.
     *
     * @return The current amount of coins deposited by the user.
     */
    @Override
    public double amountDeposited() {
        this.printAdminCoinInfo();
        return vendingMachine.getCoinStockTotal();
    }

    @Override
    public void requestRefund() {
        System.out.println("Currently in Admin Mode. To Access this functionality Logout!");
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
     * In this state it simply retrieves the selected item from the machine. The admin is not required to pay for the item.
     * Throws an error if the currently selected item is out of stock.
     *
     * @throws MachinePurchaseException Throws an error if the user has not entered enough money to purchase the item, or the item is out of stock.
     */
    @Override
    public void purchaseItem() throws MachinePurchaseException {
        Product selectedItem = vendingMachine.getSelectedItem();
        int itemStock = vendingMachine.getProductStock().get(selectedItem);
        if (itemStock == 0) {
            String errorCode = "stock";
            throw new MachinePurchaseException(errorCode);
        } else {
            System.out.println("Admin retrieved one " + selectedItem.toString() + "! The item dropped into the bucket.");
            vendingMachine.setSelectedItem(null);
            Product purchasedProduct = selectedItem;
            vendingMachine.getProductBucket().insert(purchasedProduct);
            vendingMachine.getProductStock().reduce(purchasedProduct);
        }
    }

    /**
     * This method is used to collect the products purchased by the user from the bucket.
     * In this state, the purchased item is collected from the bucket, and returned to the user.
     * Prints a message to the user describing that they have collected their item.
     *
     * @return A list containing all the products collected from the bucket.
     */
    @Override
    public List<Product> getBucketProducts() {
        int size = vendingMachine.getProductBucket().size();
        int count = 0;
        List<Product> collectedItems = new ArrayList<>();
        for (Map.Entry<Enum, Integer> entry : vendingMachine.getProductBucket().entrySet()) {
            count++;
            if(entry.getValue() > 0){
                collectedItems.add((Product) entry.getKey());
                System.out.println("You collected your " + entry.getKey().toString() + " from the bucket!");
            } else if (size == count-1){
                System.out.println("The bucket is empty. There are no items to collect from the bucket!");

            }
        }
        vendingMachine.getProductBucket().setStock(0);
        if(vendingMachine.isMachineEmptyCheck()){
            System.out.println("The machine is empty. Please refill the machine!");
        } else {
            this.printAdminProductInfo();
        }
        return collectedItems;
    }

    /**
     * This method is used to collect the refunded coins from the bucket.
     *
     * @return A list containing all the coins collected from the bucket.
     */
    @Override
    public List<Coin> getBucketCoins() {
        double changeAmount = vendingMachine.getRefundCoinStockTotal();
        List<Coin> collectedCoins = new ArrayList<>();
        if(changeAmount == 0){
            System.out.println("The bucket is empty. There are no coins to collect from the bucket!");
        } else {
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinBucket().entrySet()) {
                if(entry.getValue() > 0){
                    collectedCoins.add(entry.getKey());
                }
            }
            System.out.println("You collected your £" + Rounding.round(changeAmount) + " total withdrawn money from the bucket!");
            vendingMachine.getRefundCoinStock().setStock(0);
            vendingMachine.getCoinBucket().setStock(0);
        }
        return collectedCoins;
    }

    /**
     * This method is used by the admin to log in to the admin mode of the machine to access admin methods.
     * Requires the correct username and password to be entered.
     *
     * In this state the User is already logged in, so the method prints to the admin that they are already logged in.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @throws AdminPrivilegeException Throws an error if the username or password are not correct.
     */
    @Override
    public void login(String username, String password) throws AdminPrivilegeException {
        System.out.println("You are already logged in to Admin!");
    }

    /**
     * This method is used by the admin to logout of the admin mode.
     *
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    @Override
    public void logout(){
        System.out.println("Logged out of Admin!");
        if(vendingMachine.isMachineEmptyCheck()){
            vendingMachine.setCurrentState(vendingMachine.getProductEmptyState());
        } else {
            vendingMachine.setCurrentState(vendingMachine.getNotSelectedState());
        }
    }

    /**
     * This method is used by the admin to withdraw all coins from the machine.
     * Throws an error if the user is not in admin mode.
     *
     * @return A list of all the coins withdrawn from the machine.
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    @Override
    public List<Coin> withdrawCoins() throws AdminPrivilegeException{
        double refundAmount = Rounding.round(vendingMachine.getCoinStockTotal());

        vendingMachine.calculateChangeDenominations(refundAmount);
        vendingMachine.setCoinBucket();
        System.out.println("Your total change £" + refundAmount + " Total has been placed into the bucket. Please collect your withdrawn coins!");
        vendingMachine.getInsertedCoinStock().setStock(0);
        return vendingMachine.getBucketCoins();
    }

    /**
     * This method is used by admins to deposit coins into the machine.
     * Throws an error if the user is not in admin mode.
     *
     * @param amount The amount of coins to input for each coin type.
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    @Override
    public void depositCoins(Integer amount) throws MachineStockException{
        if(amount > 0){
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinStock().entrySet()) {
                vendingMachine.insertCoin(entry.getKey(), amount);
            }
        } else {
            throw new MachineStockException("Inserted amount of coins must be more than 0");
        }
    }

    /**
     * This method is used by an admin to refill the entered product to the machine's maximum allowed stock level.
     * Throws an error if the user is not in admin mode.
     *
     * @param product The product to refill the current machine's max stock level.
     */
    @Override
    public void adminRefillProduct(Product product) {
        int machineMaxSize = vendingMachine.getMAX_SIZE();
        int currentStockLevel = vendingMachine.getProductStock().get(product);
        if(machineMaxSize > currentStockLevel){
            vendingMachine.getProductStock().put(product, machineMaxSize);
            System.out.println("The Vending Machine was fully refilled of " + product.toString() + "!");
        } else {
            System.out.println("The Vending Machine's max stock size is: " + machineMaxSize + ". The Vending Machine is already full of " + product.toString() + ".");
        }
    }

    /**
     * This method is used by the admin to completely refill the machine to its maximum allowed stock level for
     * all products.
     */
    @Override
    public void adminRefillAllProduct() {
        int machineMaxSize = vendingMachine.getMAX_SIZE();
        vendingMachine.getProductStock().setStock(machineMaxSize);
        System.out.println("The Vending Machine was fully refilled!");
    }

    /**
     * This method is used to provide admins with a breakdown of the current coin stock levels in the machine
     * and a total value of all these coins.
     *
     * @return A string output of the current coin stock levels of the machine and total value of the coins.
     */
    @Override
    public String printAdminCoinInfo(){
        if(vendingMachine.getCurrentState() == vendingMachine.getAdminModeState()){
            double totalMachineMoney = vendingMachine.getCoinStockTotal();
            StringBuilder str = new StringBuilder();
            str.append("\n |--------------------------------------");
            str.append("\n | \t Printing Coin Stock information");
            str.append("\n |--------------------------------------");
            str.append("\n | \t Total money in machine = £").append(totalMachineMoney);
            str.append("\n |--------------------------------------");
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinStock().entrySet()) {
                Coin coin = entry.getKey();
                int value = vendingMachine.getCoinStock().get(coin);
                str.append("\n |\t Coin: ").append(coin).append(" Amount: ").append(value);
            }
            str.append("\n |--------------------------------------");
            System.out.println(str);
            return str.toString();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    /**
     * This method is used to provide the admins with a breakdown of the current product stock levels in the machine.
     *
     * @return A string output of the current product stock levels of the machine.
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    @Override
    public String printAdminProductInfo(){
        if(vendingMachine.getCurrentState() == vendingMachine.getAdminModeState()){
            StringBuilder str1 = new StringBuilder();
            str1.append("\n |--------------------------------------");
            str1.append("\n | \t Printing Product Stock information");
            str1.append("\n |--------------------------------------");
            for (Map.Entry<Enum, Integer> entry : vendingMachine.getProductStock().entrySet()) {
                Product product = (Product) entry.getKey();
                int value = vendingMachine.getProductStock().get(product);
                str1.append("\n |\t Product: ").append(product).append(" Amount: ").append(value);
            }
            str1.append("\n |--------------------------------------");
            System.out.println(str1);
            return str1.toString();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    /**
     * This method is used to provide the admins with a breakdown of the current product and coin stock levels in the machine.
     *
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    @Override
    public void printAdminInfo(){
        if(vendingMachine.getCurrentState() == vendingMachine.getAdminModeState()){
            printAdminProductInfo();
            printAdminCoinInfo();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }
}
