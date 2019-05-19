/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eastsideprep.ftc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TEspelien: Teleop Not Simple", group = "TEspelien")

public class TeleOpTEspelien extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTEspelien robot = new HardwareTEspelien();


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("", "Hello Trajan, get going");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        //robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double joystickX = gamepad1.left_stick_x;
            double joystickY = -gamepad1.left_stick_y; // Negate to get +y forward.

            //use a tank drive on only one joystick
            //use a 5x5 table
            double[][] joystickInputTable = new double[3][3];
            int simpleJoystickValue = 0;
            //gross basshyyyyy solution
            if (joystickY > 0.33) {
                if (joystickX < -0.33) {
                    simpleJoystickValue = 1;
                }
                if (joystickX < 0.33) {
                    simpleJoystickValue = 2;
                }
                if (joystickX > 0.33) {
                    simpleJoystickValue = 3;
                }
            }
            if (joystickY > -0.33) {
                if (joystickX < -0.33) {
                    simpleJoystickValue = 4;
                }
                if (joystickX < 0.33) {
                    simpleJoystickValue = 5;
                }
                if (joystickX > 0.33) {
                    simpleJoystickValue = 6;
                }
            }
            if (joystickY < -0.33) {
                if (joystickX < -0.33) {
                    simpleJoystickValue = 7;
                }
                if (joystickX < 0.33) {
                    simpleJoystickValue = 8;
                }
                if (joystickX > 0.33) {
                    simpleJoystickValue = 9;
                }
            }


            // Send telemetry message to signify robot running;
            telemetry.addData("reading controllers: \n y: ", joystickX);
            telemetry.addData("x: ", joystickY);
            telemetry.update();
            robot.rightBackMotor.setPower(joystickY);
            robot.leftBackMotor.setPower(joystickY);
            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }


    public static double getSimpleJoystickValue(int jsx, int jsy, int d) {
        double spv = 0;
        double row = Math.floor(jsx * d);

        return spv;
    }
}
