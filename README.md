# **Bank Account Management System**
This is a simple Bank Account Management System that allows users to open 
savings and current accounts, perform withdrawals and deposits, and 
manage account balances.

## **Design Choices**
The Bank Account Management System is designed to follow object-oriented
principles and layered architecture. 
Here are some of the design choices made:


## **Layered Architecture**
The application follows a layered architecture consisting of the followi
ng layers:

### Presentation Layer:
This layer is responsible for handling user interactions. In our implementation, it includes the Main class, where accounts are opened and transactions are performed.

### Service Layer:
This layer contains the business logic of the application. It includes the AccountService interface and its implementation AccountServiceImpl, which provide methods for opening accounts, performing transactions, and managing account balances.

### Data Access Layer:
This layer is responsible for managing the in-memory database and provides access to account data. It includes the SystemDB singleton class, which acts as the database and stores the account records.

## Double-Checked Locking Pattern

The SystemDB class implements the double-checked locking pattern to ensure that only one instance of the database is created. This pattern provides thread-safe initialization of the singleton instance and improves performance by minimizing the need for synchronization.

### Object-Oriented Practices

The project follows object-oriented practices to promote encapsulation, inheritance, and polymorphism. Key classes like Account, SavingsAccount, and CurrentAccount are designed as Java objects with appropriate attributes and behavior. The use of inheritance allows for code reuse and flexibility, while polymorphism enables treating different types of accounts uniformly through the Account interface.

### Testing

Unit tests are included in the AccountServiceTest class to ensure the correct behavior of the Bank Account Management System. The tests cover various scenarios, including opening accounts, performing withdrawals and deposits, handling exceptions, and checking account balances.

To run the tests, execute the AccountServiceTest class in your preferred testing framework or IDE.

### Limitations

* This implementation is built entirely in native Java without utilizing any container or dependency injection framework, such as Spring or EJB.
* The application does not leverage container-managed resources or features provided by such frameworks.
* Dependency injection and inversion of control (IoC) principles are not employed in this implementation.
* The focus of this project is to showcase core Java concepts, object-oriented programming practices and understanding of layered architecture.
