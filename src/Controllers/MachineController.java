package Controllers;

import model.Machine;
import service.MachineService;

import java.util.List;

public class MachineController {
    private final MachineService machineService = new MachineService();

    public void addMachine(String name, int capacity, String status) {
        machineService.addMachine(name, capacity, status);
    }

    public List<Machine> getAllMachines() {
        return machineService.getAllMachines();
    }
}