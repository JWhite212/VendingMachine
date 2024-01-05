import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for the coin inserted state. This is the state of the machine once a user has
 * inserted money. To proceed the user must purchase an item if enough money has been entered.
 */
public class CoinInsertedState implements State{
    private final VendingMachine vendingMachine;
    private double afterPurchaseRefundAmount = 0;

    public CoinInsertedState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * This method, changes implementation based on machines current state.
     * In this state the user inserts the chosen coin, with the inputted amount of coins.
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
        }
    }

    /**
     * Method used to tell the user the amount of coins currently deposited by them.
     *
     * @return The current amount of coins deposited by the user.
     */
    @Override
    public double amountDeposited() {
        double total = vendingMachine.getInsertedCoinStockTotal();
        return Rounding.round(total);
    }

    /**
     * This method is used to request a refund of the total inserted coins by the user.
     * In this state it cancels the order progress and sets the state to not selected state.
     * Throws an error if the refund cannot be given to the user due to current coin stock not being enough.
     *
     */
    @Override
    public void requestRefund(){
        double refundAmount = Rounding.round(amountDeposited());
        if (refundAmount == 0){
            System.out.println("You have not inserted any coins!");
        } else {
            if(refundAmount > vendingMachine.getCoinStockTotal()){
                throw new MachineStockException("CoinStock");
            }
            vendingMachine.calculateChangeDenominations(refundAmount);
            vendingMachine.setCoinBucket();
            System.out.println("Your total change £" + refundAmount + " Total has been placed into the bucket. Please collect your change!");
            vendingMachine.getInsertedCoinStock().setStock(0);
        }
        vendingMachine.getBucketCoins();
        System.out.println("Your order has been cancelled. Please select an item to proceed!");
        vendingMachine.setCurrentState(vendingMachine.getNotSelectedState());
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
     * In this state it purchases the selected item, if the money entered is enough to purchase the item.
     * Then sets the state to item purchases state.
     * Throws an error if the user has not entered enough money to purchase the item, or if the currently selected item is out of stock.
     *
     * @throws MachinePurchaseException Throws an error if the user has not entered enough money to purchase the item, or the item is out of stock.
     */
    @Override
    public void purchaseItem() throws MachinePurchaseException {
        Product selectedItem = vendingMachine.getSelectedItem();
        if(vendingMachine.getSelectedItemPrice() > amountDeposited()){
            System.out.println("You have not inserted enough money to purchase this item. You have inserted £" + amountDeposited());
            System.out.println("The currently selected item: " + selectedItem.toString() + "'s price is £" + vendingMachine.getSelectedItemPrice());
            throw new MachinePurchaseException("money");
        } else {
            int itemStock = vendingMachine.getProductStock().get(selectedItem);
            if (itemStock == 0) {
                throw new MachinePurchaseException("stock");
            } else {
                double selectedItemPrice = vendingMachine.getSelectedItemPrice();
                afterPurchaseRefundAmount = Rounding.round(vendingMachine.getInsertedCoinStockTotal() - selectedItemPrice);
                vendingMachine.calculateChangeDenominations(afterPurchaseRefundAmount);
                System.out.println(selectedItem.toString() + " was purchased!");
                vendingMachine.setSelectedItem(null);
                Product purchasedProduct = selectedItem;
                addPurchasedProductToBucket(purchasedProduct);
                vendingMachine.getProductStock().reduce(purchasedProduct);
                vendingMachine.setCurrentState(vendingMachine.getItemPurchasedState());
            }
        }
    }


    /**
     * This method is used to add the purchased product to the product bucket, and prints this information to the user.
     *
     * @param purchasedProduct The product that has been purchased by the user.
     */
    private void addPurchasedProductToBucket(Product purchasedProduct){
        System.out.println("Your purchased " + purchasedProduct.toString() + " dropped into the bucket. Please collect your purchased item!");
        vendingMachine.getProductBucket().insert(purchasedProduct);
    }

    /**
     * This method is used to collect the products purchased by the user from the bucket.
     * In this state no item has been purchased, so it prints to the user to please select an item.
     *
     * @return A list containing all the products collected from the bucket.
     */
    @Override
    public List<Product> getBucketProducts() {
        System.out.println("No item has been purchased! Please purchase an item to proceed.");
        return null;
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
            System.out.println("The bucket is empty. There are no coins to collect from the bucket!");
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
            vendingMachine.requestRefund();
            System.out.println("Logged in to Admin!");
            vendingMachine.setCurrentState(vendingMachine.getAdminModeState());
            vendingMachine.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Login");
        }
    }



}
