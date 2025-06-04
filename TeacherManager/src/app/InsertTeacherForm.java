package app;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.Toolkit;

public class InsertTeacherForm extends JFrame {
    private JTextField txtId;
    private JTextField txtFname;
    private JTextField txtLname;

    public InsertTeacherForm() {
        setTitle("Εισαγωγή Εκπαιδευτή");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/person_add.png")));
        setBounds(100, 100, 500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(50, 50, 100, 25);
        getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 50, 200, 25);
        getContentPane().add(txtId);

        JLabel lblFname = new JLabel("Όνομα:");
        lblFname.setBounds(50, 100, 100, 25);
        getContentPane().add(lblFname);

        txtFname = new JTextField();
        txtFname.setBounds(150, 100, 200, 25);
        getContentPane().add(txtFname);

        JLabel lblLname = new JLabel("Επώνυμο:");
        lblLname.setBounds(50, 150, 100, 25);
        getContentPane().add(lblLname);

        txtLname = new JTextField();
        txtLname.setBounds(150, 150, 200, 25);
        getContentPane().add(txtLname);

        JButton btnSave = new JButton("Αποθήκευση");
        btnSave.setBounds(150, 200, 120, 35);
        getContentPane().add(btnSave);
        
        JButton btnBack = new JButton("Πίσω");
        btnBack.setBounds(280, 200, 100, 30);
        btnBack.addActionListener(e -> {
            dispose();
            new TeacherMenuForm().setVisible(true);
        });
        getContentPane().add(btnBack);
        
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertTeacher();
            }
        });
    }

    private void insertTeacher() {
        String id = txtId.getText();
        String fname = txtFname.getText();
        String lname = txtLname.getText();

        if (id.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Όλα τα πεδία είναι υποχρεωτικά.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO TEACHERS (TEACHER_ID, TEACHER_FNAME, TEACHER_LNAME) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            stmt.setString(2, fname);
            stmt.setString(3, lname);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Ο εκπαιδευτής αποθηκεύτηκε επιτυχώς!");
                // Καθαρίζουμε τα πεδία
                txtId.setText("");
                txtFname.setText("");
                txtLname.setText("");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "❌ Υπάρχει ήδη εκπαιδευτής με αυτό το ID.", "Προσοχή", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Το ID πρέπει να είναι αριθμός.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Σφάλμα κατά την εισαγωγή: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
}
