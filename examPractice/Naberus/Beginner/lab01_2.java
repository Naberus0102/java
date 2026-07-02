package examPractice.Naberus.Beginner;
import java.util.Scanner;
public class lab01_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length: ");
        int length = scanner.nextInt();
        System.out.print("Enter width: ");
        int width = scanner.nextInt();
        int area = length * width;
        System.out.println("The area is: " + area);
        System.out.println("The perimeter is: " + (2 * (length + width)));
        System.out.println("Min of length and width is: " + Math.min(length, width));
        scanner.close();
    }
}
