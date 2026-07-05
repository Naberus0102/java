package examPractice.Naberus.OOP.lab04;
import java.util.Scanner;
public class product {
    private String name;
    private double price;
    private double taxRate;
    //constructor
    public product(String name, double price, double taxRate){
        this.name = name;
        this.price = price;
        this.taxRate = taxRate;
    }
    //method
    public void input(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product name: ");
        this.name = sc.nextLine();
        System.out.print("Enter product price: ");
        this.price = sc.nextDouble();
        System.out.print("Enter tax rate: ");
        this.taxRate = sc.nextDouble();
        sc.close();
    }
    public void output(){
        System.out.println("Product name: " + this.name);
        System.out.println("Product price: " + this.price);
        System.out.println("Tax rate: " + this.taxRate);
    }
    //function
    public double getTaxPrice(double price, double taxRate){
        return price * taxRate;
    }
    //main
    public static void main(String[] args) {
        System.out.println("Tax price: ");
    }
}
