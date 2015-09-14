import spread.SpreadConnection;
import spread.SpreadException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by inafalcao on 9/14/15.
 */
public class GroupConnection {

    private SpreadConnection connection;

    private static GroupConnection instance;

    private GroupConnection() {
        connection = new SpreadConnection();
        try {
            connection.connect(InetAddress.getByName("daemon.address.com"), 0, "privatename", false, false);
        } catch (SpreadException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
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
