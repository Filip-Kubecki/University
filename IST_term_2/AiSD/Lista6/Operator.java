package Lista6;

import java.util.Collection;

public class Operator{
    String name;
    int priority;

    public Operator(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
