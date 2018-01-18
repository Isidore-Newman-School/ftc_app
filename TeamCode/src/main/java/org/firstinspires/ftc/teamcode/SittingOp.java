/*
 * ███████╗██╗████████╗████████╗██╗███╗   ██╗ ██████╗      ██████╗ ██████╗
 * ██╔════╝██║╚══██╔══╝╚══██╔══╝██║████╗  ██║██╔════╝     ██╔═══██╗██╔══██╗
 * ███████╗██║   ██║      ██║   ██║██╔██╗ ██║██║  ███╗    ██║   ██║██████╔╝ (mode)
 * ╚════██║██║   ██║      ██║   ██║██║╚██╗██║██║   ██║    ██║   ██║██╔═══╝
 * ███████║██║   ██║      ██║   ██║██║ ╚████║╚██████╔╝    ╚██████╔╝██║
 * ╚══════╝╚═╝   ╚═╝      ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝      ╚═════╝ ╚═╝
 * Based off of telop tank Iterative.
 */

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="SitOp", group="Pushbot") //this sets the label for the controller
public class SittingOp extends OpMode {
    private double curve(byte curve_factor,double value) {
        double actual_factor = curve_factor / 32.0f;
        double exponent = Math.pow(2,actual_factor);
        // this makes postive and negative curve_factor's equal in shape.
        // e.g a factor of +2 would be computed as x^2
        //         whereas -2 would be computed as sqrt(x) (x^(1/2)).
        if(value < 0) {
            return -Math.pow(-value,exponent);
        } else {
            return Math.pow(value,exponent);
        }
    }

    private double deadzoneify(double threshold, double value) {
        if(Math.abs(value) < threshold) {
            return 0
        } else {
            return value
        }
    }

    HardwarePushbot robot       = new BullBot();
    double          clawOffset  = 0.0 ;
    final double    CLAW_SPEED  = 0.02;

    final double    motorRPM;
    final double    wheelDiameter; //diameter of DRIVING wheels in milimetres
    final double    wheelGap;      //the shortest distance from the center of one drive wheel to the other, in MM.

    final double    wheelCircumfrence = Math.pow(wheelDiameter/2,2) * Math.PI;//milli-meters
    final double    wheelSpeed        = motorRPM * wheelCircumfrence * 60;    //mili-meters per second

    private enum MotorMode {Normal,VStrafe_Start,VStrafe_End};
    private MotorMode motorMode = Normal;

    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Say", "You have pressed the init button.");
        //autonomous mode either goes here or in a different OpMode
    }

    @Override
    public void init_loop() {
        //not needed atm.
    }

    @Override
    public void start() {
        //Runs at the start of the play button
    }

    @Override
    public void loop() {//Runs repeatedly after '►' button pressed.
        double left_power;
        double right_power;

        //option 1: car controlls
            double throttle; double turn_val;
            throttle = deadzoneify(0.1,gamepad1.right_trigger - gamepad1.left_trigger);
            throttle = curve(37, throttle);//ease in
            turn_val    = gamepad1.left_stick_x;
            left_power  = clampDCMotor(throttle + turn_val);
            right_power = clampDCMotor(throttle - turn_val);

        //option 2: tank controlls
            // left_power  = -curve(37,gamepad1.left_stick_y);
            // right_power = -curve(37,gamepad1.right_stick_y);

        robot.leftDrive .setPower(left_power);
        robot.rightDrive.setPower(right_power);

        //controller 2 controls the claws
        clawOffset += CLAW_SPEED * //tbd


        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
        robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

        // Use gamepad buttons to move the arm up (Y) and down (A)
        if (gamepad1.y)
            robot.leftArm.setPower(robot.ARM_UP_POWER);
        else if (gamepad1.a)
            robot.leftArm.setPower(robot.ARM_DOWN_POWER);
        else
            robot.leftArm.setPower(0.0);

        // Send telemetry message to signify robot running;
        telemetry.addData("claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("left",  "%.2f", left);
        telemetry.addData("right", "%.2f", right);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
