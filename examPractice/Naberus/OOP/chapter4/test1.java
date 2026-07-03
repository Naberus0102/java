package examPractice.Naberus.OOP.chapter4;

public class test1 {
    public static void main(String[] args) {
        //declare an object of the student class and assign values to its attributes (empty values)
        student1 st1 = new student1("Eric", 20);//constructor
        //use . to access the attributes of the object and assign values to them
        System.out.println("Student name: " + st1.name + ", age: " + st1.age);
        st1.learnJava();
    }
}
