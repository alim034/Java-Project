package WordCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordCounter extends JFrame 
{
    private JTextArea inputArea;
    private JButton countButton;
    private JLabel resultLabel;

    public WordCounter() 
    {
        setTitle("Word Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        inputArea = new JTextArea(10, 30);
        countButton = new JButton("Count Words");
        resultLabel = new JLabel();

        countButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                countWords();
            }
        });

        JPanel panel = new JPanel();
        panel.add(inputArea);
        panel.add(countButton);
        panel.add(resultLabel);
        add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setVisible(true);
    }

    private void countWords() 
    {
        String text = inputArea.getText();
        // Split the string into words using space or punctuation as delimiters
        String[] words = text.split("\\s+|\\p{Punct}");

        // Initialize a counter variable to keep track of the number of words
        int wordCount = 0;
        for (String word : words) 
        {
            if (!word.isEmpty()) 
            {
                wordCount++;
            }
        }

        // Display the total count of words to the user
        resultLabel.setText("Total number of words: " + wordCount);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new WordCounter());
    }
}
