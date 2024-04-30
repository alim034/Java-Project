package ATM;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


class BankAccount 
{
    private double balance;

    public BankAccount(double initialBalance) 
    {
        this.balance = initialBalance;
    }

    public double getBalance() 
    {
        return balance;
    }

    public void deposit(double amount) 
    {
        balance += amount;
    }

    public boolean withdraw(double amount) 
    {
        if (amount <= balance) 
        {
            balance -= amount;
            return true;
        } 
        else 
        {
            return false;
        }
    }
}


public class ATMInterface extends JFrame 
{
    private BankAccount userAccount;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea messageArea;


    public ATMInterface(double initialBalance) 
    {
        userAccount = new BankAccount(initialBalance);

        setTitle("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        balanceLabel = new JLabel("Account Balance: $" + userAccount.getBalance());

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountField = new JTextField(10);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                withdrawAmount();
            }
        });


        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                depositAmount();
            }
        });


        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });


        messageArea = new JTextArea(8, 30);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        panel.add(balanceLabel);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(checkBalanceButton);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }



    private void withdrawAmount() 
    {
        double amount = getAmountFromField();

        if (amount <= 0) 
        {
            showMessage("Please enter a valid withdrawal amount.");
        } else if (userAccount.withdraw(amount)) 
        {
            showMessage("Withdrawal successful. New balance: $" + userAccount.getBalance());
        } 
        else 
        {
            showMessage("Insufficient balance for withdrawal.");
        }
    }



    private void depositAmount() 
    {
        double amount = getAmountFromField();

        if (amount <= 0) 
        {
            showMessage("Please enter a valid deposit amount.");
        } 
        else 
        {
            userAccount.deposit(amount);
            showMessage("Deposit successful. New balance: $" + userAccount.getBalance());
        }
    }



    private void checkBalance() 
    {
        showMessage("Current balance: $" + userAccount.getBalance());
    }


    private double getAmountFromField() 
    {
        try 
        {
            return Double.parseDouble(amountField.getText());
        } 
        catch (NumberFormatException e) 
        {
            return -1; // Invalid input
        }
    }


    private void showMessage(String message) 
    {
        messageArea.setText(messageArea.getText() + message + "\n");
    }

    
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new ATMInterface(1000.0));
    }
}


