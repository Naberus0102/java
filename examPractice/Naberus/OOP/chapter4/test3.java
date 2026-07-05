package examPractice.Naberus.OOP.chapter4;
//enclapsure class for the student class
public class test3 {
    public static void main(String[] args) {
        
        //firstObject.name = "John"; //private can only be accessed from the same class
        //firstObject.age = 20; //private can only be accessed from the same class
        //firstObject.mathScore = 90; //private can only be accessed from the same class
        //firstObject.scienceScore = 85; //private can only be accessed from the same class
        //firstObject.englishScore = 95; //private can only be accessed from the same class
        student3 firstObject = new student3("John", 20);
        firstObject.setAge(90);
        firstObject.setName("Jon");
        System.out.println("Student name: " + firstObject.getName() + ", age: " + firstObject.getAge());
    }
}
