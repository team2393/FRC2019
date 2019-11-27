package lambdas;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

/** Lambda Intro: How it's used in the 2020 WPILib */
public class LambdaDemo3
{
    // A PID controller needs the current value
    // of what it wants to control, like the "position"
    // or "height".
    // The result then needs to be applied to for
    // example a motor controller.
    //
    // The new WPIlib contains an updated PIDController & PIDCommand class
    // that uses lambdas, i.e. a @FunctionalInterface for that.
    //
    // Basics of new PID (doing only P control)
    static class PIDCommand
    {
        private double setpoint = 0;
        private double P;
        // DoubleSupplier & Consumer are a standard Java @FunctionalInterfaces
        private DoubleSupplier current_value_supplier;
        private DoubleConsumer correction_user;

        public PIDCommand(double P,
                          DoubleSupplier current_value_supplier,
                          DoubleConsumer correction_user)
        {
            this.P = P;
            this.current_value_supplier = current_value_supplier;
            this.correction_user = correction_user;
        }

        public void setSetpoint(double new_setpoint)
        {
            setpoint = new_setpoint;
        }

        public void execute()
        {
            // Get the current value
            double current_value = current_value_supplier.getAsDouble();
            // P(ID) algorithm
            double error = setpoint - current_value;
            double correction = P*error;
            // Apply the correction
            correction_user.accept(correction);
        }
    }

    // -------------------------------------------------------------

    // Basic idea for connecting that PIDCommand to a lift:
    private Victor lift_motor = new Victor(1);
    private Encoder lift_position = new Encoder(2, 3);
    private PIDCommand lift_pid = new PIDCommand(0.5,
                                                 () -> lift_position.getDistance(),
                                                 result -> lift_motor.setSpeed(result));
    // Or: ... new PIDCommand(0.5, lift_position::getDistance, lift_motor::setSpeed);

    void autonomousInit()
    {
        lift_pid.setSetpoint(30.0);
    }

    void autonomousPeriodic()
    {
        // PID has setpoint, P, and lambdas for getting the current position
        // and setting the motor.
        // We can simply ask it to perform the control:
        lift_pid.execute();
    }
}
