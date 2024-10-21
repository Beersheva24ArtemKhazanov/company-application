package telran.employees;
import telran.io.Persistable;
import telran.view.*;

public class Main {
    public static void main(String[] args) {
        Company company = new CompanyImpl();
        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile("employees.data");
        }
        Item[] items = CompanyItems.getItems(company);
        performMenu("Company Application" ,items);
    }

    public static void performMenu(String name, Item[] items) {
        Menu menu = new Menu(name, items);
        menu.perform(new StandardInputOutput());
    }
}