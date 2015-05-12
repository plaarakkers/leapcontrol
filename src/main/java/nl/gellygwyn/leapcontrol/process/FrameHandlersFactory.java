package nl.gellygwyn.leapcontrol.process;

import java.awt.AWTException;
import java.awt.Robot;
import nl.gellygwyn.leapcontrol.LeapControlRuntimeException;
import nl.gellygwyn.leapcontrol.process.frameactions.CloseApplicationFrameAction;
import nl.gellygwyn.leapcontrol.process.frameactions.LoggingFrameAction;
import nl.gellygwyn.leapcontrol.process.frameactions.MinimizeApplicationFrameAction;

/**
 * Factory class for initializing the {@link FrameHandlers}
 */
public class FrameHandlersFactory {

    private static final FrameHandlers frameHandlers = new FrameHandlers();

    private static final Robot robot;

    private FrameHandlersFactory() {
    }

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new LeapControlRuntimeException(e);
        }
        frameHandlers.addHandler(new FrameHandler(new LoggingFrameAction(), false));
        frameHandlers.addHandler(new FrameHandler(new CloseApplicationFrameAction(robot)));
        frameHandlers.addHandler(new FrameHandler(new MinimizeApplicationFrameAction(robot)));
    }

    public static FrameHandlers getFrameHandlers() {
        return frameHandlers;
    }
}
