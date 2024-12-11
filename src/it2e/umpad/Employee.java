package it2e.umpad;

import java.util.Scanner;

public class Employee {

    public void nameEmployee() {
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {
            System.out.println("\n----------------------");
            System.out.println("WELCOME TO EMPLOYEE PANEL");
            System.out.println("1.ADD EMPLOYEE");
            System.out.println("2.VIEW EMPLOYEE");
            System.out.println("3.UPDATE EMPLOYEE");
            System.out.println("4.DELETE EMPLOYEE");
            System.out.println("5. EXIT");
            
            int action;
            while (true) {
                System.out.print("Enter Selection: ");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 5) {
                        break;
                    }
                } else {
                    sc.next(); 
                }
                System.out.println("Invalid selection, Please enter a number between 1 and 5 only.");
            }
            
            Employee emp = new Employee(); 
            
            switch (action) {
                case 1:
                emp.addEmployee();
                emp.viewEmployee();
                break;
            case 2:
                emp.viewEmployee();
                break;
            case 3:
                emp.viewEmployee();
                emp.updateEmployee();
                emp.viewEmployee();
                break;
            case 4:
                emp.viewEmployee();
                emp.deleteEmployee();
                emp.viewEmployee();
                break;
                case 5:
                    System.out.println("Exiting...");
                    break;
            }

            while (true) {
                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next();
                if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'");
                }
            }

            if (response.equalsIgnoreCase("no")) {
                System.out.println("Going back to the main menu...\n");
            }

        } while (response.equalsIgnoreCase("yes"));
    }

    public void addEmployee() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        sc.nextLine();  

        System.out.print("First Name: ");
        String efname = sc.nextLine();

        System.out.print("Last Name: ");
        String elname = sc.nextLine();

        System.out.print("Email: ");
        String eemail = sc.nextLine();

        System.out.print("Phone Number: ");
        String econtact = sc.nextLine();

        String sql = "INSERT INTO Employee (e_fname, e_lname, e_email, e_contact) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, efname, elname, eemail, econtact);
    }

    public void viewEmployee() {
        String qry = "SELECT * FROM Employee";
        
        String[] headers = {"ID", "First Name", "Last Name", "Email", "Contact"};
        String[] columns = {"e_id", "e_fname", "e_lname", "e_email", "e_contact"};
        config conf = new config();
        conf.viewRecords(qry, headers, columns);
    }

    private void updateEmployee() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter User ID: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT e_id FROM Employee WHERE e_id=?", id) == 0) {
            System.out.println("Selected ID doesn't exist");
            System.out.print("Select Employee ID Again: ");
            id = sc.nextInt();
        }

        sc.nextLine();  

        System.out.print("Enter new First Name: ");
        String efname = sc.nextLine();

        System.out.print("Enter new Last Name: ");
        String elname = sc.nextLine();

        System.out.print("Enter new Email: ");
        String eemail = sc.nextLine();

        System.out.print("Enter new Phone Number: ");
        String econtact = sc.nextLine();

        String qry = "UPDATE User SET u_fname = ?, e_lname = ?, e_email = ?, e_contact = ? WHERE e_id = ?";
        conf.updateRecord(qry, efname, elname, eemail, econtact, id);
    }

    private void deleteEmployee() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT e_id FROM Employee WHERE e_id=?", id) == 0) {
            System.out.println("Selected ID doesn't exist.");
            System.out.print("Select Employee ID Again: ");
            id = sc.nextInt();
        }

        String sqlDelete = "DELETE FROM Employee WHERE un_id = ?";
        conf.deleteRecord(sqlDelete, id);
    }
}
