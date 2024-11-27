import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil extends JFrame {

    public Accueil() {
        
        setTitle("Accueil");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); 
       
       

        JButton ajouterClientButton = new JButton("Ajouter un client");
       
        JButton afficherClientsButton = new JButton("Afficher les clients");
    
        ajouterClientButton.setBounds(50, 30, 200, 30);
        afficherClientsButton.setBounds(50, 70, 200, 30);

        
        add(ajouterClientButton);
        add(afficherClientsButton);

       
        ajouterClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
               dispose();
               new ajoutclient();
            }
        });

        afficherClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                dispose();
                new affclient();
            }
        });
        setVisible(true);
    }
}
