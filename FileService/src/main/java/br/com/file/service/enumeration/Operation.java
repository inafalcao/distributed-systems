package br.com.file.service.enumeration;

/**
 * Created by inafalcao on 9/15/15.
 */
public enum Operation {

    READ((short) 0),
    WRITE((short)1),
    REMOVE((short)2),
    CREATE((short)3);

    short code;

    Operation(short code) {
        this.code = code;
    }


    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }
}
