package se.lexicon;

import java.util.ArrayList;

public class Contact {
    //changed to private because of encapsulation???
    private Integer id;
    private String name;
    private int mobile;

    //change to static to persist data between instances
    //changed name to contactsList for clarity (from contacts to contactsList)
    private static ArrayList<Contact> contactsList = new ArrayList<>();

    //default constructor for creating empty contact
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

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    private boolean mobileExists(int mobile) {
        for (Contact c : contactsList) {
            if (c.getMobile() == mobile) {
                return true;
            }
        }
        return false;
    }

    public void addContact(String name, int mobile) {
        if (mobileExists(mobile)) {
            IO.println("Error: Mobile number " + mobile + " already exists. Contact not added.");
        } else {
            //if mobile does not exist, add new contact
            int newId = contactsList.size() + 1;
            Contact newContact = new Contact(newId, name, mobile);
            contactsList.add(newContact);
            IO.println("Contact saved! ID: " + newId);
        }
    }

    //helper to get the contact list
    public ArrayList<Contact> getContactList() {
        return contactsList;
    }
    
    public ArrayList<Contact> getContact() {
        return getContactList();
    }

    //converts object to a readable string
    @Override
    public String toString() {
        // Added ID to the toString output, which is useful for CSV and display
        return "Contact{" +
                "id=" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", mobile=" + getMobile() +
                '}';
    }
}

/*notes
When you use ArrayList.contains(someObject) or any collection method that checks for existence, Java needs a way to decide if the object you are asking about is already in the list. It does this using the concept of equality
By default, without any special instructions from you, Java uses reference equality (the == operator). This simply asks: "Are these two variables pointing to the exact same spot in the computer's memory?"

    new Contact(1, "A", 123) is stored at Memory Address X.
    new Contact(1, "A", 123) is stored at Memory Address Y.

Even though they contain identical data, Address X is not Address Y. The default contains() method would say these two are not equal.
In your addContact(String name, int mobile) method in the previous turn, you create a new object inside the method:

// Inside the manager's addContact method
Contact newContact = new Contact(newId, name, mobile); 
contactsList.add(newContact);
 */