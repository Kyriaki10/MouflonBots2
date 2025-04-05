package org.firstinspires.ftc.teamcode.tests;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autos.odometry.PathFollower;

@Disabled
@Config
@TeleOp(name = "Testing", group = "test")

// For Random Tests

public class Test extends PathFollower {

    ElapsedTime timer = new ElapsedTime();
    @Override
    public void init() {
        initialize();
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());
    }
    @Override
    public void loop() {
        if(!(timer.seconds() > 2)) {
            driveRobot(1,0, 0.5);
        } else stopRobot();

        }
    }


