import java.util.EnumMap;
import java.util.Map;

/**
 * Class for the machine's different coin stock inventories.
 *
 * @param <K> An enum Coin Key value in the map.
 * @param <V> The integer value mapped to the enum key.
 */
public class CoinStock<K extends Enum<K>,V> implements CoinInventory {

    private final EnumMap<Coin, Integer> coinStock = new EnumMap<>(Coin.class);

    public CoinStock() {
        coinStock.put(Coin.TWO_POUND, 0);
        coinStock.put(Coin.ONE_POUND, 0);
        coinStock.put(Coin.FIFTY_PENCE, 0);
        coinStock.put(Coin.TWENTY_PENCE, 0);
        coinStock.put(Coin.TEN_PENCE, 0);
        coinStock.put(Coin.FIVE_PENCE, 0);
        coinStock.put(Coin.TWO_PENCE, 0);
        coinStock.put(Coin.ONE_PENCE, 0);
    }

    /**
     * This method is Used to set the inventory's stock to the inputted level.
     *
     * @param stockLevel The integer to set the stock inventory level to.
     */
    @Override
    public void setStock(Integer stockLevel) {
        for (Map.Entry<Coin, Integer> entry : coinStock.entrySet()) {
            coinStock.put(entry.getKey(), stockLevel);
        }
    }

    /**
     * This method is used to change the chosen key's value within the map.
     *
     * @param keyValue The enum key.
     * @param value The value being assigned within the map to the provided key.
     */
    @Override
    public void put(Item keyValue, Integer value) {
        coinStock.put((Coin) keyValue, value);
    }

    /**
     * This method is used to calculate the total value of all the coins contained
     * within the inventory.
     *
     * @return The total coin stock's money value.
     */
    @Override
    public double getMachineTotalMoneyValue() {
        double totalMachineMoneyValue = 0;
        for (Map.Entry<Coin, Integer> entry : coinStock.entrySet()) {
            totalMachineMoneyValue = totalMachineMoneyValue + (((entry.getKey()).getMoneyValue()) * (entry.getValue()));
        }
        return Rounding.round(totalMachineMoneyValue);
    }

    /**
     * This method is used to increase the chosen key's value by one.
     *
     * @param insertedItem The enum key.
     */
    @Override
    public void insert(Item insertedItem){
        int stockLevel = coinStock.get(insertedItem);
        coinStock.put((Coin) insertedItem, stockLevel+1);
    }

    /**
     * This method is used to decrease the chosen key's value by one.
     *
     * @param reducedItem The enum key.
     */
    @Override
    public void reduce(Item reducedItem){
        int stockLevel = coinStock.get(reducedItem);
        coinStock.put((Coin) reducedItem, stockLevel-1);
    }

    /**
     * This method is used to return the value of the specified key from the map.
     *
     * @param coin The key of the enum map.
     * @return The value of the associated key in the enum map.
     */
    @Override
    public Integer get(Item coin) {
        return coinStock.get(coin);
    }

    /**
     * This method is used to return the set view of the enum map.
     *
     * @return The set view of the enum map.
     */
    @Override
    public Iterable<? extends Map.Entry<Coin, Integer>> entrySet() {
        return coinStock.entrySet();
    }

    /**
     * This method is used to return the number of key-value mappings in this map.
     *
     * @return The size of the specified enum map.
     */
    @Override
    public int size() {
        return coinStock.size();
    }
}