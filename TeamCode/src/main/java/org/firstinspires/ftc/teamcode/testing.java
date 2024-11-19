package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp(name = "testing", group = "TeleOp") //puts it in a group on the phone, options are teleop and autonomous

public class testing extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor pivot;
        DcMotor slides;
        pivot = hardwareMap.get(DcMotor.class, "pivot");
        slides = hardwareMap.get(DcMotor.class, "slides");
        slides.setDirection(DcMotor.Direction.REVERSE);
        pivot.setDirection(DcMotor.Direction.FORWARD);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setTargetPosition(0);
        slides.setPower(0);
        pivot.setPower(0);
        ElapsedTime timer = new ElapsedTime();

        timer.reset();
        waitForStart();
        while(opModeIsActive()){
            if(gamepad2.dpad_up)
            {
                slides.setPower(0.8);
                timer.reset();
            }
            slides.setPower(0);
            if(gamepad2.dpad_right)
            {
                pivot.setPower(0.8);
                timer.reset();
            }
            pivot.setPower(0);
            if(gamepad2.dpad_down){
                slides.setPower(0);
                timer.reset();
            }
            if(gamepad2.dpad_left){
                pivot.setPower(0);
                timer.reset();
            }
        }


    }
}
