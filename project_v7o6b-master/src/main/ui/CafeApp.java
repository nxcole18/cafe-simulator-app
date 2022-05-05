package ui;

import model.*;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Cafe simulator application
public class CafeApp {
    private static final String JSON_SOURCE = "./data/userOrder.json";
    private Scanner input;
    private Product latte;
    private Product cappuccino;
    private Product mocha;
    private Product cheesecake;
    private Product macaron;
    private Menu drinksMenu;
    private Menu dessertMenu;
    private Order order;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs cafe application with start-up methods
    public CafeApp() throws FileNotFoundException {
        displayIntro();
        input = new Scanner(System.in);
        order = new Order("your order");
        jsonWriter = new JsonWriter(JSON_SOURCE);
        jsonReader = new JsonReader(JSON_SOURCE);
        enterCafe();
    }

    // This method references code from TellerApp
    // Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: processes user input
    private void enterCafe() {
        boolean appRunning = true;
        String command;

        initializeMenus();

        optionLoad();

        while (appRunning) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("3")) {
                optionSave();
            } else if (command.equals("s")) {
                saveOrder();
                sayGoodbye();
                appRunning = false;
            } else if (command.equals("q")) {
                sayGoodbye();
                appRunning = false;
            } else if (command.equals("l")) {
                loadOrder();
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewMenu(drinksMenu);
        } else if (command.equals("2")) {
            viewMenu(dessertMenu);
        } else if (command.equals("4")) {
            displayMenu();
        } else if (command.equals("latte")) {
            order.add(latte);
        } else if (command.equals("cappuccino")) {
            order.add(cappuccino);
        } else if (command.equals("mocha")) {
            order.add(mocha);
        } else if (command.equals("cheesecake")) {
            order.add(cheesecake);
        } else if (command.equals("macaron")) {
            order.add(macaron);
        } else if (command.equals("view")) {
            viewOrder();
        } else if (command.equals("process")) {
            processOrder();
        } else {
            System.out.println("Selection not valid, please try again.");
        }
    }

    // EFFECTS: displays current order to user
    private void viewOrder() {
        String orderContents = order.viewOrderContents();
        if (orderContents.equals("")) {
            System.out.println("It looks like your order is empty. Why don't you add an item?");
        } else {
            System.out.println("You are currently viewing your order:" + orderContents);
            System.out.println("To process your order, type 'process'.");
            System.out.println("To open the main menu, type 4.");

        }
    }

    // EFFECTS: displays process time of order
    private void processOrder() {
        int orderTime = order.totalOrderTime();
        System.out.println("Thank you for your order. Your order will be complete in " + orderTime + " minutes. Yay!");
    }

    // MODIFIES: this
    // EFFECTS: instantiates products and menus; adds products to their respective menus
    private void initializeMenus() {
        drinksMenu = new Menu();
        dessertMenu = new Menu();

        latte = new Product("Latte", 4, "Espresso with steamed milk.");
        cappuccino = new Product("Cappuccino", 5, "Espresso with steamed milk and foam.");
        mocha = new Product("Mocha", 3, "Espresso with chocolate and steamed milk.");
        cheesecake = new Product("Cheesecake", 0, "Cheesecake with strawberry toppings.");
        macaron = new Product("Macaron", 0, "Lavender-flavoured macaron.");

        drinksMenu.add(latte);
        drinksMenu.add(cappuccino);
        drinksMenu.add(mocha);

        dessertMenu.add(cheesecake);
        dessertMenu.add(macaron);
    }

    // EFFECTS: displays intro message and option menu
    private void displayIntro() {
        System.out.println("Welcome to Elysian Cafe. What can we do for you today?");
        displayMenu();
    }

    // EFFECTS: displays option menu to user
    private void displayMenu() {
        System.out.println("Please make a selection from the menu below:");
        System.out.println("\t1 - Order a drink.");
        System.out.println("\t2 - Order a dessert.");
        System.out.println("\t3 - Leave the cafe.");
    }

    // REQUIRES: menu
    // EFFECTS: displays a menu to user
    private void viewMenu(Menu m) {
        System.out.println("On today's drink menu, we have... " + m.initializeMenu());
        System.out.println("To order a product, type the product name. E.g. 'mocha' or 'cheesecake'.");
        System.out.println("To return to the previous menu, type 4. To view your order, type 'view'.");
    }

    // EFFECTS: gives user the option to save file
    private void optionSave() {
        System.out.println("Would you like to save your order before leaving the cafe?");
        System.out.println("\ts - Save \tq - Quit");
    }

    // EFFECTS: gives user the option to load a saved order
    private void optionLoad() {
        System.out.println("To load a previously saved order:");
        System.out.println("\tl - Load");
    }

    // EFFECTS: says goodbye to user
    private void sayGoodbye() {
        System.out.println("Thank you for stopping by Elysian Cafe.");
        System.out.println("We hope to see you again in the future. Goodbye now!");
        printEventLog();
    }

    // EFFECTS: prints event log associated with user's order
    private void printEventLog() {
        EventLog log = EventLog.getInstance();
        for (Event e : log) {
            System.out.println(e.getDate() + " - " + e.getDescription());
        }
    }

    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: saves order to file
    private void saveOrder() {
        try {
            jsonWriter.open();
            jsonWriter.write(order);
            jsonWriter.close();
            System.out.println("Saved " + order.getName() + " to " + JSON_SOURCE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_SOURCE);
        }
    }

    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: loads order from file
    private void loadOrder() {
        try {
            order = jsonReader.read();
            System.out.println("Loaded " + order.getName() + " from " + JSON_SOURCE + ". To view order, type 'view'.");
        } catch (IOException e) {
            System.out.println("There is no file to load from. Please start a new order.");
        }
    }




}
