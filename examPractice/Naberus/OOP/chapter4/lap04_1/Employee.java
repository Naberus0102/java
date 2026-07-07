package examPractice.Naberus.OOP.chapter4.lap04_1;
public class Employee {
    //attributes of the employee class
    private String name;
    private double salary;
    private double bonusRate;

    //method of the employee class
    public void nhapThongTin() {
        System.out.println("Enter employee name: ");
        // code to input name
        System.out.println("Enter employee salary: ");
        // code to input salary
        System.out.print("Enter employee bonus rate: ");
        // code to input bonus rate
    }
    public void xuatThongTin() {
        System.out.println("Employee Name: " + name);
        System.out.println("Employee Salary: " + salary);
        System.out.println("Employee Bonus Rate: " + bonusRate);
    }
    public double getBonusAmount() {
        return salary * bonusRate;
    }
    //constructor of the employee class (no-argument constructor)
    public Employee() {

    }
    //constructor of the employee class (parameterized constructor)
    public Employee(String name, double salary, double bonusRate) {
        this.name = name;
        this.salary = salary;
        this.bonusRate = bonusRate;
    }

    //getter and setter methods for the attributes
    public String getName () {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public double getBonusRate() {
        return bonusRate;
    }
    public void setBonusRate(double bonusRate) {
        this.bonusRate = bonusRate;
    }
}