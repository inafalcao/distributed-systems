package br.com.file.service.group;


import br.com.file.service.component.SlaveGroup;
import br.com.file.service.model.RemoteFile;
import br.com.file.service.model.RemoteFileOperations;
import br.com.file.service.service.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import spread.AdvancedMessageListener;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Slave implements Serializable, RemoteFileOperations {

    private int id;

    private boolean isBusy = false;
    private boolean isFileAvaliable = true;

    private RemoteFile remoteFile;

    private boolean waitingStatus = false;
    private int count = 0;

    @Autowired
    RemoteFileService service;

    private SpreadGroup spreadGroup;
    private String groupName;

    public Slave() {
        id = IdGenerator.getInstance().getId();
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
    }

    /**
     * Ask whether anyone possesses the same file
     */
    public void askAvailabilityMessage() {
        waitingStatus = true;
        SpreadMessage message = new SpreadMessage();
        try {
            message.setType(MessageType.GET_AVALIABILITY.code);
            message.setObject(remoteFile.getId()+";"+this.id);
            message.addGroup(groupName);
            message.setReliable();
            GroupConnection.getInstance().getConnection().multicast(message);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    public void sendAvailabilityMessage(boolean isAvaliable) {

        SpreadMessage message = new SpreadMessage();
        try {
            message.setType(MessageType.SEND_AVALIABILITY.code);
            message.setObject(isAvaliable);
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
        switch (message.getType()) {
            case 0: break;
            case 1: processGetAvailability(message); break;
            case 2: processSendAvailability(message); break;
            default: break;
        }
    }

    /* Quando chega a mensagem de pedindo disponibilidae */
    private void processGetAvailability(SpreadMessage message) {


        String mes;
        String trimmedMessage[] = new String[0];
        try {
            mes = (String) message.getObject();
            trimmedMessage = mes.split(";");
        } catch (SpreadException e) {
            e.printStackTrace();
        }

        long fileId = Integer.valueOf(trimmedMessage[0]);
        int id = Integer.valueOf(trimmedMessage[1]);

       if(remoteFile != null && this.id != id) {
           if (remoteFile.getId() == fileId) {
               sendAvailabilityMessage(false);
           } else {
               sendAvailabilityMessage(true);
           }
       } else {
           sendAvailabilityMessage(true);
       }

    }

    /* Quando chega a mensagem de disponibilidae */
    private void processSendAvailability(SpreadMessage message) {
        try {

            count++;

            boolean avl = Boolean.valueOf((Boolean)message.getObject());

            if(!avl)
                isFileAvaliable = Boolean.valueOf((Boolean)message.getObject());

            if(count == SlaveGroup.slaves.size()) {
                count = 0;
                waitingStatus = false;
            }


        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    public RemoteFile getRemoteFile() {
        return remoteFile;
    }

    public void setRemoteFile(RemoteFile remoteFile) {
        this.remoteFile = remoteFile;
    }

    @Override
    public String toString() {
        return "id = " + id;
    }

    @Override
    public  RemoteFile viewFile(RemoteFile file) {
        remoteFile = file;
        changeAvaliability(false);
        RemoteFile returnedFile = null;
        try {
            Thread.sleep(3000);
            returnedFile = Database.getInstance().viewFile(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
            changeAvaliability(true);
        } catch (Exception e) {
            e.printStackTrace();
            changeAvaliability(true);
        }
        changeAvaliability(true);
        return returnedFile;
    }

    @Override
    public void removeFile(Integer id) {

    }

    @Override
    public void editFile(RemoteFile file) {

    }

    @Override
    public void createFile(RemoteFile file) {
        remoteFile = file;
        changeAvaliability(false);
        try {
            Thread.sleep(3000);
            Database.getInstance().createFile(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
            changeAvaliability(true);
        } catch (Exception e) {
            e.printStackTrace();
            changeAvaliability(true);
        }
        changeAvaliability(true);
    }

    public void changeAvaliability(boolean b) {
        isBusy = !b;
        isFileAvaliable = b;
        if(b)
            remoteFile = null;
    }

    public boolean isBusy() {
        return isBusy || !isFileAvaliable;
    }

    public boolean isFileAvaliable() {
        return isFileAvaliable;
    }

    public boolean isWaiting() {
        return waitingStatus;
    }

}
