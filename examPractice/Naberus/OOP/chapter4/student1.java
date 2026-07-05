package examPractice.Naberus.OOP.chapter4;

public class student1 {
    //attributes of the student class
    String name;
    int age;
    //constructor of the student class (can declare a constructor with different input)
    public student1(String name1, int age1) { //you can use different variable names in the constructor, but you can also use the same variable names as the attributes of the class, but you need to use "this" keyword to refer to the attributes of the class
        this.name = name1; // this similar to pointer in C++ and it refers to the current object of the class, so this.name refers to the name attribute of the current object of the class
        this.age = age1;
    }
    //Empty constructor of the student class (can declare a constructor with different input)
    public student1() {
    }
    //java will automatically create a default constructor if you dont declare any constructor

    //method of the student class
    public void learnJava() {
        System.out.println(name + " is learning Java.");
        System.out.println("Age: " + age);
    }
}
