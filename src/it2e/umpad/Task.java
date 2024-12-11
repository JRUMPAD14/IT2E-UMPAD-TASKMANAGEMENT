
package it2e.umpad;

import java.util.Scanner;


public class Task {
   
    public void nameTask() {
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {
            System.out.println("\n----------------------");
            System.out.println("WELCOME TO TASK PANEL");
            System.out.println("1.ADD TASK");
            System.out.println("2.VIEW TASK");
            System.out.println("3.UPDATE TASK");
            System.out.println("4.DELETE TASK");
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
            Task pro = new Task(); 
            
            switch (action) {
                case 1:
                pro.addTask();
                pro.viewTask();
                break;
            case 2:
                pro.viewTask();
                break;
            case 3:
                pro.viewTask();
                pro.updateTask();
                pro.viewTask();
                break;
            case 4:
                pro.viewTask();
                pro.deleteTask();
                pro.viewTask();
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

    public void addTask() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        sc.nextLine();  

        System.out.print("Task Name: ");
        String proname = sc.nextLine();

        System.out.print("Start Date: ");
        String prosdate = sc.nextLine();

        System.out.print("Due Date: ");
        String produedate = sc.nextLine();

        System.out.print("Priority Level: ");
        String proprio = sc.nextLine();

        String sql = "INSERT INTO Task (pr_name, pr_sdate, pr_ddate, pr_priolvl) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, proname, prosdate, produedate, proprio);
    }

    public void viewTask() {
        String qry = "SELECT * FROM Task";
        String[] headers = {"ID", "Task Name", "Start Date", "Due Date", "Priority Level"};
        String[] columns = {"pr_id", "pr_name", "pr_sdate", "pr_ddate", "pr_priolvl"};
        config conf = new config();
        conf.viewRecords(qry, headers, columns);
    }

    private void updateTask() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Task ID: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT pr_id FROM Task WHERE pr_id=?", id) == 0) {
            System.out.println("Selected ID doesn't exist");
            System.out.print("Select Task ID Again: ");
            id = sc.nextInt();
        }

        sc.nextLine();  

        System.out.print("Enter new Task Name: ");
        String proname = sc.nextLine();

        System.out.print("Enter new Start Date: ");
        String prosdate = sc.nextLine();

        System.out.print("Enter new Due Date: ");
        String produedate = sc.nextLine();

        System.out.print("Enter new Priority Level: ");
        String proprio = sc.nextLine();

        String qry = "UPDATE Task SET pr_name = ?, pr_sdate = ?, pr_ddate = ?, pr_priolvl = ? WHERE pr_id = ?";
        conf.updateRecord(qry,  proname, prosdate, produedate, proprio, id);
    }

    private void deleteTask() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Task ID to delete: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT pr_id FROM Task WHERE pr_id=?", id) == 0) {
            System.out.println("Selected ID doesn't exist.");
            System.out.print("Select Task ID Again: ");
            id = sc.nextInt();
        }

        String sqlDelete = "DELETE FROM Task WHERE pr_id = ?";
        conf.deleteRecord(sqlDelete, id);
    }
}


