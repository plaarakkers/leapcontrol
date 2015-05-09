package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.Frame;
import nl.gellygwyn.leapcontrol.process.FrameAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging {@link FrameAction} that logs the call to the process for debugging purposes.
 */
public class LoggingFrameAction implements FrameAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFrameAction.class);

    @Override
    public String getName() {
        return "Log process call";
    }

    @Override
    public void processFrame(Frame frame) {
        LOGGER.info(String.format("process frame called with frame id [%d] and [%d] hands detected", frame.id(), frame.hands().count()));
    }
}
