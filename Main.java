import java.io.*;
import java.util.*;

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
}

abstract class ContactOperations {
    abstract void addContact();
    abstract void displayContacts();
    abstract void searchContact();
    abstract void updateContact();
    abstract void deleteContact();
}

class ContactBook extends ContactOperations {

    private List<Contact> contacts = new ArrayList<>();
    private final String FILE_NAME = "contacts.txt";
    private Scanner scanner = new Scanner(System.in);

    public ContactBook() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            while (true) {
                String name = br.readLine();
                if (name == null) break;
                String phone = br.readLine();
                String email = br.readLine();
                contacts.add(new Contact(name, phone, email));
            }
        } catch (IOException e) {
            // File may not exist initially
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                bw.write(c.getName()); bw.newLine();
                bw.write(c.getPhone()); bw.newLine();
                bw.write(c.getEmail()); bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("File error.");
        }
    }

    @Override
    void addContact() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        contacts.add(new Contact(name, phone, email));
        saveToFile();
        System.out.println("Contact added successfully.");
    }

    @Override
    void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        for (Contact c : contacts) {
            System.out.println(c.getName() + " | " + c.getPhone() + " | " + c.getEmail());
        }
    }

    @Override
    void searchContact() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();

        for (Contact c : contacts) {
            if (c.getName().trim().equalsIgnoreCase(name.trim())) {
                System.out.println(c.getName() + " | " + c.getPhone() + " | " + c.getEmail());
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    @Override
    void updateContact() {
        System.out.print("Enter name to update: ");
        String name = scanner.nextLine();
        for (Contact c : contacts) {
            if (c.getName().trim().equalsIgnoreCase(name.trim())) {
                System.out.print("New name: ");
                c.setName(scanner.nextLine());
                System.out.print("New phone: ");
                c.setPhone(scanner.nextLine());
                System.out.print("New email: ");
                c.setEmail(scanner.nextLine());
                saveToFile();
                System.out.println("Contact updated.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    @Override
    void deleteContact() {
        System.out.print("Enter name to delete: ");
        String name = scanner.nextLine();

        Iterator<Contact> it = contacts.iterator();
        while (it.hasNext()) {
            if (it.next().getName().equalsIgnoreCase(name)) {
                it.remove();
                saveToFile();
                System.out.println("Contact deleted.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }
}

public class Main {
    public static void main(String[] args) {

        ContactOperations contactBook = new ContactBook();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n1.Add  2.Display  3.Search  4.Update  5.Delete  6.Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> contactBook.addContact();
                case 2 -> contactBook.displayContacts();
                case 3 -> contactBook.searchContact();
                case 4 -> contactBook.updateContact();
                case 5 -> contactBook.deleteContact();
                case 6 -> System.out.println("Exiting program.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }
}


