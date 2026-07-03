package examPractice.Naberus.OOP.chapter4;

public class test {
    int totalScore(int mathScore, int scienceScore, int englishScore) {
        return mathScore + scienceScore + englishScore;
    }
    public static void main(String[] args) {
        //declare an object of the student class and assign values to its attributes (empty values)
        student st1 = new student();
        test t1 = new test();
        //use . to access the attributes of the object and assign values to them
        st1.name = "Eric";
        st1.age = 20;
        int total = t1.totalScore(85, 90, 88);
        System.out.println("Student name: " + st1.name + ", age: " + st1.age);
        System.out.println("Total score: " + total);
    }
}
