import java.util.EnumMap;
import java.util.Map;

/**
 * Class for the machine's different product inventories.
 *
 * @param <K> An enum Product Key value in the map.
 * @param <V> The integer value mapped to the enum key.
 */
public class ProductStock<K extends Enum<K>,V> implements Stock {

    private final EnumMap<Product, Integer> productStock = new EnumMap<>(Product.class);

    public ProductStock() {
        productStock.put(Product.COKE, 0);
        productStock.put(Product.SPRITE, 0);
        productStock.put(Product.WATER, 0);
        productStock.put(Product.LEMONADE, 0);
        productStock.put(Product.CRISPS, 0);
        productStock.put(Product.PEANUTS, 0);
        productStock.put(Product.CHOCOLATE, 0);
        productStock.put(Product.CANDY, 0);
    }

    /**
     * This method is Used to set the inventory's stock to the inputted level.
     *
     * @param stockLevel The integer to set the stock inventory level to.
     */
    @Override
    public void setStock(Integer stockLevel){
        for (Map.Entry<Product, Integer> entry : productStock.entrySet()) {
            productStock.put(entry.getKey(), stockLevel);
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
        productStock.put((Product) keyValue, value);
    }


    /**
     * This method is used to increase the chosen key's value by one.
     *
     * @param productStockItem The enum key.
     */
    @Override
    public void insert(Item productStockItem) {
        int stockLevel = productStock.get(productStockItem);
        productStock.put((Product) productStockItem, stockLevel+1);
    }

    /**
     * This method is used to decrease the chosen key's value by one.
     *
     * @param reducedItem The enum key.
     */
    @Override
    public void reduce(Item reducedItem) {
        int stockLevel = productStock.get(reducedItem);
        productStock.put((Product) reducedItem, stockLevel-1);
    }

    /**
     * This method is used to return the value of the specified key from the map.
     *
     * @param product The key of the enum map.
     * @return The value of the associated key in the enum map.
     */
    @Override
    public Integer get(Item product) {
        return productStock.get(product);
    }

    /**
     * This method is used to return the set view of the enum map.
     *
     * @return The set view of the enum map.
     */
    @Override
    public Iterable<? extends Map.Entry<Product, Integer>> entrySet() {
        return productStock.entrySet();
    }

    /**
     * This method is used to return the number of key-value mappings in this map.
     *
     * @return The size of the specified enum map.
     */
    @Override
    public int size() {
        return productStock.size();
    }
}