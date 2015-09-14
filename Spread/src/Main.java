import spread.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Main {

    public static void main(String args[]) {
        try {

            // Creating a group

            SpreadGroup group = new SpreadGroup();
            String groupName = "group";
            group.join(GroupConnection.getInstance().getConnection(), groupName);

            // Adding servers

            Server s1 = new Server();
            s1.connectToGroup(groupName);

            Server s2 = new Server();
            s1.connectToGroup(groupName);

            Server s3 = new Server();
            s1.connectToGroup(groupName);



            BasicMessageListener listener = new BasicMessageListener() {
                @Override
                public void messageReceived(SpreadMessage message) {
                    s1.processMessage(message);
                    s2.processMessage(message);
                    s3.processMessage(message);
                }
            };
            GroupConnection.getInstance().getConnection().add(listener);

        } catch (SpreadException e) {
            e.printStackTrace();
        }

    }


}
