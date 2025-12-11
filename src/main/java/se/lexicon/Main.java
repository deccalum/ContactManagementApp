package se.lexicon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Contact contact = new Contact();

    public static void main(String[] args) {
        boolean isRun = true;
        do {
            menu();
            int option = promptAndReadInt(scanner, "Select an option: \n");

            switch (option) {
                case 1:
                    addContact();
                    break;
                case 2:
                    getContact();
                    break;
                case 4:
                    listContacts();
                    break;
                case 5:
                    deleteContact();
                    break;
                case 6:
                    createCSV();
                    break;
                case 7:
                    randomizeContacts();
                    break;
                case 8:
                    isRun = false;
                    break;
                default:
                    IO.println("Wrong Input");
            }
        } while (isRun);
        IO.println("\nExiting Contact Management App. Goodbye!");
    }

    static void menu() {
        IO.println("\n=== Contact Management ===");
        IO.println(" [1] Add Contact");
        IO.println(" [2] Search Contact");
        IO.println(" [3] Search Contact by Number");
        IO.println(" [4] List All Contacts");
        IO.println(" [5] Delete Contact");
        IO.println(" [6] Export Contacts to CSV");
        IO.println(" [7] Randomize Contacts");
        IO.println(" [8] Exit");
    }

    static void addContact() {
        String name = promptAndRead(scanner, "\nEnter name: ");
        int mobile = promptAndReadInt(scanner, "\nEnter mobile number: ");

        if (mobile <= 0) {
            IO.println("Invalid mobile number format. Must be a positive number.");
            return;
        }
        contact.addContact(name, mobile);
    }

    static void getContact() {
        int choice = promptAndReadInt(scanner, "Search by [1] Name or [2] Mobile number? ");
        if (choice != 1 && choice != 2) {
            IO.println("Invalid option.");
            return;
        }

        int searchType = choice;
        String promptMsg = (searchType == 1) ? "Search name: " : "Search number (exact): ";
        String searchStr = promptAndRead(scanner, promptMsg);

        ArrayList<Contact> contacts = contact.getContactList();
        boolean found = false;

        IO.println("Search results for \"" + searchStr + "\":"); // "\" = removes quotes

        for (Contact c : contacts) {
            boolean match = false;

            if (searchType == 1) {
                if (c.getName().toLowerCase().contains(searchStr.toLowerCase())) {
                    match = true;
                }
            } else {
                try {
                    int searchMobile = Integer.parseInt(searchStr);
                    if (c.getMobileNR() == searchMobile) {
                        match = true;
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid mobile number format. Not a number.");
                    break;
                }
            }
            if (match) {
                IO.println(c.toString());
                found = true;
            }
        }
        if (!found) {
            IO.println("No contacts found matching \"" + searchStr + "\".");
        }
    }

    static void listContacts() {
        ArrayList<Contact> contacts = contact.getContactList();
        for (Contact c : contacts) {
            IO.println(c.toString());
        }
    }

    static void deleteContact() {
        String choiceStr = promptAndRead(scanner, "Delete by [1] ID or [2] Mobile number? ");
        if (!choiceStr.equals("1") && !choiceStr.equals("2")) {
            IO.println("Invalid option.");
            return;
        }

        int input = Integer.parseInt(choiceStr);
        String promptMsg = (input == 1) ? "Enter contact ID: " : "Enter mobile number: ";
        String searchStr = promptAndRead(scanner, promptMsg);
        int contactIDorMobile;
        try {
            contactIDorMobile = Integer.parseInt(searchStr);
        } catch (NumberFormatException e) {
            IO.println("Invalid input. Not a number.");
            return;
        }

        ArrayList<Contact> contacts = contact.getContactList();
        boolean found = false;

        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            boolean match = (input == 1 && c.getId() == contactIDorMobile)
                    || (input == 2 && c.getMobileNR() == contactIDorMobile);

            if (match) {
                contacts.remove(i);
                IO.println("Contact deleted: " + c.getName());
                found = true;
                break;
            }
        }
        if (!found) {
            IO.println("Contact not found.");
        }
    }

    static void randomizeContacts() {

        String[] names = { "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah", "Ian", "Jack" };
        Random random = new Random();

        int count = promptAndReadInt(scanner, "How many times to random contacts?");

        if (count <= 0) {
            IO.println("Count must be a positive number.");
            return;
        }
        for (int i = 0; i < count; i++) {
            // Return random name from name array
            int randomIndex = random.nextInt(names.length);
            String randomName = names[randomIndex];

            // Generate random mobile number
            int randomMobile = random.nextInt(9000000) + 1000000;

            // Create new contact from random data
            int id = contact.getContactList().size() + 1;
            contact.addContact(randomName, randomMobile);
        }

        IO.println("Total contacts: " + contact.getContactList().size());
    }

    static void createCSV() {
        // name and path
        String fileName = "Contacts.csv";
        ArrayList<Contact> contacts = contact.getContactList();

        /*
         * try-with-resources: File operations throw IOException. Java requires you to
         * explicitly handle these exceptions (either with a try-catch block or by
         * throwing the exception further up).
         */

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ID,Name,Mobile\n");
            for (Contact c : contacts) {
                writer.write(c.getId() + "," + c.getName() + "," + c.getMobileNR() + "\n");
            }
            IO.println("\nContacts exported to " + fileName + "\n");
        } catch (IOException e) {
            IO.println("Error writing to file: " + e.getMessage() + "\n");
        }
    }

    // helper for promptMessage
    static String promptAndRead(Scanner s, String promptMessage) {
        IO.println(promptMessage);
        IO.print("> ");
        String userInput = s.nextLine();
        return userInput;
    }

    static int promptAndReadInt(Scanner s, String promptMessage) {
        int userInput = 0;
        boolean validInput = false;

        while (!validInput) {
            IO.println(promptMessage);
            IO.print("> ");
            if (s.hasNextInt()) {
                userInput = s.nextInt();
                s.nextLine();
                validInput = true;
            } else {
                IO.println("Invalid input. Please enter a number.");
                s.next();
            }
        }

        return userInput; // Always returns an int after valid input is received
    }
}

/*
 * TODOS
 * add string input aceptance for various phone number formats
 * read from file to populate contact list on startup
 * add edit contact functionality
 * add validation for name input (no numbers or special characters)
 */

// ============================================================================
// UTILISED METHODS / COMMANDS IN THIS FILE
// ============================================================================

/*
 * === SCANNER INPUT METHODS ===
 * 
 * boolean java.util.Scanner.hasNextInt()
 * Returns true if the next token in this scanner's input can be interpreted as
 * an int value
 * in the default radix using the nextInt() method. The scanner does not advance
 * past any input.
 * 
 * Returns: true if and only if this scanner's next token is a valid int value
 * Throws: IllegalStateException - if this scanner is closed
 * 
 * 
 * int java.util.Scanner.nextInt() throws InputMismatchException
 * Scans the next token of the input as an int. This method will throw
 * InputMismatchException
 * if the next token cannot be translated into a valid int value.
 * IMPORTANT: Does NOT consume the newline character, leaving it in the buffer.
 * 
 * Returns: the int scanned from the input
 * Throws: InputMismatchException - if the next token does not match the Integer
 * regular expression,
 * or is out of range
 * 
 * 
 * String java.util.Scanner.nextLine()
 * Advances this scanner past the current line and returns the input that was
 * skipped.
 * This method returns the rest of the current line, excluding any line
 * separator at the end.
 * The position is set to the beginning of the next line.
 * 
 * Returns: the line that was skipped
 * 
 * 
 * String java.util.Scanner.next()
 * Finds and returns the next complete token from this scanner. A complete token
 * is preceded
 * and followed by input that matches the delimiter pattern.
 * 
 * Returns: the next token
 * 
 * Related Scanner methods not used:
 * - hasNext() // Check if another token available
 * - hasNextLine() // Check if another line available
 * - hasNextDouble() // Check if next token is a double
 * - nextDouble() // Read next double
 * - useDelimiter(String pattern) // Set custom delimiter
 * - close() // Close the scanner
 */

/*
 * === ARRAYLIST METHODS ===
 * 
 * int java.util.ArrayList.size()
 * Returns the number of elements in this list.
 * 
 * Specified by: size() in List, Overrides: size() in AbstractCollection
 * 
 * Returns: the number of elements in this list
 * 
 * 
 * E java.util.ArrayList.get(int index)
 * Returns the element at the specified position in this list.
 * 
 * Parameters: index - index of the element to return
 * Returns: the element at the specified position in this list
 * Throws: IndexOutOfBoundsException - if the index is out of range (index < 0
 * || index >= size())
 * 
 * 
 * E java.util.ArrayList.remove(int index)
 * Removes the element at the specified position in this list. Shifts any
 * subsequent elements
 * to the left (subtracts one from their indices).
 * 
 * Parameters: index - the index of the element to be removed
 * Returns: the element that was removed from the list
 * Throws: IndexOutOfBoundsException - if the index is out of range
 * 
 * 
 * boolean java.util.ArrayList.add(E e)
 * Appends the specified element to the end of this list.
 * 
 * Parameters: e - element to be appended to this list
 * Returns: true (as specified by Collection.add)
 * 
 * Related ArrayList methods not used:
 * - add(int index, E element) // Insert at specific position
 * - set(int index, E element) // Replace element at index
 * - clear() // Remove all elements
 * - isEmpty() // Check if list is empty
 * - contains(Object o) // Check if element exists
 * - indexOf(Object o) // Find first occurrence index
 * - remove(Object o) // Remove first occurrence of object
 * - addAll(Collection<? extends E> c) // Add all elements from collection
 * 
 * ARRAYLIST.CONTAINS() AND OBJECT EQUALITY:
 * 
 * When you use ArrayList.contains(someObject) or any collection method that
 * checks for existence,
 * Java needs a way to decide if the object you are asking about is already in
 * the list.
 * It does this using the concept of equality.
 * 
 * By default, without any special instructions from you, Java uses reference
 * equality (the == operator).
 * This simply asks:
 * "Are these two variables pointing to the exact same spot in the computer's memory?"
 * 
 * Example:
 * new Contact(1, "A", 123) is stored at Memory Address X.
 * new Contact(1, "A", 123) is stored at Memory Address Y.
 * 
 * Even though they contain identical data, Address X is not Address Y.
 * The default contains() method would say these two are not equal.
 * 
 * In the addContact(String name, int mobile) method in Contact.java, a new
 * object is created:
 * Contact newContact = new Contact(newId, name, mobile);
 * contactsList.add(newContact);
 * 
 * To make contains() work based on data (like checking if mobile number
 * exists), you need to
 * override the equals() and hashCode() methods in the Contact class, OR use a
 * custom search
 * method like mobileExists() that manually checks the fields you care about.
 */

/*
 * === STRING METHODS ===
 * 
 * String java.lang.String.toLowerCase()
 * Converts all of the characters in this String to lower case using the rules
 * of the default locale.
 * 
 * Returns: the String, converted to lowercase
 * 
 * 
 * boolean java.lang.String.contains(CharSequence s)
 * Returns true if and only if this string contains the specified sequence of
 * char values.
 * 
 * Parameters: s - the sequence to search for
 * Returns: true if this string contains s, false otherwise
 * 
 * 
 * boolean java.lang.String.equals(Object anObject)
 * Compares this string to the specified object. The result is true if and only
 * if the argument
 * is not null and is a String object that represents the same sequence of
 * characters as this object.
 * 
 * Parameters: anObject - The object to compare this String against
 * Returns: true if the given object represents a String equivalent to this
 * string, false otherwise
 * 
 * Related String methods not used:
 * - equalsIgnoreCase(String anotherString) // Case-insensitive comparison
 * - startsWith(String prefix) // Check if starts with prefix
 * - endsWith(String suffix) // Check if ends with suffix
 * - substring(int beginIndex, int endIndex) // Extract substring
 * - trim() // Remove leading/trailing whitespace
 * - replace(char oldChar, char newChar) // Replace characters
 * - split(String regex) // Split into array by delimiter
 * - length() // Get string length
 * - charAt(int index) // Get character at index
 * - indexOf(String str) // Find first occurrence
 * - isEmpty() // Check if string is empty
 * - toUpperCase() // Convert to uppercase
 */

/*
 * === INTEGER WRAPPER CLASS ===
 * 
 * int java.lang.Integer.parseInt(String s) throws NumberFormatException
 * Parses the string argument as a signed decimal integer. The characters in the
 * string must
 * all be decimal digits, except that the first character may be an ASCII minus
 * sign '-' ('\u002D')
 * to indicate a negative value or an ASCII plus sign '+' ('\u002B') to indicate
 * a positive value.
 * The resulting integer value is returned, exactly as if the argument and the
 * radix 10 were given
 * as arguments to the parseInt(java.lang.String, int) method.
 * 
 * Parameters: s - a String containing the int representation to be parsed
 * Returns: the integer value represented by the argument in decimal
 * Throws: NumberFormatException - if the string does not contain a parsable
 * integer
 * 
 * Related Integer methods not used:
 * - parseInt(String s, int radix) // Parse with custom radix (e.g., binary,
 * hex)
 * - valueOf(String s) // Returns Integer object instead of primitive
 * - toString(int i) // Convert int to String
 * - Integer.MAX_VALUE // Constant: 2147483647
 * - Integer.MIN_VALUE // Constant: -2147483648
 */

/*
 * === RANDOM NUMBER GENERATION ===
 * 
 * int java.util.Random.nextInt(int bound)
 * Returns a pseudorandom, uniformly distributed int value between 0 (inclusive)
 * and the
 * specified value (exclusive), drawn from this random number generator's
 * sequence.
 * 
 * Parameters: bound - the upper bound (exclusive). Must be positive.
 * Returns: the next pseudorandom, uniformly distributed int value between zero
 * (inclusive)
 * and bound (exclusive) from this random number generator's sequence
 * Throws: IllegalArgumentException - if bound is not positive
 * 
 * Related Random methods not used:
 * - nextInt() // Returns any int value
 * - nextDouble() // Returns double between 0.0 and 1.0
 * - nextBoolean() // Returns random true/false
 * - nextLong() // Returns random long
 * - nextGaussian() // Returns Gaussian distributed double
 * - setSeed(long seed) // Set seed for reproducible results
 */

/*
 * === FILE I/O (BufferedWriter & FileWriter) ===
 * 
 * java.io.FileWriter(String fileName) throws IOException
 * Constructs a FileWriter object given a file name.
 * 
 * Parameters: fileName - String The system-dependent filename
 * Throws: IOException - if the named file exists but is a directory rather than
 * a regular file,
 * does not exist but cannot be created, or cannot be opened for any other
 * reason
 * 
 * 
 * java.io.BufferedWriter(Writer out)
 * Creates a buffered character-output stream that uses a default-sized output
 * buffer.
 * Buffering improves performance by reducing the number of system calls.
 * 
 * Parameters: out - A Writer
 * 
 * 
 * void java.io.BufferedWriter.write(String str) throws IOException
 * Writes a string.
 * 
 * Parameters: str - String to be written
 * Throws: IOException - If an I/O error occurs
 * 
 * Try-with-resources statement:
 * Automatically closes resources (like BufferedWriter) when the try block
 * exits,
 * even if an exception occurs. Resources must implement AutoCloseable
 * interface.
 * 
 * Syntax: try (ResourceType resource = new ResourceType()) { ... }
 * 
 * Related I/O methods/classes not used:
 * - BufferedReader // Read from files
 * - FileReader // Character file input
 * - Files.readAllLines(Path path) // Read entire file into List<String>
 * - Files.write(Path path, byte[] bytes) // Write bytes to file
 * - PrintWriter // Formatted text output with print/println
 */

/*
 * === CONTROL FLOW & ITERATION ===
 * 
 * Enhanced for loop (for-each):
 * for (ElementType element : collection) { ... }
 * - Iterates over all elements in a collection or array
 * - Cannot modify collection during iteration (throws
 * ConcurrentModificationException)
 * - Cannot access index directly
 * - Cleaner syntax for read-only operations
 * 
 * Index-based for loop:
 * for (int i = 0; i < list.size(); i++) { ... }
 * - Allows safe removal during iteration (by controlling index)
 * - Provides index for operations needing position
 * - Better for modifications that change list size
 * 
 * WHY index-based is safer for deletion:
 * When removing element at index i, all subsequent elements shift left.
 * Using for-each or Iterator, the iteration state can become invalid.
 * With index-based loop, you control the index and can break after removal.
 */