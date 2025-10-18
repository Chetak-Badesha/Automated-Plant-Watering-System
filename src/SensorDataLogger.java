import java.util.ArrayList;
import java.util.List;

public class SensorDataLogger {
    private List<Double> voltageData = new ArrayList<>();

    /**
     * Name: log
     * Purpose: Stores a new voltage reading into memory.
     */
    public void log(double voltage) {
        voltageData.add(voltage);
    }

    /**
     * Name: getData
     * Purpose: Returns the list of stored voltage values.
     */
    public List<Double> getData() {
        return voltageData;
    }
}
