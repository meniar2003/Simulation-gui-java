import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class affclient extends JFrame {

    private JComboBox<String> specialtyComboBox, clubComboBox;
    private JTable clientTable;

    public affclient() {
        
        setTitle("Afficher les clients");
        ImageIcon show = new ImageIcon("C:\\Users\\Menyar\\Desktop\\projet JAVA\\projet\\show.png");
        setIconImage(show.getImage());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JLabel specialtyLabel = new JLabel("Specialité:");
        JLabel clubLabel = new JLabel("Club:");

        String[] specialties = {"", "informatique", "gestion", "comptabilité"};
        specialtyComboBox = new JComboBox<>(specialties);

        String[] clubs = {"", "sport", "musique", "danse"};
        clubComboBox = new JComboBox<>(clubs);

        JButton afficherButton = new JButton("Afficher les clients");

        clientTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(clientTable);

        setLayout(null);
        specialtyLabel.setBounds(10, 10, 80, 25);
        specialtyComboBox.setBounds(100, 10, 150, 25);
        clubLabel.setBounds(10, 40, 80, 25);
        clubComboBox.setBounds(100, 40, 150, 25);
        afficherButton.setBounds(10, 70, 200, 30);
        scrollPane.setBounds(10, 110, 480, 200);

        
        add(specialtyLabel);
        add(specialtyComboBox);
        add(clubLabel);
        add(clubComboBox);
        add(afficherButton);
        add(scrollPane);


        afficherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayClients();
            }
        });
JButton backButton = new JButton("Back");


backButton.setBounds(220, 70, 100, 30);
add(backButton);


backButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose(); 
        new Accueil();
    }
});
        
        setVisible(true);
    }

    private void displayClients() {
        String selectedSpecialty = (String) specialtyComboBox.getSelectedItem();
        String selectedClub = (String) clubComboBox.getSelectedItem();

        try {
            
            String url = "jdbc:mysql://localhost:3306/entreprise";
            String username = "root";
            String password = "manou123";
            Connection connection = DriverManager.getConnection(url, username, password);

           
            String sql = "SELECT * FROM client WHERE (? = '' OR spec = ?) AND (? = '' OR club = ?)";

            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, selectedSpecialty);
                preparedStatement.setString(2, selectedSpecialty);
                preparedStatement.setString(3, selectedClub);
                preparedStatement.setString(4, selectedClub);

                
                ResultSet resultSet = preparedStatement.executeQuery();
                DefaultTableModel model = new DefaultTableModel();

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    model.addColumn(metaData.getColumnLabel(columnIndex));
                }

                
                while (resultSet.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        rowData[columnIndex - 1] = resultSet.getObject(columnIndex);
                    }
                    model.addRow(rowData);
                }

                
                clientTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
}
