package org.firstinspires.ftc.teamcode.IntoTheDeep;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name = "Gluons TeleOp NEW2", group = "TeleOp") //puts it in a group on the phone, options are teleop and autonomous
public class Teleop2024 extends LinearOpMode { //linear op mode allows for more control, ALWAYS USE LINEAR OP MODE
    @Override //idfk why this is here it just needs to be for the code to not crash
    public void runOpMode() throws InterruptedException { //same as above
        DcMotor frontLeft;  //declares all motors, if you don't know this by now you have issues
        DcMotor backLeft;
        DcMotor frontRight;
        DcMotor backRight;
        // DcMotor pulley1;
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); //sets the drive train motors to the proper thing in the robot config
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        // pulley1 = hardwareMap.dcMotor.get("pulley1");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // pulley1.setDirection(DcMotor.Direction.REVERSE);
        //Dhruv likes To smile(stupid coder wrote that)
        //DcMotor pulley2;
        /*pulley2 = hardwareMap.dcMotor.get("pulley2");
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
        DcMotor intake;
        intake = hardwareMap.dcMotor.get("intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //intake doesn't need encoder if u think so ur stupid
        intake.setDirection(DcMotor.Direction.FORWARD); //you can set direction to forward or reverse
        intake.setPower(0);

        Servo box1; //initalizes servo
        box1 = hardwareMap.servo.get("servo1"); //sets hw map for servo, hw stands for hardware map not homework...

        Servo box2;
        box2 = hardwareMap.servo.get("servo2");
        box1.setPosition(0.75);
        box2.setPosition(0.75);
        Servo droneLauncher;
        droneLauncher = hardwareMap.servo.get("droneLauncher");
        droneLauncher.setPosition(0.0);
        box1.setDirection(Servo.Direction.FORWARD);
        box2.setDirection(Servo.Direction.FORWARD);
        boolean turn = false; //boolean for toggle for box, this actually requires 25 iq to understand
        boolean running = false;
        boolean runningDown = false;

         */
        boolean manual = false;


        double x; //for math for drive train, this actually requires 140 iq to understand
        double y;
        double rx;
        int high_position = 3200;
        double maxSpeed = 0.8;
        char liftState = 'l';
        ElapsedTime timer = new ElapsedTime();
        int boxTime = 1;


        timer.reset();
        waitForStart(); //this means that all code after this point runs AFTER you click start, everything before runs on initalization
        while(opModeIsActive()) //same as above, all code needs to be in this while loop
        {
            y = -gamepad1.left_stick_y; //yo no hablo ingles, es mathematicas por la drive train
            x = gamepad1.left_stick_x;
            rx = gamepad1.right_stick_x;
            frontRight.setPower(y -x-rx);
            frontLeft.setPower(x + (y + rx));
            backLeft.setPower(y -x + rx);
            backRight.setPower(y + x - rx);
            if(gamepad2.right_bumper)
            {
                manual = true;
            }
            frontLeft.setDirection(DcMotor.Direction.REVERSE); //these need to be reversed because how the gobilda motors are its stupid
            backLeft.setDirection(DcMotor.Direction.REVERSE);

           /*
            telemetry.addData("Box Position: ",box1.getPosition());
            telemetry.addData("Drone position", droneLauncher.getPosition());
            telemetry.addData("Slides position",pulley1.getTargetPosition());

            */
            telemetry.addData("Timer for FSM", timer.seconds());
            telemetry.update();
            idle(); //allows robot to catch up to the code
        }
    }
}