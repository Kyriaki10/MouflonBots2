package org.firstinspires.ftc.teamcode.autos.odometry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Pose2d;

@Config
public class PathFollower extends OdometryLocalization {



    public static class PIDControllers {
        public PIDFController xController;
        public PIDFController yController;
        public PIDFController headingController;

        public PIDControllers() {

            xController = new PIDFController(Translational_Kp, Translational_Ki, Translational_Kd, 0);
            yController = new PIDFController(Translational_Kp, Translational_Ki, Translational_Kd, 0);
            headingController = new PIDFController(Rotational_Kp, Rotational_Ki, Rotational_Kd, 0);

        }
    }

    public void driveRobot(double x, double y, double turn) {
        double leftFrontPower = y + x + turn;
        double rightFrontPower = y - x - turn;
        double leftBackPower = y - x + turn;
        double rightBackPower = y + x - turn;

        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
    }

    public void stopRobot(){
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    public void moveToPose(Pose2d targetPose, double maxPower) {
        PIDControllers pidControllers = new PIDControllers();

        double errorX, errorY, errorHeading;

        do {
            Pose2d currentPose = odometry.getPose();


            errorX = targetPose.getX() - currentPose.getX();
            errorY = targetPose.getY() - currentPose.getY();
            errorHeading = targetPose.getHeading() - currentPose.getHeading();

            double xPower = pidControllers.xController.calculate(errorX);
            double yPower = pidControllers.xController.calculate(errorY);
            double turnPower = pidControllers.xController.calculate(errorHeading);

            xPower = Math.min(maxPower, xPower);
            yPower = Math.min(maxPower, yPower);
            turnPower = Math.min(maxPower, turnPower);

            driveRobot(xPower, yPower, turnPower);
        }
        while (Math.abs(errorX) < 0.5 || Math.abs(errorY) < 0.5 || Math.abs(errorHeading) < 0.5); {
            stopRobot();
        }

    }

    @Override
    public void init() {
    }

    @Override
    public void loop() {
    }
}
