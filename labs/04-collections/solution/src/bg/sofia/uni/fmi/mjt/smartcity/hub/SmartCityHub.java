package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartCamera;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartLamp;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.util.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class SmartCityHub {
    private Map<DeviceType, Integer> types;
    private Map<SmartDevice, Double> devices;

    public SmartCityHub() {
        types = new EnumMap<>(DeviceType.class);
        types.put(DeviceType.CAMERA, 0);
        types.put(DeviceType.LAMP, 0);
        types.put(DeviceType.TRAFFIC_LIGHT, 0);
        devices = new LinkedHashMap<>();
    }

    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException         in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        if (device == null) {
            throw new IllegalArgumentException();
        }
        for (SmartDevice sd : devices.keySet()) {
            if (sd.getId().equals(device.getId())) {
                throw new DeviceAlreadyRegisteredException();
            }
        }
        int value = types.get(device.getType()) + 1;
        types.put(device.getType(), value);

        double powerConsumption = Duration.between(device.getInstallationDateTime(), LocalDateTime.now()).toHours() * device.getPowerConsumption();
        devices.put(device, powerConsumption);
        //throw new UnsupportedOperationException();
    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException  in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        if (device == null) {
            throw new IllegalArgumentException();
        }
        for (SmartDevice sd : devices.keySet()) {
            if (sd.getId().equals(device.getId())) {
                devices.remove(device);
                int value = types.get(device.getType()) - 1;
                types.put(device.getType(), value);
                return;
            }
        }
        throw new DeviceNotFoundException();
        //throw new UnsupportedOperationException();
    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException  in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        for (SmartDevice sd : devices.keySet()) {
            if (sd.getId().equals(id)) {
                return sd;
            }
        }
        throw new DeviceNotFoundException();
        //throw new UnsupportedOperationException();
    }

    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }
        return types.get(type);
        //throw new UnsupportedOperationException();
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     * <p>
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s multiplied by the stated power consumption of the device.
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        List<Map.Entry<SmartDevice, Double>> list = new LinkedList<Map.Entry<SmartDevice, Double>>(devices.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<SmartDevice, Double>>() {
            public int compare(Map.Entry<SmartDevice, Double> o1,
                               Map.Entry<SmartDevice, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Collection<String> topNDevicesByPowerConsumption = new ArrayList<>();
        int i = 1;
        for (SmartDevice sd : devices.keySet()) {
            topNDevicesByPowerConsumption.add(sd.getId());
            i++;
            if (i == n) {
                break;
            }
        }
        return topNDevicesByPowerConsumption;

        //throw new UnsupportedOperationException();
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the SmartCityHub (registration != installation).
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        //LinkedList<SmartDevice> firstNDevicesByRegistration = devices.keySet().sublist(0,n);
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        Collection<SmartDevice> firstNDevicesByRegistration = new ArrayList<>();
        int i = 1;
        for (SmartDevice sd : devices.keySet()) {
            firstNDevicesByRegistration.add(sd);
            i++;
            if (i == n) {
                break;
            }
        }
        return firstNDevicesByRegistration;
        //throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        SmartCityHub sch = new SmartCityHub();

        try {
            sch.register(new SmartCamera("mn", 78, LocalDateTime.now()));
        } catch (DeviceAlreadyRegisteredException e) {
            e.printStackTrace();
        }
        try {
            sch.register(new SmartLamp("n", 98, LocalDateTime.now()));
        } catch (DeviceAlreadyRegisteredException e) {
            e.printStackTrace();
        }
        ArrayList<String> al = (ArrayList<String>) sch.getTopNDevicesByPowerConsumption(1);
        System.out.println(al.get(0));
    }

    /*
    @Override
    public int compareTo(Object o) {
        return 0;
    }*/
}


