package org.firstinspires.ftc.teamcode.autos.odometry;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class PIDTest extends PathFollower{

    Pose2d startPose = new Pose2d(0,0, new Rotation2d(Math.toRadians(0)));
    Pose2d endPose = new Pose2d(50,0, new Rotation2d(Math.toRadians(0)));


    @Override
    public void init() {
        super.init();
        initialize();
    }

    @Override
    public void loop() {
        super.loop();
        moveToPose(startPose, 0.5);
        moveToPose(endPose, 0.5);

        telemetry.addData("Current Position", odometry.getPose().getX());
        telemetry.update();
    }
}
