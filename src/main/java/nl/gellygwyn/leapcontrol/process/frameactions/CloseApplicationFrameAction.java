package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import nl.gellygwyn.leapcontrol.process.FrameAction;

/**
 *
 * {@link FrameAction} that sends a close application call if the conditions are met. Conditions: New closed right hand detected. Action Send the ALT + F4 key combination.
 */
public class CloseApplicationFrameAction implements FrameAction {

    private int handId;

    private final Robot robot;

    public CloseApplicationFrameAction(Robot robot) {
        this.robot = robot;
    }

    @Override
    public String getName() {
        return "Close application";
    }

    @Override
    public void processFrame(Frame frame) {
        if (frame.hands().count() == 1) {
            Hand hand = frame.hands().get(0);
            if (hand.isValid() && hand.id() != handId && hand.isRight() && hand.fingers().extended().isEmpty()) {

                robot.keyPress(KeyEvent.VK_ALT);
                robot.keyPress(KeyEvent.VK_F4);

                robot.keyRelease(KeyEvent.VK_F4);
                robot.keyRelease(KeyEvent.VK_ALT);

                handId = hand.id();
            }
        }
    }

}
