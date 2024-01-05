# VendingMachine
A vending machine project modelled in Java, aimed at showcasing strong design principles, and clear, well-documented, and thoroughly tested code. The project aimed to demonstrate my understanding of fundamental concepts in object-oriented programming like encapsulation, abstraction, inheritance, and polymorphism. 


### State Design Pattern
The vending machine project uses the ***State Design Pattern***. 

This was implemented to allow for a notion of state.
The main idea of the State pattern is to allow the object to change its behaviour based on its situation, without changing its class implementation.

Instead of all logic for each of the states being spread across all methods. We can encapsulate the logic in dedicated classes, and apply the Single Responsibility and Open/Closed Principle. Resulting in cleaner and more maintainable code.
For example, after a user has deposited several coins, but has not yet made a purchase, the state
of the machine accounts for the balance deposited but not spent. After a
purchase does happen, the machine state is updated to keep track
of the lost item and accumulated money.

### Testing

As part of my program, I implemented [JUnit Tests](test/VendingMachineTest.java), thoroughly exercising all the different possible
interactions between the machine, users and owners. This ensured that the program ran as intended and had all edge cases covered.

### Functionality

[For in-depth descriptions of each class's functionality, refer to this document](src/README.md)

The vending machine functionality involves:
+ The machine accepts British pound sterling in the form of coins.
+ The machine contains a variety of drinks and snacks.
+ Users may deposit coins into the machine.
+ Once a sufficient amount of money has been deposited, users may withdraw a drink or snack of their choice.
+ Users may cancel their purchase and withdraw the money they have deposited.
+ Once a drink or snack has been purchased, the remaining change is returned to the user.
+ The owner of the vending machine can add new contents and deposit/withdraw money.
