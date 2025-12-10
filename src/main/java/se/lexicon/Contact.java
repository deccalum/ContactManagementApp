package se.lexicon;

import java.util.ArrayList;
import java.util.Objects;

public class Contact {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private Integer id;
    private String name;
    private int mobile;

    public Contact() {
    }

    public Contact(Integer id, String name, int mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public void addContact(Contact contact){
        if(contacts.contains(contact)) {
            IO.println("This contact is already saved");
            return;
        }
        contacts.add(contact);
    }

    public ArrayList<Contact> getContact(){
        /*for (Contact contact: contacts){
            IO.println(contact);
        }*/
        return contacts;

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

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + getName() + '\'' +
                ", mobile=" + getMobile() +
                '}';
    }
}