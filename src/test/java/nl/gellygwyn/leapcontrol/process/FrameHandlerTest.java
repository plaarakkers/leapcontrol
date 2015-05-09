package nl.gellygwyn.leapcontrol.process;

import com.leapmotion.leap.Frame;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link FrameHandler}
 *
 * @author plaarakkers
 */
@RunWith(MockitoJUnitRunner.class)
public class FrameHandlerTest {

    @Mock
    private FrameAction frameAction;

    @Mock
    private Frame frame;

    private FrameHandler frameHandler;

    @Test
    public void getActionName() {
        //arrange
        when(frameAction.getName()).thenReturn("test");
        frameHandler = new FrameHandler(frameAction);

        //act
        String name = frameHandler.getActionName();

        //assert
        assertEquals("test", name);
    }

    @Test
    public void processFrameDisabled() {
        //arrange
        frameHandler = new FrameHandler(frameAction, false);

        //act
        frameHandler.processFrame(frame);

        //assert
        verifyZeroInteractions(frameAction);
    }

    @Test
    public void processFrameEnabled() {
        //arrange
        frameHandler = new FrameHandler(frameAction);

        //act
        frameHandler.processFrame(frame);

        //assert
        verify(frameAction).processFrame(any(Frame.class));
    }
}
