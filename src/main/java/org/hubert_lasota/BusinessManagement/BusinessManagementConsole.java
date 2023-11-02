package org.hubert_lasota.BusinessManagement;


import org.hubert_lasota.BusinessManagement.customer.CustomerMenuManager;
import org.hubert_lasota.BusinessManagement.customer.CustomerRepository;
import org.hubert_lasota.BusinessManagement.employee.EmployeeRepository;
import org.hubert_lasota.BusinessManagement.order.OrderMenuManager;
import org.hubert_lasota.BusinessManagement.order.OrderRepository;
import org.hubert_lasota.BusinessManagement.product.ProductMenuManager;
import org.hubert_lasota.BusinessManagement.product.ProductRepository;
import org.hubert_lasota.BusinessManagement.security.Account;
import org.hubert_lasota.BusinessManagement.security.AccountRepository;
import org.hubert_lasota.BusinessManagement.security.SecurityManager;

import java.util.Scanner;

import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.createStarFrame;

public class BusinessManagementConsole {
    public static final Scanner userInput = new Scanner(System.in);

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
            if (securityManager.isAuthenticated(account.getUsername(), account.getPassword())) {
                account = accountRepository.findByUsername(account.getUsername());
                homeMenuManager.homeMenu();

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

        Account user = new Account("user", "user", "EMPLOYEE");
        accountRepository.save(user);

    }

    private static Account loginForm() {
        System.out.print("LOGIN: ");
        String username = userInput.nextLine();
        System.out.print("PASSWORD: ");
        String password = userInput.nextLine();

        return new Account(username, password);
    }

}
