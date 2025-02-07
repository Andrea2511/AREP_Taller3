package co.edu.eci.arep.WorkClass;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;

public class InvokePrintMembers {

    public static void main(String... args) {
        try {

            //Creation
            Class<?> c = Class.forName(args[0]);
            Class[] argTypes = new Class[]{Member[].class, String.class};
            Method m = c.getDeclaredMethod("printMembers", argTypes);

            Class otraClase = LinkedList.class;
            System.out.format("invoking %s.printMembers()%n", c.getName());
            m.invoke(null, otraClase.getDeclaredFields(), "Fields");
            // production code should handle these exceptions more gracefully
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
