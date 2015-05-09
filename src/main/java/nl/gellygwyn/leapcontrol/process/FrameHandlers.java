package nl.gellygwyn.leapcontrol.process;

import com.leapmotion.leap.Frame;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import nl.gellygwyn.leapcontrol.LeapControlRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * List class that handles a list of {@link FrameHandler}s.
 */
public class FrameHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrameHandlers.class);

    private final Map<String, FrameHandler> frameHandlers = new HashMap<>();

    public void addHandler(FrameHandler frameHandler) {
        if (frameHandlers.containsKey(frameHandler.getActionName())) {
            LOGGER.error("Handler with name {} is already added.", frameHandler.getActionName());
            throw new LeapControlRuntimeException("Error adding handler.");
        }

        frameHandlers.put(frameHandler.getActionName(), frameHandler);
    }

    /**
     * Calls the processing of the frame for each framehandler.
     *
     * @param frame The frame to process.
     */
    public void processFrame(Frame frame) {
        frameHandlers.values().stream().forEach(f -> processFrameWithErrorHandling(f, frame));
    }

    private void processFrameWithErrorHandling(FrameHandler frameHandler, Frame frame) {
        try {
            frameHandler.processFrame(frame);
        } catch (Exception e) {
            LOGGER.error("Exception while processing frame", e);
        }
    }

    public Collection<String> getFrameHandlerNames() {
        return frameHandlers.values().stream().map(FrameHandler::getActionName).sorted().collect(Collectors.toList());
    }

    public boolean isEnabled(String name) {
        return getFrameHandler(name).isEnabled();
    }

    public void setEnabled(String name, boolean enabled) {
        getFrameHandler(name).setEnabled(enabled);
    }

    private FrameHandler getFrameHandler(String name) {
        FrameHandler frameHandler = frameHandlers.get(name);
        if (frameHandler == null) {
            throw new LeapControlRuntimeException(String.format("No frame handler with name %s found", name));
        }

        return frameHandler;
    }
}
