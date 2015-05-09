package nl.gellygwyn.leapcontrol.process;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import java.util.TimerTask;

/**
 *
 * LeapControlFrameRetrieveTask
 */
public class LeapControlFrameRetrieveTask extends TimerTask {

    private final Controller controller;

    private final FrameHandlers frameHandlers;

    private long lastProcessedFrameId = 0;

    public LeapControlFrameRetrieveTask(Controller controller, FrameHandlers frameHandlers) {
        this.controller = controller;
        this.frameHandlers = frameHandlers;
    }

    @Override
    public void run() {
        Frame frame = controller.frame();
        if (frame.isValid() && frame.id() != lastProcessedFrameId) {
            frameHandlers.processFrame(frame);
            lastProcessedFrameId = frame.id();
        }
    }

}
