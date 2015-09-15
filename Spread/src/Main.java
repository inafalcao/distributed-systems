import spread.SpreadException;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Main {

    public static void main(String args[]) {

        String groupName = "group";

        // Adding servers

        Server s1 = new Server();
        s1.connectToGroup(groupName);

        Server s2 = new Server();
        s2.connectToGroup(groupName);

        Server s3 = new Server();
        s3.connectToGroup(groupName);

        try {
            Thread.sleep(5000);
            GroupConnection.getInstance().getConnection().disconnect();
        } catch (SpreadException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
