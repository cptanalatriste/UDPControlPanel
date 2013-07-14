package pe.edu.pucp.teleprocesamiento.udpdemo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.net.SocketException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.ui.ApplicationFrame;
import pe.edu.pucp.teleprocesamiento.udpdemo.udp.UDPClient;
import pe.edu.pucp.teleprocesamiento.udpdemo.udp.UDPServer;

/**
 *
 * Frame that shows the current value of the monitored variable through a
 * Dynamic Chart, it also allows the user to send a Set-Point request.
 *
 * @author Carlos G. Gavidia (cgavidia@acm.org)
 */
public class ControlPanelFrame extends ApplicationFrame {

    public static final String FRAME_TITLE = "Control del Proceso";
    public static final String BUTTON_LABEL = "Cambiar Set Point";
    public static final String TIME_AXIS_LABEL = "hh:mm:ss";
    public static final String VALUE_AXIS_LABEL = "Y";
    private static final int ITEM_NUMBER = 2 * 60;
    private static final int SERIES_NUMBER = 0;
    private static final int COLUMN_NUMBER = 3;
    private static final int DELAY = 50;
    private static final String SERIES_KEY = "Control del Motor";
    private static final double LOWER_BOUND = 0;
    private static final double UPPER_BOUND = 500;
    private Timer timer;
    private final DynamicTimeSeriesCollection timeSeriesCollection;

    public ControlPanelFrame() throws SocketException {
        super(FRAME_TITLE);
        timeSeriesCollection =
                new DynamicTimeSeriesCollection(1, ITEM_NUMBER, new Second());
        timeSeriesCollection.setTimeBase(new Second(new Date()));
        timeSeriesCollection.addSeries(new float[ITEM_NUMBER], SERIES_NUMBER, SERIES_KEY);
        JFreeChart jFreeChart = createChart(timeSeriesCollection);

        final JTextField setPointField = new JTextField(COLUMN_NUMBER);
        final JButton setPointButton = new JButton(BUTTON_LABEL);
        setPointButton.addActionListener(new UDPClient(setPointField));
        this.add(new ChartPanel(jFreeChart), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(setPointField);
        buttonPanel.add(setPointButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(DELAY, new UDPServer(this));
    }

    public void updateTimeSeries(float[] newData) {
        timeSeriesCollection.advanceTime();
        timeSeriesCollection.appendData(newData);
    }

    public void start() {
        timer.start();
    }

    private JFreeChart createChart(DynamicTimeSeriesCollection timeSeriesCollection) {
        JFreeChart jFreeChart = ChartFactory.createTimeSeriesChart(FRAME_TITLE,
                TIME_AXIS_LABEL, VALUE_AXIS_LABEL, timeSeriesCollection, true,
                true, false);
        XYPlot xyPlot = jFreeChart.getXYPlot();
        ValueAxis domainAxis = xyPlot.getDomainAxis();
        domainAxis.setAutoRange(true);
        ValueAxis rangeAxis = xyPlot.getRangeAxis();
        rangeAxis.setRange(LOWER_BOUND, UPPER_BOUND);
        return jFreeChart;
    }
}
