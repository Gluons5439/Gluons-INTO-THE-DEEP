package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="autoparkblue ",group="Autonomous")
public class autoparkblue extends LinearOpMode {

    @Override

    public void runOpMode() throws InterruptedException {

        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        DcMotor frontLeft;  //declares all motors, if you don't know this by now you have issues
        DcMotor backLeft;
        DcMotor frontRight;
        DcMotor backRight;
        DcMotor slides;
        DcMotor pivot;
        slides = hardwareMap.dcMotor.get("slides");
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setDirection(DcMotor.Direction.FORWARD);
        slides.setPower(0);
        pivot = hardwareMap.dcMotor.get("pivot");
        pivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pivot.setDirection(DcMotor.Direction.FORWARD);
        pivot.setPower(0);
        //Dhruv likes To smile(stupid coder wrote that)

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); //sets the drive train motors to the proper thing in the robot config
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        //intake = hardwareMap.get(DcMotor.class, "intake");
        frontLeft.setDirection(DcMotor.Direction.REVERSE); //these need to be reversed because how the gobilda motors are its stupid
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Servo box1; //initalizes servo
        //box1 = hardwareMap.servo.get("servo1"); //sets hw map for servo, hw stands for hardware map not homework...
        //Servo box2;
        //box2 = hardwareMap.servo.get("servo2");
        //box1.setDirection(Servo.Direction.REVERSE);
        //box2.setDirection(Servo.Direction.REVERSE);
        /*final double X_LEFT = 0;
        final double X_MID = 530;
        final double X_RIGHT = 1060;
        final double Y_DUCK = 340;
        float leftPos = 0;
        float topPos = 0;
        char result = 'm';
        boolean failsafe = false;
        boolean found = false;

         */
        waitForStart();
        if (opModeIsActive()) {

            // Push telemetry to the Driver Station.
            telemetry.update();

            // Save CPU resources; can resume streaming when needed.
               /* if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                */

            // Share the CPU.
            sleep(20);
            // Finding the element
            int minDetections = 1; //minimum number of "frames" the robot detects the element for to ensure it is detected properly
            int detections = 0;
            long startTime = System.currentTimeMillis();

            frontLeft.setPower(-0.8);
            backLeft.setPower(0.8);
            frontRight.setPower(0.8);
            backRight.setPower(-0.8);
            Thread.sleep(600);

            /**
             * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
             */
    /*private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

    }   // end method telemetryTfod()

     */

        }
    }
}