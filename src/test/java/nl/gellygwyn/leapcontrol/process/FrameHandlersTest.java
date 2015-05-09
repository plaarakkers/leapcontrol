package nl.gellygwyn.leapcontrol.process;

import com.leapmotion.leap.Frame;
import nl.gellygwyn.leapcontrol.LeapControlRuntimeException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link FrameHandlers}
 *
 * @author plaarakkers
 */
@RunWith(MockitoJUnitRunner.class)
public class FrameHandlersTest {

    @Mock
    private FrameHandler frameHandler1;

    @Mock
    private FrameHandler frameHandler2;

    @Mock
    private Frame frame;

    private final FrameHandlers frameHandlers = new FrameHandlers();

    @Before
    public void before() {
        when(frameHandler1.getActionName()).thenReturn("b");
        when(frameHandler2.getActionName()).thenReturn("a");
        when(frameHandler2.isEnabled()).thenReturn(true);

        frameHandlers.addHandler(frameHandler1);
        frameHandlers.addHandler(frameHandler2);
    }

    @Test(expected = LeapControlRuntimeException.class)
    public void addHandlerSameName() {
        //arrange
        FrameHandler frameHandler = mock(FrameHandler.class);

        when(frameHandler.getActionName()).thenReturn("b");

        //act
        frameHandlers.addHandler(frameHandler);
    }

    @Test
    public void getFrameHandlerNames() {
        //act
        String[] names = new String[2];
        names = frameHandlers.getFrameHandlerNames().toArray(names);

        //assert
        assertEquals(2, names.length);
        assertEquals("a", names[0]);
        assertEquals("b", names[1]);
    }

    @Test(expected = LeapControlRuntimeException.class)
    public void isEnabledNotExistingFrameHandler() {
        //act
        frameHandlers.isEnabled("notExisting");
    }

    @Test
    public void isEnabled() {
        //act
        boolean enabled = frameHandlers.isEnabled("a");

        //assert
        assertTrue(enabled);
    }

    @Test(expected = LeapControlRuntimeException.class)
    public void setEnabledNotExistingFrameHandler() {
        //act
        frameHandlers.isEnabled("notExisting");
    }

    @Test
    public void setEnabled() {
        //act
        frameHandlers.setEnabled("b", true);

        //assert
        verify(frameHandler1).setEnabled(true);
    }

    @Test
    public void processFrame() {
        //act
        frameHandlers.processFrame(frame);

        //assert
        verify(frameHandler1).processFrame(frame);
        verify(frameHandler2).processFrame(frame);
    }

    @Test
    public void processFrameException() {
        //arrange
        doThrow(Exception.class).when(frameHandler2).processFrame(frame);

        //act
        frameHandlers.processFrame(frame);

        //assert
        verify(frameHandler1).processFrame(frame);
        verify(frameHandler2).processFrame(frame);
    }
}
