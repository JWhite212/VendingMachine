import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for the item purchased state. This is the state of the machine when an item has been purchased by the user.
 */
public class ItemPurchasedState implements State{
    private final VendingMachine vendingMachine;

    public ItemPurchasedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
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
        System.out.println("Please collect your money and items from the bucket!");
    }

    /**
     * Method used to tell the user the amount of coins currently deposited by them.
     * In this state, it is used to calculate and print to the user the amount of change they will
     * be returned after purchasing the selected item.
     *
     * @return The current amount of coins deposited by the user.
     */
    @Override
    public double amountDeposited() {
        double depositedAmount = vendingMachine.getRefundCoinStockTotal();
        System.out.println("You have £" + depositedAmount + " still remaining deposited in the machine!");
        return depositedAmount;
    }

    /**
     * This method is used to request a refund of the total inserted coins by the user.
     * In this state it places the remaining change into the coin bucket.
     * Throws an error if the refund cannot be given to the user due to current coin stock not being enough.
     *
     */
    @Override
    public void requestRefund() {
        double refundAmount = Rounding.round(amountDeposited());
        if (refundAmount == 0){
            System.out.println("You have no coins in the machine to refund. Please collect your items from the bucket!");
        } else {
            if(refundAmount > vendingMachine.getCoinStockTotal()){
                throw new MachineStockException("CoinStock");
            }
            vendingMachine.calculateChangeDenominations(refundAmount);
            vendingMachine.setCoinBucket();
            System.out.println("Your total change £" + refundAmount + " Total has been placed into the bucket. Please collect your change!");
            vendingMachine.getBucketCoins();
            vendingMachine.getBucketProducts();
            vendingMachine.getInsertedCoinStock().setStock(0);
            vendingMachine.getRefundCoinStock().setStock(0);
        }

    }


    /**
     * This method prints to the user that they need to collect their items from the bucket to progress.
     */
    private void printCollectBucketItems(){
        System.out.println("Please collect your money and items from the bucket!");
    }

    /**
     * This method is used by the user to select the item they wish to purchase using the code of the item.
     * In this state prints a message to the user that they need to collect their items from the bucket.
     * Throws an error if the code entered does not match any codes of the products in the machine.
     *
     * @param code product code of item to be selected by the user.
     * @throws MachineSelectionException Throws error if the code entered by the user is an incorrect code.
     */
    @Override
    public void selectItem(String code) throws MachineSelectionException {
        printCollectBucketItems();
    }

    /**
     * This method is used to return the currently selected item to the user.
     * In this state prints a message to the user that they need to collect their items from the bucket.
     *
     * @return null.
     */
    @Override
    public Product currentItem() throws MachineSelectionException {
        printCollectBucketItems();
        return null;
    }

    /**
     * This method is used to purchase the currently selected item.
     * In this state prints a message to the user that they need to collect their items from the bucket.
     * Throws an error if the user has not entered enough money to purchase the item, or if the currently selected item is out of stock.
     *
     * @throws MachinePurchaseException Throws an error if the user has not entered enough money to purchase the item, or the item is out of stock.
     */
    @Override
    public void purchaseItem() throws MachinePurchaseException {
        printCollectBucketItems();
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
            vendingMachine.setCurrentState(vendingMachine.getProductEmptyState());
        } else {
            vendingMachine.setCurrentState(vendingMachine.getNotSelectedState());
        }
        return collectedItems;
    }

    /**
     * This method is used to collect the refunded coins from the bucket.
     * In this state, the refunded coins are collected from the bucket before the machine is reset to its default state.
     *
     * @return A list containing all the coins collected from the bucket.
     */
    @Override
    public List<Coin> getBucketCoins() {
        double changeAmount = vendingMachine.getRefundCoinStockTotal();
        List<Coin> collectedCoins = new ArrayList<>();
        if(changeAmount == 0){
            System.out.println("The bucket is empty. There are no items to collect from the bucket!");
        } else {
            for (Map.Entry<Coin, Integer> entry : vendingMachine.getCoinBucket().entrySet()) {
                if(entry.getValue() > 0){
                    for(int i = 0;entry.getValue() > i;i++){
                        collectedCoins.add(entry.getKey());
                    }
                }
            }
            System.out.println("You collected your £" + Rounding.round(changeAmount) + " total change from the bucket!");
            vendingMachine.getRefundCoinStock().setStock(0);
            vendingMachine.getCoinBucket().setStock(0);
        }
        return collectedCoins;
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
            vendingMachine.getAdminModeState().printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }




}
