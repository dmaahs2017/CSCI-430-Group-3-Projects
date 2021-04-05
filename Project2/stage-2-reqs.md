[[toc]]

## 1.	In the ClientMenuState, the Context has stored the ClientID for the current client; all operations are for that ClientID. The state will have operations for the following:
- [x]	Show client details. The state invokes a method on Facade to get the Client object and then gets the client details. Note that the ClientID is available in the Context.
- [x]	Show list of products with sale prices.  The state invokes a method on Facade to get an iterator, and then extracts the needed information.
- [x]	Show client transactions. The state invokes a method on Facade to get the Client object and then gets the transaction details for the client. Note that the ClientID is available in the Context.
- [x]	Modify client's shopping cart (New State).  A menu is displayed; actor can view cart cart contents (system displays contents),  add product (system prompts for id and qty), remove product(system prompts for id) or change quantity(system prompts for id and qty).
    - [x] Add new shopping cart state
- [x]	Display client's waitlist.
- [x]	Logout. System transitions to the previous  state, which has to be remembered in the context. (If previous state was the OpeningState, it goes there; otherwise it goes to ClerkMenuState.)

## 2.	In the ClerkMenuState, we have the following operations:
- [x]	Add a client. Gets details of new client; calls method on Facade.
- [x]	Show list of products with quantities and sale prices.  The state invokes a method on Facade to get an iterator, and then extracts the needed information.
- [x]	Query system about clients (New State). A menu is displayed; actor can choose to display  all clients,  see list of clients with outstanding balance or see list of clients with no transactions.
    - [x] Add new state
- [x]	Become a client. The actor will be asked to input a ClientID; if valid, this ID will be stored in Context, and the system transitions to the  ClientMenuState.
- [x]	Display the waitlist for a product. The state asks the actor for productid; calls method on Facade to get an iterator. 
- [x]	Receive a shipment. The state asks the actor for productid and quantity; calls method on Facade to get an iterator. Displays each waitlisted order and performs operation requested by actor (skip or fill).
- [x]	Logout. System transitions to the previous  state, which has to be remembered in the context. (If previous state was the OpeningState, it goes there; otherwise it goes to ManagerMenuState.)

## 3.	In the ManagerMenuState, we have the following operations:
- [x]	Add a product
- [x]	Add a supplier
- [x]	Show list of suppliers
- [x]	Show list of suppliers for a product, with purchase prices
    - [x] Add Purchase Prices
- [x]	Show list of products for a supplier, with purchase prices
    - [x] Add Purchase Prices
- [x]	Add a supplier for a product. Actor provides productID, supplierID and purchase price 
    - [x] Move this to manager state from clerk state
- [ ]	Modify purchase price for a particular product from a particular supplier. Actor provides productID, supplierID and purchase price 
- [x]	Become a salesclerk
- [x]	Logout.

## Issues
- [ ] when recieving shipment for a product supplier ID is not validated.
