import java.util.List;

/**
 * Interface to provide classes with admin state methods.
 * These methods can only be used when the admin has logged into the machine.
 */
public interface AdminState extends State{
    void logout() throws AdminPrivilegeException;

    // Take the money out of the machine
    List<Coin> withdrawCoins() throws AdminPrivilegeException;

    // Add money to the machine
    void depositCoins(Integer amount) throws AdminPrivilegeException;

    // Stocks an item
    // throws an exception if already full
    void adminRefillProduct(Product product) throws AdminPrivilegeException;

    // Stocks all items
    // throws an exception if already full
    void adminRefillAllProduct() throws AdminPrivilegeException;

    String printAdminCoinInfo() throws AdminPrivilegeException;

    String printAdminProductInfo() throws AdminPrivilegeException;

    void printAdminInfo() throws AdminPrivilegeException;
}
