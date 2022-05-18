package kp.home_control.device;

import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;

public interface DeviceVisitor {
    void visit(StandardBulb standardBulb);

    void visit(Speakers speakers);
}
