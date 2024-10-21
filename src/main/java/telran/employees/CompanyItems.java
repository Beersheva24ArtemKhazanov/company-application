package telran.employees;

import java.util.HashSet;
import java.util.List;

import telran.view.*;

public class CompanyItems {
    private static Company company;
    final static long MIN_ID = 1;
    final static long MAX_ID = 300;
    final static int MIN_SALARY = 5000;
    final static int MAX_SALARY = 30000;
    final static int MIN_WAGE = 10;
    final static int MAX_WAGE = 150;
    final static int MIN_HOURS = 6;
    final static int MAX_HOURS = 12;
    final static float MIN_PERCENT = 0.01f;
    final static float MAX_PERCENT = 3;
    final static long MIN_SALES = 5000;
    final static long MAX_SALES = 20000;
    final static float MIN_FACTOR = 1.5f;
    final static float MAX_FACTOR = 4;
    final static String[] DEPARTMENTS = { "QA", "Audit", "Development", "Management" };

    // TODO
    public static Item[] getItems(Company company) {
        CompanyItems.company = company;
        Item[] res = {
                Item.of("Add Employee", CompanyItems::displaySubMenu),
                Item.of("Display Employee Data", CompanyItems::displayEmployeeData),
                Item.of("Fire Employee", CompanyItems::fireEmployee),
                Item.of("Department Salary Budget", CompanyItems::displayDepartmentSalaryBudget),
                Item.of("List of Departments", CompanyItems::displayListOfDepartments),
                Item.of("Display Managers with Most Factor", CompanyItems::displayManagersWithMostFactor),
                Item.ofExit()
        };
        return res;
    }

    static void displaySubMenu(InputOutput io) {
        Item[] subItems = {
                Item.of("Hire Employee", CompanyItems::addEmployee),
                Item.of("Hire Wage Employee", CompanyItems::addWageEmployee),
                Item.of("Hire Sales Person", CompanyItems::addSalesPerson),
                Item.of("Hire Manager", CompanyItems::addManager),
                Item.of("<-- Back", CompanyItems::goBack),
                Item.ofExit()
        };
        Main.performMenu("Kinds of adding Employees", subItems);
    }

    static void goBack(InputOutput io) {
        Item[] items = CompanyItems.getItems(company);
        Main.performMenu("Company Application", items);
    }

    static void addEmployee(InputOutput io) {
        addEmployeeByClassName("", io);

    }

    private static void addEmployeeByClassName(String className, InputOutput io) {
        io.writeLine("\n");
        Employee empl = null;
        long id = io.readNumberRange(String.format("Enter ID value in the range [%d-%d]", MIN_ID, MAX_ID),
                "Wrong ID value", MIN_ID, MAX_ID).longValue();
        int salary = io.readNumberRange(String.format("Enter salary value in the range [%d-%d]", MIN_SALARY, MAX_SALARY),
                        "Wrong salary value", MIN_SALARY, MAX_SALARY).intValue();
        HashSet<String> departmentsSet = new HashSet<>(List.of(DEPARTMENTS));
        String department = io.readStringOptions("Enter department from " + departmentsSet,
                "Must be one out from " + departmentsSet, departmentsSet);

        if (className == "") {
            empl = new Employee(id, salary, department);
        }

        if (className == "Manager") {
            float factor = io.readNumberRange(String.format("Enter factor value in the range [%d-%d]", MIN_FACTOR, MAX_FACTOR), 
            "Wrong factor format", MIN_FACTOR, MAX_FACTOR).floatValue();
            empl = new Manager(id, salary, department, factor);
        }

        if (className == "WageEmployee" || className == "SalesPerson") {
            int wage = io.readNumberRange(String.format("Enter wage value in the range [%d-%d]", MIN_WAGE, MAX_WAGE),
             "Wrong wage format", MIN_WAGE, MAX_WAGE).intValue();
            int hours = io.readNumberRange(String.format("Enter hours value in the range [%d-%d]", MIN_HOURS, MAX_HOURS),
             "Wrong hour format", MIN_HOURS, MAX_HOURS).intValue();
            if (className == "SalesPerson") {
                float percent = io.readNumberRange(String.format("Enter percent value in the range [%d-%d]", MIN_PERCENT, MAX_PERCENT), 
                "Wrong percent format", MIN_PERCENT, MAX_PERCENT).floatValue();
                long sales = io.readNumberRange(String.format("Enter sales value in the range [%d-%d]", MIN_SALES, MAX_SALES),
                 "Wrong sales format", MIN_SALES, MAX_SALES).longValue();
                empl = new SalesPerson(id, salary, department, wage, hours, percent, sales);
            }
            empl = new WageEmployee(id, salary, department, wage, hours);
        }
        company.addEmployee(empl);
        io.writeLine("You are added the following Employee into the company");
        io.writeLine(empl);
    }

    static void addWageEmployee(InputOutput io) {
        addEmployeeByClassName("WageEmployee", io);
    }

    static void addSalesPerson(InputOutput io) {
        addEmployeeByClassName("SalesPerson", io);
    }

    static void addManager(InputOutput io) {
        addEmployeeByClassName("Manager", io);
    }

    static void displayEmployeeData(InputOutput io) {
        io.writeLine("\n");
        long id = io.readNumberRange(String.format("Enter ID value in the range [%d-%d]", MIN_ID, MAX_ID),
                "Wrong or non-exist ID value", MIN_ID, MAX_ID).longValue();
        io.writeLine(company.getEmployee(id));

    }

    static void fireEmployee(InputOutput io) {
        io.writeLine("\n");
        long id = io.readNumberRange(String.format("Enter ID value in the range [%d-%d]", MIN_ID, MAX_ID),
                "Wrong or non-exist ID value", MIN_ID, MAX_ID).longValue();
        io.writeLine(company.removeEmployee(id));
    }

    static void displayDepartmentSalaryBudget(InputOutput io) {
        HashSet<String> departmentsSet = new HashSet<>(List.of(DEPARTMENTS));
        String department = io.readStringOptions("Enter department from " + departmentsSet,
                "Must be one out from " + departmentsSet, departmentsSet);
        io.writeLine(company.getDepartmentBudget(department));
    }

    static void displayListOfDepartments(InputOutput io) {
        HashSet<String> departmentsSet = new HashSet<>(List.of(DEPARTMENTS));
        io.writeLine(departmentsSet);
    }

    static void displayManagersWithMostFactor(InputOutput io) {
        io.writeLine(company.getManagersWithMostFactor());
    }
}
