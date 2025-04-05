package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public abstract class Robot2 extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack;
    public DcMotorEx intakeSlide, cascade;
    Servo cascadeDump, intakePitch;
    CRServo activeIntake;
    double turnMultiplier = 0.8;

    // Cascade Variables

    static final int cascadeHighBasket = -800;
    static final int cascadeLowBasket = -450;
    int cascadeDown = 0;
    double dumpDeposit = 0.07;
    double dumpIdle = 0.82;
    ElapsedTime cascadeTimer = new ElapsedTime();
    double dumpTimer = 1.5;

    // ExtendingIntake Variables

    public static int slideExtend = 830; // 10.4329:1 to 8.3521:1 // 575 rpm to 718 rpm
    public  static int slideTarget;
    int slideIdle = 0;

    public static double pitchStart = 0.85;
    public static double pitchIdle = 0.7;
    public static double pitchTransfer = 1;
    public static double pitchExtend = 0.5;
    public static double pitchCollect = 0.35;
    public static double pitchTarget = pitchCollect;

    double intakeTimer = 0.5;
    ElapsedTime transferTimer = new ElapsedTime();


    public enum CascadeState {
        CASCADE_IDLE, CASCADE_EXTEND_HIGH, CASCADE_EXTEND_LOW, CASCADE_DEPOSIT, CASCADE_RETRACT, CASCADE_TRANSFER
    }
    CascadeState cascadeState = CascadeState.CASCADE_IDLE;

    public enum IntakeState {
        INTAKE_IDLE, INTAKE_EXTEND, INTAKE_COLLECT, INTAKE_RETRACT, INTAKE_TRANSFER
    }
    IntakeState intakeState = IntakeState.INTAKE_IDLE;

    public static int cascadeTarget;


    public void initHardware() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        cascade = hardwareMap.get(DcMotorEx.class, "cascade");
        cascadeDump = hardwareMap.get(Servo.class, "cascadeDump");
        intakeSlide = hardwareMap.get(DcMotorEx.class, "intakeSlide");
        intakePitch = hardwareMap.get(Servo.class, "intakePitch");
        activeIntake = hardwareMap.get(CRServo.class, "activeIntake");

        cascade.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cascade.setTargetPosition(0);
        cascade.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setTargetPosition(0);
        intakeSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        cascadeDump.setPosition(dumpIdle);
        intakePitch.setPosition(pitchStart);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        pitchTarget = pitchCollect;

    }



}