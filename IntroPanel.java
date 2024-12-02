// Package
package ePortfolio;

// Libraries
import javax.swing.*;
import java.awt.*;

/** 
 *  The IntroPanel class is a JPanel that displays an introduction to the program.
 *  It is displayed when the program is first run.
 *  It contains a title, description, and instructions.
 * 
 *  @author Markus Gavra
 *  @version 3.0
 *  @since November 29th, 2024
 */

public class IntroPanel extends JPanel 
{
    // Constructor
    public IntroPanel() 
    {
        // Set layout for the Intro Panel
        setLayout(new BorderLayout());

        // Create a panel to center the content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Create a label for the title
        JLabel titleLabel = new JLabel("Welcome to ePortfolio.", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create a text area for the description
        JTextArea descriptionArea = new JTextArea(
                "Choose a command from the \"Commands\" menu to buy or sell " +
                "an investment, update prices for all investments, get gain for the " +
                "portfolio, search for relevant investments, or quit the program."
        );

        // Set the font and properties of the description area
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(getBackground());
        descriptionArea.setColumns(38);

        // Add components to the center panel
        centerPanel.add(titleLabel, gbc);
        centerPanel.add(descriptionArea, gbc);

        // Add components to the main panel
        add(centerPanel, BorderLayout.CENTER);
    }
}