import java.io.*;
import java.util.*;

public class Javareader
{
    Scanner sc = new Scanner(new File("F:\\CSVDemo.csv"));
    sc.useDelimiter(",");   //sets the delimiter pattern
    while (sc.hasNext())  //returns a boolean value
    {
        System.out.print(sc.next());  //find and returns the next complete token from this scanner
    }

}
