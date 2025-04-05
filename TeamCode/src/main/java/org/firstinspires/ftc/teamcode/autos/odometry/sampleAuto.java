package org.firstinspires.ftc.teamcode.autos.odometry;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "followerTest")
public class sampleAuto extends  PathFollower {


    Pose2d scorePose = new Pose2d(20,70, new Rotation2d(38));
    Pose2d sample1Pose = new Pose2d(50,50, new Rotation2d(0));
    Pose2d sample2Pose = new Pose2d(50,55, new Rotation2d(0));
    Pose2d sample3Pose = new Pose2d(50,60, new Rotation2d(0));

    @Override
    public void init() {
        initialize();
    }

    @Override
    public void loop() {
        super.loop();
        odometry.updatePose();
        switch (autonomousState) {
            case 1:
                driveRobot(0.25,0,0);
                autoTimer.reset();
                setAutonomousState(2);
                break;
            case 2:
                if (autoTimer.seconds() > 2) {
                    stopRobot();
                }
        }
        telemetry.addData( "CurrentPosition", odometry.getPose());
        telemetry.addData("poseX", odometry.getPose().getX());
        telemetry.addData("targetX", scorePose.getX());
        telemetry.addData("poseY", odometry.getPose().getY());
        telemetry.addData("targetY", scorePose.getY());
        telemetry.addData("poseHeading", odometry.getPose().getHeading());
        telemetry.addData("targetHeading", scorePose.getHeading());
        telemetry.addData("heading", imu.getRobotYawPitchRollAngles().getYaw());
        telemetry.addData("State", autonomousState);
        telemetry.update();
    }
}
