package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import nl.gellygwyn.leapcontrol.process.FrameAction;

/**
 *
 * {@link FrameAction} that sends a minimize application call if the conditions are met. Conditions: At least extended
 * index finger of right hand detected in two frames with a quick swipe to the left (>75 for the x-coordinate. Action:
 * Send the CTRL + ALT + 0 key combination.
 */
public class MinimizeApplicationFrameAction implements FrameAction {

    private int processedHandId;

    private float previousIndexFingerXPosition = Integer.MIN_VALUE;

    private final Robot robot;

    public MinimizeApplicationFrameAction(Robot robot) {
        this.robot = robot;
    }

    @Override
    public String getName() {
        return "Minimize application";
    }

    @Override
    public void processFrame(Frame frame) {
        if (frame.hands().count() == 1) {
            Hand hand = frame.hands().get(0);
            //only process the if a valid right hand with at least the extended index finger is detected
            if (hand.isValid() && hand.isRight() && hand.id() != processedHandId && hand.fingers().extended()
                .fingerType(Finger.Type.TYPE_INDEX).count() == 1) {
                Finger indexFinger = hand.fingers().extended().fingerType(Finger.Type.TYPE_INDEX).get(0);
                if (indexFinger.isValid()) {
                    if (previousIndexFingerXPosition - indexFinger.tipPosition().getX() > 75) {
                        performAction();
                        processedHandId = hand.id();
                        previousIndexFingerXPosition = Integer.MIN_VALUE;
                    } else {
                        previousIndexFingerXPosition = indexFinger.tipPosition().getX();
                    }
                }

            }
        }
    }

    private void performAction() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_0);

        robot.keyRelease(KeyEvent.VK_0);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

}
