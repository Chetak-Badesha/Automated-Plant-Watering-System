# Automated Plant Watering System

## Overview
This project implements a semi-automated plant watering system using Java and the Firmata protocol. It reads soil moisture data from a sensor and controls a water pump based on thresholds. The system also logs data and displays soil moisture trends visually using `StdDraw`.

Key features:
- Reads soil moisture voltage from an analog sensor (A0).
- Controls a water pump via a digital pin (D2) using defined thresholds.
- Logs sensor readings over time.
- Simulates an OLED display for moisture and pump status.
- Plots soil moisture voltage over time for visualization.

## Project Structure
```
MainProjectEECS1021/
├── lib/                     # Required libraries (Firmata4j, JSSC, StdLib, etc.)
├── src/                     # Java source files
│   ├── MainApp.java
│   ├── PumpController.java
│   ├── SensorDataLogger.java
│   ├── PumpOutput.java
│   └── JunitTest.java       # Optional unit tests
├── .gitignore
└── README.md
```

## Dependencies
- Firmata4j
- JSSC
- Princeton StdLib (`StdDraw`)
- JUnit Jupiter (for unit testing)

## Hardware Setup
- Soil Moisture Sensor connected to A0 (analog pin).
- Water Pump connected to D2 (digital pin).
- Arduino Nano / compatible board running StandardFirmata firmware.

## Usage
1. Connect the Arduino board to your PC (default: `COM3` in code).
2. Compile the Java project using your IDE or command line.
3. Run `MainApp.java`.
4. Monitor the console output for soil moisture voltage and pump status.
5. View the plotted graph of soil moisture over time using `StdDraw`.

### Pump Thresholds
- DRY_THRESHOLD: 2.30 V → Pump turns ON
- WET_THRESHOLD: 1.50 V → Pump turns OFF

## Unit Tests
- `JunitTest.java` provides basic unit tests for the `PumpController`.
- Uncomment and configure the `PumpOutput` interface for testing.

## Author
**Chetak Badesha**

## License
This project is open-source. Feel free to use and modify for educational purposes.
