import java.util.*;
import java.io.*;

class Student {
    String name;
    double marks;

    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    String getGrade() {
        if (marks >= 85) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 50) return "C";
        else return "Fail";
    }
}

public class CodeAlpha_StudentGradeTracker {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Load data from file
        loadFromFile();

        // Login
        if (!login()) {
            System.out.println("Access denied. Exiting...");
            return;
        }

        int choice;
        do {
            System.out.println("\n====== Student Grade Tracker ======");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student Marks");
            System.out.println("5. Delete Student");
            System.out.println("6. Sort Students by Marks");
            System.out.println("7. Show Statistics");
            System.out.println("0. Save & Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: searchStudent(); break;
                case 4: updateStudent(); break;
                case 5: deleteStudent(); break;
                case 6: sortStudents(); break;
                case 7: showStatistics(); break;
                case 0: saveToFile(); System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 0);
    }

    // ------------------- LOGIN SYSTEM -------------------
    static boolean login() {
        String USERNAME = "admin";
        String PASSWORD = "admin123";

        System.out.print("Enter username: ");
        String u = sc.nextLine();
        System.out.print("Enter password: ");
        String p = sc.nextLine();

        if (u.equals(USERNAME) && p.equals(PASSWORD)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid credentials!");
            return false;
        }
    }

    // ------------------- ADD STUDENT -------------------
    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter marks (0-100): ");
        double marks = sc.nextDouble();
        sc.nextLine(); // consume newline

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks! Must be 0-100.");
            return;
        }

        students.add(new Student(name, marks));
        System.out.println("Student added successfully!");
    }

    // ------------------- VIEW STUDENTS -------------------
    static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println("Name: " + s.name + 
                               ", Marks: " + s.marks + 
                               ", Grade: " + s.getGrade());
        }
    }

    // ------------------- SEARCH STUDENT -------------------
    static void searchStudent() {
        System.out.print("Enter student name to search: ");
        String searchName = sc.nextLine();
        boolean found = false;

        for (Student s : students) {
            if (s.name.equalsIgnoreCase(searchName)) {
                System.out.println("Found -> Name: " + s.name +
                                   ", Marks: " + s.marks +
                                   ", Grade: " + s.getGrade());
                found = true;
                break;
            }
        }

        if (!found) System.out.println("Student not found!");
    }

    // ------------------- UPDATE STUDENT -------------------
    static void updateStudent() {
        System.out.print("Enter student name to update: ");
        String name = sc.nextLine();
        boolean found = false;

        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) {
                System.out.print("Enter new marks: ");
                double newMarks = sc.nextDouble();
                sc.nextLine();
                if (newMarks < 0 || newMarks > 100) {
                    System.out.println("Invalid marks!");
                    return;
                }
                s.marks = newMarks;
                System.out.println("Marks updated successfully!");
                found = true;
                break;
            }
        }

        if (!found) System.out.println("Student not found!");
    }

    // ------------------- DELETE STUDENT -------------------
    static void deleteStudent() {
        System.out.print("Enter student name to delete: ");
        String name = sc.nextLine();
        boolean removed = students.removeIf(s -> s.name.equalsIgnoreCase(name));

        if (removed) System.out.println("Student deleted successfully!");
        else System.out.println("Student not found!");
    }

    // ------------------- SORT STUDENTS -------------------
    static void sortStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to sort.");
            return;
        }

        students.sort((s1, s2) -> Double.compare(s2.marks, s1.marks)); // Descending
        System.out.println("Students sorted by marks (Highest to Lowest).");
        viewAllStudents();
    }

    // ------------------- STATISTICS -------------------
    static void showStatistics() {
        if (students.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        double total = 0;
        double highest = students.get(0).marks;
        double lowest = students.get(0).marks;

        for (Student s : students) {
            total += s.marks;
            if (s.marks > highest) highest = s.marks;
            if (s.marks < lowest) lowest = s.marks;
        }

        double average = total / students.size();

        System.out.println("\n--- Class Statistics ---");
        System.out.println("Total Students: " + students.size());
        System.out.println("Average Marks: " + average);
        System.out.println("Highest Marks: " + highest);
        System.out.println("Lowest Marks: " + lowest);
    }

    // ------------------- FILE SAVE & LOAD -------------------
    static void saveToFile() {
        try (PrintWriter pw = new PrintWriter("students.txt")) {
            for (Student s : students) {
                pw.println(s.name + "," + s.marks);
            }
            System.out.println("Data saved to file.");
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                students.add(new Student(parts[0], Double.parseDouble(parts[1])));
            }
            System.out.println("Data loaded from file.");
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
}
