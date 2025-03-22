package org.firstinspires.ftc.teamcode.autos.odometry;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "followerTest")
public class sampleAuto extends  PathFollower {


    Pose2d scorePose = new Pose2d(0,0, new Rotation2d(Math.toRadians(0)));
    Pose2d sample1Pose = new Pose2d(0,0, new Rotation2d(Math.toRadians(0)));
    Pose2d sample2Pose = new Pose2d(0,0, new Rotation2d(Math.toRadians(0)));
    Pose2d sample3Pose = new Pose2d(0,0, new Rotation2d(Math.toRadians(0)));

    @Override
    public void init() {
        super.init();
        initialize();
    }

    @Override
    public void loop() {
        super.loop();
        switch (autonomousState) {
            case 1:
                moveToPose(scorePose, 1);
                setAutonomousState(2);
                break;
            case 2:
                if (Math.abs (scorePose.getX() - odometry.getPose().getX()) < 1) {

                }
        }
        telemetry.addData( "CurrentPosition", odometry.getPose());

        telemetry.update();
    }
}
