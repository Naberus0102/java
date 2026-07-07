package examPractice.Naberus.Data_structure_and_String_Manipulation.ArrayList;
import java.util.ArrayList;
public class test {
    public static void main(String[] args) {
        //using constructor to create non primitive data type
        //generic data type
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        System.out.println(list + " " + list.size() + " " + list.get(0) + " " + list.get(1));
    }
}
