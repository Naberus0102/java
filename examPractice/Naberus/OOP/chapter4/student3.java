//enclapsure class for the student class
package examPractice.Naberus.OOP.chapter4;

public class student3 {
    //attributes of the student class
    private String name;
    private int age;
    private String address;
    //constructor of the student class (can declare a constructor with different input)
    public student3(String name, int age) { 
    //but you should use the same variable names as the attributes of the class, but you need to use "this" keyword to refer to the attributes of the class
            this.name = name; // this similar to pointer in C++ and it refers to the current object of the class, so this.name refers to the name attribute of the current object of the class
            this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
    //you can generate getter and setter by right clicking, chose source, then chose generate getter and setter
