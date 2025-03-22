package org.firstinspires.ftc.teamcode.tests;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleops.Robot;

@Disabled
@Config
@TeleOp(name = "Testing", group = "test")

// For Random Tests

public class Test extends Robot {

    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());
        waitForStart();

        while(opModeIsActive()) {

            telemetry.addData("Target", cascadeTarget);
            telemetry.addData("Position", cascade.getCurrentPosition());
            telemetry.update();

        }
    }

}
