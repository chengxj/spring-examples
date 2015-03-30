package foo;

import java.util.HashMap;
import java.util.Map;

public class Service {
    public Map<String, String> find() {
        System.out.println("find() is running");
        return new HashMap<String, String>() {
            {
                put("name", "Jeoygin");
            }
        };
    }

    public Map<String, String> test() {
        System.out.println("test() is running");
        return find();
    }
}
