package co.edu.eci.arep.appServer;

import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MicroServer {

    //cargan los metodos
    public static Map<String, Method> services = new HashMap();
    public static final Map<Class<?>, Object> instances = new HashMap();

    /**
     * Main method to simulate requests to the server
     * @param args
     */
    public static void main(String[] args) {
        try {
            loadComponents();
            System.out.println(simulateRequest("/greeting"));
            System.out.println(simulateRequest("/greeting?name=Andrea"));
            System.out.println(simulateRequest("/greeting?name=User3"));
            System.out.println(simulateRequest("/pi"));
            System.out.println(simulateRequest("/e"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Simule a request to the server and return the response as a string in the HTTP format
     * @param url the url of the request
     * @return the response as a string in the HTTP format
     */
    private static String simulateRequest(String url) {

        try {
            String route = url.split("\\?")[0];
            String queryString = url.contains("?") ? url.split("\\?")[1] : "";

            Method m = services.get(route);
            if (m == null) {
                return "HTTP/1.1 404 NOT FOUND\r\n\r\nRuta no encontrada";
            }

            Object instance = instances.get(m.getDeclaringClass());
            Object[] params = extractParams(m, queryString);

            Object response = m.invoke(instance, params);

            return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\"resp\":\"" + response + "\"}";

        } catch (Exception e) {
            return "HTTP/1.1 500 INTERNAL SERVER ERROR\r\n\r\nError interno del servidor";
        }
    }

    /**
     * Extract the parameters from the query string
     * @param m the method to extract the parameters
     * @param queryString the query string
     * @return the parameters extracted from the query string
     */
    private static Object[] extractParams(Method m, String queryString) {
        String[] queryPairs = queryString.split("&");
        Map<String, String> queryParams = new HashMap<>();

        for (String pair : queryPairs) {
            String[] entry = pair.split("=");
            if (entry.length == 2) {
                queryParams.put(entry[0], entry[1]);
            }
        }

        Parameter[] parameters = m.getParameters();
        Object[] paramValues = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            RequestParam annotation = parameters[i].getAnnotation(RequestParam.class);
            if (annotation != null) {
                String paramName = annotation.value();
                String defaultValue = annotation.defaultValue();

                // Aquí se obtiene el valor del parámetro desde la URL
                String value = queryParams.getOrDefault(paramName, defaultValue);
                paramValues[i] = value; // Asigna el valor correcto
            }
        }
        return paramValues;
    }

    /**
     * Load the components of the server
     * @throws Exception
     */
    static void loadComponents() {
        String packageName = "co.edu.eci.arep.appServer";
        String[] classNames = {
                "MathController",
                "GreetingController"
        };

        for (String className : classNames) {
            try {
                Class<?> c = Class.forName(packageName + "." + className);
                if (c.isAnnotationPresent(RestController.class)) {
                    Object instance = c.getDeclaredConstructor().newInstance();
                    instances.put(c, instance);

                    for (Method m : c.getDeclaredMethods()) {
                        if (m.isAnnotationPresent(GetMapping.class)) {
                            GetMapping annotation = m.getAnnotation(GetMapping.class);
                            services.put(annotation.value(), m);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
