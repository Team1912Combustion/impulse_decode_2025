package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.nextftc.java.Claw;
import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;

public class Shooting {

    Timer m_timer;

    public Command ShootCombo(Vision vision, Drive drive, Catapult catapult) {
        return new SequentialGroup(
                new InstantCommand(()-> vision.myWaitForTarget()).endAfter(3),
                new InstantCommand(()-> drive.squareToTarget()).endAfter(3.),
                new InstantCommand(()-> catapult.launch()).endAfter(3.),
                new InstantCommand(()-> catapult.load()).endAfter(3.)
        );
    }

}
