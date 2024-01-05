/**
 * Class containing the main method of the program to simulate user interaction.
 */
public class VendingMachineMain {
    public static VendingMachine vend;

    public static void main(String[] args) {
        vend = new VendingMachine(20,10,10);
        // For tests see Folder "test"
        // The Class VendingMachineTest contains all tests required to test the full
        // Functionality of the vending machine and all interactions required by the task.

        // Main class provides a majority of the interactions available within the machine to
        // test some user interaction functionality.
        vend.selectItem("0004");
        vend.currentItem();
        vend.amountDeposited();

        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);

        vend.requestRefund();

        vend.selectItem("1002");
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.purchaseItem();

        vend.login("OwnerUsername","P4ssw0rd");
        vend.adminRefillProduct(Product.COKE);
        vend.printAdminProductInfo();
        vend.adminRefillAllProduct();
        vend.printAdminProductInfo();

        vend.depositCoins(50);
        vend.printAdminCoinInfo();

        vend.withdrawCoins();
        vend.printAdminCoinInfo();

        vend.depositCoins(50);
        vend.printAdminCoinInfo();

        vend.logout();

        vend.selectItem("0003");
        vend.currentItem();
        vend.insertCoin(Coin.ONE_POUND, 3);
        vend.insertCoin(Coin.FIFTY_PENCE, 2);
        vend.insertCoin(Coin.TWO_PENCE, 2);
        vend.insertCoin(Coin.ONE_PENCE, 4);
        vend.amountDeposited();
        vend.getStock("0003");
        vend.purchaseItem();
        vend.getStock("0003");
        System.out.println("Collect Change = " + vend.getCollectedCoins());
        System.out.println("Collect Item = " + vend.getCollectedProducts());

    }

}
