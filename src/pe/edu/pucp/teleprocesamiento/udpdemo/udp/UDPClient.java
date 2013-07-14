package pe.edu.pucp.teleprocesamiento.udpdemo.udp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import pe.edu.pucp.teleprocesamiento.udpdemo.view.ControlPanelFrame;

/**
 * Allows to send a Set-Point change through UDP using GUI.
 *
 *
 * @author Carlos G. Gavidia (cgavidia@acm.org)
 */
public class UDPClient implements ActionListener {

    public static final String HOST_IP = "127.0.0.1";
    private static final int INTERNAL_SEND_PORT = 6502;
    private DatagramSocket datagramSocket;
    private JTextField setPointField;

    public UDPClient(JTextField setPointField) {
        this.setPointField = setPointField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            byte[] buffer;
            String tempString = String.valueOf(Double.parseDouble(setPointField.getText()));
            System.out.println("tempString: " + tempString);
            buffer = tempString.getBytes();
            datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket;
            datagramPacket = new DatagramPacket(buffer, buffer.length,
                    InetAddress.getByName(HOST_IP), INTERNAL_SEND_PORT);
            System.out.println("Sending data to port: " + datagramPacket.getPort());
            datagramSocket.send(datagramPacket);
            datagramSocket.disconnect();
            datagramSocket.close();
        } catch (Exception ex) {
            Logger.getLogger(ControlPanelFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
