/*
   PROGRAM: Main.java
   Written by Meijie Shih
   This program asks user to choose a letter grade and display students under selected letter grade.
   The grade list is built up by linked list structure.
   The program includes catching FileNotFoundException, case-insensitive input, and validation of input.
*/

import java.io.*;
import java.util.*;

public class Main {
    // variables
    static String filename = "studentinfo.txt";

    static GradeNode headNode, lastNode;

    static final int PROJECT_NUMBER = 4;
    static final double PROJECT = 0.5;
    static final double M_EXAM = 0.2;
    static final double F_EXAM = 0.3;

    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("Student by Letter Grade\n");

        System.out.println("Opening file......");
        // read the file, throw error message if file was not found
        File file = new File(filename);
        Scanner fScan = new Scanner(file); // the error message will prompt if file was not found
        System.out.println("Processing file......");

        headNode = new GradeNode();
        lastNode = headNode;

        // build the linked list
        buildLinkedList(fScan, lastNode);

        // choose letter grade
        Scanner scan = new Scanner(System.in);
        String choose = chooseLetter(scan);

        // print results
        printGradeList(headNode, choose);
    }

    /**
     * The buildLinkedList method builds a linked list which stores id, average, and letter grade of all students.
     */
    private static void buildLinkedList(Scanner fScan, GradeNode lastNode) {
        while (fScan.hasNextLine()) {
            double p1, p2, p3, p4, m_exam, f_exam, avg;
            String id, grade, letter;
            String[] temp = fScan.nextLine().split(" "); // temp = [id, p1, p2, midterm, p3, p4, final]
            p1 = Double.parseDouble(temp[1]);
            p2 = Double.parseDouble(temp[2]);
            p3 = Double.parseDouble(temp[4]);
            p4 = Double.parseDouble(temp[5]);
            m_exam = Double.parseDouble(temp[3]);
            f_exam = Double.parseDouble(temp[6]);

            // avg = project_avg * 0.5 + midterm * 0.2 + final * 0.3
            avg = ((p1 + p2 + p3 + p4) / PROJECT_NUMBER) * PROJECT + m_exam * M_EXAM + f_exam * F_EXAM;

            id = temp[0];
            grade = String.format("%,.1f", avg); // overall average with 1 decimal place
            letter = letterGrade(grade); // convert average grade to letter grade

            // build linked list with id, avg, and letter grade
            GradeNode currNode = new GradeNode(id, grade, letter);
            lastNode.insertAfter(currNode);
            lastNode = currNode;
        }
        // close file
        fScan.close();
    }

    /**
     * The chooseLetter method asks users to choose a letter grade that they want to display.
     * The method is case-insensitive, and validates users' input.
     */
    private static String chooseLetter(Scanner s) {
        String opt;
        printTable(); // print the table of range of letter grade
        System.out.print("Enter the letter grade earned by students to display: ");
        opt = s.next().toUpperCase(); // in case of entering lower case
        opt = validate(s, opt); // validate user's input
        s.close(); // close scanner
        return opt;
    }

    /**
     * The printGradeList method displays the students under the selected letter grade.
     * The method prints a message if there is no student under the selected letter grade,
     * displays the students with ascending ID.
     */
    private static void printGradeList(GradeNode headNode, String c) {
        GradeNode currNode;
        ArrayList<String> result = new ArrayList<>();
        printTitle(c);
        currNode = headNode.getNext();
        while (currNode != null){
            if (currNode.printGradeInfo(c) != null){
                result.add(currNode.printGradeInfo(c));
            }
            currNode = currNode.getNext();
        }

        // if size of result is zero, it means there is no student under selected letter grade
        if (result.size() == 0) {
            System.out.println("No student under letter grade " + c);
        }
        else {
            Collections.sort(result); // sort the student ID in ascending order, extra points!!!
            System.out.printf("%6s%6s%n", "ID", "Avg");
            for (String s: result) {
                System.out.println(s);
            }
        }
        System.out.println("\nEnd of results.");
    }

    /**
     * The letterGrade method determines letter grade by the student's average.
     */
    private static String letterGrade(String g) {
        double avg = Double.parseDouble(g);
        String l;
        if (94 <= avg){
            l = "A";
        }
        else if (90 <= avg) {
            l = "A-";
        }
        else if (87 <= avg) {
            l = "B+";
        }
        else if (83 <= avg) {
            l = "B";
        }
        else if (80 <= avg) {
            l = "B-";
        }
        else if (77 <= avg) {
            l = "C+";
        }
        else if (73 <= avg) {
            l = "C";
        }
        else if (70 <= avg) {
            l = "C-";
        }
        else if (67 <= avg) {
            l = "D+";
        }
        else if (63 <= avg) {
            l = "D";
        }
        else if (60 <= avg) {
            l = "D-";
        }
        else {
            l = "F";
        }
        return l;
    }

    /**
     * The printTable method prints a table of range of letter grade.
     */
    private static void printTable() {
        System.out.printf("%n%5s%10s%n", "Letter Grade", "Range");
        Object[][] table = new String[12][];
        table[0] = new String[] {"A ", "94-100"};
        table[1] = new String[] {"A-", "90-93.9"};
        table[2] = new String[] {"B+", "87-89.9"};
        table[3] = new String[] {"B ", "83-86.9"};
        table[4] = new String[] {"B-", "80-82.9"};
        table[5] = new String[] {"C+", "77-79.9"};
        table[6] = new String[] {"C ", "73-76.9"};
        table[7] = new String[] {"C-", "70-72.9"};
        table[8] = new String[] {"D+", "67-69.9"};
        table[9] = new String[] {"D ", "63-66.9"};
        table[10] = new String[] {"D-", "60-62.9"};
        table[11] = new String[] {"F ", "<60"};
        for (Object[] row: table) {
            System.out.printf("%7s%15s%n", row);
        }
    }

    /**
     * The validate method validates user's input, and is case-insensitive.
     */
    private static String validate(Scanner s, String o) {
        while (!o.equals("A") &&
               !o.equals("A-") &&
               !o.equals("B+") &&
               !o.equals("B") &&
               !o.equals("B-") &&
               !o.equals("C+") &&
               !o.equals("C") &&
               !o.equals("C-") &&
               !o.equals("D+") &&
               !o.equals("D") &&
               !o.equals("D-") &&
               !o.equals("F")){
            System.out.print("Incorrect letter grade entered, try again: ");
            o = s.next().toUpperCase(); // in case of entering lower case
        }
        return o;
    }

    /**
     * The printTitle method prints title of selected letter grade.
     */
    private static void printTitle(String c) {
        switch (c) {
            case "A" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (94 - 100%):");
            }
            case "A-" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (90 - 93.9%):");
            }
            case "B+" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (87 - 89.9%):");
            }
            case "B" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (83 - 86.9%):");
            }
            case "B-" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (80 - 82.9%):");
            }
            case "C+" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (77 - 79.9%):");
            }
            case "C" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (73 - 76.9%):");
            }
            case "C-" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (70 - 72.9%):");
            }
            case "D+" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (67 - 69.9%):");
            }
            case "D" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (63 - 66.9%):");
            }
            case "D-" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (60 - 62.9%):");
            }
            case "F" -> {
                System.out.println();
                System.out.println("List of student(s) with a " + c + " average (below 60%):");
            }
        }
    }
}