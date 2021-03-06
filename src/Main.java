import util.Input;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    static int userInput = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Contact Manager Application");

        do {
            userInput = showMenu();
        } while (userInput != 5);
    }

    public static int showMenu() {
        System.out.println("1 - View contacts");
        System.out.println("2 - Add new contact");
        System.out.println("3 - Search contact by name");
        System.out.println("4 - Delete existing contact");
        System.out.println("5 - Exit");
        System.out.println("Enter Option: [1, 2, 3, 4, 5]");

        userInput = Input.getInt(1,5);
        List<String> currentList = getCurrentContacts();
        userInputSwitchCase(userInput, currentList);
        return userInput;
    }

    public static List<String> getCurrentContacts() {
        String directory = "src";
        String filename = "contacts.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);
        List<String> contacts;

        try {
            contacts = Files.readAllLines(dataFile);
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return getCurrentContacts();
    }

    public static void userInputSwitchCase(int userInput, List<String> currentContacts) {
        switch (userInput) {
            case 1:
                readAllContacts(currentContacts);
                break;
            case 2:
                try {
                    addNewContact();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                searchForContact(currentContacts);
                break;
            case 4:
                deleteContact(currentContacts);
                break;
        }
    }

    public static void readAllContacts(List<String> contacts) {
        for (String contact : contacts) {
            System.out.println(contact);
        }
        System.out.println();
        System.out.println("Main Menu");
        System.out.println("---------------");
    }

    public static void addNewContact() throws IOException {
        String directory = "src";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        ArrayList<String> contacts = new ArrayList<>();
        System.out.println("Enter the contact's name and number:");
        contacts.add(sc.nextLine());

        Files.write(dataFile, contacts, StandardOpenOption.APPEND);
        System.out.println();
        System.out.println("New Contact: \"" + contacts.get(0) + "\" Added");
        System.out.println("Main Menu");
        System.out.println("---------------");
    }

    public static void searchForContact(List<String> contactsList) {
        System.out.println("Try searching for a contact in your directory");
        System.out.println("eg. [Po], [210-222-2222]");
        int variable = 0;
        String userInput = sc.nextLine();
        for (String contact : contactsList) {
            if (contact.toLowerCase().contains(userInput.toLowerCase())) {
                variable++;
                System.out.println();
                System.out.println(contact);
                System.out.println();
                System.out.println("Main Menu");
                System.out.println("---------------");
            }
        }

        if (variable == 0) {
            System.out.println("Contact not found in directory");
            System.out.println();
            searchForContact(contactsList);
        }
    }

    public static void deleteContact(List<String> contactsList) {
        // THIS IS MESSY, CAN BE CLEANED UP AND ALTERED LATER
        System.out.println("Which contact would you like to delete?");

        for (int i = 0; i < contactsList.size(); i++) {
            System.out.println((i + 1) + ". " + contactsList.get(i));
        }

        System.out.println("---------------");
        System.out.println("Please select the contact option from above- eg. [1, 2, 5...]");

        int deletionChoice = Input.getInt(1, contactsList.size());
        String contactDeleted = contactsList.get(deletionChoice - 1);
        contactsList.remove(deletionChoice - 1);

        String directory = "src";
        String filename = "contacts.txt";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);
        try {
            Files.write(dataFile, contactsList);

            System.out.println();
            System.out.println("Successfully Deleted Contact: \"" + contactDeleted + "\"");
            System.out.println();
            System.out.println("Main Menu");
            System.out.println("---------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
