# Route Planner Application

This Route Planner Application helps users track their journey, including stops, distances covered, and distances remaining. Users can switch between miles and kilometers and view their progress visually through a linear progress indicator.

## Features
- **Distance Calculation:** Automatically calculate the total distance and distance covered up to the current stop.
- **Unit Conversion:** Toggle between kilometers and miles.
- **Stop Navigation:** Display current stop, next stop, and distances. A "Next Stop" button allows moving to the next stop, which becomes disabled at the last stop.
- **Progress Display:** Show total distance, distance left, and a linear progress indicator with a percentage display.
- **Stop Highlighting:** Highlight the current stop in green and other stops in red.
- **Column Mode:** Display stops in a normal column for less than 10 stops or a lazy column for 10 or more stops.

## Implementation Details

### Distance Calculation

- **Total Distance Left:** Calculated based on the choice of stops.
- **Distance Covered:** Calculated up to the current stop.

### Spacer

- Used to introduce an empty block of space in the user interface.

### Unit Toggle

- **inKm:** Boolean value to store whether distances are shown in kilometers (`true`) or miles (`false`).

### Stop Display

- **Current Stop:** Name of the current stop.
- **Next Stop Distance:** Distance to the next stop. Displays "No stop available" for the last stop.
- **Next Stop Button:** Proceeds to the next stop, disabled at the last stop.

### Distance Display

- **Total Distance:** Displayed in the chosen unit (km/miles).
- **Total Distance Left:** Calculated dynamically.

### Progress Indicator

- **Linear Progress Indicator:** Shows the journey progress.
- **Percentage Indicator:** Custom utility using math to display progress percentage.

### Column Display Mode

- **Normal Column:** For less than 10 stops.
- **Lazy Column:** For 10 or more stops.

### Stop Highlighting

- **Current Stop:** Highlighted in green.
- **Other Stops:** Highlighted in red.

### Utility Function: `distanceFormat`

- Converts the distance to kilometers or miles based on the value of `inKm`.

## How to Use
1. Download or clone the GitHub Repository
2. Open the Project in Android Studio and run the application in emulator or your smartphone

OR
1. Locate the apk file in the repository in the following path: `app\build\outputs\apk\debug\app-debug.apk"
2. Install the apk file in your smartphone and run the application
