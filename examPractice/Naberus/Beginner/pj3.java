package examPractice.Naberus.Beginner;
import java.util.Scanner;
public class pj3 {
    public static void main(String[] args) {
    int a,b;
    Scanner scanner = new Scanner(System.in);
    System.out.print("First number: ");
    a = scanner.nextInt();
    System.out.print("Second number: ");
    b = scanner.nextInt();
    int c = (Math.max(a, b));
    System.out.printf("The maximum value is: %d%n", c);
    System.out.printf("The minimum value is: %d%n", Math.min(a, b));
    scanner.close();
    }
}
