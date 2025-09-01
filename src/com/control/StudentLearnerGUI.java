package com.controll;

import com.model.*;
import com.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentLearnerGUI extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final StudentService studentService = new StudentService();

    public StudentLearnerGUI() {
        setTitle("🌟 Self Learning Tracker System 🌟");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // सभी स्क्रीन पैनल बनाएं
        mainPanel.add(createWelcomePanel(), "welcome");
        mainPanel.add(createRegisterPanel(), "register");
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createDashboardPanel(), "dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("WELCOME TO SELF LEARNING TRACKER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        JButton registerButton = new JButton("🚀 Register");
        JButton loginButton = new JButton("🔑 Login");
        JButton exitButton = new JButton("❌ Exit");

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        registerButton.setFont(buttonFont);
        loginButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        exitButton.addActionListener(e -> System.exit(0));

        gbc.gridy = 0; gbc.gridwidth = 2; panel.add(titleLabel, gbc);
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panel.add(registerButton, gbc);
        gbc.gridx = 1; panel.add(loginButton, gbc);
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(exitButton, gbc);
        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField nameField = new JTextField(25);
        JTextField emailField = new JTextField(25);
        JPasswordField passwordField = new JPasswordField(25);
        JTextField phoneField = new JTextField(25);
        JTextField instituteField = new JTextField(25);
        JTextField goalField = new JTextField(25);
        JTextField targetDescField = new JTextField(25);
        JTextField dateField = new JTextField("YYYY-MM-DD", 25);
        JButton registerButton = new JButton("Register Now");
        JButton backButton = new JButton("Back to Welcome");

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);
        gbc.gridy = ++y; gbc.gridx = 0; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);
        gbc.gridy = ++y; gbc.gridx = 0; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);
        gbc.gridy = ++y; gbc.gridx = 0; panel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1; panel.add(phoneField, gbc);
        gbc.gridy = ++y; gbc.gridx = 0; panel.add(new JLabel("Institute Name:"), gbc);
        gbc.gridx = 1; panel.add(instituteField, gbc);
        gbc.gridy = ++y; gbc.gridx = 0; panel.add(new JLabel("Your Goal:"), gbc);
        gbc.gridx = 1; panel.add(goalField, gbc);
        gbc.gridy = ++y; gbc.gridx = 0; panel.add(new JLabel("Goal Description:"), gbc);
        gbc.gridx = 1; panel.add(targetDescField, gbc);
        gbc.gridy = ++y; gbc.gridx = 0; panel.add(new JLabel("Deadline (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; panel.add(dateField, gbc);

        gbc.gridy = ++y; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        registerButton.addActionListener(e -> {
            try {
                Student s = new Student(nameField.getText(), emailField.getText(), new String(passwordField.getPassword()), phoneField.getText(), instituteField.getText(), goalField.getText(), targetDescField.getText(), Date.valueOf(dateField.getText()));
                studentService.addStudent(s);
                JOptionPane.showMessageDialog(this, "✅ Registration Successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back to Welcome");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(buttonPanel, gbc);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        loginButton.addActionListener(e -> {
            try {
                if (studentService.login(emailField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "🎉 Welcome back, " + studentService.getCurrentStudent().getName() + "!");
                    cardLayout.show(mainPanel, "dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Invalid Email or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error during login: " + ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea displayArea = new JTextArea("Welcome to your dashboard! Select an option from the menu.");
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel menuPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Dashboard Menu"));

        // All 21 buttons from your console menu
        String[] buttonLabels = {
            "➕ Add Subject", "📋 View Subjects", "✏️ Update Subject", "❌ Delete Subject",
            "➕ Add Topic", "📋 View Topics", "✏️ Update Topic", "❌ Delete Topic",
            "➕ Add Interview Question", "📄 View Interview Questions", "❌ Delete Interview Question",
            "🔍 Search Topics by Subject", "🔍 Search Topics by Difficulty", "📝 Add Assignment",
            "📂 View Assignments by Status", "🔄 Update Assignment Status", "🗑️ Delete Assignment",
            "👤 View Profile", "🔧 Update Profile", "❌ Delete Profile", "🔒 Logout"
        };
        
        DashboardActionListener actionListener = new DashboardActionListener(this, studentService, displayArea, mainPanel, cardLayout);

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setActionCommand(label);
            button.addActionListener(actionListener);
            menuPanel.add(button);
        }
        
        panel.add(menuPanel, BorderLayout.WEST);
        return panel;
    }

    public static void main(String[] args) {
        // This makes sure the GUI runs on the right thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentLearnerGUI().setVisible(true);
            }
        });
    }
}

/**
 * A single ActionListener class to handle all dashboard button clicks,
 * just like your 'switch' statement in the console app.
 */
class DashboardActionListener implements ActionListener {
    private final JFrame parentFrame;
    private final StudentService ss;
    private final JTextArea displayArea;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    public DashboardActionListener(JFrame parent, StudentService service, JTextArea display, JPanel mainP, CardLayout cl) {
        this.parentFrame = parent;
        this.ss = service;
        this.displayArea = display;
        this.mainPanel = mainP;
        this.cardLayout = cl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            switch (command) {
                case "➕ Add Subject":
                    String subjectName = JOptionPane.showInputDialog(parentFrame, "Enter Subject Name:");
                    if (isValid(subjectName)) {
                        ss.addSubject(new Subject(subjectName, ss.getCurrentStudent().getStudentId()));
                        displayArea.setText("✅ Subject '" + subjectName + "' Added Successfully!");
                    }
                    break;

                case "📋 View Subjects":
                    displayArea.setText(ss.viewSubjects());
                    break;
                
                case "✏️ Update Subject":
                    String updateIdStr = JOptionPane.showInputDialog(parentFrame, "Enter Subject ID to update:");
                    if(isValid(updateIdStr)) {
                        String newName = JOptionPane.showInputDialog(parentFrame, "Enter new subject name:");
                        if (isValid(newName)) {
                             ss.updateSubject(Integer.parseInt(updateIdStr), newName);
                             displayArea.setText("✅ Subject updated!");
                        }
                    }
                    break;

                case "❌ Delete Subject":
                    String deleteIdStr = JOptionPane.showInputDialog(parentFrame, "Enter Subject ID to delete:");
                    if(isValid(deleteIdStr)) {
                        ss.deleteSubject(Integer.parseInt(deleteIdStr));
                        displayArea.setText("✅ Subject deleted!");
                    }
                    break;
                    
                case "➕ Add Topic":
                    showAddTopicDialog();
                    break;
                    
                case "📋 View Topics":
                    displayArea.setText(ss.viewTopics());
                    break;

                case "👤 View Profile":
                    displayArea.setText(ss.viewProfile());
                    break;

                case "🔧 Update Profile":
                    showUpdateProfileDialog();
                    break;
                
                case "❌ Delete Profile":
                     int choice = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete your entire profile? This cannot be undone.", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                     if (choice == JOptionPane.YES_OPTION) {
                         ss.deleteProfile();
                         JOptionPane.showMessageDialog(parentFrame, "Profile deleted successfully.");
                         ss.logout();
                         cardLayout.show(mainPanel, "welcome");
                     }
                     break;

                case "🔒 Logout":
                    ss.logout();
                    cardLayout.show(mainPanel, "welcome");
                    break;
                    
                default:
                    displayArea.setText("Functionality for '" + command + "' is under construction.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentFrame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void showAddTopicDialog() throws SQLException {
        JTextField subIdField = new JTextField(5);
        JTextField nameField = new JTextField(15);
        JTextArea descArea = new JTextArea(3, 15);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"weak", "average", "strong"});
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Subject ID:")); panel.add(subIdField);
        panel.add(new JLabel("Topic Name:")); panel.add(nameField);
        panel.add(new JLabel("Description:")); panel.add(new JScrollPane(descArea));
        panel.add(new JLabel("Status:")); panel.add(statusCombo);
        
        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Add New Topic", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Topic topic = new Topic(nameField.getText(), descArea.getText(), Integer.parseInt(subIdField.getText()), ss.getCurrentStudent().getStudentId(), (String)statusCombo.getSelectedItem());
            if (ss.addTopic(topic)) {
                displayArea.setText("✅ Topic added successfully!");
            } else {
                 displayArea.setText("❌ Failed to add topic.");
            }
        }
    }
    
    private void showUpdateProfileDialog() throws SQLException {
        Student s = ss.getCurrentStudent();
        JTextField nameField = new JTextField(s.getName(), 20);
        JTextField phoneField = new JTextField(s.getPhoneNumber(), 20);
        JTextField instituteField = new JTextField(s.getInstituteName(), 20);
        JTextField goalField = new JTextField(s.getGoal(), 20);
        JTextArea goalDescArea = new JTextArea(s.getTargetDesc(), 3, 20);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);
        panel.add(new JLabel("Institute:")); panel.add(instituteField);
        panel.add(new JLabel("Goal:")); panel.add(goalField);
        panel.add(new JLabel("Goal Description:")); panel.add(new JScrollPane(goalDescArea));

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Update Profile", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if(ss.updateProfile(nameField.getText(), phoneField.getText(), instituteField.getText(), goalField.getText(), goalDescArea.getText())) {
                displayArea.setText("✅ Profile updated successfully!");
            } else {
                displayArea.setText("❌ Profile update failed.");
            }
        }
    }
    
    // Helper method to check if input is valid
    private boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }
}