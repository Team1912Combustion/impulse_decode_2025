package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Catapult;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(group = "Test", name = "Catapult")
public class TestCatapult extends NextFTCOpMode {

    public TestCatapult() {
        addComponents(
                new SubsystemComponent(
                        Catapult.INSTANCE)
        );
    }

    @Override public void onWaitForStart() { }

    @Override
    public void onInit() {
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
    }

    @Override public void onUpdate() {

        boolean catapultLaunchButton = gamepad1.right_bumper;
        boolean catapultLoadButton = gamepad1.right_trigger > 0.2;
        if (catapultLaunchButton && catapultLoadButton) {
            catapultLaunchButton = false;
        }

        if (catapultLaunchButton) {
            Catapult.INSTANCE.launch().schedule();
            telemetry.addLine("Catapult: Launch");
        } else if (catapultLoadButton) {
            Catapult.INSTANCE.load().schedule();
            telemetry.addLine("Catapult: Load");
        } else {
            Catapult.INSTANCE.hold().schedule();
            telemetry.addLine("Catapult: Hold");
        }
        telemetry.addData("Catapult: position:", Catapult.INSTANCE.getPosition());

        telemetry.update();

    }
}
