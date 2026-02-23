package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Lift;

@TeleOp(name = "TestLift")
@Disabled
public class TestLift extends OpMode {

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        Lift.INSTANCE.init(hardwareMap);
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        boolean liftOutButton = gamepad1.a;
        boolean liftUpButton = gamepad1.b;
        if (liftOutButton && liftUpButton) {
            liftOutButton = false;
        }

        // LIFT CODE
        if (liftOutButton) {
            org.firstinspires.ftc.teamcode.subsystems.Lift.INSTANCE.tip();
            telemetry.addLine("Lift: Tip");
        } else if (liftUpButton) {
            org.firstinspires.ftc.teamcode.subsystems.Lift.INSTANCE.stow();
            telemetry.addLine("Lift: Stow");
        } else {
            org.firstinspires.ftc.teamcode.subsystems.Lift.INSTANCE.hold();
            telemetry.addLine("Lift: Off");
        }
        telemetry.addData("Lift: position:", org.firstinspires.ftc.teamcode.subsystems.Lift.INSTANCE.getPosition());

        telemetry.update();
    }
}
