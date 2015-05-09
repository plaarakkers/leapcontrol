/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.gellygwyn.leapcontrol.process;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link LeapControlFrameRetrieveTask}
 *
 * @author plaarakkers
 */
@RunWith(MockitoJUnitRunner.class)
public class LeapControlFrameRetrieveTaskTest {

    @Mock
    private Controller controller;

    @Mock
    private Frame frame;

    @Mock
    FrameHandlers frameHandlers;

    private LeapControlFrameRetrieveTask leapControlFrameRetrieveTask;

    @Before
    public void before() {
        leapControlFrameRetrieveTask = new LeapControlFrameRetrieveTask(controller, frameHandlers);
        when(controller.frame()).thenReturn(frame);

        when(frame.isValid()).thenReturn(true);
        when(frame.id()).thenReturn(1L);
    }

    @Test
    public void runInvalidFrame() {
        //arrange
        when(frame.isValid()).thenReturn(false);

        //act
        leapControlFrameRetrieveTask.run();

        //assert
        verify(frameHandlers, never()).processFrame(frame);
    }

    @Test
    public void runSameFrame() {
        //act
        leapControlFrameRetrieveTask.run();
        leapControlFrameRetrieveTask.run();

        //assert
        verify(frameHandlers).processFrame(frame);
    }

}
