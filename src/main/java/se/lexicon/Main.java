package se.lexicon;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Contact contact = new Contact();

    static void main() {
        boolean isRun = true;
        int option;
        do {
            menu();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    addContact();
                    break;
                case 2:
                    getContact();
                    break;
                case 3:
                    listContacts();
                    break;
                case 4:
                    isRun = false;
                    break;
                default:
                    IO.println("Wrong Input");
            }
        } while (isRun);

    }

    static void menu() {
        IO.println("=== Contact Management ===");
        IO.println(" 1- Add Contact: ");
        IO.println(" 2- Search Contact: ");
        IO.println(" 3- Display All Contacts: ");
        IO.println(" 4- Exit: ");
        IO.println(" Choose an option: ");
    }

    static void addContact() {
        // Scanner scanner= new Scanner(System.in);

        IO.println("Enter name: ");
        String name = scanner.next();

        contact.setName(name);
        IO.println("Enter mobile: ");
        int mobile = scanner.nextInt();
        contact.setMobile(mobile);
        IO.println("saved! ");
        int id = contact.getContact().size() + 1;
        contact.addContact(new Contact(id, name, mobile));
    }

    static void getContact(){
        IO.println("Search: ");
        String name = scanner.next();
        ArrayList<Contact> contacts = contact.getContact();
        for (Contact c: contacts){
            if(c.getName().equalsIgnoreCase(name)){
                IO.println(c.toString());
                return;
            }
        }
        IO.println("Contact not found.");
    }

    static void listContacts() {
        ArrayList<Contact> contacts = contact.getContact();
        for (Contact c : contacts) {
            IO.println(c.toString());
        }
    }
}