package org.hubert_lasota.BusinessManagement;

import org.hubert_lasota.BusinessManagement.customer.CustomerMenuManager;
import org.hubert_lasota.BusinessManagement.exception.WrongInputException;
import org.hubert_lasota.BusinessManagement.order.OrderMenuManager;
import org.hubert_lasota.BusinessManagement.product.ProductMenuManager;

import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.*;
import static org.hubert_lasota.BusinessManagement.ui.HomeMenuUIData.*;
import static org.hubert_lasota.BusinessManagement.BusinessManagementConsole.userInput;

public class HomeMenuManager {
    private static HomeMenuManager homeMenuManager;
    private OrderMenuManager orderMenuManager;
    private CustomerMenuManager customerMenuManager;
    private ProductMenuManager productMenuManager;

    private HomeMenuManager() {
        orderMenuManager = OrderMenuManager.getInstance();
        customerMenuManager = CustomerMenuManager.getInstance();
        productMenuManager = ProductMenuManager.getInstance();

    }

    public static HomeMenuManager getInstance() {
        if(homeMenuManager == null) {
            homeMenuManager = new HomeMenuManager();
        }
        return homeMenuManager;
    }

    public void homeMenu() {
      while (true) {
           System.out.println(createTable(HOME_MENU_TITLE, HOME_MENU_CONTENT));

           int result = userInput.nextInt();
           userInput.nextLine();
           switch (result) {
               case 1:
                   orderMenuManager.orderMenu();
                   break;
               case 2:
                    customerMenuManager.customerMenu();
                   break;
               case 3:
                   productMenuManager.productMenu();
                   break;
               case 4:
                   //myAccountMenu();
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

    private void exit() {
        System.out.println(createTableFrame());
        System.exit(0);
    }

}
