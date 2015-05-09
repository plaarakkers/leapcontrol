package nl.gellygwyn.leapcontrol.process;

import com.leapmotion.leap.Frame;

/**
 * Action that processes a frame from
 *
 */
public interface FrameAction {

    /**
     * Returns the name for this action. The name should be unique for the list of actions
     *
     * @return The name for the action.
     */
    String getName();

    void processFrame(Frame frame);
}
