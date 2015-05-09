package nl.gellygwyn.leapcontrol.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import nl.gellygwyn.leapcontrol.process.FrameHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * LeapControlTray
 */
public class LeapControlTray {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeapControlTray.class);

    private final Stage stage;

    private final FrameHandlers frameHandlers;

    public LeapControlTray(Stage stage, FrameHandlers frameHandlers) {
        this.stage = stage;
        this.frameHandlers = frameHandlers;
    }

    public void start() {
        Toolkit.getDefaultToolkit();

        if (!SystemTray.isSupported()) {
            LOGGER.error("System tray not supported. Exiting application");
            Platform.exit();
        }

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            final Image image = ImageIO.read(ClassLoader.getSystemResource("leapcontrol.png"));
            final SystemTray tray = SystemTray.getSystemTray();
            final TrayIcon trayIcon = new TrayIcon(image);

            JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.addActionListener(e -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add(exitItem);
            popupMenu.addSeparator();
            addFrameHandlerActions(popupMenu);

            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popupMenu.setLocation(e.getX(), e.getY());
                        popupMenu.setInvoker(popupMenu);
                        popupMenu.setVisible(true);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    popupMenu.setVisible(false);
                }
            });

            tray.add(trayIcon);
        } catch (IOException e) {
            LOGGER.error("Problem loading tray icon. Exiting application", e);
            Platform.exit();
        } catch (AWTException e) {
            LOGGER.error("Problem adding trayicon. Exiting application", e);
            Platform.exit();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOGGER.error("error initialising look and feel. Exiting application", e);
            Platform.exit();
        }
    }

    private void addFrameHandlerActions(JPopupMenu popupMenu) {
        for (String name : frameHandlers.getFrameHandlerNames()) {
            JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(name, frameHandlers.isEnabled(name));
            checkBoxMenuItem.addActionListener(new FrameHandlerActionListener(name));

            popupMenu.add(checkBoxMenuItem);
        }
    }

    private class FrameHandlerActionListener implements ActionListener {

        private final String name;

        private FrameHandlerActionListener(String name) {
            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frameHandlers.setEnabled(name, ((AbstractButton) e.getSource()).isSelected());
        }

    }
}
