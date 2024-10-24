package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name="EncoderAutoFarBlue",group="Autonomous")
public class EncoderAutoFarBlue extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "blueObject.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/myCustomModel.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "Pixel",
            "blue_object"
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    @Override

    public void runOpMode() throws InterruptedException {
        initTfod();
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        DcMotor frontLeft;  //declares all motors, if you don't know this by now you have issues
        DcMotor backLeft;
        DcMotor frontRight;
        DcMotor backRight;
        DcMotor intake;
        DcMotor pulley1;
        pulley1 = hardwareMap.dcMotor.get("pulley1");
        pulley1.setDirection(DcMotor.Direction.REVERSE);
        //Dhruv likes To smile(stupid coder wrote that)
        DcMotor pulley2;
        pulley2 = hardwareMap.dcMotor.get("pulley2");
        pulley2.setDirection(DcMotor.Direction.FORWARD);
        pulley1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pulley2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pulley1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pulley2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pulley1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pulley2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pulley1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pulley2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pulley1.setTargetPosition(0);
        pulley2.setTargetPosition(0);
        pulley2.setPower(0);
        pulley1.setPower(0);
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); //sets the drive train motors to the proper thing in the robot config
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        frontLeft.setDirection(DcMotor.Direction.REVERSE); //these need to be reversed because how the gobilda motors are its stupid
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setTargetPosition(0);
        frontLeft.setTargetPosition(0);
        backLeft.setTargetPosition(0);
        backRight.setTargetPosition(0);
        Servo box1; //initalizes servo
        box1 = hardwareMap.servo.get("servo1"); //sets hw map for servo, hw stands for hardware map not homework...
        Servo box2;
        box2 = hardwareMap.servo.get("servo2");
        box1.setPosition(0.75);
        box2.setPosition(0.75);
        final double X_LEFT = 0;
        final double X_RIGHT = 1500;
        float leftPos = 0;
        float topPos = 0;
        char result = 'm';
        boolean failsafe = false;
        boolean found = false;
        boolean done = false;
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
                // Finding the element
                int minDetections=1; //minimum number of "frames" the robot detects the element for to ensure it is detected properly
                int detections=0;
                long startTime=System.currentTimeMillis();
                while (!found)
                {
                    telemetry.addData("found=", found);
                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getRecognitions();
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
                                telemetry.update();
                                if(recognition.getLabel().equals("Pixel"))
                                {
                                    detections++;
                                    if(detections>=minDetections)
                                    {
                                        found = true;
                                        leftPos=recognition.getLeft();
                                        topPos=recognition.getTop();
                                        break;
                                    }
                                }

                                i++;

                            }
                        }
                    }
                    long elapsedTime=System.currentTimeMillis();
                    long num=elapsedTime-startTime;
                    if(num>2000) {
                        failsafe=true;
                        break;
                    }
                }
                telemetry.addData("Left: ",leftPos);
                telemetry.addData("Top: ", topPos);

                //Detecting which position element is at (from left and top)
                if(leftPos<=X_LEFT+175)
                {
                    result='l';
                }
                else if(leftPos>=X_LEFT && leftPos<300)
                {
                    result='m';
                }
                else
                {
                    result='r';
                }
                telemetry.addData("Level: ", result);
                telemetry.update();
                if(result == 'm')
                {
                    while(!done) {
                        frontLeft.setPower(0.8);
                        backLeft.setPower(0.8);
                        backRight.setPower(0.8);
                        frontRight.setPower(0.8);
                        frontLeft.setTargetPosition(-1250);
                        frontRight.setTargetPosition(1250);
                        backLeft.setTargetPosition(1250);
                        backRight.setTargetPosition(-1250);
                        Thread.sleep(375);
                        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
                        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setTargetPosition(-2400);
                        frontLeft.setTargetPosition(-2400);
                        backRight.setTargetPosition(-2400);
                        backLeft.setTargetPosition(-2400);
                        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(2000);
                        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
                        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setTargetPosition(1150);
                        frontLeft.setTargetPosition(-1150);
                        backRight.setTargetPosition(1150);
                        backLeft.setTargetPosition(-1150);
                        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1200);
                        intake.setPower(0.1);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        intake.setTargetPosition(40);
                        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1500);
                        intake.setPower(0.1);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        intake.setTargetPosition(-40);
                        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1500);
                        intake.setPower(0);
                        frontLeft.setPower(0);
                        backLeft.setPower(0);
                        backRight.setPower(0);
                        frontRight.setPower(0);
                        done=true;
                    }
                }
                else if(failsafe || result == 'l')
                {
                    while(!done) {
                        frontLeft.setPower(0.8);
                        backLeft.setPower(0.8);
                        backRight.setPower(0.8);
                        frontRight.setPower(0.8);
                        frontRight.setTargetPosition(-1000);
                        frontLeft.setTargetPosition(-1000);
                        backRight.setTargetPosition(-1000);
                        backLeft.setTargetPosition(-1000);
                        Thread.sleep(1000);
                        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
                        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setTargetPosition(850);
                        frontLeft.setTargetPosition(-850);
                        backRight.setTargetPosition(850);
                        backLeft.setTargetPosition(-850);
                        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1000);
                        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
                        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setTargetPosition(-500);
                        frontLeft.setTargetPosition(-500);
                        backRight.setTargetPosition(-500);
                        backLeft.setTargetPosition(-500);
                        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1000);
                        intake.setPower(0.1);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        intake.setTargetPosition(40);
                        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1500);
                        intake.setPower(0.1);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        intake.setTargetPosition(-40);
                        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1500);
                        intake.setPower(0);
                        frontLeft.setPower(0);
                        backLeft.setPower(0);
                        backRight.setPower(0);
                        frontRight.setPower(0);
                        done=true;
                    }
                }
                else if(result == 'r')
                {
                    while(!done) {
                        frontLeft.setPower(0.8);
                        backLeft.setPower(0.8);
                        backRight.setPower(0.8);
                        frontRight.setPower(0.8);
                        frontLeft.setTargetPosition(-610);
                        frontRight.setTargetPosition(610);
                        backLeft.setTargetPosition(610);
                        backRight.setTargetPosition(-610);
                        Thread.sleep(450);
                        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
                        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        frontRight.setTargetPosition(-775);
                        frontLeft.setTargetPosition(-775);
                        backRight.setTargetPosition(-775);
                        backLeft.setTargetPosition(-775);
                        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1000);
                        intake.setPower(0.1);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        intake.setTargetPosition(40);
                        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1500);
                        intake.setPower(0.1);
                        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        intake.setTargetPosition(-40);
                        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        Thread.sleep(1500);
                        intake.setPower(0);
                        frontLeft.setPower(0);
                        backLeft.setPower(0);
                        backRight.setPower(0);
                        frontRight.setPower(0);
                        done=true;
                    }
                }

            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

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

}