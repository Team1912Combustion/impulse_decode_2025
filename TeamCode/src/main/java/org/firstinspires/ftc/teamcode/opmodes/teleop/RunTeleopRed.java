package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "TeleopRed")
public class RunTeleopRed extends NextFTCOpMode {

    public RunTeleopRed() {
        addComponents(
            new SubsystemComponent(
                    Intake.INSTANCE,
                    Catapult.INSTANCE,
                    Lift.INSTANCE,
                    Drive.INSTANCE,
                    Vision.INSTANCE)
            );
    }

    public boolean amIBlue() {
        return false;
    }

    @Override public void onInit() {
        Vision.INSTANCE.setAlliance(amIBlue());
    }
    @Override public void onWaitForStart() { }

    @Override public void onStartButtonPressed() {
        // schedule Drive once
        Drive.INSTANCE.driverControlled.schedule();

        // do the button bindings once on Start, not in the update() loop
        Gamepads.gamepad2().leftBumper()
                .whenBecomesTrue(Intake.INSTANCE.intake_in())
                .whenBecomesFalse(Intake.INSTANCE.intake_off());
        Gamepads.gamepad2().leftTrigger().greaterThan(0.2)
                .whenBecomesTrue(Intake.INSTANCE.intake_out());

        Gamepads.gamepad2().rightBumper()
                .whenBecomesTrue(Catapult.INSTANCE.launch());
        Gamepads.gamepad2().rightTrigger().greaterThan(0.2)
                .whenBecomesTrue(Catapult.INSTANCE.load());

        Gamepads.gamepad2().dpadUp().whenBecomesTrue(Lift.INSTANCE.push());
        Gamepads.gamepad2().dpadDown().whenBecomesTrue(Lift.INSTANCE.pull());
    }

    @Override public void onStop() {
        Drive.INSTANCE.stop().schedule();
        Intake.INSTANCE.intake_off().schedule();
        Catapult.INSTANCE.hold().schedule();
        Lift.INSTANCE.hold().schedule();
    }

    @Override public void onUpdate() {
    }
}
