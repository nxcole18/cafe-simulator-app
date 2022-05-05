package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Order;
import model.Product;
import persistence.JsonReader;
import persistence.JsonWriter;

// Ordering window GUI for CafeAppGUI
public class OrderGUI extends JFrame {
    private static final Color PINK = new Color(247,217,209);
    private static final String JSON_SOURCE = "./data/userOrder.json";
    private static final int WIDTH = 700;
    private static final int HEIGHT = 487;
    private static final String LATTE_IMAGE_FILE = "latte.png";
    private static final String MOCHA_IMAGE_FILE = "mocha.png";
    private static final String CHEESECAKE_IMAGE_FILE = "cheesecake.png";
    private static final String MACARON_IMAGE_FILE = "macaron.png";

    private JPanel menuPanel;
    private JPanel buttonPanel;
    private JPanel titlePanel;
    private JScrollPane orderPanel;

    private JButton removeFromOrderButton;
    private JButton saveButton;
    private JButton loadButton;

    private Product latte;
    private Product mocha;
    private Product cheesecake;
    private Product macaron;
    private JButton latteButton;
    private JButton mochaButton;
    private JButton cheesecakeButton;
    private JButton macaronButton;

    private JList list;
    private Order order;
    private DefaultListModel orderList;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: constructs the frame for the order GUI of CafeAppGUI
    public OrderGUI() {
        super("Elysian Cafe - Order");
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        initializeOrderGUI();

        add(titlePanel, BorderLayout.BEFORE_FIRST_LINE);
        add(menuPanel, BorderLayout.LINE_START);
        add(orderPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

        setVisible(true);
    }

    // EFFECTS: initializes the items within the order GUI and creates its panels
    private void initializeOrderGUI() {
        order = new Order("your order");
        jsonWriter = new JsonWriter(JSON_SOURCE);
        jsonReader = new JsonReader(JSON_SOURCE);
        orderList = new DefaultListModel();

        convertOrderToList();
        createMenuPanel();
        createOrderPanel();
        createButtonPanel();
        createTitlePanel();
    }

    // MODIFIES: this
    // EFFECTS: creates the menu panel within the order GUI; calls method to set up product buttons
    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setSize(WIDTH / 2, HEIGHT);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.WHITE);

        setUpProductButtons();
    }

    // EFFECTS: creates the products offered at the cafe
    private void initializeProducts() {
        latte = new Product("Latte", 4, "Espresso with steamed milk.");
        mocha = new Product("Mocha", 3, "Espresso with chocolate and steamed milk.");
        cheesecake = new Product("Cheesecake", 0, "Cheesecake with strawberry toppings.");
        macaron = new Product("Macaron", 0, "Lavender-flavoured macaron.");
    }

    // MODIFIES: this
    // EFFECTS: creates the product buttons and adds them to menuPanel; calls method to set up button listeners
    private void setUpProductButtons() {
        initializeProducts();

        latteButton = new JButton(new ImageIcon(LATTE_IMAGE_FILE));
        mochaButton = new JButton(new ImageIcon(MOCHA_IMAGE_FILE));
        cheesecakeButton = new JButton(new ImageIcon(CHEESECAKE_IMAGE_FILE));
        macaronButton = new JButton(new ImageIcon(MACARON_IMAGE_FILE));

        JButton[] productButtons = {latteButton, mochaButton, cheesecakeButton, macaronButton};

        for (JButton b : productButtons) {
            b.setMaximumSize(new Dimension(b.getMinimumSize().width, b.getMinimumSize().height));
            b.setBackground(Color.WHITE);
            menuPanel.add(b);
        }
        setUpProductButtonListener();
    }

    // MODIFIES: this
    // EFFECTS: sets the action events for the product buttons when they're pressed
    private void setUpProductButtonListener() {
        ActionListener latteButtonListener = e -> {
            order.add(latte);
            orderList.addElement(latte.getProductName());
        };
        latteButton.addActionListener(latteButtonListener);

        ActionListener mochaButtonListener = e -> {
            order.add(mocha);
            orderList.addElement(mocha.getProductName());
        };
        mochaButton.addActionListener(mochaButtonListener);

        ActionListener cheesecakeButtonListener = e -> {
            order.add(cheesecake);
            orderList.addElement(cheesecake.getProductName());
        };
        cheesecakeButton.addActionListener(cheesecakeButtonListener);

        ActionListener macaronButtonListener = e -> {
            order.add(macaron);
            orderList.addElement(macaron.getProductName());
        };
        macaronButton.addActionListener(macaronButtonListener);
    }

    // MODIFIES: this
    // EFFECTS: converts order to orderList (DefaultListModel)
    private void convertOrderToList() {
        ArrayList<String> temp = order.getProductNames();
        for (String s : temp) {
            orderList.addElement(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the title panel with "Menu" and "Order" JLabels
    private void createTitlePanel() {
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
        titlePanel.setBackground(Color.darkGray);

        JLabel menuLabel = new JLabel("Menu");
        menuLabel.setBorder(BorderFactory.createEmptyBorder(10,120,10,294)); // hardcoded
        menuLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        menuLabel.setForeground(Color.white);
        titlePanel.add(menuLabel);

        JLabel orderLabel = new JLabel("Order");
        orderLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        orderLabel.setForeground(Color.white);
        titlePanel.add(orderLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates the order panel for the order GUI
    private void createOrderPanel() {
        convertOrderToList();
        list = new JList(orderList);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        orderPanel = new JScrollPane(list);
        orderPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
    }

    // MODIFIES: this
    // EFFECTS: creates the button panel for the order GUI
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

        removeFromOrderButton = new JButton("Remove Item from Order");
        saveButton = new JButton("Save Order");
        loadButton = new JButton("Load Existing Order");

        setUpButtonPanelButtonListeners();

        JButton[] buttonSet = {saveButton, loadButton, removeFromOrderButton};

        for (JButton b : buttonSet) {
            b.setFont(new Font("Sans Serif", Font.PLAIN, 13));
            b.setBackground(PINK);
        }

        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createHorizontalStrut(246));
        buttonPanel.add(removeFromOrderButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // MODIFIES: this
    // EFFECTS: sets the action events for when the buttons in buttonPanel are pressed
    private void setUpButtonPanelButtonListeners() {
        ActionListener removeFromOrderButtonListener = e -> {
            int index = list.getSelectedIndex();
            order.remove(index);
            orderList.remove(index);
        };
        removeFromOrderButton.addActionListener(removeFromOrderButtonListener);

        ActionListener saveButtonListener = e -> saveOrder();
        saveButton.addActionListener(saveButtonListener);

        ActionListener loadButtonListener = e -> {
            loadOrder();
            orderList.removeAllElements();
            convertOrderToList();
            orderPanel.updateUI();
            loadButton.setEnabled(false);
        };
        loadButton.addActionListener(loadButtonListener);
    }

    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: saves order to the file
    private void saveOrder() {
        try {
            jsonWriter.open();
            jsonWriter.write(order);
            jsonWriter.close();
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
        } catch (IOException e) {
            System.out.println("There is no file to load from. Please start a new order.");
        }
    }


}
