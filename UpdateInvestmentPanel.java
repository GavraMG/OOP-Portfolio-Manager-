// Package
package ePortfolio;

// Libraries
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * UpdatePricesPanel provides a GUI for updating investment prices.
 * The user can navigate through the investments using "Prev" and "Next" buttons,
 * update the price of the current investment, and save the changes.
 * 
 * @author Markus Gavra
 * @version 3.0
 * @since November 29th, 2024
 */
public class UpdateInvestmentPanel extends JPanel 
{
    // GUI Components
    private JTextField symbolField, nameField, priceField;
    private JButton prevButton, nextButton, saveButton;
    private JTextArea messageArea;

    // Track the current investment index
    private int currentIndex = 0;

    // Constructor
    public UpdateInvestmentPanel() 
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
        String[] labels = {"Symbol", "Name", "Price"};
        JTextField[] fields = {symbolField = new JTextField(15), nameField = new JTextField(15), priceField = new JTextField(15)};

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
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        prevButton = new JButton("Prev");
        nextButton = new JButton("Next");
        saveButton = new JButton("Save");
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(saveButton);

        // Add padding around buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Create the top section with input and buttons
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(inputPanel, BorderLayout.CENTER);
        topSection.add(buttonPanel, BorderLayout.EAST);

        // Create dashed border for the selling section
        Border dashedBorder = BorderFactory.createDashedBorder(UIManager.getColor("Label.foreground"), 1, 1, 0, true);
        Border titledBorder = BorderFactory.createTitledBorder(dashedBorder, "Updating investments");
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
        prevButton.addActionListener(e -> prev());
        nextButton.addActionListener(e -> next());
        saveButton.addActionListener(e -> save());

        // Initialize the first investment display
        displayInvestment();
    }

    /*
     * Method to display the current investment details.
     * Displays the symbol, name, and price of the current investment.
     * Enables navigation buttons based on the current index.
     * Disables symbol and name fields for editing.
     * Displays a message if no investments are available.
     */
    public void displayInvestment() 
    {
        // Get the list of all investments
        ArrayList<Investment> investments = Investment.getInvestments();

        // If no investments exist, disable buttons and clear fields
        if (investments.isEmpty())
        {
            symbolField.setText("");
            nameField.setText("");
            priceField.setText("");
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            saveButton.setEnabled(false);
            messageArea.setText("No investments available to update.");
            return;
        }

        else 
        {
            // Enable the save button if there's at least one investment
            saveButton.setEnabled(true);
        }

        // Get the current investment
        Investment currentInvestment = investments.get(currentIndex);

        // Call displayInvestment and pass the required details
        String investmentDetails = currentInvestment.toString();

        // Display investment details in the text area
        messageArea.setText(investmentDetails);

        // Display investment details
        symbolField.setText(currentInvestment.getSymbol());
        nameField.setText(currentInvestment.getName());
        priceField.setText(String.format("%.2f", currentInvestment.getPrice()));

        // Disable symbol and name fields
        symbolField.setEditable(false);
        nameField.setEditable(false);

        // Enable/disable navigation buttons
        prevButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < investments.size() - 1);
    }

    /*
     * Method to navigate to the previous investment.
     * Decrements the current index and displays the investment details.
     */
    private void prev() 
    {
        // Get the current list index of the investments
        if (currentIndex > 0) 
        {
            // Decrement the current index and display the investment details
            currentIndex--;
            displayInvestment();
        }
    }

    /*
     * Method to navigate to the next investment.
     * Increments the current index and displays the investment details.
     */
    private void next() 
    {
        ArrayList<Investment> investments = Investment.getInvestments();
        
        if (currentIndex < investments.size() - 1) 
        {
            // Increment the current index and display the investment details
            currentIndex++;
            displayInvestment();
        }
    }

    /*
     * Method to save the updated price of the current investment.
     * Validates the input field and updates the price of the current investment.
     * Displays a success message if the price is updated successfully.
     * Handles exceptions for invalid inputs.
     * Handles exceptions for negative price.
     */
    public void save() 
    {
        // Get the list of all investments
        ArrayList<Investment> investments = Investment.getInvestments();

        // If no investments exist, display a message and exit
        if (investments.isEmpty()) 
        {
            // Show error message if no investments are available
            messageArea.setText("No investments available to update.");
            return;
        }

        // Get the current investment details
        String symbol = symbolField.getText().trim();
        String name = nameField.getText().trim();

        try 
        {
            // Parse and validate the new price
            double newPrice = Double.parseDouble(priceField.getText());
            
            // Check if price is positive
            if (newPrice <= 0) 
            {
                // Show error message for invalid price
                throw new IllegalArgumentException("Price must be a positive number.");
            }

            // Update the price of the current investment
            Investment updatedInvestment = Investment.updatePrices(symbol, name, newPrice);

            // Show success message and updated investment details
            messageArea.setText(String.format("Price updated successfully:\n\n%s", updatedInvestment.toString()));

        } 

        // Exception handlers for invalid inputs
        catch (NumberFormatException ex) 
        {
            // Show error message for invalid number inputs
            messageArea.setText("Error: Invalid price. Please enter a valid positive number.");
        } 
        
        // Exception handlers for negative price
        catch (IllegalArgumentException ex) 
        {
            // Show error message for invalid price
            messageArea.setText("Error: " + ex.getMessage());
        }
    }
}