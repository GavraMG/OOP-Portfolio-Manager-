// Package
package ePortfolio;

// Libraries
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Investment superclass manages the investment portfolio.
 * It supports adding, selling, updating prices, getting total gains, searching, and managing stocks and mutual funds.
 * 
 * @author Markus Gavra
 * @version 3.0
 * @since November 29th, 2024
 */

public abstract class Investment 
{
    // Attributes for each investment
    protected String symbol;
    protected String name;
    protected int quantity;
    protected double price;
    protected double bookValue;

    // Static list of all investments and a keyword index for searching
    private static final ArrayList<Investment> investmentList = new ArrayList<>();
    private static final HashMap<String, List<Integer>> keywordIndex = new HashMap<>();

    // Constructor
    public Investment(String symbol, String name, int quantity, double price) 
    {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = calculateBookValue(quantity, price);
    }

    // Copy constructor for Investment
    public Investment(Investment other) 
    {
        this.symbol = other.symbol;
        this.name = other.name;
        this.quantity = other.quantity;
        this.price = other.price;
        this.bookValue = other.bookValue;
    }

    // Abstract method for calculating book value
    protected abstract double calculateBookValue(int quantity, double price);
    public abstract double calculatePayment(int quantity, double price);
    public abstract double calculateGain(int quantity, double payment);

    // Getters and setters
    public String getSymbol() 
    { 
        return symbol; 
    }
    
    public void setSymbol(String symbol) 
    { 
        this.symbol = symbol; 
    }

    public String getName() 
    { 
        return name; 
    }
    
    public void setName(String name) 
    { 
        this.name = name; 
    }

    public int getQuantity() 
    { 
        return quantity; 
    }

    public void setQuantity(int quantity) 
    { 
        this.quantity = quantity; 
    }

    public double getPrice() 
    { 
        return price; 
    }

    public void setPrice(double price) 
    { 
        this.price = price; 
    }

    public double getBookValue() 
    { 
        return bookValue; 
    }
    public void setBookValue(double bookValue) 
    { 
        this.bookValue = bookValue; 
    }

    /*
     * Overridden equals method to compare two investments based on their attributes.
     */
    @Override
    public boolean equals(Object other) 
    {
        if (this == other) return true; // Same reference
        if (other == null || getClass() != other.getClass()) return false; // Check class compatibility

        Investment that = (Investment) other;

        return quantity == that.quantity &&
               Double.compare(that.price, price) == 0 &&
               Double.compare(that.bookValue, bookValue) == 0 &&
               symbol.equalsIgnoreCase(that.symbol) &&
               name.equalsIgnoreCase(that.name);
    }

    /*
     * Overridden toString method to display investment details.
     */
    @Override
    public String toString() 
    {
        return String.format(
            "Current Investment Details:\nType: %s\nSymbol: %s\nName: %s\nQuantity: %d\nPrice: $%.2f\nBook Value: $%.2f\n",
            this.getClass().getSimpleName(),
            symbol,
            name,
            quantity,
            price,
            bookValue
        );
    }

    /**
     * Adds a new investment to the portfolio.
     * If an investment with the same symbol exists, updates its quantity and book value.
     *
     * @param type     The type of investment (Stock or Mutual Fund).
     * @param symbol   The investment symbol.
     * @param name     The investment name.
     * @param quantity The investment quantity.
     * @param price    The investment price per unit.
     * @return A message indicating the result of the operation.
     */
    public static String addInvestment(String type, String symbol, String name, int quantity, double price) 
    {
        // Validate symbol, name, quantity, and price
        if (symbol == null || symbol.isEmpty()) 
        {
            // Return an error message if the symbol is empty
            throw new IllegalArgumentException("Symbol cannot be empty.");
        }

        if (name == null || name.isEmpty()) 
        {
            // Return an error message if the name is empty
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (quantity <= 0) 
        {
            // Return an error message if the quantity is not positive
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (price <= 0) 
        {
            // Return an error message if the price is not positive
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
    
        // Check if the investment already exists
        for (Investment investment : investmentList) 
        {
            // Update existing investment if the symbol matches
            if (investment.getSymbol().equalsIgnoreCase(symbol)) 
            {
                // Update stock investment
                if (investment instanceof Stock) 
                {
                    // Stock-specific commission fee
                    double commissionFee = 9.99; 
                    double additionalBookValue = (quantity * price) + commissionFee;
                    investment.setQuantity(investment.getQuantity() + quantity);
                    investment.setBookValue(investment.getBookValue() + additionalBookValue);
                    
                    // Return success message and display the updated investment
                    return "Existing investment updated successfully.\n\n" + investment;
                } 

                // Update mutualfund investment
                else if (investment instanceof MutualFund)
                {
                    // Mutual fund does not have a commission fee
                    double additionalBookValue = (quantity * price);
                    investment.setQuantity(investment.getQuantity() + quantity);
                    investment.setBookValue(investment.getBookValue() + additionalBookValue);
                    
                    // Return success message and display the updated investment
                    return "Existing investment updated successfully.\n\n" + investment;
                }
            }
        }
    
        // Create and add a new investment
        Investment newInvestment = switch (type.toLowerCase()) 
        {
            // Create a new investment based on the type
            case "stock" -> new Stock(symbol, name, quantity, price);
            case "mutual fund" -> new MutualFund(symbol, name, quantity, price);
            default -> null;
        };
        
        // Return an error message if the investment type is invalid
        if (newInvestment == null) return "Invalid investment type.";
    
        // Add the new investment to the list
        investmentList.add(newInvestment);
        updateKeywordIndex(newInvestment, investmentList.size() - 1);
    
        // Return success message and display the new investment
        return "New investment added successfully.\n\n" + newInvestment;
    }
    

    /**
     * Updates the keyword index for a newly added investment.
     */
    private static void updateKeywordIndex(Investment investment, int position) 
    {
        // Split the investment name into keywords
        String[] keywords = investment.getName().toLowerCase().split("\\s+");
        
        // Add each keyword to the index
        for (String keyword : keywords) 
        {
            keywordIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(position);
        }
    }

    /**
     * Sells an investment from the portfolio.
     *
     * @param symbol   The symbol of the investment to sell.
     * @param quantity The quantity of the investment to sell.
     * @param price    The price at which to sell the investment.
     * @return A message indicating the result of the operation.
     */
    public static String sellInvestment(String symbol, int quantityToSell, double price) 
    {
        // Validate symbol, quantity, and price
        if (symbol == null || symbol.isEmpty()) 
        {
            // Return an error message if the symbol is empty
            throw new IllegalArgumentException("Symbol cannot be empty.");
        }

        if (quantityToSell <= 0) 
        {
            // Return an error message if the quantity to sell is not positive
            throw new IllegalArgumentException("Quantity to sell must be greater than zero.");
        }

        if (price <= 0) 
        {
            // Return an error message if the price is not positive
            throw new IllegalArgumentException("Price must be greater than zero.");
        }

        // Search for the investment with the given symbol
        Investment investmentToSell = null;
        for (Investment investment : investmentList) 
        {
            // Find the investment with the matching symbol
            if (investment.getSymbol().equalsIgnoreCase(symbol)) 
            {
                // Set the investment to sell
                investmentToSell = investment;
                break;
            }
        }

        // If no matching investment is found, return an error message
        if (investmentToSell == null) 
        {
            // Return an error message if the investment is not found
            throw new IllegalArgumentException("Investment with symbol '" + symbol + "' not found.");
        }

        // Check if the quantity is valid
        if (quantityToSell <= 0) 
        {
            // Return an error message if the quantity to sell is not positive
            throw new IllegalArgumentException("Error: Quantity to sell must be a positive number.");
        }

        if (quantityToSell > investmentToSell.getQuantity()) 
        {
            // Return an error message if the quantity to sell is more than the available quantity
            throw new IllegalArgumentException("Not enough quantity available to sell.");
        }

        // Update the investment's price before proceeding with the sale
        investmentToSell.setPrice(price);

        // Calculate payment and gain
        double payment = investmentToSell.calculatePayment(quantityToSell, price);
        double gain = investmentToSell.calculateGain(quantityToSell, payment);

        // Update the investment's quantity and book value
        int remainingQuantity = investmentToSell.getQuantity() - quantityToSell;

        if (remainingQuantity == 0) 
        {
            // Remove the investment from the portfolio if all shares/units are sold
            investmentList.remove(investmentToSell);
            return String.format(
                "Sale completed. All units sold. Payment: $%.2f, Gain: $%.2f. \n\n Investment removed from the portfolio.\n\n",
                payment, gain
            );
        } 

        else 
        {
            // Update the investment's quantity
            investmentToSell.setQuantity(remainingQuantity);

            // Update the book value proportionally
            double newBookValue = investmentToSell.getBookValue() * remainingQuantity / (remainingQuantity + quantityToSell);
            investmentToSell.setBookValue(newBookValue);

            return String.format(
                "Sale completed. Payment: $%.2f, Gain: $%.2f. Remaining units: %d\n\n%s",
                payment, gain, remainingQuantity, investmentToSell.toString()
            );
        }
    }

    /**
     * Updates the prices of investments interactively.
     * Enables navigation through investments and allows the user to update the price of each investment.
     * 
     * @param index The index of the investment to be updated.
     * @param newPrice The new price to be set for the investment.
     * @return A message indicating the result of the operation.
     */
    public static Investment updatePrices(String symbol, String name, double newPrice) 
    {
        // Check if the investment list is empty
        if (investmentList.isEmpty()) 
        {
            // Return an error message if the investment list is empty
            throw new IllegalStateException("No investments available to update.");
        }

        // Search for the investment matching the symbol and name
        Investment investmentToUpdate = null;
        for (Investment investment : investmentList) 
        {
            if (investment.getSymbol().equalsIgnoreCase(symbol) && investment.getName().equalsIgnoreCase(name)) 
            {
                investmentToUpdate = investment;
                break;
            }
        }

        // Check if the investment was found
        if (investmentToUpdate == null) 
        {
            // Return an error message if the investment is not found
            throw new IllegalArgumentException(String.format("No investment found with Symbol: %s and Name: %s.", symbol, name));
        }

        // Validate the new price
        if (newPrice <= 0) 
        {
            // Return an error message if the price is not positive
            throw new IllegalArgumentException("Price must be a positive number.");
        }

        // Update the price of the investment
        investmentToUpdate.setPrice(newPrice);

        // Return the updated investment
        return investmentToUpdate;
    }

    /**
     * Retrieves the investment at the specified index for displaying its details.
     * 
     * @param index The index of the investment.
     * @return The investment at the specified index, or null if the index is invalid.
     */
    public static Investment getInvestmentAtIndex(int index) 
    {
        // Check if the index is valid
        if (index < 0 || index >= investmentList.size()) 
        {
            // Return null if the index is invalid
            System.err.println("Invalid index: " + index + ". Index must be between 0 and " + (investmentList.size() - 1));
            return null;
        }

        // Return the investment at the specified index
        return investmentList.get(index);
    }

    /**
     * Calculates the total gain for the investment portfolio.
     *
     * @return The total gain for the investment portfolio.
     */
    public static double calculateTotalGain() 
    {
        // Initialize total gain
        double totalGain = 0.0;
        
        // Calculate the total gain for each investment
        for (Investment investment : investmentList) 
        {
            // Calculate the gain for each investment
            double gain = (investment.getPrice() * investment.getQuantity()) - investment.getBookValue();
            totalGain += gain;
        }
        
        // Return the total gain for the investment portfolio
        return totalGain;
    }

    /**
     * Searches for investments based on criteria such as symbol, keywords, and price range.
     *
     * @param symbol     The symbol to search for.
     * @param nameKeywords The keywords to search for in the investment name.
     * @param minPrice   The minimum price for filtering.
     * @param maxPrice   The maximum price for filtering.
     * @return A list of investments matching the search criteria.
     */
    public static List<Investment> searchInvestments(String symbol, String nameKeywords, Double minPrice, Double maxPrice) 
    {
        // Validate the search criteria
        if (minPrice != null && minPrice < 0) 
        {
            // Return an error message if the minimum price is negative
            throw new IllegalArgumentException("Minimum price cannot be negative.");
        }

        if (maxPrice != null && maxPrice < 0) 
        {
            // Return an error message if the maximum price is negative
            throw new IllegalArgumentException("Maximum price cannot be negative.");
        }

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) 
        {
            // Return an error message if the minimum price is greater than the maximum price
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price.");
        }

        // Create a list to store the search results
        List<Investment> results = new ArrayList<>();

        // Iterate over all investments and filter based on search criteria
        for (Investment investment : investmentList) 
        {
            // Check if the investment matches the search criteria
            boolean matchesSymbol = symbol.isEmpty() || investment.getSymbol().equalsIgnoreCase(symbol);
            boolean matchesKeywords = nameKeywords.isEmpty() || investment.getName().toLowerCase().contains(nameKeywords.toLowerCase());
            boolean matchesPrice = (minPrice == null || investment.getPrice() >= minPrice) && (maxPrice == null || investment.getPrice() <= maxPrice);

            // Add the investment to the results if it matches all criteria
            if (matchesSymbol && matchesKeywords && matchesPrice) 
            {
                // Add the investment to the search results
                results.add(investment);
            }
        }

        // Return the list of search results
        return results;
    }

    /**
     * Static method to get a defensive copy of the investment list.
     */
    public static ArrayList<Investment> getInvestments() 
    {
        // Create a new list and copy each investment to the new list
        ArrayList<Investment> copyList = new ArrayList<>();
        
        // Copy each investment to the new list
        for (Investment investment : investmentList) 
        {
            // Copy stock investments
            if (investment instanceof Stock) 
            {
                copyList.add(new Stock((Stock) investment));
            } 
            
            // Copy mutual fund investments
            else if (investment instanceof MutualFund) 
            {
                copyList.add(new MutualFund((MutualFund) investment));
            }
        }

        // Return the copied list
        return copyList;
    }

    /**
     * Stock subclass representing stock investments.
     */
    public static class Stock extends Investment 
    {
        // Constants
        private static final double COMMISSION_FEE = 9.99;

        // Constructor
        public Stock(String symbol, String name, int quantity, double price) 
        {
            super(symbol, name, quantity, price);
        }

        // Copy constructor for Stock
        public Stock(Stock other) 
        {
            super(other);
        }

        /*
         * Calculate the book value for stock investments.
         */
        @Override
        protected double calculateBookValue(int quantity, double price) 
        {
            return quantity * price + COMMISSION_FEE;
        }

        /*
         * Calculate the payment for selling stock investments.
         */
        @Override
        public double calculatePayment(int quantity, double price) 
        {
            double sellingFee = 9.99; // Stock-specific selling fee
            return (price * quantity) - sellingFee;
        }

        /*
         * Calculate the gain for selling stock investments.
         */
        @Override
        public double calculateGain(int quantity, double payment) 
        {
            return payment - (this.bookValue * quantity / this.quantity);
        }

        /*
         * Overridden toString method to display stock investment details.
         */
        @Override
        public String toString() 
        {
            return super.toString();
        }
    }

    /**
     * MutualFund subclass representing mutual fund investments.
     */
    public static class MutualFund extends Investment 
    {
        // Constructor
        public MutualFund(String symbol, String name, int quantity, double price) 
        {
            super(symbol, name, quantity, price);
        }

        // Copy constructor for MutualFund
        public MutualFund(MutualFund other) 
        {
            super(other);
        }

        /*
         * Calculate the book value for mutual fund investments.
         */
        @Override
        protected double calculateBookValue(int quantity, double price) 
        {
            return quantity * price;
        }

        /*
         * Calculate the payment for selling mutual fund investments.
         */
        @Override
        public double calculatePayment(int quantity, double price) 
        {
            double sellingFee = 45.00; // MutualFund-specific selling fee
            return (price * quantity) - sellingFee;
        }

        /*
         * Calculate the gain for selling mutual fund investments.
         */
        @Override
        public double calculateGain(int quantity, double payment) 
        {
            return payment - (this.bookValue * quantity / this.quantity);
        }

        /*
         * Overridden toString method to display mutual fund investment details.
         */
        @Override
        public String toString() 
        {
            return super.toString();
        }
    }
}