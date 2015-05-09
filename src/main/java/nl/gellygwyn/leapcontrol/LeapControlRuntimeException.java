package nl.gellygwyn.leapcontrol;

/**
 * Runtime exception for the leap control application.
 *
 */
public class LeapControlRuntimeException extends RuntimeException {

    /**
     * Creates a new instance of <code>LeapControlException</code> without detail message.
     */
    public LeapControlRuntimeException() {
    }

    /**
     * Constructs an instance of <code>LeapControlException</code> with the specified detail message.
     *
     * @param msg The detail message.
     */
    public LeapControlRuntimeException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>LeapControlException</code> with the specified cause.
     *
     * @param cause The orginial exception.
     */
    public LeapControlRuntimeException(Throwable cause) {
        super(cause);
    }

}
