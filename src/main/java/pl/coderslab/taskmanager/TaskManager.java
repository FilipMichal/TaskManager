package pl.coderslab.taskmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    private static String[][] taskList = new String[1][];

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

    }

    private static void loadTaskListFromFile() {
        String fileName = "tasks.csv";
        Path taskFilePath = Paths.get(fileName);
        File tasksFile = new File(fileName);
        int count = 0;
        try {
            Scanner scan = new Scanner(taskFilePath);
            while (scan.hasNextLine()) {
                String content = scan.nextLine();
                taskList = Arrays.copyOf(taskList, count + 1);
                taskList[count] = content.split(", ");
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < taskList.length; i++) {
//            System.out.println(Arrays.toString(taskList[i]));
//        } // sprawdzenie, czy zawartość *.csv została poprawnie przeniesiona do tablicy
    }

        private static String getUserChoice() {
//        return System.console().readLine();
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
                //obsługa
                executeRemoveChoice();
                break;
            case "list" :
                //obsługa
                executeListChoice();
                break;
        }
    }

    private static void executeListChoice() {
    }

    private static void executeRemoveChoice() {
    }

    private static void executeAddChoice() {
        Scanner scan = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Podaj opis zadania: ");
        System.out.print(ConsoleColors.RESET);
        String description = scan.nextLine();
        System.out.println(ConsoleColors.BLUE + "Podaj termin wykonania (rrrr-mm-dd): ");
        System.out.print(ConsoleColors.RESET);
        String dueDate = scan.nextLine();
        System.out.println(ConsoleColors.BLUE + "Czy to ważne zadanie (true/false): ");
        System.out.print(ConsoleColors.RESET);
        String priority = scan.nextLine();
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
