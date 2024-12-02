// Package
package ePortfolio;

// Libraries
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/** 
 *  The BuyInvestmentPanel class creates a panel that allows the user to buy an investment.
 *  The user can enter the investment's symbol, name, quantity, and price.
 *  The user can also click the "Buy" button to buy the investment.
 *  The user can also click the "Clear" button to clear the text fields.
 * 
 *  @author Markus Gavra
 *  @version 3.0
 *  @since November 29th, 2024
 */

public class BuyInvestmentPanel extends JPanel 
{
   // GUI Components
   private JComboBox<String> typeDropdown;
   private JTextField symbolField, nameField, quantityField, priceField;
   private JButton resetButton, buyButton;
   private JTextArea messageArea;

    // Constructor
    public BuyInvestmentPanel() 
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
        String[] labels = {"Type", "Symbol", "Name", "Quantity", "Price"};
        Component[] fields = new Component[5];
        
        // Initialize the fields
        typeDropdown = new JComboBox<>(new String[]{"stock", "mutual fund"});
        symbolField = new JTextField(20);
        nameField = new JTextField(30);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);
        fields = new Component[]{typeDropdown, symbolField, nameField, quantityField, priceField};

        // Add labels and fields
        for (int i = 0; i < labels.length; i++) 
        {
            // Set the grid constraints
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
        buyButton = new JButton("Buy");
        buttonPanel.add(resetButton);
        buttonPanel.add(buyButton);

        // Add padding around buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Create the top section with input and buttons
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(inputPanel, BorderLayout.CENTER);
        topSection.add(buttonPanel, BorderLayout.EAST);

        // Create dashed border for the buying section
        Border dashedBorder = BorderFactory.createDashedBorder (UIManager.getColor("Label.foreground"), 1, 1, 0, true);
        Border titledBorder = BorderFactory.createTitledBorder( dashedBorder, "Buying an investment");
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
        buyButton.addActionListener(e -> processBuy());
    }

    /*
     * Method to reset the input fields.
     */
    private void resetFields() 
    {
        typeDropdown.setSelectedIndex(0);
        symbolField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        messageArea.setText("");
    }

    /*
     * Method to process the buying of an investment.
     * Validates the input fields and adds the investment to the portfolio.
     * Displays the result in the message area.
     * If there is an error, displays an error message.
     * If the investment is added successfully, displays a success message.
     */
    private void processBuy() 
    {
        try 
        {
            // Get the input values
            String type = (String) typeDropdown.getSelectedItem();
            String symbol = symbolField.getText().trim();
            String name = nameField.getText().trim();
            String quantityStr = quantityField.getText().trim();
            String priceStr = priceField.getText().trim();

            // Execption handlers for empty fields
            if(symbol.isEmpty() && name.isEmpty() && quantityStr.isEmpty() && priceStr.isEmpty()) throw new IllegalArgumentException("All fields must be filled out.");
            if (symbol.isEmpty()) throw new IllegalArgumentException("Symbol cannot be empty.");
            if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
            if (quantityStr.isEmpty()) throw new IllegalArgumentException("Quantity cannot be empty.");
            if (priceStr.isEmpty()) throw new IllegalArgumentException("Price cannot be empty.");

            // Exception handler for checking if price is positive
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) throw new IllegalArgumentException("Quantity must be a positive integer.");

            // Exception handler for checking if quantity is positive
            double price = Double.parseDouble(priceStr);
            if (price <= 0) throw new IllegalArgumentException("Price must be a positive number.");

            // Add the investment
            String result = Investment.addInvestment(type, symbol, name, quantity, price);
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