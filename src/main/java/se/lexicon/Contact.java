package se.lexicon;

import java.util.ArrayList;

public class Contact {

    private Integer id;
    private String name;
    private int mobile;

    private static ArrayList<Contact> contactsList = new ArrayList<>();

    // default constructor for creating empty contact
    public Contact() {
    }

    public Contact(Integer id, String name, int mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMobileNR() {
        return mobile;
    }

    public void setMobileNR(int mobile) {
        this.mobile = mobile;
    }

    private boolean mobileExists(int mobile) {
        for (Contact c : contactsList) {
            if (c.getMobileNR() == mobile) {
                return true;
            }
        }
        return false;
    }

    public void addContact(String name, int mobile) {
        if (mobileExists(mobile)) {
            IO.println("Error: Mobile number " + mobile + " already exists. Contact not added.");
        } else {
            int newId = contactsList.size() + 1;
            Contact newContact = new Contact(newId, name, mobile);
            contactsList.add(newContact);
            IO.println("Contact saved! ID: " + newId);
        }
    }

    public ArrayList<Contact> getContactList() {
        return contactsList;
    }

    public ArrayList<Contact> getContact() {
        return getContactList();
    }

    // converts object to a readable string
    @Override
    public String toString() {
        return "Contact{" +
                "id=" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", mobile=" + getMobileNR() +
                '}';
    }
}

/*
 * ENCAPSULATION: Making fields private and providing public getter/setter
 * methods
 * to control access to the data.
 * 
 * WHY PRIVATE FIELDS MATTER IN THIS FILE:
 * 1. Data Protection: Prevents other classes from directly modifying fields
 * like id, name, mobile
 * - Without private: Main.java could do: contact.id = -999; (invalid data!)
 * - With private: Main.java must use: contact.setId(999); (can add validation)
 * 
 * 2. Controlled Access: Forces all access through getter/setter methods
 * - getId(), setId() - control how ID is read/written
 * - getName(), setName() - control how name is read/written
 * - getMobileNR(), setMobileNR() - control how mobile is read/written
 * 
 * 3. Future-Proofing: Can add validation logic without breaking existing code
 * - Example: setMobileNR() could validate that mobile > 0
 * - Example: setName() could check that name isn't empty or contains numbers
 * 
 * PUBLIC vs PRIVATE:
 * - public: Accessible from anywhere (Main.java, other classes, other packages)
 * - private: Only accessible within this Contact.java file
 * - mobileExists() is private because it's a helper method only needed
 * internally
 * - getId(), getName() are public because Main.java needs to read these values
 * 
 * VOID vs NON-VOID:
 * - void: Method doesn't return anything (e.g., setId(), addContact())
 * Used for actions/operations that modify state
 * - non-void: Method returns a value (e.g., getId() returns Integer, getName()
 * returns String)
 * Used for retrieving/calculating data
 * 
 * BEST PRACTICE: Fields should be private, methods should be public only if
 * needed outside
 */



/*
 * notes
 * When you use ArrayList.contains(someObject) or any collection method that
 * checks for existence, Java needs a way to decide if the object you are asking
 * about is already in the list. It does this using the concept of equality
 * By default, without any special instructions from you, Java uses reference
 * equality (the == operator). This simply asks:
 * "Are these two variables pointing to the exact same spot in the computer's memory?"
 * 
 * new Contact(1, "A", 123) is stored at Memory Address X.
 * new Contact(1, "A", 123) is stored at Memory Address Y.
 * 
 * Even though they contain identical data, Address X is not Address Y. The
 * default contains() method would say these two are not equal.
 * In your addContact(String name, int mobile) method in the previous turn, you
 * create a new object inside the method:
 * 
 * // Inside the manager's addContact method
 * Contact newContact = new Contact(newId, name, mobile);
 * contactsList.add(newContact);
 */