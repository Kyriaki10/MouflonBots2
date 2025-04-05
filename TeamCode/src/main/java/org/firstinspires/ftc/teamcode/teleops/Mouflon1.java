package org.firstinspires.ftc.teamcode.teleops;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOp")
public class Mouflon1 extends Robot2 {

    @Override
    public void runOpMode()  {
        initHardware();

        waitForStart();

        while (opModeIsActive()) {

            drivetrainControl2();
            cascadeControl();
            intakeControl();

            updateTelemetry();
        }
        stopAll();
    }


    private void drivetrainControl2() {
        if (intakeState == IntakeState.INTAKE_EXTEND ||
                intakeState == IntakeState.INTAKE_COLLECT ||
                intakeState == IntakeState.INTAKE_RETRACT ||
                cascadeState == CascadeState.CASCADE_EXTEND_HIGH ||
                cascadeState == CascadeState.CASCADE_EXTEND_LOW ||
                cascadeState == CascadeState.CASCADE_DEPOSIT) {
            turnMultiplier = 0.4;
        } else {
            turnMultiplier = 0.9;
        }

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x * turnMultiplier;

        double radians = Math.atan2(y, x);
        double power = Math.hypot(x, y);
        double sin = Math.sin(radians + Math.PI / 4);
        double cos = Math.cos(radians + Math.PI / 4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        leftFront.setPower((power * sin / max) + rx);
        leftBack.setPower((-power * cos / max) + rx);
        rightFront.setPower((-power * cos / max) - rx);
        rightBack.setPower((power * sin / max) - rx);

    }


    private void cascadeControl() {
        switch (cascadeState) {
            case CASCADE_IDLE:
                if (gamepad2.dpad_up) {
                    cascade.setTargetPosition(cascadeHighBasket);
                    cascade.setPower(1);
                    cascadeState = CascadeState.CASCADE_EXTEND_HIGH;
                }
                if (gamepad2.dpad_right) {
                    cascade.setTargetPosition(cascadeLowBasket);
                    cascade.setPower(1);
                    cascadeState = CascadeState.CASCADE_EXTEND_LOW;
                }
                break;
            case CASCADE_EXTEND_HIGH:
                if (Math.abs(cascade.getCurrentPosition() - cascadeHighBasket) < 70) {
                    cascadeDump.setPosition(dumpDeposit);
                    cascadeTimer.reset();
                    cascadeState = CascadeState.CASCADE_DEPOSIT;
                }
                break;
            case CASCADE_EXTEND_LOW:
                if (Math.abs(cascade.getCurrentPosition() - cascadeLowBasket) < 50) {
                    cascadeDump.setPosition(dumpDeposit);
                    cascadeTimer.reset();
                    cascadeState = CascadeState.CASCADE_DEPOSIT;
                }
                break;
            case CASCADE_DEPOSIT:
                if (cascadeTimer.seconds() >= dumpTimer) {
                    cascadeDump.setPosition(dumpIdle);
                    cascadeState = CascadeState.CASCADE_RETRACT;
                }
                break;
            case CASCADE_RETRACT:
                cascade.setTargetPosition(cascadeDown);
                cascade.setPower(1);
                cascadeState = CascadeState.CASCADE_TRANSFER;
                break;
            case CASCADE_TRANSFER:
                if (Math.abs(cascade.getCurrentPosition() - cascadeDown) < 20) {
                    cascade.setPower(0);
                    sleep(50);
                    cascade.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    sleep(20);
                    cascade.setTargetPosition(0);
                    cascade.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    cascadeState = CascadeState.CASCADE_IDLE;
                }
                break;
        }
        if (gamepad2.dpad_down) {
            cascade.setTargetPosition(0);
            cascade.setPower(0.5);
            cascadeDump.setPosition(dumpIdle);
            cascadeState = CascadeState.CASCADE_IDLE;
        }

    }

    private void intakeControl() {
        switch (intakeState) {
            case INTAKE_IDLE:
                if (gamepad2.y) {
                    intakeSlide.setTargetPosition(slideExtend);
                    intakeSlide.setPower(1);
                    intakePitch.setPosition(pitchExtend);
                    intakeState = IntakeState.INTAKE_EXTEND;
                }
                break;
            case INTAKE_EXTEND:
                if (gamepad2.x) {
                    activeIntake.setPower(-1);
                    intakePitch.setPosition(pitchTarget);
                    intakeState = IntakeState.INTAKE_COLLECT;
                }
                break;
            case INTAKE_COLLECT:
                if (gamepad2.a) {
                    intakeSlide.setTargetPosition(slideIdle);
                    intakeSlide.setPower(1);
                    intakePitch.setPosition(pitchTransfer);
                    intakeState = IntakeState.INTAKE_RETRACT;
                }
                if (gamepad2.left_bumper) {
                    pitchTarget += 0.003;
                    intakePitch.setPosition(pitchTarget);
                    intakeState = IntakeState.INTAKE_EXTEND;
                }
                if (gamepad2.right_bumper) {
                    pitchTarget -= 0.003;
                    intakePitch.setPosition(pitchTarget);
                    intakeState = IntakeState.INTAKE_EXTEND;
                }
                break;
            case INTAKE_RETRACT:
                if (Math.abs(intakeSlide.getCurrentPosition() - slideIdle) < 20) {
                    activeIntake.setPower(1);
                    intakeSlide.setPower(0);
                    sleep(50);
                    intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    sleep(20);
                    intakeSlide.setTargetPosition(0);
                    intakeSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    transferTimer.reset();
                    intakeState = IntakeState.INTAKE_TRANSFER;
                }
                break;
            case INTAKE_TRANSFER:
                if (transferTimer.seconds() >= intakeTimer) {
                    intakePitch.setPosition(pitchIdle);
                    activeIntake.setPower(0);
                    intakeState = IntakeState.INTAKE_IDLE;
                }
                break;
            default:
                telemetry.addLine("Cascade Error");
        }
        if (gamepad2.b) {
            activeIntake.setPower(1);
            intakePitch.setPosition(pitchExtend);
            intakeState = IntakeState.INTAKE_EXTEND;
        }
        if (gamepad1.dpad_up) {
            slideTarget = intakeSlide.getTargetPosition() + 15;
            intakeSlide.setTargetPosition(slideTarget);
            intakeSlide.setPower(0.5);
        }
        if (gamepad1.dpad_down) {
            slideTarget = intakeSlide.getTargetPosition() - 15;
            intakeSlide.setTargetPosition(slideTarget);
            intakeSlide.setPower(0.5);
        }
    }


    private void updateTelemetry() {

        telemetry.addData("Dump Position", cascadeDump.getPosition());
        telemetry.addData("Cascade State", cascadeState);
        telemetry.addData("Intake State", intakeState);
        telemetry.addData("Slide Position", intakeSlide.getCurrentPosition());
        telemetry.addData("Slide Target", intakeSlide.getTargetPosition());
        telemetry.addData("Pitch Position", intakePitch.getPosition());
        telemetry.addData("Cascade Position", cascade.getCurrentPosition());
        telemetry.addData("Cascade Target", cascade.getTargetPosition());
        telemetry.addData("CascadeState", cascadeState);
        telemetry.update();

    }

    private void stopAll() {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        cascade.setPower(0);
        intakeSlide.setPower(0);
        activeIntake.setPower(0);

        cascadeDump.setPosition(dumpIdle);
        intakePitch.setPosition(pitchIdle);
    }
    private void drivetrainControl1() {

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_y * turnMultiplier;
        double maxPower = Math.max(1.0, Math.abs(y) + Math.abs(x) + Math.abs(rx));

        leftFront.setPower((y + x + rx) / maxPower);
        leftBack.setPower((y - x + rx) / maxPower);
        rightFront.setPower((y - x - rx) / maxPower);
        rightBack.setPower((y + x - rx) / maxPower);

        if (intakeState == IntakeState.INTAKE_EXTEND ||
                intakeState == IntakeState.INTAKE_COLLECT ||
                cascadeState == CascadeState.CASCADE_EXTEND_HIGH ||
                cascadeState == CascadeState.CASCADE_EXTEND_LOW ||
                cascadeState == CascadeState.CASCADE_DEPOSIT) {
            turnMultiplier = 0.3;
        } else {
            turnMultiplier = 0.8;
        }
    }

}
