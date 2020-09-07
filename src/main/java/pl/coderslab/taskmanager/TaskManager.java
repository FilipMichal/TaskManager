package pl.coderslab.taskmanager;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    private static String[][] taskList = new String[0][];

    public static void main(String[] args) {
        TaskManager.run();
    }


    public static void run() {
        loadTaskListFromFile();
        showWelcomeMessage();
        while (true) {
            showMainMenu();
            String userChoice = getUserChoice();
            if (validateUserChoice(userChoice)) {
                executeValidChoice(userChoice);
                if (isExitChoice(userChoice)) {
                    break;
                }
            } else {
                executeInvalidChoice(userChoice);
            }
        }
        showExitMessage();
        saveTaskListToFile();
    }

    private static void saveTaskListToFile() {
        String fileName = "tasks.csv";
        StringBuilder nextItem = new StringBuilder();
        int count = 0;
        for (String[] task : taskList) {
            nextItem.append(taskList[count][0]).append(", ")
                    .append(taskList[count][1]).append(", ")
                    .append(taskList[count][2]).append("\n");
            count++;
            try (FileWriter nextOutput = new FileWriter(fileName, false)) {
                nextOutput.append(nextItem.toString());

            } catch (IOException ex) {
                System.out.println("Writing to file error.");
            }
        }
    }

    private static void loadTaskListFromFile() {
        String fileName = "tasks.csv";
        File tasksFile = new File(fileName);
        int count = 0;
        try {
            Scanner scan = new Scanner(tasksFile);
            while (scan.hasNextLine()) {
                String content = scan.nextLine();
                taskList = Arrays.copyOf(taskList, count + 1);
                taskList[count] = content.split(", ");
                count++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        for (int i = 0; i < taskList.length; i++) {
//            System.out.println(Arrays.toString(taskList[i]));
//        } // sprawdzenie, czy zawartość *.csv została poprawnie przeniesiona do tablicy
    }

        private static String getUserChoice() {

        Scanner scan = new Scanner(System.in);
        return scan.nextLine().trim();
    }

    private static boolean validateUserChoice(String userChoice) {
        String[] validChoices = {"add", "remove", "list", "exit"};
        Arrays.sort(validChoices);
        int index = Arrays.binarySearch(validChoices, userChoice); //binarySearch wymaga posortowanej tablicy!
        return index >= 0;
    }

    private static void executeValidChoice(String userChoice) {
        switch (userChoice) {
            case "add" :
                executeAddChoice();
                break;
            case "remove" :
                executeRemoveChoice();
                break;
            case "list" :
                executeListChoice();
                break;
        }
    }

    private static void executeListChoice() {
        StringBuilder taskContent = new StringBuilder();
        int count = 0;
        for (String[] task : taskList) {
            taskContent.append(count + " :\t").append(task[0] +", ").append(task[1] + ", ").append(task[2] + "\n");
            count++;
        }
        System.out.println(taskContent.toString());

    }

    private static void executeRemoveChoice() {
        Scanner toRemove = new Scanner(System.in);
        System.out.println("Select the task to remove. Enter number from 0 to " + (taskList.length - 1) + " :" );
        while (!toRemove.hasNextInt()) {
            toRemove.nextLine();
            System.out.println("Enter valid value: 0 - " + (taskList.length - 1));
        }
        int userSelected = toRemove.nextInt();
        if (userSelected < 0 || userSelected >= taskList.length) {
            System.out.println("Entered value is invalid, enter again: ");
        } else if (userSelected == taskList.length - 1) {
            taskList = Arrays.copyOf(taskList, taskList.length - 1);
        } else if (userSelected >= 0 && userSelected < taskList.length - 1){
            for (int i = userSelected; i < taskList.length - 1; i++) {
                taskList[i] = taskList[i + 1];
            }
            taskList = Arrays.copyOf(taskList, taskList.length - 1);
        }

    }

    private static void executeAddChoice() {
        Scanner scan = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Enter task description: ");
        System.out.print(ConsoleColors.RESET);
        String description = scan.nextLine();
        System.out.println(ConsoleColors.BLUE + "Enter the due date (yyyy-mm-dd): ");
        System.out.print(ConsoleColors.RESET);
        String dueDate = scan.nextLine();
        System.out.println(ConsoleColors.BLUE + "Is this task important (true/false): ");
        System.out.print(ConsoleColors.RESET);
        String priority = scan.nextLine();
        StringBuilder toAdd = new StringBuilder();
        toAdd.append(description).append(", ").append(dueDate).append(", ").append(priority);
        taskList = Arrays.copyOf(taskList, taskList.length + 1);
        taskList[taskList.length - 1] = toAdd.toString().split(", ");
    }

    private static boolean isExitChoice(String userChoice) {
        String ending = "exit";
        return ending.equalsIgnoreCase(userChoice);
    }

    private static void executeInvalidChoice(String userChoice) {
        System.out.println(ConsoleColors.YELLOW_BACKGROUND + "Invalid option" + userChoice + "Please choose a valid option.");
        System.out.print(ConsoleColors.RESET);
    }

    private static void showMainMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
        System.out.println("\tadd");
        System.out.println("\tremove");
        System.out.println("\tlist");
        System.out.println("\texit");
    }

    private static void showExitMessage() {
        System.out.println(ConsoleColors.RED + "Bye! See you back soon.");
        System.out.print(ConsoleColors.RESET);
    }

    private static void showWelcomeMessage() {
        System.out.println(ConsoleColors.RED + "Welcome in Task Manager");
        System.out.print(ConsoleColors.RESET);
    }

}