package co.edu.eci.arep.Taller;

import javax.swing.plaf.PanelUI;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MicroServer {

    //cargan los metodos
    public static Map<String, Method> services = new HashMap();

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        loadComponents(args);
        System.out.println(simulateRequest("/greeting"));
        System.out.println(simulateRequest("/pi"));
        System.out.println(simulateRequest("/e"));

    }

    private static String simulateRequest(String route) throws InvocationTargetException, IllegalAccessException {
        Method m = services.get(route);
        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application//json\r\n"
                +"\r\n"
                +"{\"resp\":\"" + m.invoke(null, "pedro") + "\"}";
        return response;
    }

    private static void loadComponents(String[] args) throws ClassNotFoundException {

        for(int i=0; i < args.length; i++){

            Class c = Class.forName(args[i]);

            if(!c.isAnnotationPresent(RestController.class)){
                System.exit(0);
            }

            for(Method m : c.getDeclaredMethods()){

                if(m.isAnnotationPresent(GetMapping.class)){
                    GetMapping a = m.getAnnotation(GetMapping.class);
                    services.put(a.value(), m);
                }
            }
        }

    }

}
