
package it2e.umpad;

import java.util.Scanner;


public class It2eUmpad {

    
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    boolean validInput = false;
    int action = 0;

    while (true) { 
        validInput = false;

        System.out.println("=================================");
        System.out.println("WELCOME TO TASK MANAGEMENT");
        System.out.println("");
        System.out.println("1. EMPLOYEE");
        System.out.println("2. TASK");
        System.out.println("3. ASSIGNMENT");
        System.out.println("4. REPORTS");
        System.out.println("5. EXIT");
        System.out.println("-------------------------------");

        
        while (!validInput) {
            System.out.print("Enter Action: ");
            String input = sc.nextLine();

            if (input.isEmpty()) {
                System.out.println("Please enter a number between 1-5: ");
            } else {
                try {
                    action = Integer.parseInt(input);
                    if (action >= 1 && action <= 5) {
                        validInput = true; 
                    } else {
                        System.out.println("Pick only from 1-5: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please pick only from 1-5: ");
                }
            }
        }

        if (action == 5) {
            System.out.println("Exiting...");
            break; 
        }

        switch (action) {
            case 1:
                Employee emp = new Employee(); 
                emp.nameEmployee();
                break;
            case 2:
                Task pro = new Task(); 
                pro.nameTask();
                break;
            case 3:
                Assign as = new Assign(); 
                as.assignTask();
                break;
            case 4:
                Reportss rs = new Reportss();
                rs.reportMenu();
                 break;
            default:
                System.out.println("Invalid action. Please try again.");
        }

        
    }

    System.out.println("Thank You!");
}

    }


    
   

