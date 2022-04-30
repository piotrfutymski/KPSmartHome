package kp.home_control.device;

public interface DeviceVisitor {
    void visit(StandardBulb standardBulb);

    void visit(Speakers speakers);
}
