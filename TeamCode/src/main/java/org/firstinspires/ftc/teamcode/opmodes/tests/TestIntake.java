package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "TestIntake")
public class TestIntake extends OpMode {

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        Intake.INSTANCE.init(hardwareMap);
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

        boolean intakeInButton = gamepad1.left_trigger > 0.2;
        boolean intakeOutButton = gamepad1.left_bumper;
        if (intakeOutButton && intakeInButton) {
            intakeInButton = false;
            intakeOutButton = false;
        }

        // INTAKE CODE
        if (intakeInButton) {
            org.firstinspires.ftc.teamcode.subsystems.Intake.INSTANCE.intakein();
            telemetry.addLine("Intake: In");
        } else if (intakeOutButton) {
            org.firstinspires.ftc.teamcode.subsystems.Intake.INSTANCE.intakeout();
            telemetry.addLine("Intake: Out");
        } else {
            org.firstinspires.ftc.teamcode.subsystems.Intake.INSTANCE.intakeoff();
            telemetry.addLine("Intake: Off");
        }

        telemetry.update();
    }
}
