package org.firstinspires.ftc.teamcode.autos.odometry;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.kinematics.HolonomicOdometry;
import com.arcrobotics.ftclib.kinematics.Odometry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.autos.driveEncoders.RobotAuto;

@Config
@Autonomous
public class OdometryLocalization extends RobotAuto {




    public MotorEx leftEncoder, rightEncoder, middleEncoder;
    public static double trackWidth = 42  / 2.54;
    public static double centerWheelOffset = -18.5 / 2.54;
    public static double WheelDiameter = 6 / 2.54;
    public static double TICK_PER_REV = 8192;
    public static double DISTANCE_PER_PULSE = (Math.PI * (WheelDiameter) / TICK_PER_REV);

    public static double Translational_Kp = 0.05; // 0.01 - 0.1
    public static double Translational_Ki = 0;
    public static double Translational_Kd = 0; // 0.001 - 0.01
    public static double Rotational_Kp = 0.01;  // 0.01 - 0.05
    public static double Rotational_Ki = 0;
    public static double Rotational_Kd = 0; // 0.001 - 0.005
    public Odometry odometry;

    ElapsedTime autoTimer = new ElapsedTime();

    @Override
    public void init() {
        initialize();
    }

    @Override
    public void loop() {
        odometry.updatePose();
        PositionTracker.robotPose = odometry.getPose();

        telemetry.addData("robotPose", odometry.getPose());
        telemetry.addData("imuHeading", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        telemetry.addData("imuHeadingError", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - odometry.getPose().getHeading());
        telemetry.addData("leftPos", leftEncoder.getCurrentPosition());
        telemetry.addData("rightPos", rightEncoder.getCurrentPosition());
        telemetry.addData("midPos", middleEncoder.getCurrentPosition());
        telemetry.update();

        autoTimer.reset();
    }

    public static class PositionTracker {
        public  static Pose2d robotPose;
    }

    public void initialize() {
        initHardware();
        leftEncoder = new MotorEx(hardwareMap, "leftBack");
        rightEncoder = new MotorEx(hardwareMap, "rightFront");
        middleEncoder = new MotorEx(hardwareMap, "rightBack");

        leftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        rightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        middleEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);

        leftEncoder.setInverted(true);
        rightEncoder.setInverted(false);
        middleEncoder.setInverted(true);

        odometry = new HolonomicOdometry(
                leftEncoder::getDistance,
                rightEncoder::getDistance,
                middleEncoder::getDistance,
                trackWidth,
                centerWheelOffset
        );

        leftEncoder.resetEncoder();
        rightEncoder.resetEncoder();
        middleEncoder.resetEncoder();
        odometry.updatePose(new Pose2d(0,0,new Rotation2d(0)));

    }
}