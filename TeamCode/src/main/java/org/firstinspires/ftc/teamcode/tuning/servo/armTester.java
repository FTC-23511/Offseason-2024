package org.firstinspires.ftc.teamcode.tuning.servo;

import static org.firstinspires.ftc.teamcode.subsystem.System.checkButton;
import static org.firstinspires.ftc.teamcode.subsystem.System.round;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystem.Deposit;

@Config
@TeleOp
public class armTester extends LinearOpMode {
    public static double leftArmPos = 0.5;
    public static double rightArmPos = 0.5;
    public static boolean USE_DASHBOARD = false;

    @Override
    public void runOpMode() throws InterruptedException {
        Deposit deposit = new Deposit(hardwareMap);

        Gamepad currentGamepad1 = new Gamepad();

        deposit.leftArm.setPosition(leftArmPos);
        deposit.rightArm.setPosition(rightArmPos);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (USE_DASHBOARD){
                deposit.leftArm.setPosition(leftArmPos);
                deposit.rightArm.setPosition(rightArmPos);
            } else if (gamepad1.dpad_up  && checkButton(currentGamepad1, "dpad_up")) {
                leftArmPos += 0.01;
            } else if (gamepad1.dpad_down && checkButton(currentGamepad1, "dpad_down")) {
                leftArmPos -= 0.01;
            } else if (gamepad1.dpad_right  && checkButton(currentGamepad1, "dpad_right")) {
                rightArmPos += 0.01;
            } else if (gamepad1.dpad_left && checkButton(currentGamepad1, "dpad_left")) {
                rightArmPos -= 0.01;
            }

            if (gamepad1.square || gamepad1.cross) {
                deposit.leftArm.setPosition(leftArmPos);
            } else if (gamepad1.triangle || gamepad1.circle) {
                deposit.rightArm.setPosition(rightArmPos);
            }

            leftArmPos = Math.max(Math.min(leftArmPos, 1), 0);

            rightArmPos = Math.max(Math.min(rightArmPos, 1), 0);

            currentGamepad1.copy(gamepad1);

            telemetry.addData("leftArm getPosition", round(deposit.leftArm.getPosition(), 2));
            telemetry.addData("rightArm getPosition", round(deposit.rightArm.getPosition(), 2));
            telemetry.addData("leftArmPos", round(leftArmPos, 2));
            telemetry.addData("rightArmPos", round(rightArmPos, 2));
            telemetry.update();
        }
    }
}