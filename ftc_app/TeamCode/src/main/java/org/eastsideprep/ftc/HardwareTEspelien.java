package org.eastsideprep.ftc;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Hardware definitions for 15203
 */
public class HardwareTEspelien {
    /* Public OpMode members. */
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;

        /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareTEspelien() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        // Define and Initialize Motors
        //leftFrontMotor = hwMap.dcMotor.get("LF");
        //rightFrontMotor = hwMap.dcMotor.get("RF");
        leftBackMotor = hwMap.dcMotor.get("leftBackMotor");
        rightBackMotor = hwMap.dcMotor.get("rightBackMotor");
        //armMotor = hwMap.dcMotor.get("A");
        //marker = hwMap.servo.get("M");

        leftBackMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.REVERSE);
        //leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        //rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.

        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
         /*
         *
         * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
         * periodic tick.  This is used to compensate for varying processing times for each cycle.
         * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
         *
         * @param periodMs  Length of wait cycle in mSec.
         */

    public void waitForTick(long periodMs) {

        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

