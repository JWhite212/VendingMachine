import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the Context class for the state design pattern.
 * Class for the Vending Machine containing all the different State objects, and collections for
 * product and coin to share between states. Provides a range of method implementations to provide
 * user interaction with the Vending Machine.
 *
 * @author Jamie White
 *
 */
public class VendingMachine {

    private final CoinSlot coinSlot;
    private final Keypad keypad;
    private final State itemPurchasedState;
    private final State itemSelectedState;
    private final State notSelectedState;
    private final State coinInsertedState;
    private final State productEmptyState;
    private final AdminState adminModeState;
    private State currentState;
    private final int MAX_SIZE;
    private final int productLevel;
    private final int changeLevel;
    private Product selectedItem;
    private String selectedItemCode;
    private double selectedItemPrice;

    private final CoinStock<Coin, Integer> coinStock = new CoinStock<>();
    private final CoinStock<Coin, Integer> insertedCoinStock = new CoinStock<>();
    private final CoinStock<Coin, Integer> refundCoinStock = new CoinStock<>();
    private final Stock<Product, Integer> productStock = new ProductStock<>();
    private final Stock<Product, Integer> productBucket = new ProductStock<>();
    private final CoinStock<Coin, Integer> coinBucket = new CoinStock<>();
    private List<Product> collectedProducts = new ArrayList<>();
    private List<Coin> collectedCoins = new ArrayList<>();

    /**
     * Constructor for the Vending Machine. Initialises the Vending Machine based on three parameters
     * specified by the Vending Machine administrator.
     *
     * @param MAX_SIZE  The max size of the Vending Machine, the maximum amount of Products it can store of one type.
     * @param productLevel  The amount of each product the machine starts with.
     * @param changeLevel   The amount of each coin the machine starts with.
     * @throws MachineInitialisationError   Throws error if values are negative values or if the product level is greater than the max size of the machine.
     */
    public VendingMachine(int MAX_SIZE, int productLevel, int changeLevel) throws MachineInitialisationError {
        this.MAX_SIZE = MAX_SIZE;
        this.productLevel = productLevel;
        this.changeLevel = changeLevel;

        this.coinSlot = new CoinSlot(this);
        this.keypad = new Keypad(this);

        if(MAX_SIZE < 1){
            throw new MachineInitialisationError("Size");
        } else if(productLevel < 0){
            throw new MachineInitialisationError("Product");
        }else if(MAX_SIZE < productLevel){
            throw new MachineInitialisationError("ProductMax");
        } else if(changeLevel < 0){
            throw new MachineInitialisationError("Change");
        } else {
            coinStock.setStock(changeLevel);
            productStock.setStock(productLevel);
        }

        itemPurchasedState = new ItemPurchasedState(this);
        coinInsertedState = new CoinInsertedState(this);
        itemSelectedState = new ItemSelectedState(this);
        notSelectedState = new NotSelectedState(this);
        productEmptyState = new ProductEmptyState(this);
        adminModeState = new AdminModeState(this);

        if(productLevel > 0){
            currentState = notSelectedState;
        } else {
            currentState = productEmptyState;
        }

    }

    /**
     * Takes a state parameter and sets the current state to this state.
     * Used to progress the machine between states.
     *
     * @param state The state to set the current state to.
     */
    void setCurrentState(State state) {
        this.currentState = state;
    }

    /**
     * @return Returns the current state the machine is in.
     */
    State getCurrentState() {
        return currentState;
    }

    /**
     * @return Returns the item purchased state.
     */
    State getItemPurchasedState() { return itemPurchasedState; }

    /**
     * @return Returns the coin inserted state.
     */
    State getCoinInsertedState() { return coinInsertedState; }

    /**
     * @return Returns the item selected state.
     */
    State getItemSelectedState() { return itemSelectedState; }

    /**
     * Sets the currently selected item to Null.
     * Sets the currently selected item code to Null.
     * This is the base state of the machine; if the machine is not empty.
     *
     * @return Returns the not selected state. This being the base state of the machine.
     */
    State getNotSelectedState() {
        setSelectedItem(null);
        setSelectedItemCode(null);
        return notSelectedState;
    }

    /**
     * This is the base state of the machine, if the machine is empty.
     * Requires Admin to refill machine to progress from this state.
     *
     * @return Returns the product empty state.
     */
    State getProductEmptyState() {
        System.out.println("The machine is empty! All stock has been sold!");
        return productEmptyState;
    }

    /**
     * This is the state authorised only for users upon entering the correct login parameters.
     *
     * @return Returns the admin mode state.
     */
    AdminState getAdminModeState() {
        return adminModeState;
    }

    /**
     * This method is used to get the vending machine's coin slot object
     *
     * @return The object coin slot.
     */
    CoinSlot getCoinSlot() {
        return this.coinSlot;
    }

    /**
     * This method is used to get the vending machine's keypad object
     *
     * @return The object keypad.
     */
    public Keypad getKeypad() {
        return this.keypad;
    }

    /**
     * @return The max size of the machine.
     */
    int getMAX_SIZE() {
        return this.MAX_SIZE;
    }

    /**
     * @return The starting product level of the machine.
     */
    int getProductLevel() {
        return this.productLevel;
    }

    /**
     * @return The starting change level of the machine.
     */
    int getChangeLevel() {
        return this.changeLevel;
    }

    /**
     * @return The collection CoinStock. This is the machines overall amount of contained coins.
     */
    CoinStock<Coin, Integer> getCoinStock() { return coinStock; }

    /**
     * @return The collection InsertedCoinStock. This is the machines overall inserted coins.
     */
    CoinStock<Coin, Integer> getInsertedCoinStock() {
        return insertedCoinStock;
    }

    /**
     * @return The collection Refund coin stock. This is the machines overall coins to be refunded to the user.
     */
    CoinStock<Coin, Integer> getRefundCoinStock() {
        return refundCoinStock;
    }

    /**
     * @return The collection Product stock. This is the machines overall amount of products in stock.
     */
    Stock<Product, Integer> getProductStock() { return productStock; }

    /**
     * @return The collection product bucket. This is the machines current stock of products in the bucket.
     */
    Stock<Product, Integer> getProductBucket() { return productBucket; }

    /**
     * @return The collection coin bucket. This is the machines current stock of coins in the bucket.
     */
    CoinStock<Coin, Integer> getCoinBucket() { return coinBucket; }

    /**
     * @return The total summed value of the coins in the collection coin Stock.
     */
    double getCoinStockTotal() { return coinStock.getMachineTotalMoneyValue(); }

    /**
     * @return The total summed value of the coins in the collection inserted coin Stock.
     */
    double getInsertedCoinStockTotal() { return insertedCoinStock.getMachineTotalMoneyValue(); }

    /**
     * @return The total summed value of the coins in the collection refund coin Stock.
     */
    double getRefundCoinStockTotal() { return refundCoinStock.getMachineTotalMoneyValue(); }

    /**
     * @return The total summed value of the coins in the collection coin bucket.
     */
    double getCoinBucketTotal() { return coinBucket.getMachineTotalMoneyValue(); }

    /**
     * Method used to copy the contents of the Refund coin stock collection to the coin bucket collection.
     */
    void setCoinBucket() {
        for (Map.Entry<Coin, Integer> entry : refundCoinStock.entrySet()) {
            Coin coin = entry.getKey();
            int value = refundCoinStock.get(coin);
            coinBucket.put(coin,value);
        }
    }

    /**
     * This method sets the currently selected item's code.
     *
     * @param code The item code of the currently selected item.
     */
    void setSelectedItemCode(String code) {
        this.selectedItemCode = code;
    }

    /**
     * This method sets the currently selected item.
     *
     * @param selectedItem The currently selected item by the user.
     */
    void setSelectedItem(Product selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * This method returns the currently selected item.
     *
     * @return The currently selected item.
     */
    Product getSelectedItem() {
        return selectedItem;
    }

    /**
     * This method, changes implementation based on machines current state.
     * In its correct state. It inserts the chosen coin and chosen amount into the machine.
     *
     * @param coin Coin to be inserted.
     * @param coinAmount Amount of the chosen coin to be inserted.
     * @throws MachineStockException Throws exception if coin amount is not a number greater than 0.
     */
    public void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException{
        currentState.insertCoin(coin, coinAmount);
        if(getInsertedCoinStockTotal() > 0){
            System.out.println("Total money inserted = £" + amountDeposited());
        }
    }


    /**
     * Method used to tell the user the amount of coins currently deposited by them.
     *
     * @return The current amount of coins deposited by the user.
     */
    public double amountDeposited() {
        return currentState.amountDeposited();
    }

    /**
     * This method is used to request a refund of the total inserted coins by the user.
     * If in the item selected state, it cancels the current order and returns the user to the base machine state.
     *
     */
    public void requestRefund() {
        currentState.requestRefund();
    }

    /**
     * This method is used to check if the machine is completely empty of all products.
     * If it is, the machine is progressed to product empty state.
     *
     * @return True if the machine is empty of all products. False if the machine is not empty of products.
     */
    boolean isMachineEmptyCheck(){
        for (Map.Entry<Enum, Integer> entry : productStock.entrySet()) {
            if(productStock.get((Product) entry.getKey()) > 0){
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to calculate the change coin denominations to return to the user based on the 
     * amount of change to be refunded. Method ensures that change refunded to user is the largest available
     * coins in stock, totaling to the required amount to be refunded.
     *
     * @param amount The amount of money to break down into the largest available coin denominations.
     * @return refund coin stock containing coins to be returned to the user.
     */
    Stock<Coin,Integer> calculateChangeDenominations(double amount){
        double refundAmount = amount;
        for (Map.Entry<Coin, Integer> entry : coinStock.entrySet()) {
            refundAmount = Rounding.round(refundAmount);
            if(refundAmount == 0){
                break;
            } else {
                Coin coin = entry.getKey();
                double coinValue = coin.getMoneyValue();
                int coinStockLevel = entry.getValue();
                int coinMultiplier = (int) (refundAmount / coinValue);
                if(coinMultiplier == 0){
                    continue;
                } else {
                    double remainder = (refundAmount % coinValue);
                    String coinString = coin.toString();
                    if(coinMultiplier <= coinStockLevel){
                        refundCoinStock.put(coin,coinMultiplier);
                        System.out.println(coinMultiplier + " " + coinString + " coin was placed in the bucket");
                        coinStock.put(coin,coinStockLevel-coinMultiplier);
                        refundAmount = refundAmount - (coinValue * coinMultiplier);
                    } else {
                        coinMultiplier = coinStockLevel;
                        refundCoinStock.put(coin,coinMultiplier);
                        System.out.println(coinMultiplier + " " + coinString + " coin was placed in the bucket");
                        coinStock.put(coin,coinStockLevel-coinMultiplier);
                        refundAmount = refundAmount - (coinValue * coinMultiplier);
                    }
                }
            }
        }
        return refundCoinStock;
    }

    /**
     * This method is used by the user to select the item they wish to purchase using the code of the item.
     * Throws an error if the code entered does not match any codes of the products in the machine.
     *
     * @param code product code of item to be selected by the user.
     * @throws MachineSelectionException Throws error if the code entered by the user is an incorrect code.
     */
    public void selectItem(String code) throws MachineSelectionException{
        currentState.selectItem(code);
    }

    /**
     * This method is used to alert the user of what item they have currently selected.
     * Provides information on the price of the currently selected item.
     *
     * @return The user's currently selected item.
     * @throws MachineSelectionException Throws an error if the user has not currently selected an item.
     */
    public Product currentItem() throws MachineSelectionException {
        if(this.selectedItem == null){
            throw new MachineSelectionException("noItemSelected");
        }
        return currentState.currentItem();
    }

    /**
     * This method is used to return the currently selected item's price.
     *
     * @return The currently selected item's price.
     * @throws MachineSelectionException Throws an error if the user has not currently selected an item.
     */
    double getSelectedItemPrice() throws MachineSelectionException {
        if(this.selectedItem == null){
            throw new MachineSelectionException("noItemSelected");
        }else {
            double itemPrice = Rounding.round(Product.getPrice(selectedItemCode));
            selectedItemPrice = itemPrice;
            return selectedItemPrice;
        }
    }

    /**
     * This method is used to purchase the currently selected item.
     * Throws an error if the user has not entered enough money to purchase the item, or if the currently selected item is out of stock.
     *
     * @throws MachinePurchaseException Throws an error if the user has not entered enough money to purchase the item, or the item is out of stock.
     */
    public void purchaseItem() throws MachinePurchaseException {
        currentState.purchaseItem();
        if(getCurrentState() == getItemPurchasedState()){
            requestRefund();
        }
    }

    /**
     * This method is used to collect the products purchased by the user from the bucket.
     *
     * @return A list containing all the products collected from the bucket.
     */
    public List<Product> getBucketProducts() {
        List<Product> products = currentState.getBucketProducts();
        if(products != null) {
            this.collectedProducts = products;
        }
        return this.collectedProducts;
    }

    /**
     * This method is used to check the machine is working as intended.
     *
     * @return A list containing all the products that have been collected from the bucket.
     */
    List<Product> getCollectedProducts() {
        try{
            return this.collectedProducts;
        } catch (Exception e){
            System.out.println("Collected Products is null");
        }
        return null;
    }

    /**
     * This method is used to collect the refunded coins from the bucket.
     *
     * @return A list containing all the coins collected from the bucket.
     */
    public List<Coin> getBucketCoins() {
        List<Coin> coins = currentState.getBucketCoins();
        if(coins != null) {
            this.collectedCoins = coins;
        }
        return this.collectedCoins;
    }

    /**
     * This method is used to check the machine is working as intended.
     *
     * @return A list containing all the coins that have been collected from the bucket.
     */
    List<Coin> getCollectedCoins() {
        try{
            return this.collectedCoins;
        } catch (Exception e){
            System.out.println("Collected Coins is null");
        }
        return null;
    }


    /**
     * This method is used to check the current stock level of the product with the entered code.
     * Throws an error if the code is not a code of a contained product.
     *
     * @param code the code of the product to check stock levels of.
     * @return The current amount of products currently in the machine.
     * @throws MachineSelectionException Throws error if the code is incorrect and not a code of a product.
     */
    int getStock(String code) throws MachineSelectionException {
        try {
            Product product = Product.getProducts(code);
            int stockLevel = getProductStock().get(product);
            System.out.println("Their are "+ stockLevel + " remaining " + product.toString() + " in the machine!");
            return stockLevel;
        } catch (Exception e) {
            throw new MachineSelectionException("InvalidCode");
        }
    }

    /**
     * This method is used by the admin to log in to the admin mode of the machine to access admin methods.
     * Requires the correct username and password to be entered.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @throws AdminPrivilegeException Throws an error if the username or password are not correct.
     */
    public void login(String username, String password) throws AdminPrivilegeException{
        currentState.login(username,password);
    }

    /**
     * This method is used by the admin to logout of the admin mode.
     *
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    public void logout() throws AdminPrivilegeException {
        if(getCurrentState() == getAdminModeState()) {
            adminModeState.logout();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to Logout of Admin Mode!");
        }
    }

    /**
     * This method is used to provide admins with a breakdown of the current coin stock levels in the machine
     * and a total value of all these coins.
     *
     * @return A string output of the current coin stock levels of the machine and total value of the coins.
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    public String printAdminCoinInfo() throws AdminPrivilegeException {
        if(getCurrentState() == getAdminModeState()){
            return adminModeState.printAdminCoinInfo();
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
    public String printAdminProductInfo() throws AdminPrivilegeException {
        if(getCurrentState() == getAdminModeState()){
            return adminModeState.printAdminProductInfo();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    /**
     * This method is used to provide the admins with a breakdown of the current product and coin stock levels in the machine.
     *
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    public void printAdminInfo() throws AdminPrivilegeException {
        if(getCurrentState() == getAdminModeState()){
            adminModeState.printAdminInfo();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to print Vending Machine info!");
        }
    }

    /**
     * This method is used by the admin to withdraw all coins from the machine.
     * Throws an error if the user is not in admin mode.
     *
     * @return A list of all the coins withdrawn from the machine.
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    public List<Coin> withdrawCoins() throws AdminPrivilegeException{
        if(getCurrentState() == getAdminModeState()) {
            return adminModeState.withdrawCoins();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to withdraw coins from the machine!");
        }
    }

    /**
     * This method is used by admins to deposit coins into the machine.
     * Throws an error if the user is not in admin mode.
     *
     * @param amount The amount of coins to input for each coin type.
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    public void depositCoins(Integer amount) throws AdminPrivilegeException{
        if(getCurrentState() == getAdminModeState()) {
            adminModeState.depositCoins(amount);
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to deposit coins into the machine!");
        }
    }

    /**
     * This method is used by an admin to refill the entered product to the machine's maximum allowed stock level.
     * Throws an error if the user is not in admin mode.
     *
     * @param product The product to refill the current machine's max stock level.
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    public void adminRefillProduct(Product product) throws AdminPrivilegeException {
        if(getCurrentState() == getAdminModeState()) {
            adminModeState.adminRefillProduct(product);
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to refill the Vending Machine!");
        }
    }

    /**
     * This method is used by the admin to completely refill the machine to its maximum allowed stock level for
     * all products.
     *
     * @throws AdminPrivilegeException Throws an error if the user is not in admin mode.
     */
    public void adminRefillAllProduct() throws AdminPrivilegeException{
        if(getCurrentState() == getAdminModeState()) {
            adminModeState.adminRefillAllProduct();
        } else {
            throw new AdminPrivilegeException("Must be logged in to Admin mode to refill the Vending Machine!");
        }
    }

}
