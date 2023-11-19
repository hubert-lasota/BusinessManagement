package org.hubert_lasota.BusinessManagement.ui;

public class ProductMenuUIData {
    public static final String PRODUCT_MENU_TITLE = "PRODUCT MENU";
    public static final String[] PRODUCT_MENU_CONTENT = {
            "1. Create product",
            "2. Delete product",
            "3. Show products",
            "4. Find product",
            "5. Go back to home menu"
    };

    public static final String[] PRODUCT_MENU_DELETE_CONTENT = {
            "If you want to delete product",
            "Type product's ID",
            "Type 'q' to quit"
    };

    public static final String[] PRODUCT_MENU_OPEN_EDITOR_MESSAGE = {
            "If you want to open editor on product",
            "Type product's ID",
            "If you want to quit. Type 'q'"
    };

    public static final String[] PRODUCT_MENU_EDITOR_CONTENT = {
            "1. Change product's name",
            "2. Change product's price",
            "3. Change product's description",
            "4. Delete this product",
            "5. Go back to product's menu"
    };

    public static final String PRODUCT_MENU_FIND_PRODUCTS_TITLE = "FIND PRODUCTS";

    public static final String[] PRODUCT_MENU_FIND_PRODUCTS_CONTENT = {
            "Do you want to search by:",
            "1. Name",
            "2. Price",
            "3. Description"
    };
}
