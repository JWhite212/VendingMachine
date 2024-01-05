/**
 * Class for the coin slot component of the vending machine.
 * Contains methods for the functionality of coins being inserted into the machine.
 */
public class CoinSlot {
    private final VendingMachine vendingMachine;
    public CoinSlot(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * This method is the functionality of the coins being inserted into the machine.
     * Takes an amount of coins and a coin object, and inserts that amount of coins into the machine.
     *
     * @param coin The coin inserted into the machine.
     * @param coinAmount The amount of the chosen coin inserted into the machine.
     */
    public void coinInserted(Coin coin, Integer coinAmount){
        int coinStockLevel = vendingMachine.getCoinStock().get(coin);
        coinStockLevel = coinStockLevel+coinAmount;
        int insertedCoinLevel = vendingMachine.getInsertedCoinStock().get(coin);
        insertedCoinLevel = insertedCoinLevel+coinAmount;
        vendingMachine.getCoinStock().put(coin, coinStockLevel);
        vendingMachine.getInsertedCoinStock().put(coin, insertedCoinLevel);
        System.out.println("You inserted " + coinAmount + " " + coin.toString() + " coins");
    }
}
