package examPractice.Naberus.OOP.p3;
import examPractice.Naberus.OOP.p1.P;
public class P3 extends examPractice.Naberus.OOP.p1.P {

    private void accessMembers() {
        P firstObject = new P();
        firstObject.a = 10; //public can be accessed from anywhere
       // firstObject.b = 20; //default can be accessed from the same package
        super.c = 30; //protected can be accessed from the same package and subclasses
       // firstObject.d = 40; //private can only be accessed from the same class
    }
    public static void main(String[] args) {
    }
}
