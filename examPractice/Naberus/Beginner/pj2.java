package examPractice.Naberus.Beginner;
import java.util.Scanner;
public class pj2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter your age:");
        int age = scanner.nextInt();
        System.out.printf("Enter your name:");
        String name = scanner.next();
        System.out.printf("I am %d years old and my name is %s%n", age, name);
        scanner.close();
    }
}
