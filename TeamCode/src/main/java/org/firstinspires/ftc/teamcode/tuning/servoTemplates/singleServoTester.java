package org.firstinspires.ftc.teamcode.tuning.servoTemplates;

import static org.firstinspires.ftc.teamcode.subsystem.System.checkButton;
import static org.firstinspires.ftc.teamcode.subsystem.System.round;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

//@Config
//@TeleOp
public class singleServoTester extends LinearOpMode {
    public static double servoPos = 0.5;
    public static boolean USE_DASHBOARD = false;

    @Override
    public void runOpMode() throws InterruptedException {
        Subsystem subsystem = new Subsystem(hardwareMap);

        Gamepad currentGamepad1 = new Gamepad();

        subsystem.servo.setPosition(servoPos);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (USE_DASHBOARD){
                subsystem.servo.setPosition(servoPos);
            } else if (gamepad1.dpad_up  && checkButton(currentGamepad1, "dpad_up")) {
                servoPos += 0.01;
            } else if (gamepad1.dpad_down && checkButton(currentGamepad1, "dpad_down")) {
                servoPos -= 0.01;
            }

            if (gamepad1.square || gamepad1.triangle || gamepad1.circle || gamepad1.cross) {
                subsystem.servo.setPosition(servoPos);
            }

            servoPos = Math.max(Math.min(servoPos, 1), 0);

            currentGamepad1.copy(gamepad1);

            telemetry.addData("servo getPosition", round(subsystem.servo.getPosition(), 2));
            telemetry.addData("servoPos",round(servoPos, 2));
            telemetry.update();
        }
    }
}