package org.firstinspires.ftc.teamcode.autos.driveEncoders;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Config
public abstract class RobotAuto extends OpMode {
   public DcMotorEx leftFront, leftBack, rightFront, rightBack, cascade, intakeSlide;
   public  Servo cascadeDump, intakePitch;
  public  CRServo activeIntake;
  public   IMU imu;

  public VoltageSensor voltageSensor;

    int cascadeUp = 880;
    int cascadeDown = 0;
    double dumpDeposit = 0.07;
    double dumpIdle = 0.8;

    // ExtendingIntake Variables

    int slideTransfer = 300;
    int slideIdle = 0;
    double pitchStart = 0.85;
    double pitchIdle = 0.7;
    double pitchTransfer = 0.97;
    public static double pitchCollect = 0.347;

    double intakeTimer = 1;

    public int leftFrontTarget = 0, leftBackTarget = 0, rightFrontTarget = 0, rightBackTarget = 0;

    public static final int TICKS_PER_INCH = 45;
    public static final int TICKS_PER_LATERAL_INCH = 48;
    public static final int TICKS_PER_DEGREE = 11;

    public int autonomousState = 1;

    ElapsedTime autoTimer = new ElapsedTime();

    ElapsedTime runTime = new ElapsedTime();

    double angle = 0;

    public void initHardware() {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition(leftFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        rightBack.setTargetPosition(rightBackTarget);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        cascade = hardwareMap.get(DcMotorEx.class, "cascade");
        cascadeDump = hardwareMap.get(Servo.class, "cascadeDump");
        intakeSlide = hardwareMap.get(DcMotorEx.class, "intakeSlide");
        intakePitch = hardwareMap.get(Servo.class, "intakePitch");
        activeIntake = hardwareMap.get(CRServo.class, "activeIntake");

        cascade.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cascade.setTargetPosition(cascadeDown);
        cascade.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setTargetPosition(slideIdle);
        intakeSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cascade.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        cascadeDump.setPosition(dumpIdle);
        intakePitch.setPosition(pitchStart);

        runTime.reset();

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(
                new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP)));
        imu.resetYaw();

        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());

        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
    }

    public void updateTelemetry() {
        telemetry.addData("AutoState: ", autonomousState);
        telemetry.addData("ActualHeading",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        telemetry.addData("CalculatedHeading", angle);
        telemetry.addData("HeadingError",-imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - angle);
        telemetry.addData("leftFront: ", leftFront.getCurrentPosition());
        telemetry.addData("leftBack: ", leftBack.getCurrentPosition());
        telemetry.addData("rightFront: ", rightFront.getCurrentPosition());
        telemetry.addData("rightBack: ", leftFront.getCurrentPosition());
        telemetry.addData("leftFrontTarget: ", leftFront.getTargetPosition());
        telemetry.addData("leftBackTarget: ", leftBack.getTargetPosition());
        telemetry.addData("rightFrontTarget: ", rightFront.getTargetPosition());
        telemetry.addData("rightBackTarget: ", leftFront.getTargetPosition());
        telemetry.addData("cascadeTarget: ", cascade.getTargetPosition());
        telemetry.addData("cascade: ", cascade.getCurrentPosition());
        telemetry.update();
    }

    public boolean targetReached() {
        return Math.abs(leftFront.getCurrentPosition() - leftFrontTarget) < 10
                && Math.abs(leftBack.getCurrentPosition() - leftBackTarget) < 10
                && Math.abs(rightFront.getCurrentPosition() - rightFrontTarget) < 10
                && Math.abs(rightBack.getCurrentPosition() - rightBackTarget) < 10;
    }

    public void moveForward(double inch, double maxSpeed) {

        leftFrontTarget += (int) inch * TICKS_PER_INCH;
        leftBackTarget += (int)inch * TICKS_PER_INCH;
        rightFrontTarget += (int) inch * TICKS_PER_INCH;
        rightBackTarget += (int) inch * TICKS_PER_INCH;

        leftFront.setTargetPosition(leftFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        rightBack.setTargetPosition(rightBackTarget);

        double minPower = 0.6;
        double rampTime = 0.4;
        double startTime = runTime.seconds();

        while (!targetReached()) {
            double elapsedTime = runTime.seconds() - startTime;
            double speed = Math.min(maxSpeed, minPower + (maxSpeed - minPower) * (elapsedTime / rampTime));

            leftFront.setPower(speed);
            leftBack.setPower(speed);
            rightFront.setPower(speed);
            rightBack.setPower(speed);
        }

    }

    public void moveForwardSlow(double inch, double speed) {

        leftFrontTarget += (int) inch * TICKS_PER_INCH;
        leftBackTarget += (int)inch * TICKS_PER_INCH;
        rightFrontTarget += (int) inch * TICKS_PER_INCH;
        rightBackTarget += (int) inch * TICKS_PER_INCH;

        leftFront.setTargetPosition(leftFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        rightBack.setTargetPosition(rightBackTarget);

            leftFront.setPower(speed);
            leftBack.setPower(speed);
            rightFront.setPower(speed);
            rightBack.setPower(speed);
    }

    public void turn(double degrees, double maxSpeed) {

        angle += degrees;

        leftFrontTarget += (int) degrees * TICKS_PER_DEGREE;
        leftBackTarget += (int) degrees * TICKS_PER_DEGREE;
        rightFrontTarget -= (int) degrees * TICKS_PER_DEGREE;
        rightBackTarget -= (int) degrees * TICKS_PER_DEGREE;

        leftFront.setTargetPosition(leftFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        rightBack.setTargetPosition(rightBackTarget);


        double minPower = 0.6;
        double rampTime = 0.4;
        double startTime = runTime.seconds();

        while (!targetReached()) {
            double elapsedTime = runTime.seconds() - startTime;
            double speed = Math.min(maxSpeed, minPower + (maxSpeed - minPower) * (elapsedTime / rampTime));

            leftFront.setPower(speed);
            leftBack.setPower(speed);
            rightFront.setPower(speed);
            rightBack.setPower(speed);
        }

    }

    public void strafeTo(double inches, double degrees, double maxSpeed) {
        double radians = Math.toRadians(degrees);

        double leftFrontPower, leftBackPower, rightFrontPower, rightBackPower;
        if (Math.abs(Math.tan(radians + Math.PI / 4)) > 1) {
            leftFrontPower = 1;
            leftBackPower = cot(radians);
            rightFrontPower = cot(radians);
            rightBackPower = 1;
        } else {
            leftFrontPower = Math.tan(radians + Math.PI / 4);
            leftBackPower = 1;
            rightFrontPower = 1;
            rightBackPower = Math.tan(radians + Math.PI / 4);
        }

        double leftFrontMul = Math.sin(radians + Math.PI / 4);
        double leftBackMul = Math.cos(radians + Math.PI / 4);
        double rightFrontMul = Math.cos(radians + Math.PI / 4);
        double rightBackMul = Math.sin(radians + Math.PI / 4);

        int ticks =  (int) inches * TICKS_PER_LATERAL_INCH;

        leftFrontTarget += (int) (leftFrontMul * ticks);
        leftBackTarget += (int) (leftBackMul * ticks);
        rightFrontTarget += (int) (rightFrontMul * ticks);
        rightBackTarget += (int) (rightBackMul * ticks);

        leftFront.setTargetPosition(leftFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        rightBack.setTargetPosition(rightBackTarget);

        double minPower = 0.6;
        double rampTime = 0.4;
        double startTime = runTime.seconds();

        while (!targetReached()) {
            double elapsedTime = runTime.seconds() - startTime;
            double speed = Math.min(maxSpeed, minPower + (maxSpeed - minPower) * (elapsedTime / rampTime));

            leftFront.setPower(speed * leftFrontPower);
            leftBack.setPower(speed * leftBackPower);
            rightFront.setPower(speed * rightFrontPower);
            rightBack.setPower(speed * rightBackPower);
        }

    }
    public void setAutonomousState(int autonomousState) {
        this.autonomousState = autonomousState;
    }

    public double cot(double radians) {
        return  1/Math.tan(radians + Math.PI / 4);
    }

    public void stopAll() {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        cascade.setPower(0);
        intakeSlide.setPower(0);
        activeIntake.setPower(0);

        cascadeDump.setPosition(dumpIdle);
        intakePitch.setPosition(pitchIdle);
    }

    public double voltagePowerAdjustment(){
        double voltage = voltageSensor.getVoltage();
        return Range.clip(Math.pow(12.5/voltage, 1.8), -1, 1);
    }
}


