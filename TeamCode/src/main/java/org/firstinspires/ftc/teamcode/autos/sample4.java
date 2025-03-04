package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "0+4")
public class sample4 extends RobotAuto{

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
                    turn(38, 1);
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(1);
                    autoTimer.reset();
                    setAutonomousState(3);
                }
                break;
            case 3:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeUp) < 50) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(4);
                }
                break;
            case 4:
                // pickup sample 1
                if (autoTimer.seconds() >= 1.2) {
                    turn(-105,1);
                    setAutonomousState(5);
                }
                break;
            case 5:
                if (targetReached()) {
                    strafeTo(46, 116, 0.9);
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
                    moveForward(12, 0.15);
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
                if (autoTimer.seconds() >= 0.75) {
                    activeIntake.setPower(1);
                    turn(100, 1);
                    autoTimer.reset();
                    setAutonomousState(9);
                }
                break;
            case 9:
                if (autoTimer.seconds() >= 0.75 && targetReached()) {
                    activeIntake.setPower(0);
                    intakePitch.setPosition(pitchIdle);
                    setAutonomousState(10);
                }
                break;
            case 10:
                if (targetReached()) {
                    moveForward(-27, 1);
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(1);
                    autoTimer.reset();
                    setAutonomousState(11);
                }
                break;
            case 11:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeUp) < 45 && autoTimer.seconds() >= 0.3) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(12);
                }
                break;
                // pickup sample 2
            case 12:
                if (autoTimer.seconds() >= 1.5) {
                    turn(-120,1);
                    setAutonomousState(13);
                }
                break;
            case 13:
                if (targetReached()) {
                    strafeTo(44, 125, 1);
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
                if (targetReached()  && Math.abs(cascade.getCurrentPosition() - cascadeDown) < 25) {
                    moveForward(12, 0.15);
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
                    strafeTo(-40, 95, 0.9);
                    autoTimer.reset();
                    setAutonomousState(17);
                }
                break;
            case 17:
                if (autoTimer.seconds() >= 0.7 && targetReached()) {
                    turn(125,1);
                    activeIntake.setPower(0);
                    intakePitch.setPosition(pitchIdle);
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(0.8);
                    setAutonomousState(18);
                }
                break;
            case 18:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeUp) < 45) {
                    cascadeDump.setPosition(dumpDeposit);
                    autoTimer.reset();
                    setAutonomousState(19);
                }
                break;
            // pickup sample 3
            case 19:
                if (autoTimer.seconds() >= 1.5) {
                    turn(-125, 1);
                    setAutonomousState(20);
                }
                break;
            case 20:
                if (targetReached()) {
                    strafeTo(42, 100, 1);
                    cascade.setTargetPosition(cascadeDown);
                    cascade.setPower(0.65);
                    cascadeDump.setPosition(dumpIdle);
                    activeIntake.setPower(-1);
                    intakePitch.setPosition(pitchCollect);
                    setAutonomousState(21);
                }
                break;
            case 21:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeDown) < 10) {
                    moveForward(10, 0.2);
                    setAutonomousState(22);
                }
                break;
            case 22:
                if (targetReached()) {
                    intakePitch.setPosition(pitchTransfer);
                    autoTimer.reset();
                    setAutonomousState(23);
                }
                break;
            // score sample 3
            case 23:
                if (autoTimer.seconds() >= 0.7) {
                    activeIntake.setPower(1);
                    autoTimer.reset();
                    strafeTo(43, -95, 1);
                    setAutonomousState(24);
                }
                break;
            case 24:
                if (autoTimer.seconds() >= 0.5) {
                    activeIntake.setPower(0);
                    intakePitch.setPosition(pitchIdle);
                    setAutonomousState(25);
                }
                break;
            case 25:
                if (targetReached()) {
                    turn(125, 1);
                    cascade.setTargetPosition(cascadeUp);
                    cascade.setPower(1);
                    setAutonomousState(26);
                }
                break;
            case 26:
                if (targetReached() && Math.abs(cascade.getCurrentPosition() - cascadeUp) < 25) {
                    cascadeDump.setPosition(dumpIdle);
                    autoTimer.reset();
                    setAutonomousState(27);
                }
                break;
            case 27:
                if (autoTimer.seconds() >= 1.5) {
                    cascade.setTargetPosition(cascadeDown);
                    cascade.setPower(1);
                    cascadeDump.setPosition(dumpIdle);
                    setAutonomousState(28);
                }
                break;
            case 28:
                if (Math.abs(cascade.getCurrentPosition() - cascadeDown) < 15) {
                    setAutonomousState(-1);
                }
        }
    }
}
