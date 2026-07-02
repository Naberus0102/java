package examPractice.Naberus;
import java.util.Scanner;
public class lab01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your score: ");
        int number = scanner.nextInt();
        System.out.println("Enter your name: ");
        String name = scanner.next();//notice
        System.out.println("You score: " + number + ", your name is: " + name);
        scanner.close();
    }
}
