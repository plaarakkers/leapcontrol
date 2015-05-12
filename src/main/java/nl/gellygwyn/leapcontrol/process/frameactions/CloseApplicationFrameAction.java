package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import nl.gellygwyn.leapcontrol.process.FrameAction;

/**
 *
 * {@link FrameAction} that sends a close application call if the conditions are met. Conditions: Closed (no more then
 * one extended finger detected) right hand detected after open (five extended fingers detected) right hand detected.
 * Action: Send the ALT + F4 key combination.
 */
public class CloseApplicationFrameAction implements FrameAction {

    private int processedHandId;

    private final Robot robot;

    private int previousExtendedFingersCount;

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
            //only process for valid right hands and a different detected hand
            if (hand.isValid() && hand.isRight() && hand.id() != processedHandId) {
                if (hand.fingers().extended().count() <= 1 && previousExtendedFingersCount == 5) {
                    performAction();
                    processedHandId = hand.id();
                    previousExtendedFingersCount = 0;
                } else {
                    previousExtendedFingersCount = hand.fingers().extended().count();
                }
            }
        }
    }

    private void performAction() {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F4);

        robot.keyRelease(KeyEvent.VK_F4);
        robot.keyRelease(KeyEvent.VK_ALT);
    }

}
