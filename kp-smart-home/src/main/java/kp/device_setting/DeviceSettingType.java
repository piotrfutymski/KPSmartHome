package kp.device_setting;

import lombok.*;

import java.util.function.Function;

public enum DeviceSettingType {
    BRIGHTNESS(Integer::parseInt),
    COLOR_RED(Integer::parseInt),
    COLOR_GREEN(Integer::parseInt),
    COLOR_BLUE(Integer::parseInt),
    VOLUME(Integer::parseInt),
    SPOTIFY_PLAYLIST(e->e),
    SPOTIFY_SONG(e->e),
    NEXT_BRIGHTNESS(ChangingIntSettingValue::new),
    NEXT_COLOR_RED(ChangingIntSettingValue::new),
    NEXT_COLOR_GREEN(ChangingIntSettingValue::new),
    NEXT_COLOR_BLUE(ChangingIntSettingValue::new),
    NEXT_VOLUME(ChangingIntSettingValue::new);

    public static final Integer TICK_DURATION = 100;


    private Function<String, Object> serializer;

    private Function<Object, String> deserializer;

    DeviceSettingType(Function<String, Object> serializer, Function<Object, String> deserializer){
        this.serializer = serializer;
        this.deserializer = deserializer;
    };

    DeviceSettingType(Function<String, Object> serializer){
        this.serializer = serializer;
        this.deserializer = Object::toString;
    };

    public <T> T mapValue(String value){
        return (T) serializer.apply(value);
    };

    public String saveValue(Object value){
        return deserializer.apply(value);
    };

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangingIntSettingValue{
        private Integer nextVal;
        private Integer ticks;

        public ChangingIntSettingValue(String value){
            String[] tab = value.split(",");
            nextVal = Integer.parseInt(tab[0]);
            ticks = Integer.parseInt(tab[1]);
        }

        @Override
        public String toString() {
            return nextVal.toString() + "," + ticks.toString();
        }
    }
}
