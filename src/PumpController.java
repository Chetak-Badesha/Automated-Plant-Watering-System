import org.firmata4j.Pin;
import java.io.IOException;

/**
 * Name: PumpController
 * Purpose: Controls the pump based on soil voltage using two thresholds: dry and wet.
 */
public class PumpController {
    private static final double DRY_THRESHOLD = 2.30;  // volts
    private static final double WET_THRESHOLD = 1.50;  // volts
    private Pin pump;

    public PumpController(Pin pumpPin) {
        this.pump = pumpPin;
    }

    /**
     * Name: updateState
     * Purpose: Turns pump on if soil is dry, off if soil is wet.
     */
    public void updateState(double voltage) throws IOException {
        if (voltage < DRY_THRESHOLD) {
            pump.setValue(1);
            System.out.println("State: DRY — Pump ON");
        } else if (voltage >= WET_THRESHOLD) {
            pump.setValue(0);
            System.out.println("State: WET — Pump OFF");
        } else {
            System.out.println("Soil is moist");
        }
    }
}