package ui;

import java.io.FileNotFoundException;

// Program runs here and creates a new cafe simulator application in the console
public class CafeAppUI {
    public static void main(String[] args) {
        try {
            new CafeApp();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
