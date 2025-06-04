package app;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Toolkit;

public class ManageTeacherForm extends JFrame {
    private JTextField txtSearch, txtId, txtFname, txtLname;

    public ManageTeacherForm() {
        setTitle("Διαχείριση Εκπαιδευτή");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/manage_accounts.png")));
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblSearch = new JLabel("Επώνυμο:");
        lblSearch.setBounds(30, 20, 100, 25);
        getContentPane().add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(120, 20, 150, 25);
        getContentPane().add(txtSearch);

        JButton btnSearch = new JButton("Αναζήτηση");
        btnSearch.setBounds(280, 20, 120, 25);
        getContentPane().add(btnSearch);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 70, 100, 25);
        getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 70, 150, 25);
        txtId.setEnabled(false); // Δεν επιτρέπεται αλλαγή ID
        getContentPane().add(txtId);

        JLabel lblFname = new JLabel("Όνομα:");
        lblFname.setBounds(30, 110, 100, 25);
        getContentPane().add(lblFname);

        txtFname = new JTextField();
        txtFname.setBounds(120, 110, 150, 25);
        getContentPane().add(txtFname);

        JLabel lblLname = new JLabel("Επώνυμο:");
        lblLname.setBounds(30, 150, 100, 25);
        getContentPane().add(lblLname);

        txtLname = new JTextField();
        txtLname.setBounds(120, 150, 150, 25);
        getContentPane().add(txtLname);

        JButton btnUpdate = new JButton("Ενημέρωση");
        btnUpdate.setBounds(30, 185, 120, 30);
        getContentPane().add(btnUpdate);

        JButton btnDelete = new JButton("Διαγραφή");
        btnDelete.setBounds(160, 185, 120, 30);
        getContentPane().add(btnDelete);
        
        JButton btnBack = new JButton("Πίσω");
        btnBack.setBounds(300, 185, 100, 25);
        btnBack.addActionListener(e -> {
            dispose();
            new TeacherMenuForm().setVisible(true);
        });
        getContentPane().add(btnBack);
        
        JLabel lblNewLabel = new JLabel("*Για να ενημερώσεις ένα στοιχείο, επεξεργάσου το όνομα ή το επώνυμο και πάτα 'Ενημέρωση'");
        lblNewLabel.setForeground(Color.DARK_GRAY);
        lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 9));
        lblNewLabel.setBounds(20, 215, 450, 20);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("*Για διαγραφή κάνε αναζήτηση με το επώνυμο και πάτα 'Διαγραφή'");
        lblNewLabel_1.setForeground(Color.DARK_GRAY);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 9));
        lblNewLabel_1.setBounds(20, 235, 450, 20);
        getContentPane().add(lblNewLabel_1);
        
        // Events
        btnSearch.addActionListener(e -> searchTeacher());
        btnUpdate.addActionListener(e -> updateTeacher());
        btnDelete.addActionListener(e -> deleteTeacher());
    }

    private void searchTeacher() {
        String lname = txtSearch.getText();

        if (lname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Πληκτρολογήστε επώνυμο για αναζήτηση.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM TEACHERS WHERE TEACHER_LNAME LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + lname + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtId.setText(String.valueOf(rs.getInt("TEACHER_ID")));
                txtFname.setText(rs.getString("TEACHER_FNAME"));
                txtLname.setText(rs.getString("TEACHER_LNAME"));
            } else {
                JOptionPane.showMessageDialog(this, "❌ Δεν βρέθηκε εκπαιδευτής με αυτό το επώνυμο.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Σφάλμα αναζήτησης: " + e.getMessage());
        }
    }

    private void updateTeacher() {
        String id = txtId.getText();
        String fname = txtFname.getText();
        String lname = txtLname.getText();

        if (id.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Όλα τα πεδία πρέπει να είναι συμπληρωμένα.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE TEACHERS SET TEACHER_FNAME=?, TEACHER_LNAME=? WHERE TEACHER_ID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setInt(3, Integer.parseInt(id));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Τα στοιχεία ενημερώθηκαν επιτυχώς.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Αποτυχία ενημέρωσης.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Σφάλμα ενημέρωσης: " + e.getMessage());
        }
    }

    private void deleteTeacher() {
        String id = txtId.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Καμία εγγραφή για διαγραφή.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Θέλετε σίγουρα να διαγράψετε τον εκπαιδευτή;", "Επιβεβαίωση", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM TEACHERS WHERE TEACHER_ID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Ο εκπαιδευτής διαγράφηκε.");
                txtId.setText("");
                txtFname.setText("");
                txtLname.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Δεν βρέθηκε εκπαιδευτής για διαγραφή.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Σφάλμα διαγραφής: " + e.getMessage());
        }
    }
}

