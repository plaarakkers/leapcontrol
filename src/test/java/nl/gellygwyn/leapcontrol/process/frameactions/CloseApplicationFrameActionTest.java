package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
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
 * Tests for the {@link CloseApplicationFrameAction}
 */
@RunWith(MockitoJUnitRunner.class)
public class CloseApplicationFrameActionTest extends BaseFrameActionTest {

    @Mock
    private Robot robot;

    @Mock
    private HandList extendedRightHandList;

    @Mock
    private HandList closedRightHandList;

    @Mock
    Frame frame1;

    @Mock
    Frame frame2;

    @Before
    public void before() {
        frameAction = new CloseApplicationFrameAction(robot);

        when(frame1.hands()).thenReturn(extendedRightHandList);
        when(frame2.hands()).thenReturn(closedRightHandList);

        initHandList(extendedRightHandList, 5);
        initHandList(closedRightHandList, 0);
    }

    private void initHandList(HandList handList, int extendedFingersCount) {
        Hand hand = mock(Hand.class);
        when(hand.isValid()).thenReturn(true);
        when(hand.isRight()).thenReturn(true);
        when(hand.id()).thenReturn(1);

        FingerList fingerList = mock(FingerList.class);
        when(hand.fingers()).thenReturn(fingerList);

        FingerList extendedFingerList = mock(FingerList.class);
        when(fingerList.extended()).thenReturn(extendedFingerList);

        when(extendedFingerList.count()).thenReturn(extendedFingersCount);

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
        inOrder.verify(robot).keyPress(KeyEvent.VK_ALT);
        inOrder.verify(robot).keyPress(KeyEvent.VK_F4);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_F4);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_ALT);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void multipleOpenHands() {
        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame1);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void multipleClosedHands() {
        //act
        frameAction.processFrame(frame2);
        frameAction.processFrame(frame2);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void invalidHand() {
        //arrange
        when(closedRightHandList.get(0).isValid()).thenReturn(false);

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
        verify(robot, times(2)).keyPress(anyInt());
        verify(robot, times(2)).keyRelease(anyInt());
    }

    @Test
    public void notStartedWithExtendedHand() {
        //arrange
        when(extendedRightHandList.get(0).fingers().extended().count()).thenReturn(4);

        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void notEndingWithClosedHand() {
        //arrange
        when(closedRightHandList.get(0).fingers().extended().count()).thenReturn(2);

        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void leftHand() {
        //arrange
        when(closedRightHandList.get(0).isRight()).thenReturn(false);

        //act
        frameAction.processFrame(frame1);
        frameAction.processFrame(frame2);

        //assert
        verifyZeroInteractions(robot);
    }
}
