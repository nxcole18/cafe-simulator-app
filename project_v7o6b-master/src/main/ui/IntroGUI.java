package ui;


import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Intro window GUI for CafeAppGUI
public class IntroGUI extends JFrame {
    private static final Color PINK = new Color(247,217,209);
    private static final String CAFE_IMAGE_FILE = "cafe.jfif";
    private static final int WIDTH = 580;
    private static final int HEIGHT = 770;

    private JPanel introPanel;
    private JLabel introText;
    private JLabel cafeImageLabel;
    private JButton enterButton;

    // MODIFIES: this
    // EFFECTS: constructs the intro frame for CafeAppGUI
    public IntroGUI() {
        super("Elysian Cafe");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setBackground(Color.WHITE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // This segment references code from an online resource
                // Link: http://www.java2s.com/Tutorials/Java/Swing_How_to/JOptionPane/Show_confirmation_dialog_for_closing_JFrame.htm
                int result = JOptionPane.showConfirmDialog(null,
                        "Do you wish to leave the cafe?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null,
                            "Thank you for visiting Elysian Cafe. We hope to see you soon!");
                    printEventLog();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else if (result == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        setUpIntroPanel();
        add(introPanel);

        setVisible(true);
    }

    // EFFECTS: prints event log associated with user's order
    private void printEventLog() {
        EventLog log = EventLog.getInstance();
        for (Event e : log) {
            System.out.println(e.getDate() + " - " + e.getDescription());
        }
    }

    // EFFECTS: calls necessary methods to set up intro panel contents
    private void initializePanelContents() {
        setUpCafeImage();
        setUpIntroText();
        setUpEnterButton();
    }

    // MODIFIES: this
    // EFFECTS: creates the intro panel
    private void setUpIntroPanel() {
        introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));

        initializePanelContents();

        introPanel.add(cafeImageLabel);
        introPanel.add(introText);
        introPanel.add(enterButton);
    }

    // MODIFIES: this
    // EFFECTS: sets cafeImageLabel to an image
    private void setUpCafeImage() {
        ImageIcon cafeIcon = new ImageIcon(CAFE_IMAGE_FILE);
        cafeImageLabel = new JLabel();
        cafeImageLabel.setIcon(cafeIcon);
    }

    // MODIFIES: this
    // EFFECTS: creates the intro text
    private void setUpIntroText() {
        introText = new JLabel("☆ ~ Welcome to Elysian Cafe ~ ☆");
        introText.setBorder(BorderFactory.createEmptyBorder(10,170,10,10)); // hardcoded
        introText.setFont(new Font("Sans Serif", Font.BOLD, 14));
    }

    // MODIFIES: this
    // EFFECTS: creates the enter button and calls the method to set up its button listener
    private void setUpEnterButton() {
        enterButton = new JButton("Enter the Cafe");
        enterButton.setBackground(PINK);
        enterButton.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        enterButton.setOpaque(true);
        enterButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, enterButton.getMinimumSize().height));
        setUpEnterButtonListener();
    }

    // MODIFIES: this
    // EFFECTS: sets the action event to the enter button when its pressed
    private void setUpEnterButtonListener() {
        ActionListener enterButtonListener = e -> new OrderGUI();
        enterButton.addActionListener(enterButtonListener);
    }


}
