package nl.gellygwyn.leapcontrol.process.frameactions;

import nl.gellygwyn.leapcontrol.process.FrameAction;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import org.junit.Test;

/**
 * Base tests for the {@link FrameAction}s
 *
 */
public abstract class BaseFrameActionTest {

    protected FrameAction frameAction;

    @Test
    public void getName() {
        //act
        String name = frameAction.getName();

        //assert
        assertThat(name, not(emptyOrNullString()));
    }
}
