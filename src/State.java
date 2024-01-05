import java.util.List;

/**
 * The interface for the state methods. Implementation of methods changes based on
 * machine's current state.
 */
public interface State {

    //User inserts an amount of a chosen coin
    //Throws exception if coinAmount is negative
    void insertCoin(Coin coin, Integer coinAmount) throws MachineStockException;

    // Returns current balance of inserted coins
    // Before an item has been selected, the default method is called.
    // This returns zero as no money can be inserted before an item has been selected.
    default double amountDeposited() {
        System.out.println("You have not inserted any money!");
        return 0;
    }

    // User requests a refund, coins to be placed in return bucket
    // Before any money has been inserted into the machine, the default method is called.
    default void requestRefund(){
        System.out.println("You have not entered any money to be refunded!");
    }

    // User selects item
    void selectItem(String code) throws MachineSelectionException;

    // Returns currently selected item
    Product currentItem();

    // User purchases item
    // throws exception on error, puts change in return bucket
    void purchaseItem() throws MachinePurchaseException;

    // User collects purchased products from the bucket
    List<Product> getBucketProducts();

    // User collects change from the bucket
    List<Coin> getBucketCoins();

    //User logs in to Admin mode if Username and Password are correct,
    //Throws exception if incorrect
    void login(String username, String password) throws AdminPrivilegeException;


}
