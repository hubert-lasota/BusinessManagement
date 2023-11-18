package org.hubert_lasota.BusinessManagement.menu;

import org.hubert_lasota.BusinessManagement.account.Account;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;

import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;
import static org.hubert_lasota.BusinessManagement.ui.HomeMenuUIData.*;
import static org.hubert_lasota.BusinessManagement.BusinessManagementConsole.userInput;

public class HomeMenuManager implements Menu {
    private static HomeMenuManager homeMenuManager;
    private OrderMenuManager orderMenuManager;
    private CustomerMenuManager customerMenuManager;
    private ProductMenuManager productMenuManager;
    private AccountMenuManager accountMenuManager;
    private Account account;

    private HomeMenuManager() {
        orderMenuManager = OrderMenuManager.getInstance();
        customerMenuManager = CustomerMenuManager.getInstance();
        productMenuManager = ProductMenuManager.getInstance();
        accountMenuManager = AccountMenuManager.getInstance();
    }

    public static HomeMenuManager getInstance() {
        if(homeMenuManager == null) {
            homeMenuManager = new HomeMenuManager();
        }
        return homeMenuManager;
    }

    @Override
    public void generateMenu() {
      while (true) {
           System.out.println(createTable(HOME_MENU_TITLE, HOME_MENU_CONTENT));

           int result = userInput.nextInt();
           userInput.nextLine();
           switch (result) {
               case 1:
                   orderMenuManager.generateMenu();
                   break;
               case 2:
                    customerMenuManager.generateMenu();
                   break;
               case 3:
                   productMenuManager.generateMenu();
                   break;
               case 4:
                   accountMenuManager.setAccount(account);
                   accountMenuManager.generateMenu();
                   break;
               case 5:
                   //adminMenu();
                   break;
               case 6:
                   System.out.println(createTableFrame(HOME_MENU_LOG_OUT_MESSAGE));
                    return;
               case 7:
                   exit();
               default:
                   WrongInputException.throwAndCatchException();

           }
       }
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private void exit() {
        System.out.println(createTableFrame());
        System.exit(0);
    }

}
