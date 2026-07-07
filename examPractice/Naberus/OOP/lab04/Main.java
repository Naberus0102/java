package examPractice.Naberus.OOP.lab04;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        //input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Product 1");
        System.out.println("Enter product name: ");
        String name1 = scanner.nextLine();
        System.out.println("Enter product price: ");
        double price1 = scanner.nextDouble();
        System.out.println("Enter product tax: ");
        double tax1 = scanner.nextDouble();
        Product product1 = new Product(name1, price1, tax1);
        product1.getInfo();
        
        System.out.println("Product 2");
        System.out.println("Enter product name: ");
        String name2 = scanner.next();
        System.out.println("Enter product price: ");
        double price2 = scanner.nextDouble();
        System.out.println("Enter product tax: ");
        double tax2 = scanner.nextDouble();
        Product product2 = new Product(name2, price2, tax2);
        product2.getInfo();

        //output
        System.out.println("--------------------");
        System.out.println("Product 1");
        System.out.println("Name: " + product1.getName());
        System.out.println("Tax Price: " + product1.getTaxPrice());
        System.out.println("Product 2");
        System.out.println("Name: " + product2.getName());
        System.out.println("Tax Price: " + product2.getTaxPrice());
        scanner.close();
    }
}
