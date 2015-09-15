/**
 * Created by inafalcao on 9/15/15.
 */
public enum MessageType {

    CONNECT((short) 0),
    DISCONNECT((short)1);

    short code;

    MessageType(short code) {
        this.code = code;
    }
}
