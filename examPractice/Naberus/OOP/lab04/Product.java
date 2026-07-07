package examPractice.Naberus.OOP.lab04;

public class Product {
    //attributes
    private String name;
    private double price;
    private double tax;

    //methods
    public void setInfo(String name, double price, double tax) {
        this.name = name;
        this.price = price;
        this.tax = tax;
    }
    public void getInfo() {
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Tax: " + tax);
    }

    public double getTaxPrice() {
        return tax * price;
    }

    //constructor
    public Product(String name, double price, double tax) {
        this.name = name;
        this.price = price;
        this.tax = tax;
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getTax() {
        return tax;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }
}
