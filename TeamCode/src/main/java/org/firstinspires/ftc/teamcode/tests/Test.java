package org.firstinspires.ftc.teamcode.tests;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleops.Robot;

@Disabled
@Config
@TeleOp(name = "Testing", group = "test")
public class Test extends Robot {
    @Override
    public void runOpMode() {
        initHardware();

        waitForStart();

        while (opModeIsActive()) {


            telemetry.update();
        }
    }
}
