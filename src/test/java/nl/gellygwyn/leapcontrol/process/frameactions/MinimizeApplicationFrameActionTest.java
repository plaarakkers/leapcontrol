package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Vector;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import static org.mockito.Matchers.anyInt;
import org.mockito.Mock;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the {@link MinimizeApplicationFrameAction}
 */
@RunWith(MockitoJUnitRunner.class)
public class MinimizeApplicationFrameActionTest extends BaseFrameActionTest {

    @Mock
    private Robot robot;

    @Mock
    private HandList frame1RightHandList;

    @Mock
    private HandList frame2RightHandList;

    @Mock
    private Frame frame1;

    @Mock
    private Frame frame2;

    @Before
    public void before() {
        frameAction = new MinimizeApplicationFrameAction(robot);

        when(frame1.hands()).thenReturn(frame1RightHandList);
        when(frame2.hands()).thenReturn(frame2RightHandList);

        initHandList(frame1RightHandList, 100);
        initHandList(frame2RightHandList, 20);
    }

    private void initHandList(HandList handList, float xPosition) {
        Hand hand = mock(Hand.class);
        when(hand.isValid()).thenReturn(true);
        when(hand.isRight()).thenReturn(true);
        when(hand.id()).thenReturn(1);

        FingerList fingerList = mock(FingerList.class);
        when(hand.fingers()).thenReturn(fingerList);

        FingerList extendedFingerList = mock(FingerList.class);
        when(fingerList.extended()).thenReturn(extendedFingerList);

        FingerList indexFingerList = mock(FingerList.class);
        when(extendedFingerList.fingerType(Finger.Type.TYPE_INDEX)).thenReturn(indexFingerList);

        when(indexFingerList.count()).thenReturn(1);

        Finger indexFinger = mock(Finger.class);
        when(indexFingerList.get(0)).thenReturn(indexFinger);
        when(indexFinger.isValid()).thenReturn(true);

        Vector vector = mock(Vector.class);
        when(indexFinger.tipPosition()).thenReturn(vector);

        when(vector.getX()).thenReturn(xPosition);

        when(handList.count()).thenReturn(1);
        when(handList.get(0)).thenReturn(hand);
    }

    @Test
    public void performAction() {
        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        InOrder inOrder = inOrder(robot);
        inOrder.verify(robot).keyPress(KeyEvent.VK_CONTROL);
        inOrder.verify(robot).keyPress(KeyEvent.VK_ALT);
        inOrder.verify(robot).keyPress(KeyEvent.VK_0);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_0);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_ALT);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_CONTROL);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void samePosition() {
        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame1);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void invalidHand() {
        //arrange
        when(frame1RightHandList.get(0).isValid()).thenReturn(false);

        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void multipleCallsForSameHandId() {
        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        verify(robot, times(3)).keyPress(anyInt());
        verify(robot, times(3)).keyRelease(anyInt());
    }

    @Test
    public void notStartedWithExtendedIndexfinger() {
        //arrange
        when(frame1RightHandList.get(0).fingers().extended().fingerType(Finger.Type.TYPE_INDEX).count()).thenReturn(0);

        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void notEnoughMovementToTheLeft() {
        //arrange
        when(frame2RightHandList.get(0).fingers().extended().fingerType(Finger.Type.TYPE_INDEX).get(0).tipPosition()
            .getX()).thenReturn(25f);

        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        verifyZeroInteractions(robot);
    }
}
