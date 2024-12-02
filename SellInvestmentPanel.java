// Package
package ePortfolio;

// Libraries
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * The SellInvestmentPanel class creates a panel that allows the user to sell an investment.
 * The user can enter the investment's symbol, quantity, and price.
 * The user can also click the "Sell" button to sell the investment or the "Reset" button to clear the text fields.
 * 
 * @author Markus Gavra
 * @version 3.0
 * @since November 29th, 2024
 */

public class SellInvestmentPanel extends JPanel 
{
    // GUI Components
    private JTextField symbolField, quantityField, priceField;
    private JButton resetButton, sellButton;
    private JTextArea messageArea;

    // Constructor
    public SellInvestmentPanel() 
    {
        // Set the layout for the main panel
        setLayout(new BorderLayout(0, 5));

        // Create the top panel for input fields
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Create labels and input fields
        String[] labels = {"Symbol", "Quantity", "Price"};
        JTextField[] fields = {symbolField = new JTextField(15), quantityField = new JTextField(15), priceField = new JTextField(15)};

        // Add labels and fields
        for (int i = 0; i < labels.length; i++) 
        {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            inputPanel.add(new JLabel(labels[i] + ":"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(fields[i], gbc);
        }

        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        resetButton = new JButton("Reset");
        sellButton = new JButton("Sell");
        buttonPanel.add(resetButton);
        buttonPanel.add(sellButton);

        // Add padding around buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Create the top section with input and buttons
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(inputPanel, BorderLayout.CENTER);
        topSection.add(buttonPanel, BorderLayout.EAST);

        // Create dashed border for the selling section
        Border dashedBorder = BorderFactory.createDashedBorder(UIManager.getColor("Label.foreground"), 1, 1, 0, true);
        Border titledBorder = BorderFactory.createTitledBorder(dashedBorder, "Selling an investment");
        topSection.setBorder(titledBorder);

        // Create the messages section
        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        // Create dashed border for messages section
        JPanel messagesPanel = new JPanel(new BorderLayout());
        messagesPanel.add(new JLabel("Messages"), BorderLayout.NORTH);
        messagesPanel.add(scrollPane, BorderLayout.CENTER);
        messagesPanel.setBorder(BorderFactory.createTitledBorder(dashedBorder, ""));

        // Add sections to the main panel
        add(topSection, BorderLayout.NORTH);
        add(messagesPanel, BorderLayout.CENTER);

        // Add action listeners
        resetButton.addActionListener(e -> resetFields());
        sellButton.addActionListener(e -> processSell());
    }

    /*
     * Method to reset the input fields.
     */
    private void resetFields() 
    {
        symbolField.setText("");
        quantityField.setText("");
        priceField.setText("");
        messageArea.setText("");
    }

    /*
     * Method to process the sell operation.
     * Validates the input fields and sells the investment.
     * Displays the result in the message area.
     * Handles exceptions for invalid inputs.
     * Handles exceptions for empty fields.
     * Handles exceptions for negative quantity and price.
     */
    private void processSell() 
    {
        try 
        {
            // Get the input values
            String symbol = symbolField.getText().trim();
            String quantityStr = quantityField.getText().trim();
            String priceStr = priceField.getText().trim();

            // Execption handlers for empty fields
            if (symbol.isEmpty()) throw new IllegalArgumentException("Symbol field must not be empty.");
            if (quantityStr.isEmpty()) throw new IllegalArgumentException("Quantity field must not be empty.");
            if (priceStr.isEmpty()) throw new IllegalArgumentException("Price field must not be empty.");

            // Exception handler for checking if price is positive
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) throw new IllegalArgumentException("Quantity must be a positive integer.");

            // Exception handler for checking if quantity is positive
            double price = Double.parseDouble(priceStr);
            if (price <= 0) throw new IllegalArgumentException("Price must be a positive number.");

            // Call the sellInvestment method and display the result
            String result = Investment.sellInvestment(symbol, quantity, price);
            messageArea.setText(result);
        } 

        // Exception handlers for invalid inputs
        catch (NumberFormatException e) 
        {
            // Show error message for invalid number inputs
            messageArea.setText("Error: Quantity and price must be valid numbers.");
        } 

        catch (IllegalArgumentException e) 
        {
            // Show error message for invalid inputs
            messageArea.setText("Error: " + e.getMessage());
        }
    }
}