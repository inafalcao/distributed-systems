package br.com.file.service.group;


import br.com.file.service.model.RemoteFile;
import br.com.file.service.model.RemoteFileOperations;
import spread.AdvancedMessageListener;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by inafalcao on 9/14/15.
 */
public class Server implements Serializable, RemoteFileOperations {

    private int id;
    private boolean isMaster = false;

    private Map<String, Integer> knownServers;

    private SpreadGroup spreadGroup;
    private String groupName;

    public Server() {
        id = IdGenerator.getInstance().getId();
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
    }

    public void sendElection() {
        this.isMaster = true;
        SpreadMessage message = new SpreadMessage();
        try {
            message.setType(MessageType.ELECTION.code);
            message.setObject(id);
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
        //actions[message.getType()].execute(message);

        switch (message.getType()) {
            case 0: processElection(message); break;
            default: break;
        }

    }

    private void processElection(SpreadMessage message) {
        String mes;
        try {
            mes = ((Integer) message.getObject()) + "";
            int id = Integer.valueOf(mes);

            if (this.id > id) {
                sendElection();

            }
            else {
                if(this.id != id)
                    this.isMaster = false;
            }
        } catch (SpreadException e) {
            e.printStackTrace();
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

        GroupConnection.getInstance().getConnection().remove(listener);

    }

    public boolean isMaster() {
        return isMaster;
    }

    @Override
    public String toString() {
        return "id = " + id;
    }

    @Override
    public RemoteFile viewFile(RemoteFile f) {
        return null;
    }

    @Override
    public void removeFile(Integer id) {

    }

    @Override
    public void editFile(RemoteFile file) {

    }

    @Override
    public void createFile(RemoteFile file) {

    }

    public void setIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }
}
