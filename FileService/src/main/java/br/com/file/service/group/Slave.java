package br.com.file.service.group;

import br.com.file.service.component.SlaveGroup;
import br.com.file.service.enumeration.Operation;
import br.com.file.service.model.RemoteFile;
import br.com.file.service.model.RemoteFileOperations;
import spread.SpreadGroup;
import java.io.Serializable;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Slave implements Serializable, RemoteFileOperations {

    private static int CREATE_DURATION = 5000;
    private static int READ_DURATION = 5000;
    private static int UPDATE_DURATION = 5000;
    private static int DELETE_DURATION = 5000;

    private int id;

    private  boolean isBusy = false;
    private RemoteFile remoteFile;
    private Operation currentOperation;

    Database database;

    public Slave() {
        id = IdGenerator.getInstance().getId();
        database = new Database();
    }

    public RemoteFile getRemoteFile() {
        if(remoteFile == null)
            remoteFile = new RemoteFile(-1);
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
            Thread.sleep(READ_DURATION);
            returnedFile = database.viewFile(file);

            if (returnedFile!=null)
                file.setContent(returnedFile.getContent());
            else
                file.setContent("Arquivo nao encontrado");
        } catch (InterruptedException e) {
            e.printStackTrace();
            changeAvaliability(true);
        } catch (Exception e) {
            e.printStackTrace();
            changeAvaliability(true);
        }
        changeAvaliability(true);
        file.callBack();
        System.out.println("\nSlave " + this.id + " respondeu READ\n");
        return file;
    }

    @Override
    public RemoteFile removeFile(Integer id) {
        RemoteFile file = new RemoteFile(id);
        remoteFile = file;
        changeAvaliability(false);
        try {
            Thread.sleep(DELETE_DURATION);
            database.removeFile(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
            changeAvaliability(true);
        } catch (Exception e) {
            e.printStackTrace();
            changeAvaliability(true);
        }
        changeAvaliability(true);
        file.callBack();
        showFIles();
        System.out.println("\nSlave " + this.id + " respondeu REMOVE\n");
        return file;
    }

    @Override
    public RemoteFile editFile(RemoteFile file) {
        remoteFile = file;
        changeAvaliability(false);
        try {
            Thread.sleep(UPDATE_DURATION);
            database.editFile(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
            changeAvaliability(true);
        } catch (Exception e) {
            e.printStackTrace();
            changeAvaliability(true);
        }
        changeAvaliability(true);
        file.callBack();
        showFIles();
        System.out.println("\nSlave " + this.id + " respondeu WRITE\n");
        return file;
    }

    @Override
    public RemoteFile createFile(RemoteFile file) {
        remoteFile = file;
        changeAvaliability(false);
        try {
            Thread.sleep(CREATE_DURATION);
            database.createFile(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
            changeAvaliability(true);
        } catch (Exception e) {
            e.printStackTrace();
            changeAvaliability(true);
        }
        changeAvaliability(true);
        file.callBack();
        showFIles();
        System.out.println("\nSlave " + this.id + " respondeu CREATE\n");
        return file;
    }

    public void changeAvaliability(boolean b) {
        isBusy = !b;
        if (isBusy)
            remoteFile = null;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setIsBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public Operation getCurrentOperation() {
        return currentOperation;
    }

    public void setCurrentOperation(Operation currentOperation) {
        this.currentOperation = currentOperation;
    }

    public void processOperation(Operation op, RemoteFile file) {
        switch (op.getCode()) {
            case 0:  viewFile(file);break;
            case 1: replicar(Operation.WRITE, file);  editFile(file);break;
            case 2: replicar(Operation.REMOVE, file);  removeFile(file.getId());break;
            case 3: replicar(Operation.CREATE, file);  createFile(file);break;
            default: break;
        }
    }

    public void replicar(Operation op, RemoteFile file) {
        switch (op.getCode()) {
            case 0:
                break;
            case 1:
                for (Slave s: SlaveGroup.slaves) {
                    if (s.id != this.id) {
                        s.editFile(file);
                    }
                }
                break;
            case 2:
                for (Slave s: SlaveGroup.slaves) {
                    if (s.id != this.id) {
                        s.removeFile(file.getId());
                    }
                }
            case 3:
                for (Slave s: SlaveGroup.slaves) {
                    if (s.id != this.id) {
                        s.createFile(file);
                    }
                }
                break;
            default: break;
        }
    }

    public void showFIles() {
        System.out.println("Slave " + this.id + " files:");
        this.database.showFiles();
    }

}
