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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
//Computer vision imports
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import org.firstinspires.ftc.teamcode.Hardware.Flywheel;

@TeleOp(name = "Gluons TeleOp", group = "TeleOp")

public class GluonsTeleOp extends LinearOpMode {
    Robot robot = new Robot();

    //private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    //private static final String[] LABELS = {
    //  "Ball",
    //    "Cube",
    //      "Duck",
    //        "Marker"
    //  };


    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        int slowModeButtonCD = 0;
        boolean flapUp = false;
        int flapButtonCD = 0;
        boolean released = false;
        int latchButtonCD = 0;
        boolean latched = false;
        int dropButtonCD = 0;
        boolean dropped = false;
        boolean clawPressed=false;

        String liftState="MANUAL";

        robot.robotMotors.turnOffEncoders();

        //Activates TFOD upon initialization
        // if (robot.tfod != null) {
        //     robot.tfod.activate();
        //    robot.tfod.setZoom(2, 16.0/9.0);
        // }

        waitForStart();

        while (opModeIsActive()) {


            robot.imu.loop();

            // DRIVE ====================================================
            //
            double maxPower = 1;
            double maxCarousel=0.6;
            double forward = (Math.abs(gamepad1.left_stick_y) > 0.2 ? -gamepad1.left_stick_y : 0);
            double clockwise = Math.abs(gamepad1.right_stick_x) > 0.2 ? -gamepad1.right_stick_x : 0;
            double right = Math.abs(gamepad1.left_stick_x) > 0.2 ? gamepad1.left_stick_x : 0;
//            double carousel=gamepad2.left_stick_y;
//            carousel = Range.scale(carousel, -1, 1, -maxCarousel, maxCarousel);
            //Math for drive relative to theta
            clockwise *= 1;

            double fr = forward + clockwise - right;  //+
            double br = forward + clockwise + right;  //-
            double fl = forward - clockwise + right;  //-
            double bl = forward - clockwise - right;  //+

            fl = Range.scale(fl, -1, 1, -maxPower, maxPower);
            fr = Range.scale(fr, -1, 1, -maxPower, maxPower);
            bl = Range.scale(bl, -1, 1, -maxPower, maxPower);
            br = Range.scale(br, -1, 1, -maxPower, maxPower);
            robot.robotMotors.setMotorPower(fl, fr, bl, br);
//
//
//            // BUTTONS ================================================== GAMER MOMENTS 2020
//
//            // Gamepad 1 - Driver + Intake + Foundation Arms GAMER MOMENTS 2020


            if (slowModeButtonCD == 0 && gamepad1.back) {
                if (maxPower == 1) {
                    maxPower = .5;
                } else {
                    maxPower = 1;
                }
                slowModeButtonCD = 12;
            }
//
////
//            final double x = Math.pow(gamepad1.left_stick_x*-1, 3.0);
//            final double y = Math.pow(gamepad1.left_stick_y *-1, 3.0);
//            final double rotation = Math.pow(gamepad1.right_stick_x*1, 3.0)/1.5; //changed from negative to 1
//            final double direction = Math.atan2(x, y) + robot.imu.getHeading();
//            final double speed = Math.min(1.0, Math.sqrt(x * x + y * y));
//
//            final double frontLeft = 1 * speed * Math.sin(direction + Math.PI / 4.0) + rotation;
//            final double frontRight = 1 * speed * Math.cos(direction + Math.PI / 4.0) - rotation;
//            final double backLeft = 1 * speed * Math.cos(direction + Math.PI / 4.0) + rotation;
//            final double backRight = 1 * speed * Math.sin(direction + Math.PI / 4.0) - rotation;
//
//            robot.robotMotors.setMotorPower(frontLeft, frontRight, backLeft, backRight);

//

            //Gamepad 1
            if(gamepad1.dpad_up)
            {
                robot.robotMotors.moveForwardEn(12.0);
            }
            //Drop Controls
            if(gamepad2.left_trigger>0.8)
            {
                if(!clawPressed)
                {
                    clawPressed=true;
                    if (!dropped) {
                        robot.s.open();
                        dropped = true;
                    } else {
                        robot.s.close();
                        dropped = false;
                    }
                }
                //dropButtonCD=2000;
            }
            else
            {
                clawPressed=false;
            }
//
//
//            // Gamepad 2 - Functions GAMER MOMENTS 2020
//
/*
            //Lift Control
            if(gamepad2.a)
            {
                liftState="TO_LOWER";
            }
            if(gamepad2.x)
            {
                liftState="TO_MID";
            }
            if(gamepad2.y)
            {
                liftState="TO_UPPER";
            }
            if(gamepad2.b)
            {
                liftState="TO_BASE";
            }
            if(gamepad2.right_trigger>0.8)
            {
                liftState="TO_CAP";
            }
            */
    /*
            if(gamepad2.dpad_up)
            {
                liftState="MANUAL";
                robot.lift.moveUpWithoutEncoders();
            }
            else if(gamepad2.dpad_down)
            {
                liftState="MANUAL";
                robot.lift.moveDownWithoutEncoders();
            }
            else if (gamepad1.b)
            {
                liftState="MANUAL";
                robot.lift.liftMotor.setPower(0);
            }
            else if(liftState.equals("MANUAL")) {
                robot.lift.liftMotor.setPower(0);
            }

            if(gamepad2.dpad_right) {
                robot.lift.reset();
            }
            telemetry.addData("liftState: ", liftState);
            //states: TO_LOWER, TO_MID, TO_UPPER, TO_BASE, MANUAL
            if (liftState.equals("TO_LOWER"))
            {
                robot.lift.liftLowerLevel();
            }
            else if(liftState.equals("TO_CAP"))
            {
                robot.lift.capLevel();
            }
            else if (liftState.equals("TO_MID"))
            {
                robot.lift.liftMidLevel();
            }
            else if (liftState.equals("TO_UPPER"))
            {
                robot.lift.liftUpperLevel();
            }
            else if (liftState.equals("TO_BASE"))
            {
                robot.lift.backToBase();
            }

            if (robot.lift.stopWhenReached())
            {
                liftState="MANUAL";
            }


//
            //Carousel Control
//            // Joystick Carousel
//            robot.carouselTurn.turnMotor.setPower(carousel);
            if(gamepad2.left_bumper) {
                robot.carouselTurn.startRedTurn();
            }
            else if(gamepad2.right_bumper) {
                robot.carouselTurn.startBlueTurn();
            }
            else {
                robot.carouselTurn.stopTurn();
            }

//            //Flap Control
//            if(flapButtonCD == 0 && gamepad2.y) {
//                if(!flapUp) {
//                    robot.s.upFlap();
//                    flapUp=true;
//                } else {
//                    robot.s.downFlap();
//                    flapUp=false;
//                }
//                flapButtonCD=12;
//            }
//
//            //Kicker Controls
//            if(gamepad2.right_trigger>0.2) {
//                robot.s.kick();
//            }
//            else {
//                robot.s.unkick();
//            }

//            //Release Wheelstick Controls
//            if(gamepad2.dpad_left) {
//                if(!released) {
//                    robot.s.release();
//                    released=true;
//                }
//            }

            //Latch Controls
//            if(latchButtonCD == 0 && gamepad2.b) {
//                if(!latched) {
//                    robot.s.latch();
//                    latched=true;
//                } else {
//                    robot.s.unlatch();
//                    latched=false;
//                }
//                latchButtonCD=12;
//            }

            //Computer vision
            if (robot.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = robot.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        i++;
                    }
                }
            }
*/
            // TELEMETRY STATEMENTS

            telemetry.addData("Gyro Heading", robot.imu.getHeadingDegrees());
            //      telemetry.addData("Lift Value",robot.lift.liftMotor.getCurrentPosition());
            //    telemetry.addData("Target Value",robot.lift.liftMotor.getTargetPosition());
            telemetry.addData("fl", robot.robotMotors.frontLeft.getCurrentPosition());
            telemetry.addData("fr", robot.robotMotors.frontRight.getCurrentPosition());
            telemetry.addData("bl", robot.robotMotors.backLeft.getCurrentPosition());
            telemetry.addData("br", robot.robotMotors.backRight.getCurrentPosition());

//            telemetry.addData("Alpha", h.color.alpha());
//            telemetry.addData("Red  ", h.color.red());
//            telemetry.addData("Green", h.color.green());
//            telemetry.addData("Blue ", h.color.blue());


            telemetry.update();
            // Adds everything to telemetry

            if(slowModeButtonCD>0) {
                slowModeButtonCD--;
            }
            if(flapButtonCD>0) {
                flapButtonCD--;
            }
            if(latchButtonCD>0) {
                latchButtonCD--;
            }
            if(dropButtonCD>0) {
                dropButtonCD--;
            }

            // Stops phone from queuing too many commands and breaking GAMER MOMENTS 2020
            // 25 ticks/sec
        }

    }
}
