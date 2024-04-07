 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Package extends JFrame {
    private JLabel label;
    private JLabel senderLabel;
    private JLabel recipientLabel;
    private JLabel addfeeLabel;
    private JLabel costLabel;
    private JTextField weightField;
    private JTextField addfeeField;
    private JTextField costField;
    private JTextField senderField;
    private JTextField recipientField;
    private JRadioButton standardRadio;
    private JRadioButton twoDayRadio;
    private JRadioButton overnightRadio;
    private JButton calculateButton;
    private ButtonGroup packageGroup;

    public Package() {
        // Set the frame properties
        setTitle("C & D Express");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        senderLabel = new JLabel("Sender:");
        recipientLabel = new JLabel("Recipient:");
        label = new JLabel("Weight (in pounds):");
        costLabel = new JLabel("Cost:");
        addfeeLabel = new JLabel("Flat/Additional Fee:");
        weightField = new JTextField(10);
        addfeeField = new JTextField(10);
        costField = new JTextField(10);
        senderField = new JTextField(10);
        recipientField = new JTextField(10);
        standardRadio = new JRadioButton("Standard Package");
        twoDayRadio = new JRadioButton("Two-Day Package");
        overnightRadio = new JRadioButton("Overnight Package");
        calculateButton = new JButton("Calculate");

        // Group the radio buttons
        packageGroup = new ButtonGroup();
        packageGroup.add(standardRadio);
        packageGroup.add(twoDayRadio);
        packageGroup.add(overnightRadio);

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        // Add components to the frame
        add(label);
        add(weightField);
        add(costLabel);
        add(costField);
        add(addfeeLabel);
        add(addfeeField);

        // Package type radio buttons
        JPanel packagePanel = new JPanel();
        packagePanel.add(standardRadio);
        packagePanel.add(twoDayRadio);
        packagePanel.add(overnightRadio);
        add(packagePanel);

        // Sender information
        JPanel senderPanel = new JPanel();
        senderPanel.add(senderLabel);
        senderPanel.add(senderField);
        add(senderPanel);

        // Recipient information
        JPanel recipientPanel = new JPanel();
        recipientPanel.add(recipientLabel);
        recipientPanel.add(recipientField);
        add(recipientPanel);

        add(calculateButton);

        // Add action listener to the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCost();
            }
        });

        // Add item listener to radio buttons
        standardRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Hide addfeeField when Standard Package is selected
                addfeeField.setVisible(false);
            }
        });

        twoDayRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Show addfeeField when Two-Day Package is selected
                addfeeField.setVisible(twoDayRadio.isSelected());
            }
        });

        overnightRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Show addfeeField when Overnight Package is selected
                addfeeField.setVisible(overnightRadio.isSelected());

            }
        });

        // Add item listener to "Standard Package" radio button separately
		standardRadio.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        // Show addfeeField when Standard Package is selected
		        addfeeField.setVisible(standardRadio.isSelected());
		    }
		});

    }
private void calculateCost() {
    try {
        // Get the weight from the text field
        double weight = Double.parseDouble(weightField.getText());
        double cost = Double.parseDouble(costField.getText());

        // Check if weight and cost are non-negative
        if (weight < 0 || cost < 0) {
            JOptionPane.showMessageDialog(this, "Weight and cost must be non-negative.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get sender and recipient information
        String sender = senderField.getText();
        String recipient = recipientField.getText();

        // Define the rate per pound based on the selected package type
        double CC;
        if (standardRadio.isSelected()) {
            CC = weight * cost;
        } else if (twoDayRadio.isSelected()) {
            // Parse addfeeField only when necessary
            double flatfee = addfeeField.isVisible() ? Double.parseDouble(addfeeField.getText()) : 0.0;
            CC = weight * cost + flatfee;
        } else if (overnightRadio.isSelected()) {
            // Parse addfeeField only when necessary
            double flatfee = addfeeField.isVisible() ? Double.parseDouble(addfeeField.getText()) : 0.0;
            CC = weight * cost + flatfee * weight;
        } else {
            JOptionPane.showMessageDialog(this, "Please select a package type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
           // Display the result in a dialog
           String resultMessage = String.format(
                   "Sender: %s\nRecipient: %s\nPackage Type: %s\nPackage Weight: %.2f\nTotal Cost: %.2f",
                   sender, recipient, getSelectedPackageType(), weight, CC
           );
           JOptionPane.showMessageDialog(this, resultMessage);
       } catch (NumberFormatException ex) {
           // Handle invalid input
           JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for package weight, cost, and additional fee.", "Error", JOptionPane.ERROR_MESSAGE);
       }
   }

    private void resetState() {
        // Reset text fields
        weightField.setText("");
        addfeeField.setText("");
        costField.setText("");
        senderField.setText("");
        recipientField.setText("");

        // Reset radio buttons
        packageGroup.clearSelection();

        // Reset visibility of addfeeLabel and addfeeField
        addfeeLabel.setVisible(false);
        addfeeField.setVisible(false);
    }

    private String getSelectedPackageType() {
        if (standardRadio.isSelected()) {
            return "Standard Package";
        } else if (twoDayRadio.isSelected()) {
            return "Two-Day Package";
        } else {
            return "Overnight Package";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Package().setVisible(true);
            }
        });
    }
}
