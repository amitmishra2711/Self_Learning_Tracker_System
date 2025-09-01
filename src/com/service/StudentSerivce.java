package com.service;

import com.DAO.StudentDAO;
import com.model.Student;
import com.model.Subject;
import com.model.Topic;
import com.model.InterviewQuestion;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentSerivce {

    public static Student currentStudent = null;
    private final StudentDAO dao = new StudentDAO();

    // NECESSARY CHANGE 1: Replaced the inefficient 'checkExist' and 'getStudentByEmail'
    // with a single, efficient 'login' method for the GUI.
    public boolean login(String email, String password) throws SQLException {
        currentStudent = dao.checkAndGetStudent(email, password);
        return currentStudent != null;
    }

    public void logout() {
        currentStudent = null;
    }
    
    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void addStudent(Student s) throws SQLException {
        dao.addStudent(s);
    }

    // This method has been slightly improved to be more service-oriented
    public void addSubject(Subject s) throws SQLException {
        // The service ensures the subject is added for the currently logged-in student
        if (currentStudent != null) {
            s.setStudentId(currentStudent.getStudentId());
            dao.addSubject(s);
        }
    }
    
    // NECESSARY CHANGE 2: The 'view' methods now return data for the GUI to display.
    // They are renamed to 'get...' or formatted to return a String.

    public String viewSubjects() throws SQLException {
        if (currentStudent == null) return "Please login first.";
        
        List<Subject> subjects = dao.getSubjectsByStudentId(currentStudent.getStudentId());
        if (subjects.isEmpty()) {
            return "No subjects found.";
        }
        
        StringBuilder sb = new StringBuilder("--- 📚 Your Subjects ---\n\n");
        for (Subject s : subjects) {
            sb.append(String.format("ID: %-5d | Name: %s\n", s.getSubjectId(), s.getSubjectName()));
        }
        return sb.toString();
    }

    public String viewProfile() throws SQLException {
        if (currentStudent == null) return "Please login first.";
        return dao.getProfileAsString(currentStudent.getStudentId());
    }

    public String viewTopics() throws SQLException {
        if (currentStudent == null) return "Please login first.";
        return dao.getTopicsAsString(currentStudent.getStudentId());
    }

    public boolean addTopic(Topic t) throws SQLException {
        if (currentStudent != null) {
            t.setStudentId(currentStudent.getStudentId());
            return dao.addTopic(t);
        }
        return false;
    }

    public boolean deleteProfile() throws SQLException {
        if (currentStudent != null) {
            int studentId = currentStudent.getStudentId();
            dao.deleteProfile(studentId);
            logout(); // Ensure user is logged out after profile deletion
            return true;
        }
        return false;
    }


    // --- The rest of your methods are kept as they were, assuming they will be called correctly ---

    public boolean updateSubject(int subjectID, String name) throws SQLException {
        return dao.updateSubject(subjectID, name);
    }

    public boolean deleteSubject(int subjectID) throws SQLException {
        return dao.deleteSubject(subjectID);
    }

    public boolean updateProfile(String name, String phone, String institute, String goal, String goalDesc) throws SQLException {
        if(currentStudent != null) {
             return dao.updateProfile(currentStudent.getStudentId(), name, phone, institute, goal, goalDesc);
        }
       return false;
    }

    // ... you would continue this pattern for all other methods ...
    // ... for example, viewAssignments would become a method that returns a String or List ...
}