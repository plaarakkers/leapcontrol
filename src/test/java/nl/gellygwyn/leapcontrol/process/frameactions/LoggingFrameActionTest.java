package nl.gellygwyn.leapcontrol.process.frameactions;

import com.leapmotion.leap.Frame;
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
    private Frame frame;

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
