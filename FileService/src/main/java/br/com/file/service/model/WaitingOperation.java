package br.com.file.service.model;

import br.com.file.service.enumeration.Operation;

/**
 * Created by inafalcao on 11/29/15.
 */
public class WaitingOperation {

    private RemoteFile file;
    private Operation operation;

    public WaitingOperation() {

    }

    public WaitingOperation(RemoteFile file, Operation operation) {
        this.file = file;
        this.operation = operation;
    }

    public RemoteFile getFile() {
        return file;
    }

    public void setFile(RemoteFile file) {
        this.file = file;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
