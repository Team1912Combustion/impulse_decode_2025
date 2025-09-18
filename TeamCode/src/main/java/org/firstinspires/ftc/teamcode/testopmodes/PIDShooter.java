
package org.firstinspires.ftc.teamcode.testopmodes;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="BasicShooterTuner")
public class PIDShooter extends LinearOpMode {
    @IgnoreConfigurable
    public static int TICKS_PER_REV = 28;

    public static int MAX_RPM = 5000;
    public static int TARGET_RPM = 0;
    public static int RPM_INC = 100;
    public static double VEL_SCALE = 1.;
    public static double PID_P = 2.50;
    public static double PID_I = 0.10;
    public static double PID_D = 0.20;
    public static double PID_F = 0.50;

    public String left_name = "shooter_left";
    public String right_name = "shooter_right";

    DcMotorEx left_motor;
    DcMotorEx right_motor;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "initialize...");
        telemetry.update();

        left_motor = (DcMotorEx)hardwareMap.get(DcMotor.class, left_name);
        right_motor = (DcMotorEx)hardwareMap.get(DcMotor.class, right_name);
        left_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        right_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        TARGET_RPM = 0;
        RPM_INC = 100;

        PIDFCoefficients pidfOrig = left_motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("P,I,D,F (orig)", "%.04f, %.04f, %.04f, %.04f",
                pidfOrig.p, pidfOrig.i, pidfOrig.d, pidfOrig.f);

        telemetry.addData("Status", "...initialized");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            // Change coefficients using methods included with DcMotorEx class.
            PIDFCoefficients pidfNew = new PIDFCoefficients(PID_P,PID_I,PID_D,PID_F);
            left_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfNew);
            right_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfNew);

            // Re-read coefficients and verify change.
            PIDFCoefficients pidfMod = left_motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);

            if (gamepad1.right_bumper) {
                TARGET_RPM += RPM_INC;
                if (TARGET_RPM >= MAX_RPM ) {
                    TARGET_RPM = MAX_RPM;
                }
            }
            if (gamepad1.left_bumper) {
                TARGET_RPM -= RPM_INC;
                if (TARGET_RPM < 0 ) {
                    TARGET_RPM = 0;
                }
            }

            double target_vel = TARGET_RPM / 60. * TICKS_PER_REV * VEL_SCALE;
            left_motor.setVelocity(target_vel);
            double left_vel = left_motor.getVelocity();
            double right_vel = right_motor.getVelocity();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("P,I,D,F (orig)", "%.04f, %.04f, %.04f, %.04f",
                    pidfOrig.p, pidfOrig.i, pidfOrig.d, pidfOrig.f);
            telemetry.addData("P,I,D,F (modified)", "%.04f, %.04f, %.04f, %.04f",
                    pidfMod.p, pidfMod.i, pidfMod.d, pidfMod.f);
            telemetry.addData("Target", "ticks_per_sec (%.2f)", target_vel);
            telemetry.addData("Measured", "left (%.2f), right (%.2f)", left_vel, right_vel);
            telemetry.update();
        }
    }
}
