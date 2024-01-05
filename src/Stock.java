import java.util.Map;

/**
 * An interface for methods performed on the inheriting coin stock and
 * product stock classes.
 *
 * @param <K> Key in the map.
 * @param <V> The value mapped to the key in the map.
 */
public interface Stock<K extends Enum<K>,V> {

    //Sets the all the key's values to provided integer.
    void setStock(Integer stockLevel);

    //Sets the specified key's value to provided integer
    void put(Item keyValue, Integer value);

    //Increases the specified key's value by one
    void insert(Item insertedItem);

    //Decreases the specified key's value by one
    void reduce(Item reducedItem);

    //Returns the value of the specified key from the map.
    Integer get(Item item);

    //Returns the set view of the enum map.
    Iterable<? extends Map.Entry<Enum, Integer>> entrySet();

    //Returns the number of key-value mappings in this map.
    int size();
}