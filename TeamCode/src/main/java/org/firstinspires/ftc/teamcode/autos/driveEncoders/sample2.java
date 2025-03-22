package org.firstinspires.ftc.teamcode.autos.driveEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "0+2")
public class sample2 extends RobotAuto {

    @Override
    public void init() {
        initHardware();
    }

    @Override
    public void loop() {
        updateState();
        updateTelemetry();
    }

    private void updateState() {
        switch (autonomousState) {
            case 0:
                moveForward(7, 1);
                setAutonomousState(1);
                break;
            case 1:
                if (targetReached()) {
                    turn(-90, 1);
                    setAutonomousState(2);
                }
                break;
            case 2:
                if (targetReached()) {
                    moveForward(15, 1);
                    autoTimer.reset();
                    setAutonomousState(3);
                }
                break;
            case 3:
                if (targetReached()) {
                    turn(135, 1);
                    setAutonomousState(4);
                }
                break;
            case 4:
                if (targetReached()) {
                    moveForward(-5, 1);
                    setAutonomousState(5);
                }
            case 5:
                if (targetReached()) {
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(1);
                    setAutonomousState(6);
                }
                break;
            case 6:
                if (Math.abs(cascade.getCurrentPosition() - cascadeUp) < 50) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(7);
                }
                break;
            case 7:
                if (autoTimer.seconds() > 2) {
                    cascadeDump.setPosition(dumpIdle);
                    cascade.setTargetPosition(cascadeDown);
                    cascade.setPower(0.8);
                    setAutonomousState(8);
                }
                break;
            case 8:
                if (Math.abs(cascade.getCurrentPosition() - cascadeDown) < 10) {
                    moveForward(26, 1);
                    setAutonomousState(9);
                }
                break;
            case 9:
                if (targetReached()) {
                    turn(-110, 1);
                    setAutonomousState(10);
                }
                break;
            case 10:
                if (targetReached()) {
                    strafeTo(-10, 90, 1);
                    autoTimer.reset();
                    setAutonomousState(11);
                }
                break;
            case 11:
                if (autoTimer.seconds() >= 2.5) {
                    intakePitch.setPosition(pitchCollect);
                    activeIntake.setPower(-1);
                    autoTimer.reset();
                    setAutonomousState(12);
                }
                break;
            case 12:
                if (autoTimer.seconds() >= 1.5) {
                    moveForward(9, 0.15);
                    setAutonomousState(13);
                }
                break;
            case 13:
                if (targetReached()) {
                    intakeSlide.setTargetPosition(300);
                    intakeSlide.setPower(1);
                    intakePitch.setPosition(pitchTransfer);
                    setAutonomousState(14);
                }
                break;
            case 14:
                if (Math.abs(intakeSlide.getCurrentPosition() - slideTransfer) < 10) {
                    activeIntake.setPower(1);
                    autoTimer.reset();
                    setAutonomousState(15);
                }
                break;
            case 15:
                if (autoTimer.seconds() >= intakeTimer) {
                    intakePitch.setPosition(pitchIdle);
                    intakeSlide.setTargetPosition(slideIdle);
                    intakeSlide.setPower(1);
                    activeIntake.setPower(0);
                    autoTimer.reset();
                    setAutonomousState(16);
                }
                break;
            case 16:
                if (autoTimer.seconds() >= 1) {
                    turn(105, 1);
                    setAutonomousState(18);
                }
                break;
            case 18:
                if (targetReached()) {
                    moveForward(-23, 1);
                    setAutonomousState(19);
                }
                break;
            case 19:
                if (targetReached()) {
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(1);
                    setAutonomousState(20);
                }
                break;
            case 20:
                if (Math.abs(cascade.getCurrentPosition() - cascadeUp) < 75) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(21);
                }
                break;
            case 21:
                if (autoTimer.seconds() > 2) {
                    cascadeDump.setPosition(dumpIdle);
                    cascade.setTargetPosition(cascadeDown);
                    cascade.setPower(0.5);
                    setAutonomousState(-2);
                }
                break;
        }

    }
}
