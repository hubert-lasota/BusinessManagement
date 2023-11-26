package org.hubert_lasota.BusinessManagement.menu;

import org.hubert_lasota.BusinessManagement.account.Account;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;

import static org.hubert_lasota.BusinessManagement.input.UserInputReader.readInt;
import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;
import static org.hubert_lasota.BusinessManagement.ui.HomeMenuUIData.*;

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

           int result = readInt();
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
                   // warehouse
                   break;
               case 6:
                   //adminMenu();
                   break;
               case 7:
                   System.out.println(createTableFrame(HOME_MENU_LOG_OUT_MESSAGE));
                   return;
               case 8:
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
        System.out.println(createTableFrame(HOME_MENU_EXIT_MESSAGE));
        System.exit(0);
    }

}
