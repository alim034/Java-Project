import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class AgeCalculator extends JFrame 
{
    private JTextField dayField, monthField, yearField;
    private JTextArea resultArea;

    public AgeCalculator() 
    {
        setTitle("Age Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JLabel dayLabel = new JLabel("Day:");
        JLabel monthLabel = new JLabel("Month:");
        JLabel yearLabel = new JLabel("Year:");

        dayField = new JTextField(5);
        monthField = new JTextField(5);
        yearField = new JTextField(8);

        JButton calculateButton = new JButton("Calculate Age");
        calculateButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAge();
            }
        });



        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel panel = new JPanel();
        panel.add(dayLabel);
        panel.add(dayField);
        panel.add(monthLabel);
        panel.add(monthField);
        panel.add(yearLabel);
        panel.add(yearField);
        panel.add(calculateButton);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }


    private void calculateAge() 
    {
        try 
        {
            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());

            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();

            Period age = Period.between(birthDate, currentDate);
            resultArea.setText("Your age is: " + age.getYears() + " years, " + age.getMonths() + " months, and " + age.getDays() + " days.");
        } 
        catch (DateTimeParseException | NumberFormatException ex) 
        {
            resultArea.setText("Please enter valid date values.");
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgeCalculator());
    }
}
