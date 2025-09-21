package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/*
 * Sine wave sample to demonstrate telemetry and config variables in action. Adjust the amplitude,
 * phase, and frequency of the oscillation and watch the changes propagate immediately to the graph.
 */
@Config
@Autonomous
public class PIDGraph extends LinearOpMode {
    public static double TARGET_RPM = 0.;
    public static int TICKS_PER_REV = 28;
    public static int MAX_RPM = 5000;
    public static int RPM_INC = 100;
    public static double VEL_SCALE = 1.;
    public static double PID_P = 12.0;
    public static double PID_I = 5.0;
    public static double PID_D = 0.0;
    public static double PID_F = 0.0;

    DcMotorEx left_motor;
    DcMotorEx right_motor;
    public String left_name = "shooter_left";
    public String right_name = "shooter_right";

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        left_motor = (DcMotorEx)hardwareMap.get(DcMotor.class, left_name);
        right_motor = (DcMotorEx)hardwareMap.get(DcMotor.class, right_name);
        left_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        right_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        while (opModeIsActive()) {
            PIDFCoefficients pidfNew = new PIDFCoefficients(PID_P,PID_I,PID_D,PID_F);
            left_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfNew);
            right_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfNew);

            //TARGET_RPM = Math.min(MAX_RPM,TARGET_RPM);
            double target_vel = TARGET_RPM * TICKS_PER_REV * VEL_SCALE / 60;
            left_motor.setVelocity(target_vel);
            right_motor.setVelocity(target_vel);
            double left_rpm = left_motor.getVelocity() * 60. / TICKS_PER_REV;
            double right_rpm = right_motor.getVelocity() * 60. / TICKS_PER_REV;

            telemetry.addData("TARGET", TARGET_RPM);
            telemetry.addData("LEFT", left_rpm);
            telemetry.addData("RIGHT", right_rpm);
            telemetry.update();

            sleep(20);
        }
    }
}