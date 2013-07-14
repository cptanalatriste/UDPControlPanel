package pe.edu.pucp.teleprocesamiento.udpdemo.udp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.teleprocesamiento.udpdemo.view.ControlPanelFrame;

/**
 *
 * @author Carlos G. Gavidia (cgavidia@acm.org)
 */
public class UDPServer implements ActionListener {

    public static final int RECEIVE_PORT = 6501;
    public static final int BUFFER_SIZE = 256;
    private DatagramSocket datagramSocket;
    private ControlPanelFrame controlPanelFrame;

    public UDPServer(ControlPanelFrame controlPanelFrame) throws SocketException {
        this.datagramSocket = new DatagramSocket(RECEIVE_PORT);
        this.controlPanelFrame = controlPanelFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);
            //System.out.println("Receiving data at port: " + datagramSocket.getPort());
            String data = new String(datagramPacket.getData());
            float dataAsFloat = Float.parseFloat(data);
            //System.out.println("dataAsDouble: " + dataAsFloat);
            float[] newData = new float[1];
            newData[0] = dataAsFloat;
            controlPanelFrame.updateTimeSeries(newData);
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
