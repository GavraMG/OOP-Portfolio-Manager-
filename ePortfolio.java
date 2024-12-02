// Package
package ePortfolio;

// Libraries
import javax.swing.*;
import java.awt.*;

/**
 * The ePortfolio class manages the investment portfolio of a user.
 * Users can buy, sell, update investments such as stocks and mutual funds, get total gain and search within their portfolio.
 * This class provides the main entry point for interacting with the portfolio
 * through a GUI interface. Users can perform various actions like buying investments, 
 * selling investments, updating prices, calculating total gain, and searching for specific investments.
 * 
 * Complime and Run Instructions:
 * Compile Command: javac -d bin ePortfolio/*.java
 * Run Command: java -cp bin ePortfolio.ePortfolio
 * Clean Command: rm -r bin/*
 * 
 * @author Markus Gavra
 * @version 3.0
 * @since November 29th, 2024
 */

public class ePortfolio extends JFrame
{
    // GUI Panels for different operations
    private JPanel introPanel;
    private JPanel buyInvestmentPanel;
    private JPanel sellInvestmentPanel;
    private JPanel updateInvestmentPanel;
    private JPanel totalGainPanel;
    private JPanel searchingInvestmentPanel;

    // Default constructor
    public ePortfolio()
    {
        // Set the title of the frame
        setTitle("ePortfolio");
        
        // Set the size of the frame
        setSize(600, 500);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Set the layout of the frame
        setLayout(new BorderLayout());
        
        // Initialize panels
        introPanel = new IntroPanel();
        buyInvestmentPanel = new BuyInvestmentPanel();
        sellInvestmentPanel = new SellInvestmentPanel();
        updateInvestmentPanel = new UpdateInvestmentPanel();
        totalGainPanel = new TotalGainPanel();
        searchingInvestmentPanel = new SearchingInvestmentPanel();
        
        // Add the intro panel as the initial view
        add(introPanel, BorderLayout.CENTER);
        
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Commands");

        // Create menu items
        JMenuItem buyInvestmentItem = new JMenuItem("Buy Investment");
        JMenuItem sellInvestmentItem = new JMenuItem("Sell Investment");
        JMenuItem updateInvestmentItem = new JMenuItem("Update Investment");
        JMenuItem totalGainItem = new JMenuItem("Get Total Gain");
        JMenuItem searchInvestmentsItem = new JMenuItem("Search Investments");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add menu items to the menu
        optionsMenu.add(buyInvestmentItem);
        optionsMenu.add(sellInvestmentItem);
        optionsMenu.add(updateInvestmentItem);
        optionsMenu.add(totalGainItem);
        optionsMenu.add(searchInvestmentsItem);
        optionsMenu.add(exitItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);

        // Menu item action to switch to the Buy Investment Panel
        buyInvestmentItem.addActionListener(e -> switchPanel(buyInvestmentPanel));

        // Menu item action to switch to the Sell Investment Panel
        sellInvestmentItem.addActionListener(e -> switchPanel(sellInvestmentPanel));

        // Menu item action to switch to the Update Investment Panel so that it can recalculate the total gain to be up-to-date
        updateInvestmentItem.addActionListener(e -> 
        {
            // Switch to the Update Investment Panel
            switchPanel(updateInvestmentPanel);

            // Explicitly refresh the Update Investment Panel
            if (updateInvestmentPanel instanceof UpdateInvestmentPanel) 
            {
                ((UpdateInvestmentPanel) updateInvestmentPanel).displayInvestment();
                
            }
        });
        
        // Menu item action to switch to the Total Gain Panel so that it can recalculate the total gain to be up-to-date
        totalGainItem.addActionListener(e -> 
        {
            // Switch to the TotalGainPanel
            switchPanel(totalGainPanel);

            // Explicitly refresh the Total Gain Panel
            if (totalGainPanel instanceof TotalGainPanel) 
            {
                // Call the displayTotalGain method to recalculate the total gain
                ((TotalGainPanel) totalGainPanel).displayTotalGain();
            }
        });

        // Menu item action to switch to the Searching Investment Panel
        searchInvestmentsItem.addActionListener(e -> switchPanel(searchingInvestmentPanel));

        // Menu item action to exit the program
        exitItem.addActionListener(e -> System.exit(0));

        // Set the frame to be visible
        setVisible(true);
    }

    // Method to switch panels
    private void switchPanel(JPanel panel)
    {
        // Remove all components from the frame
        getContentPane().removeAll();
        
        // Add the new panel to the frame
        add(panel, BorderLayout.CENTER);
        
        // Revalidate and repaint the frame
        revalidate();
        repaint();
    }

    // Main method to run the program
    public static void main(String[] args)
    {
        // Create a new ePortfolio object
        new ePortfolio();
    }
}
