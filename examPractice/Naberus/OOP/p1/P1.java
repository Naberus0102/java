package examPractice.Naberus.OOP.p1;

public class P1 {
    public static void main(String[] args) {
        P firstObject = new P();
        firstObject.a = 10; //public can be accessed from anywhere
        firstObject.b = 20; //default can be accessed from the same package
        firstObject.c = 30; //protected can be accessed from the same package and subclasses
       // firstObject.d = 40; //private can only be accessed from the same class
    }
}
