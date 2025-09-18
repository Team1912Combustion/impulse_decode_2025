
package org.firstinspires.ftc.teamcode.testopmodes;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="BangBangShooter")
public class BangBangShooter extends LinearOpMode {
    @IgnoreConfigurable
    public static int TICKS_PER_REV = 28;

    public static double FLYWHEEL_FULL_POWER = 1.;
    public static int MAX_RPM = 5000;
    public static int TARGET_RPM = 0;
    public static int RPM_INC = 100;
    public static double VEL_SCALE = 1.;

    public String left_name = "shooter_left";
    public String right_name = "shooter_right";

    DcMotorEx left_motor;
    DcMotorEx right_motor;

    boolean a_button_previously_pressed = false;
    boolean flywheelOn = false;

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

        telemetry.addData("Status", "...initialized");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

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

            if (gamepad1.a && !a_button_previously_pressed) {
                flywheelOn = !flywheelOn;
            }
            a_button_previously_pressed = gamepad1.a;

            if (flywheelOn) {
                // Get the current velocity from the encoded motor
                double currentVelocity = left_motor.getVelocity();

                // Bang-Bang Control Logic
                if (currentVelocity < target_vel) {
                    // If speed is too low, turn motors to full power
                    left_motor.setPower(FLYWHEEL_FULL_POWER);
                    right_motor.setPower(FLYWHEEL_FULL_POWER);
                } else {
                    // If speed is at or above target, turn motors off (coast)
                    left_motor.setPower(0);
                    right_motor.setPower(0);
                }
            } else {
                left_motor.setPower(0);
                right_motor.setPower(0);
            }

            double left_vel = left_motor.getVelocity();
            double right_vel = right_motor.getVelocity();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Flywheel", "on/off: (%b)", flywheelOn);
            telemetry.addData("Target", "ticks_per_sec (%.2f)", target_vel);
            telemetry.addData("Measured", "left (%.2f), right (%.2f)", left_vel, right_vel);
            telemetry.update();
        }
    }
}
