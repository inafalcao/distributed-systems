import spread.AdvancedMessageListener;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Server {

    private String id;
    private int priority;

    private SpreadGroup spreadGroup;
    private String groupName;

    Lambda join = (SpreadMessage m) -> showJoin(m);

    private Lambda[] actions = {join};

    public Server() {
        id = "SERVER_" + IdGenerator.getInstance().getId();
        priority = IdGenerator.getInstance().getId();
        spreadGroup = new SpreadGroup();
        GroupConnection.getInstance().getConnection().add(listener);
    }

    public void connectToGroup(String group) {
        this.groupName = group;
        try {
            spreadGroup.join(GroupConnection.getInstance().getConnection(), groupName);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
        sendJoinMessage();
    }

    public void sendJoinMessage() {
        SpreadMessage message = new SpreadMessage();
        try {

            message.setObject("join > " + this.toString());
            message.addGroup(groupName);
            message.setReliable();
            GroupConnection.getInstance().getConnection().multicast(message);

        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    AdvancedMessageListener listener = new AdvancedMessageListener() {
        @Override
        public void regularMessageReceived(SpreadMessage message) {
            try {
                System.out.println(id + " SAYS: " + message.getObject());
            } catch (SpreadException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void membershipMessageReceived(SpreadMessage message) {
            try {
                System.out.println((String) message.getObject());
            } catch (SpreadException e) {
                e.printStackTrace();
            }
        }

    };

    public void processMessage(SpreadMessage message) {
        actions[message.getType()].execute(message);
    }

    void showJoin(SpreadMessage message) {

    }

    @Override
    public String toString() {
        return "(" + id + "; " + "PRIORITY: " + priority + ")";
    }
}
