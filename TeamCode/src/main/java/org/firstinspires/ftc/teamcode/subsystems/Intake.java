package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

public class Intake extends Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    private String motor_name = "intake_motor";
    public DcMotor intake_motor;

    private double INTAKE_IN_POWER = 1.0;
    private double INTAKE_OUT_POWER = -0.5;
    private double INTAKE_OFF_POWER = 0.0;




    public void intakein(){
        intake_motor.setPower(INTAKE_IN_POWER);
    }
    public void intakeout(){
        intake_motor.setPower(INTAKE_OUT_POWER);
    }
    public void intakeoff(){
        intake_motor.setPower(INTAKE_OFF_POWER);
    }



    public Command intake_in(){
        return new InstantCommand(this::intakein);
    }
    public Command intake_out(){
        return new InstantCommand(this::intakeout);
    }
    public Command intake_off(){
        return new InstantCommand(this::intakeoff);
    }
    


    @Override
    public void initialize() {
    intake_motor = OpModeData.INSTANCE.getHardwareMap().get(DcMotor.class, motor_name);
    }



}
