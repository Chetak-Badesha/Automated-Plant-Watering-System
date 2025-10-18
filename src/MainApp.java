import org.firmata4j.IODevice;
import java.awt.Font;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import edu.princeton.cs.introcs.StdDraw;
import java.io.IOException;
import java.util.*;

public class MainApp {
    private static final String PORT = "COM3";
    private static final byte SENSOR_PIN = 15; // A0
    private static final byte PUMP_PIN = 2;    // D2
    private static final int SAMPLE_INTERVAL = 3000; // milliseconds

    private static IODevice grove;
    private static SensorDataLogger logger = new SensorDataLogger();
    private static PumpController controller;

    public static void main(String[] args) throws IOException, InterruptedException {
        grove = new FirmataDevice(PORT);
        grove.start();
        grove.ensureInitializationIsDone();

        Pin soilSensor = grove.getPin(SENSOR_PIN); // A0
        soilSensor.setMode(Pin.Mode.ANALOG);

        Pin pumpPin = grove.getPin(PUMP_PIN); // D2
        pumpPin.setMode(Pin.Mode.OUTPUT);
        controller = new PumpController(pumpPin);

        // Timer for repeated sampling
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    double analogValue = soilSensor.getValue();
                    double voltage = analogValue * 5.0 / 1023.0;

                    logger.log(voltage);
                    controller.updateState(voltage);
                    boolean pumpOn = voltage < 2.30;
                    displayOLED(voltage, pumpOn);
                    plotGraph();

                } catch (Exception e) {
                    System.err.println("Error reading sensor: " + e.getMessage());
                }
            }
        }, 0, SAMPLE_INTERVAL);
    }

    /**
     * Name: displayOLED
     * Purpose: Simulates OLED display using console output.
     */
    private static void displayOLED(double voltage) {
        System.out.printf("OLED Display: Moisture Voltage = %.2f V%n", voltage);
    }

    /**
     * Name: plotGraph
     * Purpose: Plot graph based on given data
     */
    private static void plotGraph() {
        List<Double> data = logger.getData();

       // Canvas
        StdDraw.clear();
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(-5, 65);
        StdDraw.setYscale(-0.5, 6.0);

        //Set font for all text using Java import
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Axis Labels
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(30, -0.8, "Time (s)");
        StdDraw.text(-7, 2.5, "Soil Moisture Voltage (V)", 90);

        // Title
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 18));
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.text(30, 5.5, "Soil Moisture Voltage Over Time");

        // Font for axis numbers
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 14));
        StdDraw.setPenColor(StdDraw.BLACK);

        // X-Axis ticks and labels
        for (int x = 0; x <= 60; x += 10) {
            StdDraw.line(x, 0, x, -0.1);
            StdDraw.text(x, -0.3, String.valueOf(x));
        }

        // Y-Axis ticks and labels
        for (double y = 0; y <= 5; y += 0.5) {
            StdDraw.line(0, y, -1, y);
            StdDraw.text(-2.5, y, String.format("%.1f", y));
        }

        // Line drawn from data of sensor
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        for (int i = 1; i < data.size(); i++) {
            double x0 = (i - 1) * SAMPLE_INTERVAL / 1000.0;
            double x1 = i * SAMPLE_INTERVAL / 1000.0;
            double y0 = data.get(i - 1);
            double y1 = data.get(i);
            StdDraw.line(x0, y0, x1, y1);
        }

        StdDraw.show();
    }

    /**
     * Name: displayOLED
     * Purpose: Simulates OLED display with voltage and pump status.
     */
    private static void displayOLED(double voltage, boolean isPumpOn) {
        System.out.println("==============================");
        System.out.printf(" OLED Display (Simulated)\n");
        System.out.printf(" Moisture Voltage: %.2f V\n", voltage);
        System.out.printf(" Pump Status: %s\n", isPumpOn ? "ON" : "OFF");
        System.out.println("==============================");
    }
}