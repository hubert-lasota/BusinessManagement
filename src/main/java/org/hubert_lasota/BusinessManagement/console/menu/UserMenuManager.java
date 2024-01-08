package org.hubert_lasota.BusinessManagement.console.menu;

import org.hubert_lasota.BusinessManagement.entity.address.Address;
import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.user.User;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.service.AddressService;
import org.hubert_lasota.BusinessManagement.service.EmployeeService;
import org.hubert_lasota.BusinessManagement.service.UserService;

import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.readInt;
import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.readLine;
import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createStarBorder;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTable;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTableHeader;
import static org.hubert_lasota.BusinessManagement.console.ui.data.UserMenuUIData.USER_MENU_CONTENT;
import static org.hubert_lasota.BusinessManagement.console.ui.data.UserMenuUIData.USER_MENU_TITLE;

public class UserMenuManager implements Menu {
    private final UserService userService;
    private final EmployeeService employeeService;
    private final AddressService addressService;
    private final User user;

    public UserMenuManager(UserService userService,
                           EmployeeService employeeService,
                           AddressService addressService,
                           User user) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.addressService = addressService;
        this.user = user;
    }


    @Override
    public void generateMenu() {
        while (true) {
            System.out.println(createTable(USER_MENU_TITLE, USER_MENU_CONTENT));
            int inputResult = readInt();
            switch (inputResult) {
                case 1:
                    showInformationAboutMyAccount();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    return;
                default:
                    WrongInputException.throwAndCatchException();
            }
        }
    }

    public void showInformationAboutMyAccount() {
        Employee employeeInfo;
        Address employeeAddress;
        try {
            employeeInfo = employeeService.findEmployeeById(user.getEmployeeId());
            employeeAddress = addressService.findAddressById(employeeInfo.getId());
        } catch (NoSuchIdException e) {
            System.out.println(createStarBorder(e.getMessage()));
            return;
        }
        System.out.println(createTableHeader("My Account"));
        System.out.println(employeeInfo);
        System.out.println(employeeAddress);
        System.out.println("\nlogin: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
    }

    public void changePassword() {
        System.out.println("Current password: " + user.getPassword());
        System.out.print("Type new password: ");
        String newPassword = readLine();
        userService.updatePassword(user.getUsername(), newPassword);
    }

}
