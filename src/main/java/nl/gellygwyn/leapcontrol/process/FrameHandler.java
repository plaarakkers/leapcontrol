package nl.gellygwyn.leapcontrol.process;

import com.leapmotion.leap.Frame;

/**
 * Holder for a {@link FrameAction} that handles the global processing of the frame.
 */
public class FrameHandler {

    private final FrameAction frameAction;

    private boolean enabled;

    /**
     * Constructor
     *
     * @param frameAction The frame action
     */
    public FrameHandler(FrameAction frameAction) {
        this(frameAction, true);
    }

    /**
     * Constructor
     *
     * @param frameAction The frame action
     * @param enabled Indicator if the frame handler is active
     */
    public FrameHandler(FrameAction frameAction, boolean enabled) {
        this.frameAction = frameAction;
        this.enabled = enabled;
    }

    /**
     * Returns the name of the frame action in the handler.
     *
     * @return The name of the frame action.
     */
    public String getActionName() {
        return frameAction.getName();
    }

    /**
     * Calls the process frame of the frame action.
     *
     * @param frame The frame to use in the process.
     */
    public void processFrame(Frame frame) {
        if (enabled) {
            frameAction.processFrame(frame);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
