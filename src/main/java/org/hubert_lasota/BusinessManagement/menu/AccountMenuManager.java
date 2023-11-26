package org.hubert_lasota.BusinessManagement.menu;

import org.hubert_lasota.BusinessManagement.account.Account;
import org.hubert_lasota.BusinessManagement.employee.Employee;
import org.hubert_lasota.BusinessManagement.exception.NoAccountsInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.repository.AccountRepository;
import org.hubert_lasota.BusinessManagement.repository.EmployeeRepository;

import java.time.LocalDate;

import static org.hubert_lasota.BusinessManagement.input.UserInputReader.readInt;
import static org.hubert_lasota.BusinessManagement.input.UserInputReader.readLine;
import static org.hubert_lasota.BusinessManagement.ui.AccountMenuUIData.*;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;

public class AccountMenuManager implements Menu {
    private static AccountMenuManager accountMenuManager;
    private AccountRepository accountRepository;
    private EmployeeRepository employeeRepository;
    private Account account;

    private AccountMenuManager() {
        accountRepository = AccountRepository.getInstance();
        employeeRepository = EmployeeRepository.getInstance();
    }

    public static AccountMenuManager getInstance() {
        if(accountMenuManager == null) {
            accountMenuManager = new AccountMenuManager();
        }
        return accountMenuManager;
    }

    @Override
    public void generateMenu() {
        while (true) {
            System.out.println(createTable(ACCOUNT_MENU_TITLE, ACCOUNT_MENU_CONTENT));
            int inputResult = readInt();
            switch (inputResult) {
                case 1:
                    showInformationAboutMyAccount();
                    break;
                case 2:
                    changeLogin();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }
        }
    }

    public void showInformationAboutMyAccount() {
        Employee employeeInfo = employeeRepository.findById(account.getEmployeeId()).orElseThrow(NoSuchIdException::new);
        System.out.println(createTitleOfTable("My Account"));
        System.out.println(employeeInfo);
        System.out.println("\nlogin: " + account.getUsername());
        System.out.println("password: " + account.getPassword());

        openEditor();
    }

    private void openEditor() {
        System.out.println(createStarFrame("Do you want to edit information y/n?"));
        String inputResult = readLine();
        if(inputResult.equalsIgnoreCase("q")) {
            Employee employee = employeeRepository.findById(account.getEmployeeId()).orElseThrow(NoSuchIdException::new);
            String tableTitle = "Edit: " + employee.getFirstName() + " " + employee.getLastName();
            System.out.println(createTable(tableTitle, ACCOUNT_MENU_EDIT_CONTENT));
            int result = readInt();
            editMyAccount(result);
        }
    }

    private void editMyAccount(int result) {
        Employee employeeToUpdate = employeeRepository.findById(account.getEmployeeId()).orElseThrow(NoSuchIdException::new);
        switch (result) {
            case 1:
                updateFirstName(employeeToUpdate);
                break;
            case 2:
                updateLastName(employeeToUpdate);
                break;
            case 3:
                updateDateOfBirth(employeeToUpdate);
                break;
            case 4:
                updateStreetWithNumber(employeeToUpdate);
                break;
            case 5:
                updatePostalCode(employeeToUpdate);
                break;
            case 6:
                updateCity(employeeToUpdate);
                break;
            case 7:
                updateCountry(employeeToUpdate);
                break;
            case 8:
                return;
            default:
                WrongInputException.throwAndCatchException();
        }
    }

    private static void updateFirstName(Employee employeeToUpdate) {
        System.out.print("Type new first name: ");
        String firstName = readLine();
        employeeToUpdate.setFirstName(firstName);
    }

    private static void updateLastName(Employee employeeToUpdate) {
        System.out.print("Type new last name: ");
        String lastName = readLine();
        employeeToUpdate.setLastName(lastName);
    }

    private static void updateDateOfBirth(Employee employeeToUpdate) {
        System.out.println("Type new date of birth");
        System.out.print("Type day of birth: ");
        int day = readInt();
        System.out.print("Type month of birth: ");
        int month = readInt();
        System.out.print("Type year of birth: ");
        int year = readInt();
        LocalDate dateOfBirth = LocalDate.of(year, month, day);
        employeeToUpdate.setDateOfBirth(dateOfBirth);
    }

    private static void updateStreetWithNumber(Employee employeeToUpdate) {
        System.out.print("Type new street with number: ");
        String streetWithNumber = readLine();
        employeeToUpdate.getAddress().setStreetWithNumber(streetWithNumber);
    }

    private static void updatePostalCode(Employee employeeToUpdate) {
        System.out.print("Type new postal code: ");
        String postalCode = readLine();
        employeeToUpdate.getAddress().setPostalCode(postalCode);
    }

    private static void updateCity(Employee employeeToUpdate) {
        System.out.print("Type new city: ");
        String city = readLine();
        employeeToUpdate.getAddress().setCity(city);
    }

    private static void updateCountry(Employee employeeToUpdate) {
        System.out.print("Type new country: ");
        String country = readLine();
        employeeToUpdate.getAddress().setCountry(country);
    }

    public void changeLogin() {
        System.out.println(createTitleOfTable("Change login"));
        System.out.println("Current login: " + account.getUsername());
        System.out.print("Type new login: ");
        String login = readLine();
        if(!isLoginInDatabase(login)) {
            account.setUsername(login);
            System.out.println(createStarFrame("You successfully changed login"));
        } else {
            System.out.println(createStarFrame("This login exists in database!"));
        }
    }

    private boolean isLoginInDatabase(String login) {
        return accountRepository.findAll().orElseThrow(NoAccountsInDatabaseException::new)
                .stream()
                .noneMatch(a -> a.getUsername().equals(login));
    }

    public void changePassword() {
        System.out.println(createTitleOfTable("Change password"));
        System.out.println("Current password: " + account.getPassword());
        System.out.print("Type new password: ");
        String password = readLine();
        account.setPassword(password);
        System.out.println(createStarFrame("You successfully changed password!"));
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
