package org.hubert_lasota.BusinessManagement;


import org.hubert_lasota.BusinessManagement.customer.Customer;
import org.hubert_lasota.BusinessManagement.employee.Employee;
import org.hubert_lasota.BusinessManagement.menu.CustomerMenuManager;
import org.hubert_lasota.BusinessManagement.menu.HomeMenuManager;
import org.hubert_lasota.BusinessManagement.repository.CustomerRepository;
import org.hubert_lasota.BusinessManagement.repository.EmployeeRepository;
import org.hubert_lasota.BusinessManagement.menu.OrderMenuManager;
import org.hubert_lasota.BusinessManagement.repository.OrderRepository;
import org.hubert_lasota.BusinessManagement.menu.ProductMenuManager;
import org.hubert_lasota.BusinessManagement.repository.ProductRepository;
import org.hubert_lasota.BusinessManagement.account.Account;
import org.hubert_lasota.BusinessManagement.repository.AccountRepository;
import org.hubert_lasota.BusinessManagement.security.SecurityManager;

import java.time.LocalDate;


import static org.hubert_lasota.BusinessManagement.reader.Reader.readLine;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.createStarFrame;

public class BusinessManagementConsole {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        addDataToDatabase();
        HomeMenuManager homeMenuManager = HomeMenuManager.getInstance();
        SecurityManager securityManager = SecurityManager.getInstance();
        AccountRepository accountRepository = AccountRepository.getInstance();

        while (true) {
            Account account = loginForm();
            if (securityManager.authenticate(account.getUsername(), account.getPassword())) {
                account = accountRepository.findByUsername(account.getUsername());
                homeMenuManager.setAccount(account);
                homeMenuManager.generateMenu();
            } else {
                System.out.println(createStarFrame("Wrong password or login"));
            }
        }
    }

    private static void addDataToDatabase() {
        CustomerMenuManager customerMenuManager = CustomerMenuManager.getInstance();
        OrderMenuManager orderMenuManager = OrderMenuManager.getInstance();
        ProductMenuManager productMenuManager = ProductMenuManager.getInstance();
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
        OrderRepository orderRepository = OrderRepository.getInstance();
        ProductRepository productRepository = ProductRepository.getInstance();
        AccountRepository accountRepository = AccountRepository.getInstance();

        Employee employee = new Employee.Builder("First Name", "Last name", LocalDate.now()).build();
        employeeRepository.save(employee);

        Account user = new Account(employee.getID(),"user", "user", "EMPLOYEE");
        accountRepository.save(user);

        Customer.Address address = new Customer.Address("xx22", "32-300", "Cracow", "Poland");
        Customer customer = new Customer("Paper Company", address);
        customerRepository.save(customer);
    }

    private static Account loginForm() {
        System.out.print("LOGIN: ");
        String username = readLine();
        System.out.print("PASSWORD: ");
        String password = readLine();

        return new Account(username, password);
    }

}
