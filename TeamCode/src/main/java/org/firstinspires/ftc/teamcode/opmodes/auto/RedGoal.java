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

    private boolean iAmBlue() { return false;}

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
        Catapult.INSTANCE.hold();
        AutoDrive.INSTANCE.driveStraight(0.2, 0.8, -12.);
        AutoDrive.INSTANCE.holdHeading(0.5, 0., 1.);
        AutoDrive.INSTANCE.turnAndHoldHeading(0.5, -60., 1.);
        AutoDrive.INSTANCE.strafeStraight(0.2, 0.8, -24.);
    }

}
