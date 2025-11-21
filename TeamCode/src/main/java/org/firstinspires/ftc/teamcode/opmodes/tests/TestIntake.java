package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(group = "Test", name = "Intake")
public class TestIntake extends NextFTCOpMode {

    public TestIntake() {
        addComponents(
                new SubsystemComponent(
                        Intake.INSTANCE)
        );
    }

    @Override public void onWaitForStart() { }

    @Override
    public void onInit() {
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
    }

    @Override public void onUpdate() {

        boolean intakeInButton = gamepad1.left_trigger > 0.2;
        boolean intakeOutButton = gamepad1.left_bumper;
        if (intakeOutButton && intakeInButton) {
            intakeInButton = false;
            intakeOutButton = false;
        }

        // INTAKE CODE
        if (intakeInButton) {
            Intake.INSTANCE.intake_in().schedule();
            telemetry.addLine("Intake: In");
        } else if (intakeOutButton) {
            Intake.INSTANCE.intake_out().schedule();
            telemetry.addLine("Intake: Out");
        } else {
            Intake.INSTANCE.intake_off().schedule();
            telemetry.addLine("Intake: Off");
        }

        telemetry.update();

    }
}
