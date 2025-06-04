package app;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;

public class TeacherMenuForm extends JFrame {

    public TeacherMenuForm() {
        setTitle("Μενού Εκπαιδευτών");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/groups.png")));
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JButton btnInsert = new JButton("Εισαγωγή Εκπαιδευτή");
        btnInsert.setBounds(100, 50, 220, 40);
        getContentPane().add(btnInsert);

        JButton btnManage = new JButton("Αναζήτηση / Διαχείριση Εκπαιδευτή");
        btnManage.setBounds(100, 120, 220, 40);
        getContentPane().add(btnManage);
        
        JButton btnBack = new JButton("Πίσω");
        btnBack.setBounds(280, 200, 100, 30);
        btnBack.addActionListener(e -> {
            dispose(); // Κλείνει το τρέχον παράθυρο
            new StartForm().setVisible(true); // Ανοίγει το προηγούμενο
        });
        getContentPane().add(btnBack);
        // Άνοιγμα Insert Form
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new InsertTeacherForm().setVisible(true);
                dispose();
            }
        });

        // Άνοιγμα Update/Delete Form
        btnManage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageTeacherForm().setVisible(true);
                dispose();
            }
        });
       
    }
}
