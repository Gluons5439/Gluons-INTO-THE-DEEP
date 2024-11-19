package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
@TeleOp(name = "teleop2024", group = "TeleOp") //puts it in a group on the phone, options are teleop and autonomous

public class teleop2024 extends LinearOpMode { //linear op mode allows for more control, ALWAYS USE LINEAR OP MODE
    @Override //idfk why this is here it just needs to be for the code to not crash
    public void runOpMode() throws InterruptedException { //same as above
        DcMotor frontLeft;  //declares all motors, if you don't know this by now you have issues
        DcMotor backLeft;
        DcMotor frontRight;
        DcMotor backRight;
        DcMotor slides;
        DcMotor pivot;
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); //sets the drive train motors to the proper thing in the robot config
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        slides = hardwareMap.dcMotor.get("slides");
        pivot = hardwareMap.get(DcMotor.class, "pivot");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //sets runmode to using encoders u should be able to tell this if u can read
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //pivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slides.setDirection(DcMotor.Direction.REVERSE);
        //Dhruv likes To smile(stupid coder wrote that) so does aanaaanthaaa
        //DcMotor pulley2;
        //pulley2 = hardwareMap.dcMotor.get("pulley2");
        pivot.setDirection(DcMotor.Direction.FORWARD);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //pulley2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //pulley2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //pivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //pulley2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //pulley2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //pivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setTargetPosition(0);
        //pivot.setTargetPosition(0);
        //pulley2.setTargetPosition(0);
        //pulley2.setPower(0);
        slides.setPower(0);
        pivot.setPower(0);
        /*
        DcMotor intake;
        intake = hardwareMap.dcMotor.get("intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //intake doesn't need encoder if u think so ur stupid
        intake.setDirection(DcMotor.Direction.FORWARD); //you can set direction to forward or reverse
        intake.setPower(0);
        */
        Servo claw; //initializes servo
        claw = hardwareMap.servo.get("test"); //sets hw map for servo, hw stands for hardware map not homework...

        //Servo box2;
        //box2 = hardwareMap.servo.get("servo2");
        claw.setPosition(0);
        //box2.setPosition(0.75);
        //Servo droneLauncher;
        //droneLauncher = hardwareMap.servo.get("droneLauncher");
        //droneLauncher.setPosition(0.0);
        claw.setDirection(Servo.Direction.FORWARD);
        //box2.setDirection(Servo.Direction.FORWARD);
        boolean turn = false; //boolean for toggle for box, this actually requires 25 iq to understand
        boolean running = false;
        boolean runningDown = false;


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
            //TRANSLATION: I don't speak english, it's math for the drive train
            x = gamepad1.left_stick_x;
            rx = gamepad1.right_stick_x;
            frontRight.setPower(-(y -x-rx));
            frontLeft.setPower(-(x + (y + rx)));
            backLeft.setPower(-(y -x + rx));
            backRight.setPower(-(y + x - rx));
            if(gamepad2.right_stick_button)
            {
                manual = true;
            }
            if(gamepad2.right_bumper)
            {
                claw.setPosition(0.33);
            }
            if(gamepad2.left_bumper)
            {
                claw.setPosition(0);
            }
            frontLeft.setDirection(DcMotor.Direction.REVERSE); //these need to be reversed because how the gobilda motors are its stupid
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            //if(!manual && (gamepad2.right_stick_button))
            //{
            //    pivot.setPower(0.5);
            //    timer.reset();
            //}
            if(timer.seconds()>boxTime)
            {
                pivot.setTargetPosition(0);
                //pulley2.setTargetPosition(0); // the bottom
                //runningDown = false;
            }
            frontLeft.setDirection(DcMotor.Direction.REVERSE); //these need to be reversed because how the gobilda motors are its stupid
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            if(gamepad2.dpad_right)
            {
                pivot.setPower(0.8);
                timer.reset();
            }
            pivot.setPower(0);
            if(!gamepad2.dpad_right || gamepad2.dpad_left)
            {
                pivot.setPower(0);
            }
            if(gamepad2.dpad_up || running) { //when dpad up is pressed it makes pulley go up to encoder position UPPER, upper is a number for TICKS not REVOLUTIONS
                if (liftState == 'l') {
                    slides.setTargetPosition(300);
                    //pulley2.setTargetPosition(600); //moves the pulley slightly up
                    running = true;
                    liftState = 'u';
                    timer.reset(); //resets timer
                }
                if (liftState == 'u' && !slides.isBusy()) //slides are up and pulley is not running
                {
                    slides.setTargetPosition(300);
                    //box1.setPosition(0.65);
                    //box2.setPosition(0.65);
                    liftState = 'm'; // turns the box
                }
                if (liftState == 'm' && !slides.isBusy()) {
                    slides.setTargetPosition(slides.getCurrentPosition() + 200);
                    //pulley2.setTargetPosition(pulley2.getCurrentPosition()+200); // moves up from current position by 200 ticks
                }
                //pulley2.setPower(0.8);
                slides.setPower(0.5); //use 0.8 rather than 1 for consistency
            }
            else if(!manual && (gamepad2.dpad_down)){ //when dpad down is pressed it makes pulley go to encoder position LOWER
                if(liftState == 'm') //we are at the max
                {
                    //box1.setPosition(0.75);
                    //box2.setPosition(0.75);
                    timer.reset();
                    liftState = 'l';
                    runningDown = true; //time to go down!
                }

                if(timer.seconds()>boxTime)
                {
                    slides.setTargetPosition(0);
                    //pulley2.setTargetPosition(0); // the bottom
                    runningDown = false;
                }
                //pulley2.setPower(0.8);
                slides.setPower(0.8);

            }
            if(manual && gamepad2.dpad_down){
                slides.setTargetPosition(slides.getCurrentPosition()-200);
                //pulley2.setTargetPosition(pulley2.getCurrentPosition()-200); //slides move slightly down from current position
                //pulley2.setPower(0.8);
                slides.setPower(0.8);
            }
            else if(slides.isBusy() )//|| pulley2.isBusy())
            {
                //This is to not automatically set the power to 0 while the pulleys are running
            }
            else
            {

                slides.setPower(0);
                //pulley2.setPower(0); //Stops the pulley
            }
            /*if(gamepad2.y) //when b is pressed and is in 0 position turns the two box servos
            {
                test.setPosition(test.getPosition()+0.05);// research this, because negative numbers aint possible.
                //box2.setPosition(box2.getPosition()+0.05);
                Thread.sleep(50);
                turn = false;
            }
            if(gamepad2.x) {
                test.setPosition(test.getPosition() - 0.05);// research this, because negative numbers aint possible.
                //box2.setPosition(box2.getPosition() - 0.05);
                Thread.sleep(50);
                turn = true;
            }

             */

            telemetry.addData("Claw Position: ",claw.getPosition());
            //telemetry.addData("Drone position", droneLauncher.getPosition());

            telemetry.addData("Slides position",slides.getTargetPosition());


            telemetry.addData("Timer for FSM", timer.seconds());
            telemetry.update();
            idle(); //allows robot to catch up to the code
        }
    }
}

