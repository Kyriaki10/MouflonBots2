package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "0+3")
public class sample3 extends RobotAuto{

    @Override
    public void init() {
        initHardware();
    }

    @Override
    public void loop(){
        autoTest();
        updateTelemetry();
    }
    private void autoTest() {
        switch (autonomousState) {
            // score preload
            case 1:
                strafeTo(34, -70, 0.9);
                setAutonomousState(2);
                break;
            case 2:
                if (targetReached()) {
                    turn(42, 1);
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(1);
                    autoTimer.reset();
                    setAutonomousState(3);
                }
                break;
            case 3:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeUp) < 70) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(4);
                }
                break;
            case 4:
                // pickup sample 1
                if (autoTimer.seconds() >= 1.2) {
                    turn(-112, 1);
                    setAutonomousState(5);
                }
                break;
            case 5:
                if (targetReached()) {
                    strafeTo(45.7, 113, 1);
                    cascade.setTargetPosition(cascadeDown);
                    cascade.setPower(0.6);
                    cascadeDump.setPosition(dumpIdle);
                    activeIntake.setPower(-1);
                    intakePitch.setPosition(pitchCollect);
                    setAutonomousState(6);
                }
                break;
            case 6:
                if (Math.abs(cascade.getCurrentPosition() - cascadeDown) < 20 && targetReached()) {
                    moveForwardSlow(11, 0.25);
                    setAutonomousState(7);
                }
                break;
            case 7:
                if (targetReached()) {
                    intakePitch.setPosition(pitchTransfer);
                    autoTimer.reset();
                    setAutonomousState(8);
                }
                break;
                // score sample 1
            case 8:
                if (autoTimer.seconds() >= 0.7) {
                    activeIntake.setPower(1);
                    turn(106, 1);
                    autoTimer.reset();
                    setAutonomousState(9);
                }
                break;
            case 9:
                if (autoTimer.seconds() >= 0.5 && targetReached()) {
                    activeIntake.setPower(0);
                    intakePitch.setPosition(pitchIdle);
                    setAutonomousState(10);
                }
                break;
            case 10:
                if (targetReached()) {
                    moveForward(-24.5, 1);
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(1);
                    autoTimer.reset();
                    setAutonomousState(11);
                }
                break;
            case 11:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeUp) < 70 && autoTimer.seconds() >= 0.3) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(12);
                }
                break;
                // pickup sample 2
            case 12:
                if (autoTimer.seconds() >= 1.2) {
                    turn(-125, 1);
                    setAutonomousState(13);
                }
                break;
            case 13:
                if (targetReached()) {
                    strafeTo(44, 117, 1);
                    cascade.setTargetPosition(cascadeDown);
                    cascade.setPower(0.6);
                    cascadeDump.setPosition(dumpIdle);
                    activeIntake.setPower(-1);
                    intakePitch.setPosition(pitchCollect);
                    autoTimer.reset();
                    setAutonomousState(14);
                }
                break;
            case 14:
                if (targetReached()  && Math.abs(cascade.getCurrentPosition() - cascadeDown) < 75) {
                    moveForwardSlow(12, 0.15);
                    setAutonomousState(15);
                }
                break;
            case 15:
                if (targetReached()) {
                    intakePitch.setPosition(pitchTransfer);
                    autoTimer.reset();
                    setAutonomousState(16);
                }
                break;
                // score sample 2
            case 16:
                if (autoTimer.seconds() >= 0.7) {
                    activeIntake.setPower(1);
                    strafeTo(-39, 95, 0.9);
                    autoTimer.reset();
                    setAutonomousState(17);
                }
                break;
            case 17:
                if (autoTimer.seconds() >= 0.5 && targetReached()) {
                    turn(128, 1);
                    activeIntake.setPower(0);
                    intakePitch.setPosition(pitchIdle);
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(0.8);
                    setAutonomousState(18);
                }
                break;
            case 18:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeUp) < 75) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(19);
                }
                break;
            case 19:
                if (autoTimer.seconds() >= 1.2) {
                    cascade.setTargetPosition(cascadeDown);
                    cascade.setPower(0.65);
                    cascadeDump.setPosition(dumpIdle);
                    setAutonomousState(20);
                }
                break;
            case 20:
                if (Math.abs(cascade.getCurrentPosition() - cascadeDown) < 75) {
                    setAutonomousState(-1);
                }
                break;
        }
    }
}
