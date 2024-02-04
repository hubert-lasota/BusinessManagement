package org.hubert_lasota.BusinessManagement.console;


import org.hubert_lasota.BusinessManagement.console.menu.*;
import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.user.User;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.repository.*;
import org.hubert_lasota.BusinessManagement.security.SecurityManager;
import org.hubert_lasota.BusinessManagement.service.*;

import java.time.LocalDate;

import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.readLine;
import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createStarBorder;

public class BusinessManagementConsole {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        addDataToDatabase();

        UserRepository userRepository = InMemoryUserRepository.getInstance();
        EmployeeRepository employeeRepository = InMemoryEmployeeRepository.getInstance();
        OrderRepository orderRepository = InMemoryOrderRepository.getInstance();
        ProductRepository productRepository = InMemoryProductRepository.getInstance();
        CustomerRepository customerRepository = InMemoryCustomerRepository.getInstance();
        AddressRepository addressRepository = InMemoryAddressRepository.getInstance();

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        ProductService productService = new ProductServiceImpl(productRepository);
        OrderService orderService = new OrderServiceImpl(orderRepository, productService);
        UserService userService = new UserServiceImpl(userRepository);
        EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);
        AddressService addressService = new AddressServiceImpl(addressRepository);

        OrderMenuManager orderMenuManager = new OrderMenuManager(orderService, customerService, productService);
        ProductMenuManager productMenuManager = new ProductMenuManager(productService);
        CustomerMenuManager customerMenuManager = new CustomerMenuManager(customerService, addressService);

        SecurityManager securityManager = new SecurityManager(userService);
        while (true) {
            User user = loginForm();
            if (securityManager.authenticate(user.getUsername(), user.getPassword())) {
                user = userRepository.findById(user.getUsername()).orElseThrow(NoSuchIdException::new);
                UserMenuManager userMenuManager = new UserMenuManager(userService, employeeService , addressService, user);
                HomeMenuManager homeMenuManager = new HomeMenuManager(
                        orderMenuManager, customerMenuManager, productMenuManager, userMenuManager, user, securityManager);

                homeMenuManager.generateMenu();
            } else {
                System.out.println(createStarBorder("Wrong password or login"));
            }
        }
    }

    private static void addDataToDatabase() {
        InMemoryEmployeeRepository inMemoryEmployeeRepository = InMemoryEmployeeRepository.getInstance();
        InMemoryUserRepository inMemoryUserRepository = InMemoryUserRepository.getInstance();

        Employee employee = new Employee("First Name", "Last name", LocalDate.now());
        inMemoryEmployeeRepository.save(employee);

        User user = new User(employee.getId(),"user", "user", "EMPLOYEE");
        inMemoryUserRepository.save(user);

        //Customer customer = new Customer("Paper Company", "xx22", "32-300", "Cracow", "Poland");
    }

    private static User loginForm() {
        System.out.print("LOGIN: ");
        String username = readLine();
        System.out.print("PASSWORD: ");
        String password = readLine();

        return new User(username, password);
    }

}
