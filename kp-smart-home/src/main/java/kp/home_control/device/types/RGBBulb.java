package kp.home_control.device.types;

import kp.device_setting.DeviceSettingType;
import kp.home_control.device.AbstractDevice;
import kp.home_control.device.Device;
import kp.home_control.device.DeviceVisitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class RGBBulb extends AbstractDevice {

    TelnetClient telnetClient;
    public RGBBulb(String name){
        super(name);
    }
    @Override
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }

    @Override
    public Boolean isOnline() {
        return true;
    }

    @Override
    public List<DeviceSettingType> getCapabilities() {
        return List.of(
                DeviceSettingType.COLOR,
                DeviceSettingType.NEXT_COLOR,
                DeviceSettingType.BRIGHTNESS,
                DeviceSettingType.NEXT_BRIGHTNESS
        );
    }

    public void setBrightness(Integer brightness){
        setColor(brightness * (65536 + 256 + 1));
    }

    public void setColor(Integer color) {
        setColor(color, 50);
    }
    public void setColor(Integer color, Integer ticks) {
        log.info("On device {}, setting color: ({})", getName(), color);
        PrintStream outputStream = new PrintStream(telnetClient.getOutputStream(), true);
        String msg = "{\"id\":1,\"method\":\"set_rgb\",\"params\":[" + color +", \"smooth\", " + ticks + "]}\r\n";
        outputStream.println(msg);
    }

    public Color getColor(){
        OutputStream outputStream = telnetClient.getOutputStream();
        InputStream inputStream = telnetClient.getInputStream();

        /*String RGB = String.valueOf(color.getRed()*65536 + color.getGreen()*256 + color.getBlue());
        String msg = "{\"id\":1,\"method\":\"set_rgb\",\"params\":[" + RGB +", \"smooth\", 100]}\r\n";
        try {
            outputStream.write(msg.getBytes(StandardCharsets.US_ASCII));
        } catch (IOException e) {
            log.warn("On device {}, can't set color: R({}) G({}) B({})", getName(), color.getRed(), color.getGreen(), color.getBlue());
        }*/
        return new Color(0,0,0);
    }

    public void setTelnetClient(TelnetClient telnetClient) {
        this.telnetClient = telnetClient;
    }
}
