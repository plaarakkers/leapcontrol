# leapcontrol

Tray application to perform actions based on received frames from the Leap motion controller. Uses polling because this seemed more stable than the listener.

Used 2.2.5.x SDK for Linux from https://developer.leapmotion.com/.

### Current actions are
- Close application: Action that sends an ALT + F4 command when a closed right hand is detected (closing the hand above the controller works best).
- Log process call: Action that is disabled at startup and logs information of the frame.

### Tray icon actions:
- exit: Closes leapcontrol
- [List of actions]: Information about the status (enabled/disabled) of the current actions. Selecting the action toggles the status.

## Building and running leapcontrol

The used libraries are for 64 bit linux and the Windows libraries are not included. The generated Windows batch file is also disabled. With the correct libraries and script setup leapcontrol should also work in windows.

- Run gradlew distZip to build the distribution zip.
- Extract the generated 'build/distributions/leapcontrol.zip' in the desired location.
- Go to the 'bin' directory in the extracted location and run the 'leapcontrol' script.
