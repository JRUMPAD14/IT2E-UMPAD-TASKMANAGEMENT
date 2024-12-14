package it2e.umpad;

import java.sql.*;
import java.util.Scanner;

public class Reportss {

    private final Scanner input = new Scanner(System.in);
    private final config conf = new config();

    public void reportMenu() {
        boolean exit = true;

        do {
            System.out.println("===========================================|");
            System.out.println("                                           |");
            System.out.println("              Reports Menu                 |");
            System.out.println("                                           |");
            System.out.println("===========================================|");
            System.out.println("1.           VIEW REPORTS                  |");
            System.out.println("2.         INDIVIDUAL REPORTS              |");
            System.out.println("3.              EXIT                       |");
            System.out.println("===========================================|");
            System.out.print("Choose an option (1-3): ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    viewReport();
                    break;
                case 2:
                    individualReport();
                    break;
                case 3:
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 3.");
            }
        } while (exit);

        System.out.println("Thank you for using the Report Generator!");
        input.close();
    }

    private void viewReport() {
        System.out.println("============================================================|");
        System.out.println("                Viewing Employee & Task Report              |");
        System.out.println("============================================================|");

        String query = """
                SELECT e.e_id, e.e_fname, e.e_lname, t.pr_id, t.pr_name, t.pr_sdate, t.pr_ddate, t.pr_priolvl
                FROM Employee e
                LEFT JOIN Task t ON e.e_id = t.pr_id
                """;

        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.printf("%-10s %-15s %-15s %-10s %-30s %-15s %-15s %-15s%n",
                              "EMP ID", "FIRST NAME", "LAST NAME", "TASK ID",
                              "TASK NAME", "START DATE", "DUE DATE", "PRIORITY");

            while (rs.next()) {
                System.out.printf("%-10d %-15s %-15s %-10d %-30s %-15s %-15s %-15s%n",
                                  rs.getInt("e_id"), rs.getString("e_fname"),
                                  rs.getString("e_lname"), rs.getInt("pr_id"),
                                  rs.getString("pr_name"), rs.getString("pr_sdate"),
                                  rs.getString("pr_ddate"), rs.getString("pr_priolvl"));
            }
        } catch (SQLException e) {
            System.err.println("Error generating general report: " + e.getMessage());
        }
    }

    private void individualReport() {
        System.out.print("Enter Employee ID to view task details: ");
        int employeeId;

        try {
            employeeId = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric Employee ID.");
            return;
        }

        System.out.println("=========================================================");
        System.out.println("           INDIVIDUAL EMPLOYEE & TASK REPORT             ");
        System.out.println("=========================================================");

        String employeeQuery = "SELECT * FROM Employee WHERE e_id = ?";
        String taskQuery = "SELECT * FROM Task WHERE pr_id = ?";

        try (Connection conn = conf.connectDB();
             PreparedStatement empStmt = conn.prepareStatement(employeeQuery)) {

            empStmt.setInt(1, employeeId);
            try (ResultSet rsEmployee = empStmt.executeQuery()) {
                if (rsEmployee.next()) {
                    System.out.println("Employee Information:");
                    System.out.printf("Employee ID: %d%n", rsEmployee.getInt("e_id"));
                    System.out.printf("First Name: %s%n", rsEmployee.getString("e_fname"));
                    System.out.printf("Last Name: %s%n", rsEmployee.getString("e_lname"));
                    System.out.printf("Email: %s%n", rsEmployee.getString("e_email"));
                    System.out.printf("Contact No: %s%n", rsEmployee.getString("e_contact"));

                    System.out.println("\nTask Information:");
                    System.out.printf("%-10s %-30s %-15s %-15s %-15s%n", 
                                      "TASK ID", "TASK NAME", "START DATE", "DUE DATE", "PRIORITY");

                    try (PreparedStatement taskStmt = conn.prepareStatement(taskQuery)) {
                        taskStmt.setInt(1, employeeId);
                        try (ResultSet rsTask = taskStmt.executeQuery()) {
                            boolean hasTasks = false;
                            while (rsTask.next()) {
                                hasTasks = true;
                                System.out.printf("%-10d %-30s %-15s %-15s %-15s%n",
                                                  rsTask.getInt("pr_id"),
                                                  rsTask.getString("pr_name"),
                                                  rsTask.getString("pr_sdate"),
                                                  rsTask.getString("pr_ddate"),
                                                  rsTask.getString("pr_priolvl"));
                            }

                            if (!hasTasks) {
                                System.out.println("No tasks found for this employee.");
                            }
                        }
                    }
                } else {
                    System.out.println("Employee with ID " + employeeId + " not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error generating individual report: " + e.getMessage());
        }
    }
}
