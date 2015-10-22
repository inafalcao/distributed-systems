/**
 * Created by inafalcao on 9/14/15.
 */
public class IdGenerator {

    private int currentId = 1;

    private static IdGenerator instance;

    private IdGenerator() {

    }

    public static IdGenerator getInstance() {
        if (instance == null)
            instance = new IdGenerator();
        return instance;
    }

    public int getId() {
        return currentId++;
    }

}
