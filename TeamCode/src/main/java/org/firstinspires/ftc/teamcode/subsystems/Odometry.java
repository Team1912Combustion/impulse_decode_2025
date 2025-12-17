
package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.geometry.Pose2d;
import com.seattlesolvers.solverslib.geometry.Rotation2d;

public class Odometry implements Subsystem {

    private PinPoint pinpoint;
    private Telemetry telemetry;

    private double robot_x;
    private double robot_y;
    private double robot_heading = 0;
    private boolean iAmBlue = true;
    private boolean iAmAtGoal = true;

    public Odometry(final HardwareMap hardwareMap, final Telemetry m_telemetry) {
        pinpoint = new PinPoint(hardwareMap);
        telemetry = m_telemetry;
    }

    public double getX() {
        return robot_x;
    }
    public double getY() {
        return robot_y;
    }

    public double getHeading() {
        return robot_heading;
    }

    public double getRot() {
        return fixRot(robot_heading * Math.PI / 180.);
    }

    public Pose2d getPose2d() {
        return new Pose2d(robot_x, robot_y, new Rotation2d(getRot()));
    }

    public void set(double x, double y, double heading) {
        robot_x = x;
        robot_y = y;
        robot_heading = heading;
        pinpoint.setPose(robot_x, robot_y, robot_heading);
    }

    public void set(Pose2d pose) {
        robot_x = pose.getX();
        robot_y = pose.getY();
        robot_heading = pose.getHeading() * 180. / Math.PI;
        pinpoint.setPose(robot_x, robot_y, robot_heading);
    }

    public void set(Pose2D pose) {
        pinpoint.setPose2D(pose);
        robot_x = pose.getX(DistanceUnit.INCH);
        robot_y = pose.getY(DistanceUnit.INCH);
        robot_heading = pose.getHeading(AngleUnit.DEGREES);
        pinpoint.setPose(robot_x, robot_y, robot_heading);
    }

    public void setHeading(double heading) {
        robot_heading = fixHeading(heading);
    }

    public void add(double x, double y) {
        robot_x += x;
        robot_y += y;
    }

    public void send() {
        telemetry.addData("Odo pose x:y:r",
                "%f:%f:%f", robot_x, robot_y, robot_heading);
    }

    public void update() {
        Pose2d pose = pinpoint.getPose2d();
        robot_x = pose.getX();
        robot_y = pose.getY();
        robot_heading = radToDeg(fixRot(pose.getHeading()));
    }

    private double degToRad(double angle) {
        return angle * Math.PI / 180.;
    }
    private double radToDeg(double angle) {
        return angle / Math.PI * 180.;
    }

    private double fixHeading(double heading) {
        double fixedHeading = heading;
        while (fixedHeading <= -180.) {
            fixedHeading += 360.;
        }
        while (fixedHeading > 180.) {
            fixedHeading += 360.;
        }
        return fixedHeading;
    }

    private double fixRot(double rot) {
        double fixedRot = rot;
        while (fixedRot <= -Math.PI) {
            fixedRot += 2. * Math.PI;
        }
        while (fixedRot > Math.PI) {
            fixedRot += 2. * Math.PI;
        }
        return fixedRot;
    }

    public void init(boolean m_iAmBlue, boolean m_iAmAtGoal) {
        iAmBlue = m_iAmBlue;
        iAmAtGoal = m_iAmAtGoal;
        if (iAmAtGoal) {
            if (iAmBlue) {
                robot_y = 0;
                robot_x = 0;
                robot_heading = 0;
            } else {
                robot_y = 0;
                robot_x = 0;
                robot_heading = 0;
            }
        } else {
            if (iAmBlue) {
                robot_y = 0;
                robot_x = 0;
                robot_heading = 0;
            } else {
                robot_y = 0;
                robot_x = 0;
                robot_heading = 0;
            }
        }
        set(robot_x, robot_y, robot_heading);
    }

    public Command run_update() {
        return new InstantCommand(this::update, this)
                .andThen(new InstantCommand(this::send, this));
    }

    @Override
    public void periodic() {
        update();
    }

}
