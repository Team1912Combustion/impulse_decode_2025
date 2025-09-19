package org.firstinspires.ftc.teamcode.testopmodes;

import com.bylazar.graph.GraphManager;
import com.bylazar.graph.PanelsGraph;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "ShooterTunerGraph")
public class ShooterTunerGraph extends NextFTCOpMode {


    private final GraphManager graphManager = PanelsGraph.INSTANCE.getManager();
    private final TelemetryManager panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    public ShooterTunerGraph() {
        super(Shooter.INSTANCE);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onStartButtonPressed() {
        Shooter.INSTANCE.updateConstants().invoke();
    }

    @Override
    public void onUpdate() {
        if (gamepad1.right_bumper) {
            Shooter.TARGET_RPM += Shooter.RPM_INC;
            if (Shooter.TARGET_RPM >= Shooter.MAX_RPM ) {
                Shooter.TARGET_RPM = Shooter.MAX_RPM;
            }
        }
        if (gamepad1.left_bumper) {
            Shooter.TARGET_RPM -= Shooter.RPM_INC;
            if (Shooter.TARGET_RPM < 0 ) {
                Shooter.TARGET_RPM = 0;
            }
        }
        Shooter.INSTANCE.updateConstants().invoke();
        graphManager.addData("RPM",Shooter.INSTANCE.getRPM());
        graphManager.addData("leftRPM",Shooter.INSTANCE.getLeftRPM());
        graphManager.addData("rightRPM",Shooter.INSTANCE.getRightRPM());
        graphManager.update();
        panelsTelemetry.addData("RPM",Shooter.INSTANCE.getRPM());
        panelsTelemetry.addData("leftRPM",Shooter.INSTANCE.getLeftRPM());
        panelsTelemetry.addData("rightRPM",Shooter.INSTANCE.getRightRPM());
        panelsTelemetry.update();
    }

    @Override
    public void onStop() {
        Shooter.INSTANCE.stop().invoke();
    }
}
