package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Lift;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(group = "Test", name = "Lift")
public class TestLift extends NextFTCOpMode {

    public TestLift() {
        addComponents(
                new SubsystemComponent(
                        Lift.INSTANCE)
        );
    }

    @Override public void onWaitForStart() { }

    @Override
    public void onInit() {
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
    }

    @Override public void onUpdate() {

        boolean liftOutButton = gamepad1.a;
        boolean liftUpButton = gamepad1.b;
        if (liftOutButton && liftUpButton) {
            liftOutButton = false;
        }

        // LIFT CODE
        if (liftOutButton) {
            Lift.INSTANCE.push().schedule();
            telemetry.addLine("Lift: Push");
        } else if (liftUpButton) {
            Lift.INSTANCE.pull().schedule();
            telemetry.addLine("Lift: Pull");
        } else {
            Lift.INSTANCE.hold().schedule();
            telemetry.addLine("Lift: Hold");
        }
        telemetry.addData("Lift: position:", org.firstinspires.ftc.teamcode.subsystems.Lift.INSTANCE.getPosition());
        telemetry.update();

    }
}
