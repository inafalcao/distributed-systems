import spread.SpreadConnection;
import spread.SpreadException;

/**
 * Created by inafalcao on 9/14/15.
 */
public class GroupConnection {

    private SpreadConnection connection;

    private static GroupConnection instance;

    private GroupConnection() {
        connection = new SpreadConnection();
        try {
            connection.connect(null, 0, "privatename", false, false);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    public static GroupConnection getInstance() {
        if (instance == null)
            instance = new GroupConnection();
        return instance;
    }

    public SpreadConnection getConnection() {
        return connection;
    }

}
