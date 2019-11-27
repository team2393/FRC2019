package lambdas;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

/** Lambda Intro: How it's used in the 2020 WPILib */
public class LambdaDemo3
{
    // A PID controller needs the current value
    // of what it wants to control, like the "position"
    // or "height".
    //
    // The new WPIlib contains an updated PIDController class
    // that uses lambdas, i.e. a @FunctionalInterface for that.
    //
    // Basics of new PID controller (doing only P control)
    static class PIDController
    {
        private double setpoint = 0;
        private double P;
        // DoubleSupplier is a standard Java @FunctionalInterface
        private DoubleSupplier current_value_supplier;

        public PIDController(double P, DoubleSupplier current_value_supplier)
        {
            this.P = P;
            this.current_value_supplier = current_value_supplier;
        }

        public void setSetpoint(double new_setpoint)
        {
            setpoint = new_setpoint;
        }

        double calculate()
        {
            double current_value = current_value_supplier.getAsDouble();
            double error = setpoint - current_value;
            return P*error;
        }
    }



    // Basic idea for connecting that PIDController to a lift:
    private Victor lift_motor = new Victor(1);
    private Encoder lift_position = new Encoder(2, 3);
    private PIDController lift_pid = new PIDController(0.5, () -> lift_position.getDistance());
    // Or: ...                       new PIDController(0.5, lift_position::getDistance);

    void autonomousInit()
    {
        lift_pid.setSetpoint(30.0);
    }

    void autonomousPeriodic()
    {
        // PID has setpoint, P, and a lambda for getting the current position.
        // We can simply ask it to calculate the motor speed:
        lift_motor.setSpeed(lift_pid.calculate());
    }
}
