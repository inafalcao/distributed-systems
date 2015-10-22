import spread.AdvancedMessageListener;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.io.Serializable;
import java.util.*;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Server implements Serializable, Re {

    private String id;
    private int priority;

    private Map<String, Integer> knownServers;

    private SpreadGroup spreadGroup;
    private String groupName;

    private Lambda join = (SpreadMessage m) -> processJoin(m);
    private Lambda leave = (SpreadMessage m) -> processLeave(m);

    private Lambda[] actions = {join, leave};

    public Server() {
        id = "SERVER_" + IdGenerator.getInstance().getId();
        priority = IdGenerator.getInstance().getId();
        spreadGroup = new SpreadGroup();
        GroupConnection.getInstance().getConnection().add(listener);
        knownServers = new HashMap<>();
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
            message.setType(MessageType.CONNECT.code);
            message.setObject(id + ";" + priority);
            message.addGroup(groupName);
            message.setReliable();
            GroupConnection.getInstance().getConnection().multicast(message);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    public void sendLeaveMessage() {
        SpreadMessage message = new SpreadMessage();
        try {
            message.setType(MessageType.DISCONNECT.code);
            message.setObject(id + ";" + priority);
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
                processMessage(message);
            } catch (SpreadException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void membershipMessageReceived(SpreadMessage message) {

        }

    };

    public void processMessage (SpreadMessage message) throws SpreadException {
        actions[message.getType()].execute(message);
    }

    private void processJoin(SpreadMessage message) {
        String mes;
        String trimmedMessage[] = new String[0];
        try {
            mes = (String) message.getObject();
            trimmedMessage = mes.split(";");
        } catch (SpreadException e) {
            e.printStackTrace();
        }

        knownServers.put(trimmedMessage[0], Integer.valueOf(trimmedMessage[1]));
        System.out.println(id + " SAYS:\n [" + trimmedMessage[0] + "; PRIORITY " + trimmedMessage[1] + "] joined the group");
        System.out.println(" Master now is " + findMaster() + "\n");
        showMembers();
    }

    private void processLeave(SpreadMessage message) {
        String processedMessage;
        String trimmedMessage[] = new String[0];
        try {
            processedMessage = (String) message.getObject();
            trimmedMessage = processedMessage.split(";");
        } catch (SpreadException e) {
            e.printStackTrace();
        }

        if(!trimmedMessage[0].equals(id)) {
            knownServers.remove(trimmedMessage[0]);

            System.out.println(id + " SAYS:\n [" + trimmedMessage[0] + "; PRIORITY " + trimmedMessage[1] + "] left the group");
            System.out.println(" Master now is " + findMaster() + "\n");
            showMembers();
        }
    }

    public String findMaster() {
        Iterator it = knownServers.entrySet().iterator();
        int prioritary = -1;
        String master = "";
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            if(((int)pair.getValue()) > prioritary) {
                prioritary = (int)pair.getValue();
                master = "[" + pair.getKey() + "; PRIORITY " + pair.getValue() + "]";
            }
        }
        return master;
    }

    private void showMembers() {
        Iterator it = knownServers.entrySet().iterator();
        String members = "-- Group Members\n";
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            members += "[" + pair.getKey() + "; PRIORITY " + pair.getValue() + "]\n";
        }
        System.out.println(members);
        System.out.println("--------------------------------------------");
    }

    public void leave() {
        sendLeaveMessage();
        try {
            this.spreadGroup.leave();
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "(" + id + "; " + "PRIORITY: " + priority + ")";
    }
}
