package org.hubert_lasota.BusinessManagement.ui;

public class CustomerMenuUIData {
    public static final String CUSTOMER_MENU_TITLE = "CUSTOMER MENU";
    public static final String[] CUSTOMER_MENU_CONTENT = {
            "1. Add new customer",
            "2. Delete customer",
            "3. Show all customers",
            "4. Find customer",
            "5. Go back to home menu"
    };

    public static final String[] CUSTOMER_MENU_DELETE_CONTENT = {
            "If you want to delete product",
            "Type product's ID",
            "Type 'q' to quit"
    };

    public static final String CUSTOMER_MENU_FIND_CUSTOMERS_TITLE = "FIND CUSTOMERS";

    public static final String[] CUSTOMER_MENU_FIND_CUSTOMERS_CONTENT = {
            "Do you want to search by:",
            "1. ID",
            "2. Name",
            "3. Street with number",
            "4. Postal code",
            "5. City",
            "6. Country"
    };

    public static final String[] CUSTOMER_MENU_OPEN_EDITOR_MESSAGE = {
            "If you want to open editor on customer",
            "Type customer's ID",
            "If you want to quit. Type 'q'"
    };


    public static final String[] CUSTOMER_MENU_EDITOR_CONTENT = {
            "1. Change customer's name",
            "2. Change customer's ",
                "\tstreet with number",
            "3. Change customer's postal code",
            "4. Change customer's city",
            "5. Change customer's country",
            "6. Delete this customer",
            "7. Go back to customer's menu"
    };
}
