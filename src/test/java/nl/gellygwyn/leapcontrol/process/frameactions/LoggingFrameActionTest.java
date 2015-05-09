/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.HandList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link LoggingFrameAction}
 */
@RunWith(MockitoJUnitRunner.class)
public class LoggingFrameActionTest extends BaseFrameActionTest {

    @Mock
    private HandList handList;

    @Before
    public void setUp() {
        frameAction = new LoggingFrameAction();
    }

    @Test
    public void processFrameCorrect() {
        //arrange
        when(frame.hands()).thenReturn(handList);

        //act
        frameAction.processFrame(frame);
    }
}
