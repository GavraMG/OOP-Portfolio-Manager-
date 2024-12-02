// Package
package ePortfolio;

// Libraries
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 *  The SearchingInvestmentPanel class creates a panel that allows the user to search for an investment.
 *  The user can enter the investment's symbol and click the "Search" button to search for the investment.
 *  The user can also click the "Reset" button to clear the text field.
 * 
 * @author Markus Gavra
 * @version 3.0
 * @since November 29th, 2024
 */

public class SearchingInvestmentPanel extends JPanel 
{
    // GUI Components
    private JTextField symbolField, namekeywordField, lowpriceField, highpriceField;
    private JButton resetButton, searchButton;
    private JTextArea messageArea;
    
    // Constructor
    public SearchingInvestmentPanel() 
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
        String[] labels = {"Symbol", "Name / Keywords", "Low price", "High price"};
        JTextField[] fields = {symbolField = new JTextField(15), namekeywordField = new JTextField(15), lowpriceField = new JTextField(15), highpriceField = new JTextField(15)};

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
        searchButton = new JButton("Search");
        buttonPanel.add(resetButton);
        buttonPanel.add(searchButton);

        // Add padding around buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Create the top section with input and buttons
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(inputPanel, BorderLayout.CENTER);
        topSection.add(buttonPanel, BorderLayout.EAST);

        // Create dashed border for the selling section
        Border dashedBorder = BorderFactory.createDashedBorder(UIManager.getColor("Label.foreground"), 1, 1, 0, true);
        Border titledBorder = BorderFactory.createTitledBorder(dashedBorder, "Searching an investment");
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
        searchButton.addActionListener(e -> processSearch());
        
    }     

    /*
     * Method to reset the input fields.
     */
    private void resetFields() 
    {
        symbolField.setText("");
        namekeywordField.setText("");
        lowpriceField.setText("");
        highpriceField.setText("");
        messageArea.setText("");
    }

    /*
     * Method to process the search for an investment.
     * Searches for an investment based on the symbol, name, and price range.
     * Displays the search results in the message area.
     * Displays an error message for invalid inputs.
     * Displays an error message for unexpected errors.
     * Handles exceptions for invalid number inputs.
     */
    private void processSearch() 
    {
        try {
            // Get the values from the input fields
            String symbol = symbolField.getText().trim();
            String namekeyword = namekeywordField.getText().trim();
            String lowprice = lowpriceField.getText().trim();
            String highprice = highpriceField.getText().trim();

            // Sets the low and high price values to null
            Double lowPriceValue = null;
            Double highPriceValue = null;

            // Validate and parse low price input
            if (!lowprice.isEmpty()) 
            {
                // Parse the low price value
                lowPriceValue = Double.parseDouble(lowprice);
            }

            // Validate and parse high price input
            if (!highprice.isEmpty()) 
            {
                // Parse the high price value
                highPriceValue = Double.parseDouble(highprice);
            }

            // Check if low price is greater than high price
            if (lowPriceValue != null && highPriceValue != null && lowPriceValue > highPriceValue) 
            {
                // Show error message for low price greater than high price
                messageArea.setText("Error: Low price cannot be greater than high price.");
                return;
            }

            // Perform the search
            java.util.List<Investment> results = Investment.searchInvestments(symbol, namekeyword, lowPriceValue, highPriceValue);

            // Display results
            if (results.isEmpty()) 
            {
                // Show message if no investments are found
                messageArea.setText("No investments found matching the search criteria.");
            } 

            else 
            {
                // Clear the message area
                messageArea.setText(""); 
                
                // Display each investment found
                for (Investment investment : results)
                {
                    messageArea.append(String.format(
                        "Type: %s\nSymbol: %s\nName: %s\nQuantity: %d\nPrice: $%.2f\n\n",
                        investment.getClass().getSimpleName(),
                        investment.getSymbol(),
                        investment.getName(),
                        investment.getQuantity(),
                        investment.getPrice()
                    ));
                }
            }
        }
        
        // Exception handlers for invalid inputs
        catch (NumberFormatException e) 
        {
            // Show error message for invalid number inputs
            messageArea.setText("Error: Invalid price input. Please enter valid numbers for the price fields.");
        } 
        
        // Exception handlers for unexpected errors
        catch (Exception e) 
        {
            // Show error message for unexpected errors
            messageArea.setText("Error: An unexpected error occurred. " + e.getMessage());
        }
    }
}
