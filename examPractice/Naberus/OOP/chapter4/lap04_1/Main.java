package examPractice.Naberus.OOP.chapter4.lap04_1;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of employees: ");
        int n = scanner.nextInt();
        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter information for employee " + (i + 1) + ":");
            employees[i] = new Employee();
            employees[i].nhapThongTin();
        }
        System.out.println("Employee information:");
        for (int i = 0; i < n; i++) {
            employees[i].xuatThongTin();
            System.out.println("Bonus amount: " + employees[i].getBonusAmount());
        }
        scanner.close();
    }
}
