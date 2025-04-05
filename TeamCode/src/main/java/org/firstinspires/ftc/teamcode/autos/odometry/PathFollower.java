package org.firstinspires.ftc.teamcode.autos.odometry;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

@Config
public class PathFollower extends OdometryLocalization {

    public Pose2d targetPose = new Pose2d(0,0, new Rotation2d(0));


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
            double yPower = pidControllers.yController.calculate(errorY);
            double turnPower = pidControllers.headingController.calculate(errorHeading);

            xPower = Math.signum(xPower) * Math.min(Math.abs(xPower), maxPower);
            yPower = Math.signum(yPower) * Math.min(Math.abs(yPower), maxPower);
            turnPower = Math.signum(turnPower) * Math.min(Math.abs(turnPower), maxPower);

            driveRobot(xPower, yPower, turnPower);

            telemetry.addData("xPower", xPower);
            telemetry.addData("yPower", yPower);
            telemetry.addData("turnPower", turnPower);
            telemetry.addData("targetPose", targetPose);
            telemetry.addData("currentPose", odometry.getPose());
            telemetry.update();


        } while (Math.abs(errorX) < 0.5 || Math.abs(errorY) < 0.5 || Math.abs(errorHeading) < 0.5);

        stopRobot();
    }

    public void moveX(double inches, double maxPower) {
        PIDControllers pidControllers = new PIDControllers();
        double errorX;

        do{
            double currentXPosition = odometry.getPose().getX();
            double xTarget =  currentXPosition + inches;
            errorX = xTarget - currentXPosition;

            double xPower = pidControllers.xController.calculate(errorX);
            xPower = Math.signum(xPower) * Math.min(Math.abs(xPower), maxPower);
            driveRobot(xPower, 0,0);

            telemetry.addData("xTarget", xTarget);
            telemetry.addData("xPose", currentXPosition);
            telemetry.addData("errorX", errorX);
            telemetry.addData("xPower", xPower);
            telemetry.update();

        } while((Math.abs(errorX) > 0.5));

        stopRobot();


    }


    public void turn(double degrees, double maxPower) {
        PIDControllers pidControllers = new PIDControllers();
        double headingError;
        double initialHeadingPosition = odometry.getPose().getHeading();
        double headingTarget = initialHeadingPosition + degrees;

        do {
            double currentHeadingPosition = odometry.getPose().getHeading();
            headingError = headingTarget - currentHeadingPosition ;

            double turnPower = pidControllers.headingController.calculate(headingError);
            turnPower = Math.signum(turnPower) * Math.min(turnPower, maxPower);
            driveRobot(0,0, turnPower);

            telemetry.addData("headingTarget", headingTarget);
            telemetry.addData("headingPose", currentHeadingPosition);
            telemetry.addData("headingError", headingError);
            telemetry.addData("imuHeading", imu.getRobotYawPitchRollAngles().getYaw());
            telemetry.addData("turnPower", turnPower);
            telemetry.update();

        } while ((Math.abs(headingError) > 0.5));
        stopRobot();
    }

    public void moveY(double inches, double maxPower) {
        PIDControllers pidControllers = new PIDControllers();
        double yError;
        double initialYPosition = odometry.getPose().getY();
        double yTarget = initialYPosition + inches;

        do {
            double currentYPosition = odometry.getPose().getY();
            yError = yTarget - currentYPosition;

            double yPower = pidControllers.yController.calculate(yError);
            yPower = Math.signum(yPower) * Math.min(yPower, maxPower);
            driveRobot(0, yPower, 0);

            telemetry.addData("xTarget", yTarget);
            telemetry.addData("xPose", currentYPosition);
            telemetry.addData("yError", yError);
            telemetry.addData("yPower", yPower);
            telemetry.update();

        } while (Math.abs(yError) > 0.5);
        stopRobot();
    }

    public boolean poseReached(Pose2d target) {
        return Math.abs(target.getX() - odometry.getPose().getX()) < 0.5 &&
                Math.abs(target.getY() - odometry.getPose().getY()) < 0.5 &&
                Math.abs(Math.toDegrees(target.getHeading() - odometry.getPose().getHeading())) < 3;
    }

    public boolean poseReachedX(double xTarget) {
        return Math.abs(odometry.getPose().getX() - xTarget) < 1;
    }

    public boolean poseReachedY(double yTarget) {
        return Math.abs(odometry.getPose().getX() - yTarget) < 1;
    }

    public boolean poseReachedHeading(double headingTarget) {
        return Math.abs(odometry.getPose().getX() - headingTarget) < 1;
    }

    @Override
    public void init() {
    }

    @Override
    public void loop() {
    }
}
