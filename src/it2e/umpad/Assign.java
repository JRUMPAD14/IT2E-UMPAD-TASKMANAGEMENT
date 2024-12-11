
package it2e.umpad;

import java.util.Scanner;

public class Assign {
     
    public void assignTask() {
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {
            System.out.println("\n------------------------------");
            System.out.println("WELCOME TO ASSIGN TASK PANEL");
            System.out.println("1.ADD ASSIGN TASK");
            System.out.println("2.VIEW ASSIGN TASK");
            System.out.println("3.UPDATE ASSIGN TASK");
            System.out.println("4.DELETE ASSIGN TASK");
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
            Assign as = new Assign(); 
            
            switch (action) {
                case 1:
                as.addAssign();
                as.viewAssign();
                break;
            case 2:
                as.viewAssign();
                break;
            case 3:
                as.viewAssign();
                as.updateAssign();
                as.viewAssign();
                break;
            case 4:
                as.viewAssign();
                as.deleteAssign();
                as.viewAssign();
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
    
public void addAssign() {
    
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    
    Employee emp = new Employee();
    emp.viewEmployee();
    
    System.out.print("Enter the ID of the Employee: ");
    int eid = sc.nextInt();
    
    String employeeSql = "SELECT e_id FROM Employee WHERE e_id=?";
    
    
    while (conf.getSingleValue(employeeSql, eid) == 0) {
        System.out.println("Selected Employee ID doesn't exist.");
        System.out.print("\nSelect Employee ID Again: ");
        eid = sc.nextInt();
    }
    
    Task pro = new Task();
    pro.viewTask();
    
    System.out.print("Enter the ID of the Task: ");
    int tid = sc.nextInt();
    
    String taskSql = "SELECT pr_id FROM Task WHERE pr_id=?";
    
   
    while (conf.getSingleValue(taskSql, tid) == 0) {
        System.out.println("Selected Task ID doesn't exist.");
        System.out.print("\nSelect Task ID Again: ");
        tid = sc.nextInt();
    }
    
    sc.nextLine(); 
    
    System.out.print("Status (Assigned, In-progress, Completed, Delayed): ");
    String status = sc.nextLine();

    System.out.print("Actual Date Finish: ");
    String dateFinish = sc.nextLine();

    String insertSql = "INSERT INTO Assign (e_id, pr_id, a_status, a_dfinish) VALUES (?, ?, ?, ?)";
    conf.addRecord(insertSql, String.valueOf(eid), String.valueOf(tid), status, dateFinish);
}


    public void viewAssign() {
        
        String empqry = "SELECT a_id,e_lname,pr_name,pr_sdate, pr_ddate, pr_priolvl,a_status, a_dfinish FROM Assign " 
                +"LEFT JOIN Employee ON Employee.e_id = Assign.e_id "
                + "LEFT JOIN Task ON Task.pr_id = Task.pr_id";
        
        String [] Headers = {"ASSIGNED ID","EMPLOYEE NAME","TASK NAME","START DATE", "DUE DATE", "PRIORITY LEVEL", "STATUS","DATE FINISH"};
        String[] Columns = {"a_id", "e_lname", "pr_name","pr_sdate", "pr_ddate", "pr_priolvl", "a_status", "a_dfinish"};
       config conf = new config();
       conf.viewRecords(empqry, Headers, Columns);
    }

    private void updateAssign() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Assign ID: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT a_id FROM Assign WHERE a_id=?", id) == 0) {
            System.out.println("Selected ID doesn't exist");
            System.out.print("Select Assign ID Again: ");
            id = sc.nextInt();
        }

        sc.nextLine();  

        System.out.print("Enter new Status: ");
        String status = sc.nextLine();

        System.out.print("Enter Date Finish: ");
        String dDone = sc.nextLine();

        String qry = "UPDATE Assign SET a_status = ?, a_dfinish = ? WHERE a_id = ?";
        conf.updateRecord(qry,  status, dDone, id);
    }

    private void deleteAssign() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Assign ID to Delete: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT a_id FROM Assign WHERE a_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist.");
            System.out.print("Select Assign ID Again: ");
            id = sc.nextInt();
        }

        String sqlDelete = "DELETE FROM Assign WHERE a_id = ?";
        conf.deleteRecord(sqlDelete, id);
    }
}




