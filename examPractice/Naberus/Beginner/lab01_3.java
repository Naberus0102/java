package examPractice.Naberus.Beginner;
import java.util.Scanner;
public class lab01_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length: ");
        int length = scanner.nextInt();
        System.out.println("The volume is: " + Math.pow(length, 3));
        scanner.close();
    }
}
