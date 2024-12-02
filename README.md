# ePortfolio Management System - Object-Oriented Programming (Year 2) A2 Version 2.0

## Overview of my Assignment
    - Using java I updated my ePortfolio Management System and built version 2.0 which builds on the foundation of version 1.0. It was orginally designed to help the user manage      
      their investment portfolio. The user could buy / add, sell, and update different types of investments, specifically stocks and mutual funds. The program also provides functionality to calculate and display the total gain of the investments, allowing users to have an accurate overview of their financial portfolio. The key things changed in version 2.0 was that I took the stock and mutualfund files and made a united file called "Investment.java" which is built to hold all investment in one arraylist and is spilt into three class which are one super class, a stock subclass, and a mutualfund subclass. I also implemented a loading and saving txt file feature where user now can have there portfolios saved to a txt file of there choosing after the program is ended and then can have them reloaded automatically when they start the program up again. I also reworked the search feature of the program to use hashmaps to speed up the process when searching for specific investments within the portfolio

## User Guide (Building and Running the Program)

## Building the Program
    
    - Make sure that you have Java Development Kit (JDK) installed on your system
    
    - To compile the Java files you need to navigate to the root folder of the program and running:
    
    - javac -d bin ePortfolio/*.java

    - This command will compile all Java source files and place the compiled files into the bin directory

## Running the Program

    - To run the ePortfolio Management System, use the following command from the root folder:

    - java -cp bin ePortfolio.ePortfolio

    - This command will start the program and display the main menu

## Cleaning the Program

    - To clean the  ePortfolio Management System program, use the following command from the root folder:
    
    - rm -rf bin/*

    - This command will clean the bin 

## Running Javadoc for the Program

    - To run the Javadoc for ePortfolio Management System, use the following command from the root folder:
    
    - javadoc -d a3doc ePortfolio

    - This command will create the Javadoc and put it into a folder called "a3doc"

## Using the Program
    - The program presents a menu with options to:
    - Buying Investment: Add a new stock or mutual fund to your portfolio.
    - Selling Investment: Sell a specific investment, either entirely or partially.
    - Update Prices: Update the price of all investments in your portfolio.
    - Get Total Gain: Calculate the total gain of your investments.
    - Search Investments: Search for investments based on certain criteria.
    - Save Investments: User can save their portfolio before exiting the program
    - Quit: Exit the program.

    - The user can select an option by typing the corresponding command (e.g., buy, sell, etc.)
    - The program will prompt for additional information as required to complete each action 

## Test Plan

### Buy Investment
   
   **Objective:** 
        - Verify that the user can add investments to the portfolio with correct input handling.
    
   **Conditions to Test:**
        1. **Valid Investment Type:** 
            - Test with "stock" and "mutualfund" (case-insensitive).
        2. **Invalid Investment Type:** 
            - Test with invalid types (e.g., "crypto", "realestate"), expecting a warning message.
        3. **Duplicate Symbol:** 
            - Attempt to add an investment with a symbol that already exists, and ensure the system prompts to add more quantity or cancel.
        4. **Quantity and Price Validation:** 
            - Valid quantity and price values.
            - Negative or zero values for quantity and price (should display an error message).
        5. **Boundary Conditions for Price:** 
            - Ensure `Helper.isValidPrice` function correctly validates boundary conditions (e.g., "-50.00", "50.00-", "10.00-50.00").

   **Expected Result:** 
        - Investments are added successfully when inputs are valid. Warnings are displayed for invalid types, duplicate symbols, and invalid quantity/price values.

### Sell Investment
   
   **Objective:** 
        - Verify that the user can sell investments and that quantity is updated or removed if zero.

   **Conditions to Test:**
        1. **Valid Investment Type:** 
            - Test with "stock" and "mutualfund" (case-insensitive).
        2. **Invalid Investment Type:** 
            - Test with invalid types (e.g., "crypto", "realestate"), expecting a warning message.
        3. **Invalid Symbol:** 
            - Try selling an investment with a symbol not in the portfolio, expecting an error message.
        4. **Quantity to Sell:**
            - Sell partial quantity (ensure quantity updates correctly).
            - Sell the exact quantity owned (investment should be removed).
            - Sell more than the quantity owned (expect an error).
        5. **Boundary Conditions:** 
            - Handle zero or negative values for quantity.

   **Expected Result:** 
        - Investment is sold successfully when inputs are valid, with accurate updates to quantity or removal from the portfolio. Invalid types, symbols, or quantities trigger  
          appropriate warnings.


### Update Prices

   **Objective:** 
        - Ensure users can update prices of existing investments.

   **Conditions to Test:**
        1. **Valid Price Update:** 
            - Update with a positive, valid price.
        3. **Invalid Price:** 
            - Test with negative, zero, or non-numeric prices, expecting an error message.
        4. **Empty Portfolio:** 
            - Try to update prices when there are no investments, expecting an appropriate message.

   **Expected Result:** 
        - Prices are updated accurately for valid inputs, while invalid inputs prompt warnings.

### Get Total Gain

   **Objective:** 
        - Verify that total gain calculation works as expected.

   **Conditions to Test:**
        1. **Non-Empty Portfolio:** 
            - Calculate total gain for a portfolio with multiple investments.
        2. **Empty Portfolio:** 
            - Calculate total gain when there are no investments (should display a message indicating zero gain).
        3. **Boundary Case:** 
            - Test when only one investment is in the portfolio.

   **Expected Result:** 
        - The total gain is calculated accurately for non-empty portfolios and displays zero or an appropriate message for empty portfolios.

### Search Investments

   **Objective:** 
        - Ensure users can search for investments based on symbol, keywords, and price range.

   **Conditions to Test:**
        1. **Symbol Search:**
            - Valid symbol that exists in the portfolio.
            - Symbol that does not exist in the portfolio.
        2. **Keyword Search:**
            - Investment name with multiple keywords (e.g., "Toronto Bank") in different positions:
                - Keyword(s) not found.
                - Keyword at the start of the investment name.
                - Keyword at the end of the investment name.
                - Keyword(s) in between the start and end of the investment name.
        3. **Price Range Search:**
            - Exact price.
            - Range with min and max prices (e.g., "10.00-50.00").
            - Only min price specified (e.g., "10.00-").
            - Only max price specified (e.g., "-50.00").
            - Price range where no investments fall within the range.
        4. **Combined Search:** 
            - Symbol, keywords, and price range combined.

   **Expected Result:** 
        - Investments matching the search criteria are displayed correctly, and no results are shown if no investments match.

### Quit Command

   **Save Portfolio**

   **Objective:** 
        - Verify that investments are saved correctly to a file.
        - Ensure the program exits gracefully when the user wishes to quit.

   **Conditions to Test:**
        1. **Valid File Name:** Save with a valid file name.
        2. **Invalid File Name:** Attempt to save with invalid file names or paths (e.g., special characters, restricted paths).
        3. **Non-Empty Portfolio:** Save when the portfolio has investments.
        4. **Empty Portfolio:** Save when there are no investments (file should still be created but empty).
        5. Accept reasonable variations of the quit command (e.g., "q", "Q", "Quit", "quit", "QUIT").
        6. Reject unreasonable inputs like "bye", "exit", or any unrelated words, and display a warning message.

   **Expected Result:** 
        - Portfolio saves successfully for valid file names. Invalid file names trigger an error message.
        - Program exits on valid inputs; for invalid inputs, a warning is displayed and user is prompted to try again.