import spread.SpreadException;
import spread.SpreadMessage;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Server {

    private String id;
    private int priority;
    private String group;

    Lambda join = (SpreadMessage m) -> showJoin(m);

    private Lambda[] actions = {join};

    public Server() {
        id = "SERVER_" + IdGenerator.getInstance().getId();
        priority = IdGenerator.getInstance().getId();
    }

    public void connectToGroup(String group) {
        this.group = group;
        sendJoinMessage();
    }

    public void sendJoinMessage() {
        SpreadMessage message = new SpreadMessage();
        try {

            message.setObject("join > " + this.toString());
            message.addGroup(group);
            message.setReliable();
            GroupConnection.getInstance().getConnection().multicast(message);

        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

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
