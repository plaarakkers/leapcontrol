package nl.gellygwyn.leapcontrol;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import nl.gellygwyn.leapcontrol.process.FrameHandlers;
import nl.gellygwyn.leapcontrol.process.FrameHandlersFactory;
import nl.gellygwyn.leapcontrol.process.LeapControlFrameRetrieveTask;
import nl.gellygwyn.leapcontrol.ui.LeapControlTray;

public class LeapControl extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);

        FrameHandlers frameHandlers = FrameHandlersFactory.getFrameHandlers();

        LeapControlTray leapControlTray = new LeapControlTray(stage, frameHandlers);

        SwingUtilities.invokeLater(leapControlTray::start);

        Controller controller = new Controller();
        controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
        Arrays.stream(Gesture.Type.values()).forEach(g -> controller.enableGesture(g));

        //Using polling with timertask because it looks like this is more stable than the listener.
        TimerTask timerTask = new LeapControlFrameRetrieveTask(controller, frameHandlers);
        new Timer(true).schedule(timerTask, new Date(), 100);
    }
}
