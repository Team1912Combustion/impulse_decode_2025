package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.ActiveOpMode;
import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Intake;


import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;


public class AtWall {

    double driveSpeed = 0.25;
    double slowSpeed = 0.15;
    double minDriveSpeed = 0.05;
    double turnSpeed = 0.20;
    double holdTime = 0.5;

    public static void runTest() {

        ElapsedTime m_timer = new ElapsedTime();
        m_timer.reset();
        while (ActiveOpMode.INSTANCE.isActive() && m_timer.seconds() < 25.) { }
        AutoDrive.INSTANCE.driveStraight(0.2, 0.6, 15, 2);
    }

}


