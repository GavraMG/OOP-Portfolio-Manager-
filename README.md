# 💼 ePortfolio Management System - Object-Oriented Programming Project

<p align="center">
  <img width="700" alt="ePortfolio Management Screenshot" src="https://github.com/user-attachments/assets/f43767bc-74af-49e2-95cb-2a518dcfe1b0">
</p>

## 📖 Table of Contents

- [About My Project](#-about-my-project)
  - [Features](#-features)
  - [Tech Used](#-tech-used)
  - [How to Run](#-how-to-run)
  - [How It Works](#-how-it-works)
- [Contact](#-contact)

<br/>

## About My Project
The ePortfolio Management System is a Java-based project that allows users to manage their investment portfolio. Designed as a hands-on Object-Oriented Programming (OOP) project, it enables users to buy, sell, update, and track investments like stocks and mutual funds, as well as calculate total gains. The system provides a comprehensive way for users to interact with their financial portfolio through a Graphical User Interface (GUI).

Built as part of my Year 2 OOP coursework, this project incorporates advanced features such as HashMap indexing for faster searches and file I/O to save and reload portfolios, showcasing my proficiency with Java and GUI development.

<br/>

## 🚀 Features

- **Buy and Sell Investments:** Users can add or sell stocks and mutual funds to maintain their financial portfolio.
- **Update Prices:** Ability to update investment prices, ensuring the portfolio reflects current market conditions.
- **Search Investments:** Fast and intuitive search using HashMaps for keyword and criteria-based filtering.
- **Total Gain Calculation:** Calculate and display the total gain from all investments, giving users a clear view of their financial performance.
- **Save and Load Portfolio:** Users can save their portfolio to a text file upon exiting and reload it when starting the application again, ensuring persistence.
- **User-Friendly GUI:** Built using Java Swing, the system provides a clean, intuitive interface to manage investments.

<br/>

## 💻 Tech Used

This program uses:

- **Java:** The core programming language used to build the portfolio management logic.
- **Java Swing:** Used to create the Graphical User Interface for user interaction.
- **Object-Oriented Principles:** Features like inheritance and polymorphism are utilized to create the `Investment` superclass and its subclasses (`Stock` and `MutualFund`).
- **File I/O:** For saving and loading the user's portfolio data between sessions.
- **HashMap:** Used for efficient searching of investments.

<br/>

## 🛠️ How to Run

1. **Build the Program**
    - Make sure you have Java Development Kit (JDK) installed.
    - Navigate to the root folder of the program and compile using:
      ```sh
      javac -d bin ePortfolio/*.java
      ```
    - This command compiles all Java files and places the compiled output into the `bin` directory.

2. **Run the Program**
    - Start the application using the following command from the root folder:
      ```sh
      java -cp bin ePortfolio.ePortfolio
      ```
    - This command will launch the ePortfolio Management System.

3. **Clean the Program**
    - To clean the compiled files, run the following command:
      ```sh
      rm -rf bin/*
      ```

4. **Generate Javadoc**
    - You can generate the program's documentation using:
      ```sh
      javadoc -d a3doc ePortfolio
      ```
    - The generated Javadoc will be saved in the `a3doc` folder.

<br/>

## 📝 How It Works

The ePortfolio Management System is controlled through a command menu, with the following commands available to navigate and operate within the program:

- **Buy Investment:** Allows the user to add a new investment (either stock or mutual fund) to their portfolio. This can be accessed by selecting the "Buy Investment" option from the Commands menu.
- **Sell Investment:** Users can sell a portion or the entirety of an existing investment by selecting the "Sell Investment" option.
- **Update Prices:** This feature lets users update the current prices of all investments. By choosing the "Update Investment" option, users can navigate through their portfolio and modify the investment prices.
- **Get Total Gain:** Selecting "Get Total Gain" will calculate and display the total gain from all investments in the portfolio.
- **Search Investments:** Users can search for specific investments by selecting the "Search Investments" option. The search can be performed using criteria such as symbol, keywords, and price range.
- **Save Investments:** The program will prompt users to save their portfolio before exiting to ensure that all changes are preserved.
- **Quit:** Users can exit the program by selecting the "Exit" option in the menu. This ensures that all data is saved to the specified file before closing.

These commands can be selected from the GUI menu bar, making navigation straightforward and intuitive. The program prompts for any additional information as needed to complete the actions effectively.

<br/>

## ✉️ Contact

Got any questions? Feel free to reach out!

- [Email](mailto:markusgavra@gmail.com)
- [LinkedIn](https://www.linkedin.com/in/markus-gavra)

