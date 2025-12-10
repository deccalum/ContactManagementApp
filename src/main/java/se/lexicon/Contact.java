package se.lexicon;

import java.util.ArrayList;

public class Contact {

    ArrayList<Contact> contacts = new ArrayList<>();
    Integer id;
    String name;
    int mobile;

    public Contact() {
    }

    public Contact(Integer id,String name, int mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    void addContact(Contact contact){
        if(contacts.contains(contact)){
            IO.println("This contact is already saved");
        }
        else contacts.add(contact);
    }
    ArrayList<Contact> getContact(){
        /*for (Contact contact: contacts){
            IO.println(contact);
        }*/
        return contacts;

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