package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MotorTest", group = "test")
public class MotorTest extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack;
    Servo intakePitch;
    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        intakePitch = hardwareMap.get(Servo.class, "intakePitch");
        intakePitch.setPosition(0.8);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        waitForStart();

        while (opModeIsActive()) {

            int Average = (Math.abs(leftFront.getCurrentPosition())+ Math.abs(leftBack.getCurrentPosition()) + Math.abs(rightBack.getCurrentPosition()) + Math.abs(rightFront. getCurrentPosition()) / 4);

            telemetry.addData("leftFront: ", leftFront.getCurrentPosition());
            telemetry.addData("leftBack: ", leftBack.getCurrentPosition());
            telemetry.addData("rightFront: ", rightFront.getCurrentPosition());
            telemetry.addData("rightBack: ", rightBack.getCurrentPosition());
            telemetry.addData("Average", Average);
            telemetry.update();

        }

    }
}
