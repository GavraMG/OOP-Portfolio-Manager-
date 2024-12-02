// Package
package ePortfolio;

// Libraries
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  The TotalGainPanel class creates a panel that displays the total gain of the user's investments.
 *  The user can view the total gain of all investments in the portfolio.
 *  The total gain is calculated by subtracting the total cost of all investments from the total value of all investments.
 * 
 * @author Markus Gavra
 * @version 3.0
 * @since November 29th, 2024
 */

public class TotalGainPanel extends JPanel 
{
    // GUI Components
    private JTextField totalGainField;
    private JTextArea messageArea;

    // Constructor
    public TotalGainPanel()
    {
        // Set the layout for the main panel
        setLayout(new BorderLayout(10, 10));

        // Create the top panel for total gain display
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create and add label for Total Gain
        JLabel totalGainLabel = new JLabel("Total Gain:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(totalGainLabel, gbc);

        // Create and add text field for displaying total gain
        totalGainField = new JTextField(15);
        totalGainField.setEditable(false); // Non-editable
        gbc.gridx = 1;
        topPanel.add(totalGainField, gbc);

        // Add border to the top panel
        Border dashedBorder = BorderFactory.createDashedBorder(UIManager.getColor("Label.foreground"), 1, 1, 0, true);
        Border titledBorder = BorderFactory.createTitledBorder(dashedBorder, "Total Gain");
        topPanel.setBorder(titledBorder);

        // Create the messages section
        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false); // Non-editable
        JScrollPane scrollPane = new JScrollPane(messageArea);

        // Create dashed border for messages section
        JPanel messagesPanel = new JPanel(new BorderLayout());
        messagesPanel.add(new JLabel("Messages:"), BorderLayout.NORTH);
        messagesPanel.add(scrollPane, BorderLayout.CENTER);
        messagesPanel.setBorder(BorderFactory.createTitledBorder(dashedBorder, ""));

        // Add components to the main panel
        add(topPanel, BorderLayout.NORTH);
        add(messagesPanel, BorderLayout.CENTER);

        // Calculate and display the total gain
        displayTotalGain();
    }

    /**
     *  The displayTotalGain method updates the GUI to display individual investment details and total gain.
     *  Calculates the total gain for the investment portfolio.
     * @return The total gain for the investment portfolio.
     */
    public void displayTotalGain() 
    {
        // Clear the message area
        messageArea.setText("");

        double totalGain = 0.00;

        try 
        {
            // Get the list of all investments
            ArrayList<Investment> investments = Investment.getInvestments();

            // If no investments exist, display a message and exit
            if (investments.isEmpty()) 
            {
                // Shows message if no investments are available
                messageArea.setText("No investments available to calculate total gain.");
                totalGainField.setText("0.00");
                return;
            }

            // Iterate through each investment
            for (Investment investment : investments) 
            {
                // Calculate the gain for the current investment
                double gain = (investment.getPrice() * investment.getQuantity()) - investment.getBookValue();
               
                // Add this investment's gain to the total gain
                totalGain += gain;

                // Append investment details
                messageArea.append(String.format(
                    "Type: %s\nSymbol: %s\nName: %s\nQuantity: %d\nPrice: %.2f\nBook Value: %.2f\nGain: %.2f\n\n",
                    investment.getClass().getSimpleName(),
                    investment.getSymbol(),
                    investment.getName(),
                    investment.getQuantity(),
                    investment.getPrice(),
                    investment.getBookValue(),
                    gain
                ));
            }

            // Update the total gain field
            totalGainField.setText(String.format("%.2f", totalGain));
        } 

        // Exception handlers for total gain calculation
        catch (Exception ex) 
        {
            // Shows error message if total gain cannot be calculated
            messageArea.setText("Error calculating total gain: " + ex.getMessage());
            totalGainField.setText("0.00");
        }
    }
}