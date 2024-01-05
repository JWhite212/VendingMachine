# Class Description:
My program uses a state design pattern to simulate the idea of state. The different states are represented through different state classes which implement methods from the State interface. Based on the vending machine’s current state, the methods perform different actions. This allows the user to progress through the purchasing process of the vending machine, with a maintained shared state between each step of the process. The VendingMachine class acts as the context for the state design pattern, providing a centralised class from which, depending on the state, calls upon the specific state’s implementation of the required behaviours of the machine.

### [AdminModeState.java](AdminModeState.java)

Class for the admin mode state. This is the state of the machine when a user has logged into admin mode.
From this state, the admin can access a range of admin methods to manage the contents of the machine.

### [AdminPrivilegeException.java](AdminPrivilegeException.java)

Class for admin privilege exceptions. Catches exceptions when the user does not enter the correct
login details, or when the user attempts to use a method without being in the admin state.

### [AdminState.java](AdminState.java)

Interface to provide classes with admin state methods. These methods can only be used when the
admin has logged into the machine. This interface is created to separate these methods from the State interface which it extends, so that they can be implemented only in the correct states.

### [Coin.java](Coin.java)

Enum class which contains a range of coins and their associated money value. With method implementations to
interact with the Enum class objects.

### [CoinInsertedState.java](CoinInsertedState.java)

Class for the coin inserted state. This is the state of the machine once a user has inserted money. To proceed the user
must purchase an item if enough money has been entered. The user can cancel the in-process purchase by requesting a refund.
If cancelled, the machine returns to the default not selected state.

### [CoinInventory.java](CoinInventory.java)

Interface for the coin inventories that provides a method to calculate the current total money value of the coin stock collections.

### [CoinSlot.java](CoinSlot.java)

Class for the coin slot component of the vending machine. Contains methods for the functionality of coins being inserted into the machine.

### [CoinStock.java](CoinStock.java)

Class to blueprint the machine's different coin stock inventories. Provides methods to interact with the collections. 

### [Item.java](Item.java)

Item interface to group the Product and Coin Enum classes. Useful for implementing generic method parameters on the Stock interface.

### [ItemPurchasedState.java](ItemPurchasedState.java)

Class for the item purchased state. This is the state of the machine when an item has been purchased by the user.
From here the user collects their purchased product from the bucket and any change that was returned to them by the machine.
The machine is then reset to the default, not selected state. If the machine is empty of all stock, it goes into the empty state.

### [ItemSelectedState.java](ItemSelectedState.java)

Class for the item selected state. This is the state of the machine once a user has selected an item.
To proceed, the user must insert money. The user can change their selected item, and check the price of the item they have selected.
Once the user has inserted money, the state changes to the coin inserted state.

### [Keypad.java](Keypad.java)

Class for the keypad component of the vending machine. Contains methods for the functionality of selecting a product that the user wishes to purchase by entering its code.

### [MachineInitialisationError.java](MachineInitialisationError.java)

Class for machine setup errors. Catches errors when the machine is not setup with valid values.
This ensures the machine is established with values that do not break the state of the machine going forward.

### [MachinePurchaseException.java](MachinePurchaseException.java)

Class for Machine purchase errors. Catches errors when the user attempts to perform invalid purchase actions.
Invalid actions such as purchasing an item that is out of stock, the user has not inserted enough money to purchase the selected item, or the user tries to
request a refund when they have not inserted any money.

### [MachineSelectionException.java](MachineSelectionException.java)

Class for machine selection exceptions. Catches errors when the user enters an invalid product
code or the user tries to perform actions without having an item selected.

### [MachineStockException.java](MachineStockException.java)

Class for machine stock errors. Catches errors when the user attempts to perform actions, without providing a valid inserted coin amount, or
when the machine does not contain enough change to provide the user with a refund.

### [NotSelectedState.java](NotSelectedState.java)

Class for the not selected state. this is the default state of the vending machine. If the machine is empty of all products,
the machine’s default state is the product empty state. To progress the user must select an item to purchase.

### [Product.java](Product.java)

Enum class Product containing a range of products and their associated price and code. The Class provides methods to interact with the Products.

### [ProductEmptyState.java](ProductEmptyState.java)

Class for the product empty state. This is the default state of the machine when the machine has no remaining products stocked.
To leave this state the admin must log in and refill the machine. From this state, no actions can be performed by the
base user, the admin is required to refill the machine to escape this state.

### [ProductStock.java](ProductStock.java)

Class for the machine's different product inventories. This class provides methods for interacting with the Product stock collections. 

### [Rounding.java](Rounding.java)

Interface to provide the method Round to the program. This method is used to ensure the calculations using money are accurate.

### [State.java](State.java)

The interface for the different State classes’ methods. The implementations of the interface's methods are overridden
by each state to provide different behaviours, related to the current state of the machine.

### [Stock.java](Stock.java)

An interface implemented by the different Stock classes. The methods overridden from this interface are performed on the
inheriting coin stock and product stock classes. This provides classes with CRUD methods to interact with the collections.

### [VendingMachine.java](VendingMachine.java)

This is the context class for the state design pattern.
This is the Class for the Vending Machine containing all the different State objects, and collections for product and coin to share between states.
Provides a range of method implementations to provide user interaction with the Vending Machine.

### [VendingMachineMain.java](VendingMachineMain.java)

Class containing the main method of the program to simulate user interaction. Contains a few basic user interaction sequences to test the program functionality.

### [VendingMachineTest.java](VendingMachineTest.java)

Class containing all JUnit tests for the Vending Machine. The Class VendingMachineTest contains all tests required to test the
full functionality of the vending machine and all possible interactions required by the task.
