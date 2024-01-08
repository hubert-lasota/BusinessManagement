package org.hubert_lasota.BusinessManagement.console.ui.data;

public class OrderMenuUIData {
    public static final String ORDER_MENU_TITLE = "ORDER MENU";
    public static final String[] ORDER_MENU_CONTENT = {
            "1. Create an order",
            "2. Delete an order",
            "3. Show orders",
            "4. Find orders",
            "5. Go back to home menu"
    };

    public static final String ORDER_MENU_FIND_ORDERS_TITLE = "FIND ORDERS";

    public static final String[] ORDER_MENU_FIND_ORDERS_CONTENT = {
            "Do you want to search by:",
            "1. ID",
            "2. Customer",
            "3. Order value",
            "4. Created between dates",
            "5. Updated between dates",
            "6. Order comments",
            "7. Order status"
    };

    public static final String[] ORDER_MENU_OPEN_EDITOR_MESSAGE = {
            "If you want to open editor on order, ",
            "type order's ID",
            "If you want to quit, type 'q'"
    };

    public static final String[] ORDER_MENU_EDITOR_CONTENT = {
            "1. Change order status",
            "2. Change order comments",
            "3. Go back order's menu"
    };

}
