package br.com.file.service.group;

/**
 * Created by inafalcao on 9/15/15.
 */
public enum MessageType {

    ELECTION((short) 0),
    GET_AVALIABILITY((short)1),
    SEND_AVALIABILITY((short)2);

    short code;

    MessageType(short code) {
        this.code = code;
    }
}
