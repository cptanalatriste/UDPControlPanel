package pe.edu.pucp.teleprocesamiento.udpdemo;

import java.awt.EventQueue;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.ui.RefineryUtilities;
import pe.edu.pucp.teleprocesamiento.udpdemo.view.ControlPanelFrame;

/**
 *
 * @author Carlos G. Gavidia (cgavidia@acm.org)
 */
public class ControlPanel {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ControlPanelFrame controlPanelFrame = new ControlPanelFrame();
                    controlPanelFrame.pack();
                    RefineryUtilities.centerFrameOnScreen(controlPanelFrame);
                    controlPanelFrame.setVisible(true);
                    controlPanelFrame.start();
                } catch (SocketException ex) {
                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
