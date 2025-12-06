package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.ActiveOpMode;
import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

public class RedGoal{

    private static boolean iAmBlue() { return false;}

    double driveSpeed = 0.25;
    double slowSpeed = 0.15;
    double minDriveSpeed = 0.05;
    double turnSpeed = 0.20;
    double holdTime = 0.5;

    public static void runTest() {
        ElapsedTime m_timer = new ElapsedTime();
        m_timer.reset();
        Catapult.INSTANCE.load();
        m_timer.reset();
        while (m_timer.milliseconds() < 200.) { }
        Catapult.INSTANCE.launch();
        m_timer.reset();
        while (m_timer.milliseconds() < 200.) { }
        Catapult.INSTANCE.load();
        m_timer.reset();
        while (m_timer.milliseconds() < 1000.) { }
        Catapult.INSTANCE.hold();
        m_timer.reset();
        while (m_timer.milliseconds() < 1000.) { }
        AutoDrive.INSTANCE.sendTelemetry(false);
        m_timer.reset();
        while (m_timer.milliseconds() < 1000.) { }
        AutoDrive.INSTANCE.driveStraight(0.2, 0.6, -52.5, 4.5);
        AutoDrive.INSTANCE.holdHeading(0.8, 0., 0.5);
        if (iAmBlue()) {
            AutoDrive.INSTANCE.turnAndHoldHeading(0.8, 43.5, 0.5);
        } else {
            AutoDrive.INSTANCE.turnAndHoldHeading(0.8, -43.5, 0.5);
        }
        Intake.INSTANCE.intakein();
        AutoDrive.INSTANCE.driveStraight(0.2, 0.27, 46.,6);
        m_timer.reset();
        while (m_timer.milliseconds() < 200.) { }
        Intake.INSTANCE.intakeoff();
        if (iAmBlue()) {
            AutoDrive.INSTANCE.turnAndHoldHeading(0.8, 5, 0.5);
            AutoDrive.INSTANCE.strafeStraight(0.2, 0.6, -29, 3);
        } else {
            AutoDrive.INSTANCE.turnAndHoldHeading(0.8, -5, 0.5);
            AutoDrive.INSTANCE.strafeStraight(0.2, 0.6, 29, 3);
        }
        AutoDrive.INSTANCE.driveStraight(0.2, 0.6, 25,2.5);
        Catapult.INSTANCE.load();
        m_timer.reset();
        while (m_timer.milliseconds() < 200.) { }
        Catapult.INSTANCE.launch();
        m_timer.reset();
        while (m_timer.milliseconds() < 200.) { }
        Catapult.INSTANCE.hold();


    }

}
