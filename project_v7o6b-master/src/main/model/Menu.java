package model;

import java.util.LinkedList;

// Represents a menu of products
public class Menu {
    private LinkedList<Product> menu;

    // EFFECTS: constructs an empty menu with no products
    public Menu() {
        menu = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a product (p) to the menu
    public void add(Product p) {
        this.menu.add(p);
    }

    public int getMenuSize() {
        return this.menu.size();
    }

    // This method is inspired by a StackOverflow post (response 2)
    // Link: https://stackoverflow.com/questions/1532461/stringbuilder-vs-string-concatenation-in-tostring-in-java
    // EFFECTS: returns a formatted, readable menu
    public String initializeMenu() {
        StringBuilder contents = new StringBuilder();

        for (Product p : this.menu) {
            contents.append("\n\t- ");
            contents.append(p.getProductName());
            contents.append(", ");
            contents.append(p.getProductDescription());
        }

        return contents.toString();
    }



}
