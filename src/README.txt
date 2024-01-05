Username: JW895

Program Description:
My program uses a state design pattern to simulate the idea of state. The different states are represented through different state classes which implement methods from the State interface. Based on the vending machine’s current state, the methods perform different actions. This allows the user to progress through the purchasing process of the vending machine, with a maintained shared state between each step of the process. The VendingMachine class acts as the context for the state design pattern, providing a centralised class from which, depending on the state, calls upon the specific state’s implementation of the required behaviours of the machine.

AdminModeState.java
Class for the admin mode state. This is the state of the machine when a user has logged into admin mode.
From this state the admin can access a range of admin methods to manage the contents of the machine.

AdminPrivilegeException.java
Class for admin privilege exceptions. Catches exceptions when the user does not enter the correct
login details, or when the user attempts to use a method without being in admin state.

AdminState.java
Interface to provide classes with admin state methods. These methods can only be used when the
admin has logged into the machine. This interface is created to separate these methods from the State interface which it extends, so that they can be implemented only on correct states.

Coin.java
Enum class containing a range of coins and their associated money value. With method implementations to
interact with the Enum class.

CoinInsertedState.java
Class for the coin inserted state. This is the state of the machine once a user has inserted money. To proceed the user
must purchase an item if enough money has been entered. The user can cancel the in-process purchase by requesting a refund.
If cancelled, the machine returns to the default not selected state.

CoinInventory.java
Interface for the coin inventories that provides a method to calculate their current total money value of the coin stock collections.

CoinSlot.java
Class for the coin slot component of the vending machine. Contains methods for the functionality of coins being inserted into the machine.

CoinStock.java
Class to blueprint the machine's different coin stock inventories. Provides methods to interact with the collections. 

Item.java
Item interface to group the Product and Coin Enum classes. Useful for implementing generic method parameters on the Stock interface.

ItemPurchasedState.java
Class for the item purchased state. This is the state of the machine when an item has been purchased by the user.
From here the user collects their purchased product from the bucket, and any change returned to them by the machine.
The machine is then reset to the default not selected state, or empty state, if the machine is empty of all stock.

ItemSelectedState.java
Class for the item selected state. This is the state of the machine once a user has selected an item.
To proceed, the user must insert money. The user can change their selected item, check the price of the item.
Once the user has inserted money, the state changes to the coin inserted state.

Keypad.java
Class for the keypad component of the vending machine. Contains methods for the functionality of selecting a product the user wishes to purchase by entering its code.

MachineInitialisationError.java
Class for machine setup errors. Catches errors when the machine is not setup with valid values.
This ensures the machine is setup with values that do not break the state of the machine going forward.

MachinePurchaseException.java
Class for Machine purchase errors. Catches errors when the user attempts to perform invalid purchase actions.
Invalid actions such as purchasing an item that is out of stock, the user has not inserted enough money to purchase the selected item, or the user tries to
request a refund when they have not inserted any money.

MachineSelectionException.java
Class for machine selection exceptions. Catches errors when the user enters an invalid product
code, or the user tries to perform actions without having an item selected.

MachineStockException.java
Class for machine stock errors. Catches errors when the user attempts to perform actions, without providing a valid inserted coin amount, or
when the machine does not contain enough change to provide the user with a refund.

NotSelectedState.java
Class for the not selected state. this is the default state of the vending machine. If the machine is empty of all products,
the machine’s default state is the product empty state. To progress the user must select an item to purchase.

Product.java
Enum class Product containing a range of products and their associated price and code. The Class provides methods to interact with the Products.

ProductEmptyState.java
Class for the product empty state. This is the default state of the machine when the machine has no remaining products stocked.
To leave this state the admin must log in and refill the machine. From this state, no actions can be performed by the
base user, the admin is required to refill the machine to escape this state.

ProductStock.java
Class for the machine's different product inventories. This class provides methods for interacting with the Product stock collections. 

Rounding.java
Interface to provide the method Round to the program. This method is used to ensure the calculations using money are accurate.

State.java
The interface for the different State classes’ methods. The implementations of the interfaces methods are overridden
by each state to provide different behaviours, appropriate to the current state of the machine.

Stock.java
An interface implemented by the different Stock classes. The methods overridden from this interface are performed on the
inheriting coin stock and product stock classes. This provides classes with CRUD methods to interact with the collections.

VendingMachine.java
This is the context class for the state design pattern.
Class for the Vending Machine containing all the different State objects, and collections for product and coin to share between states.
Provides a range of method implementations to provide user interaction with the Vending Machine.

VendingMachineMain.java
Class containing the main method of the program to simulate user interaction. Contains a few basic user interaction sequences to test the program.

VendingMachineTest.java
Class containing all JUnit tests for the Vending Machine. The Class VendingMachineTest contains all tests required to test the
full functionality of the vending machine and all interactions required by the task.
