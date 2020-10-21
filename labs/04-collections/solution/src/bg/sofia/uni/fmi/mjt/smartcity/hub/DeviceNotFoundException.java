package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class DeviceNotFoundException extends Exception {
    DeviceNotFoundException(){
        System.out.println("No device with this ID is registered.");
    }
}
