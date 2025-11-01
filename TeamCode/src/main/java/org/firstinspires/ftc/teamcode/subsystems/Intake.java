package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

public class Intake extends Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    public DcMotor intake_motor;

    private double INTAKE_IN_POWER = 1.0;
    private double INTAKE_OUT_POWER = -0.5;
    private double INTAKE_OFF_POWER = 0.0;
    private double intakePower = INTAKE_OFF_POWER;

    public void Initialize() {
    intake_motor = OpModeData.INSTANCE.getHardwareMap().get(DcMotor.class, "intake_motor");
    }



}
