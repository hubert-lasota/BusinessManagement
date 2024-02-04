package org.hubert_lasota.BusinessManagement.console.menu;

import org.hubert_lasota.BusinessManagement.entity.user.User;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.security.Security;

import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.readInt;
import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createBorder;
import static org.hubert_lasota.BusinessManagement.console.ui.TableGenerator.createTable;
import static org.hubert_lasota.BusinessManagement.console.ui.data.HomeMenuUIData.*;

public class HomeMenuManager implements Menu {
    private final OrderMenuManager orderMenuManager;
    private final CustomerMenuManager customerMenuManager;
    private final ProductMenuManager productMenuManager;
    private final UserMenuManager userMenuManager;
    private final User user;
    private final Security security;


    public HomeMenuManager(OrderMenuManager orderMenuManager,
                           CustomerMenuManager customerMenuManager,
                           ProductMenuManager productMenuManager,
                           UserMenuManager userMenuManager,
                           User user,
                           Security security) {
      this.orderMenuManager = orderMenuManager;
      this.customerMenuManager = customerMenuManager;
      this.productMenuManager = productMenuManager;
      this.userMenuManager = userMenuManager;
      this.user = user;
      this.security = security;
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
                   userMenuManager.generateMenu();
                   break;
               case 5:
                   System.out.println(createBorder(HOME_MENU_LOG_OUT_MESSAGE));
                   return;
               case 6:
                   exit();
               default:
                   WrongInputException.throwAndCatchException();
           }
       }
    }

    private void exit() {
        System.out.println(createBorder(HOME_MENU_EXIT_MESSAGE));
        System.exit(0);
    }

}
