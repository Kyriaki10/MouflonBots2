package org.firstinspires.ftc.teamcode.autos.driveEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "park")
public class park extends RobotAuto {

    @Override
    public void init() {
        initHardware();
    }

    @Override
    public void loop() {
        updateState();
        updateTelemetry();
    }


        public void updateTelemetry() {
        telemetry.addData("leftFront: ", leftFront.getCurrentPosition());
        telemetry.addData("leftBack: ", leftBack.getCurrentPosition());
        telemetry.addData("rightFront: ", rightFront.getCurrentPosition());
        telemetry.addData("rightBack: ", leftFront.getCurrentPosition());
        telemetry.addData("leftFrontTarget: ", leftFront.getTargetPosition());
        telemetry.addData("leftBackTarget: ", leftBack.getTargetPosition());
        telemetry.addData("rightFrontTarget: ", rightFront.getTargetPosition());
        telemetry.addData("rightBackTarget: ", leftFront.getTargetPosition());
        telemetry.addData("AutoState: ", autonomousState);
        telemetry.update();
    }

    private void updateState() {
        switch (autonomousState) {
            case 1:
                moveForward(20, 1);
                setAutonomousState(2);
                break;
            case 2:
                if (targetReached()) {
                    setAutonomousState(-1);
                }
                break;

        }
    }
}

