package kp.home_control.device;

import lombok.Getter;

@Getter
public abstract class AbstractDevice implements Device{

    protected String name;

    public AbstractDevice(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
