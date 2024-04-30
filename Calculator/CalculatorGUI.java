package Calculator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class CalculatorGUI extends JFrame 
{


    private JTextField inputField;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton clearButton;
    private JButton equalsButton;
    private double firstNumber;
    private String operation;


    public CalculatorGUI() 
    {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);

        initializeComponents();
        setLayout();

        setVisible(true);
    }


    private void initializeComponents() 
    {
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 24));
        inputField.setEditable(false);

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) 
        {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            int number = i;
            numberButtons[i].addActionListener(e -> inputField.setText(inputField.getText() + number));
        }


        operationButtons = new JButton[4];
        operationButtons[0] = new JButton("+");
        operationButtons[1] = new JButton("-");
        operationButtons[2] = new JButton("*");
        operationButtons[3] = new JButton("/");

        for (JButton button : operationButtons) 
        {
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(e -> {
                firstNumber = Double.parseDouble(inputField.getText());
                operation = button.getText();
                inputField.setText("");
            });
        }


        clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 18));
        clearButton.addActionListener(e -> {
            inputField.setText("");
            firstNumber = 0;
            operation = null;
        });

        equalsButton = new JButton("=");
        equalsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        equalsButton.addActionListener(e -> {
            double secondNumber = Double.parseDouble(inputField.getText());
            double result = performOperation(firstNumber, secondNumber, operation);
            inputField.setText(String.valueOf(result));
            operation = null;
        });
    }


    
    private double performOperation(double num1, double num2, String operation) 
    {
        
        switch (operation) 
        {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
                    return 0;
                }
            default:
                return num2;
        }
    }


    private void setLayout() 
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));



        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(operationButtons[0]);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(operationButtons[1]);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(operationButtons[2]);
        buttonPanel.add(clearButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equalsButton);
        buttonPanel.add(operationButtons[3]);




        setLayout(new BorderLayout());
        add(inputField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }


    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
}
