import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ajoutclient extends JFrame {

    private JTextField firstNameField, lastNameField;
    private JComboBox<String> specialtyComboBox, clubComboBox;

    public ajoutclient() {
        
        setTitle("ajouter un client");
        ImageIcon add = new ImageIcon("C:\\Users\\Menyar\\Desktop\\projet JAVA\\projet\\add.png");
        setIconImage(add.getImage());
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel firstNameLabel = new JLabel("Nom");
        JLabel lastNameLabel = new JLabel("Prenom");
        JLabel specialtyLabel = new JLabel("Specialité:");
        JLabel clubLabel = new JLabel("Club:");

        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);

        String[] specialties = {"choix de specialités","informatique", "gestion", "comptabilité"};
        specialtyComboBox = new JComboBox<>(specialties);

        String[] clubs = {"choix du clubs","sport", "musique", "danse"};
        clubComboBox = new JComboBox<>(clubs);

        JButton submitButton = new JButton("Submit");
        JButton resetButton = new JButton("Reset");
        JButton backButton = new JButton("Back");

        
        setLayout(null);

        // Set bounds for each component
        firstNameLabel.setBounds(10, 10, 80, 25);
        firstNameField.setBounds(100, 10, 150, 25);
        lastNameLabel.setBounds(10, 40, 80, 25);
        lastNameField.setBounds(100, 40, 150, 25);
        specialtyLabel.setBounds(10, 70, 80, 25);
        specialtyComboBox.setBounds(100, 70, 150, 25);
        clubLabel.setBounds(10, 100, 80, 25);
        clubComboBox.setBounds(100, 100, 150, 25);
        submitButton.setBounds(10, 130, 80, 25);
        resetButton.setBounds(100, 130, 80, 25);
        backButton.setBounds(190, 130, 80, 25);

        // Add components to the frame
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(specialtyLabel);
        add(specialtyComboBox);
        add(clubLabel);
        add(clubComboBox);
        add(submitButton);
        add(resetButton);
        add(backButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                if (validateFields()) {
                    insertIntoDatabase();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

         resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                resetForm();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                dispose(); 
                new Accueil();
          }
        });
        // Display the frame
        setVisible(true);
    }

    private boolean validateFields() {
        // Check if all fields are filled
        return !firstNameField.getText().isEmpty() &&
                !lastNameField.getText().isEmpty() &&
                specialtyComboBox.getSelectedIndex() != 0 &&
                clubComboBox.getSelectedIndex() != 0;
    }

    private void insertIntoDatabase() {
        try {
        
            String url = "jdbc:mysql://localhost:3306/entreprise";
            String username = "root";
            String password = "manou123";
            Connection connection = DriverManager.getConnection(url, username, password);

            // SQL query to insert data into the 'client' table
            String sql = "INSERT INTO client (nom, prenom, spec, club) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, firstNameField.getText());
                preparedStatement.setString(2, lastNameField.getText());
                preparedStatement.setString(3, (String) specialtyComboBox.getSelectedItem());
                preparedStatement.setString(4, (String) clubComboBox.getSelectedItem());

               
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if the insertion was successful
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Client added successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add client.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    


       

        
    }

   

    private void resetForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        specialtyComboBox.setSelectedIndex(0);
        clubComboBox.setSelectedIndex(0);
    }

    
}


