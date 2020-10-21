package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class DeviceAlreadyRegisteredException extends Exception{

    DeviceAlreadyRegisteredException(){
        System.out.println("Device with this ID is already registered.");
    }

}
