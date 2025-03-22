package org.firstinspires.ftc.teamcode.autos.odometry;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.kinematics.HolonomicOdometry;
import com.arcrobotics.ftclib.kinematics.Odometry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autos.driveEncoders.RobotAuto;

@Config
@Autonomous
public class OdometryLocalization extends RobotAuto {




    public MotorEx leftEncoder, rightEncoder, middleEncoder;
    public static double trackWidth = 40  / 2.54;
    public static double centerWheelOffset = -18.5 / 2.54;
    public static double WheelDiameter = 90 / 2.54;
    public static double TICK_PER_REV = 8192;
    public static double DISTANCE_PER_PULSE = (Math.PI * (WheelDiameter / 2)) / TICK_PER_REV;

    public static double Translational_Kp = 0.00001;
    public static double Translational_Ki = 0;
    public static double Translational_Kd = 0;
    public static double Rotational_Kp = 0;
    public static double Rotational_Ki = 0;
    public static double Rotational_Kd = 0;
    public Odometry odometry;


    @Override
    public void init() {
        initialize();
    }

    @Override
    public void loop() {
        odometry.updatePose();
        PositionTracker.robotPose = odometry.getPose();

        telemetry.addData("robotPose", odometry.getPose());
        telemetry.addData("leftPos", leftEncoder.getCurrentPosition());
        telemetry.addData("rightPos", rightEncoder.getCurrentPosition());
        telemetry.addData("midPos", middleEncoder.getCurrentPosition());
        telemetry.update();
    }

    public static class PositionTracker {
        public  static Pose2d robotPose;
    }

    public void initialize() {
        initHardware();
        leftEncoder = new MotorEx(hardwareMap, "leftFront");
        rightEncoder = new MotorEx(hardwareMap, "rightBack");
        middleEncoder = new MotorEx(hardwareMap, "leftBack");

        leftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        rightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        middleEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);

        leftEncoder.setInverted(false);
        rightEncoder.setInverted(false);
        middleEncoder.setInverted(false);

        odometry = new HolonomicOdometry(
                leftEncoder::getDistance,
                rightEncoder::getDistance,
                middleEncoder::getDistance,
                trackWidth,
                centerWheelOffset
        );


    }
}