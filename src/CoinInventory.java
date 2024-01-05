/**
 * Interface for the coin inventories that provides a method to calculate
 * their current total money value.
 *
 * @param <K> Key of the map.
 * @param <V> Key's value in the mapping
 */
public interface CoinInventory<K extends Enum<K>,V> extends Stock {

    //Method used to calculate value of all coins within an inventory.
    double getMachineTotalMoneyValue();

}
