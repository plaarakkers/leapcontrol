/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.FingerList;
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
    private HandList handList;

    @Mock
    private Hand hand;

    @Mock
    private FingerList fingerList;

    @Mock
    private FingerList fingerListExtended;

    @Before
    public void before() {
        frameAction = new CloseApplicationFrameAction(robot);

        when(fingerList.extended()).thenReturn(fingerListExtended);
        when(hand.fingers()).thenReturn(fingerList);
        when(frame.hands()).thenReturn(handList);

        //Basic correct hand detection setup.
        when(fingerListExtended.isEmpty()).thenReturn(true);
        when(handList.count()).thenReturn(1);
        when(handList.get(0)).thenReturn(hand);
        when(hand.isValid()).thenReturn(true);
        when(hand.isRight()).thenReturn(true);
        when(hand.id()).thenReturn(1);
    }

    @Test
    public void processFrameCorrect() {
        //act
        frameAction.processFrame(frame);

        //assert
        InOrder inOrder = inOrder(robot);
        inOrder.verify(robot).keyPress(KeyEvent.VK_ALT);
        inOrder.verify(robot).keyPress(KeyEvent.VK_F4);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_F4);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_ALT);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void processFrameSameHand() {
        //act
        frameAction.processFrame(frame);
        frameAction.processFrame(frame);

        //assert
        verify(robot, times(2)).keyPress(anyInt());
        verify(robot, times(2)).keyRelease(anyInt());
    }

    @Test
    public void processFrameInvalidHand() {
        //arrange
        when(hand.isValid()).thenReturn(false);

        //act
        frameAction.processFrame(frame);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void processFrameLeftHand() {
        //arrange
        when(hand.isRight()).thenReturn(false);

        //act
        frameAction.processFrame(frame);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void processFrameMultipleHands() {
        //arrange
        when(handList.count()).thenReturn(2);

        //act
        frameAction.processFrame(frame);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void processFrameNoHands() {
        //arrange
        when(handList.count()).thenReturn(0);

        //act
        frameAction.processFrame(frame);

        //assert
        verifyZeroInteractions(robot);
    }

    @Test
    public void processFrameExtendedFingers() {
        //arrange
        when(fingerListExtended.isEmpty()).thenReturn(false);

        //act
        frameAction.processFrame(frame);

        //assert
        verifyZeroInteractions(robot);
    }
}
