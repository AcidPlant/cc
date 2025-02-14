package service;

import model.Machine;
import repository.MachineRepository;

import java.util.List;

public class MachineService {
    private final MachineRepository machineRepository = new MachineRepository();

    public void addMachine(String name, int capacity, String status) {
        Machine machine = new Machine(0, name, capacity, status);
        machineRepository.addMachine(machine);
    }

    public List<Machine> getAllMachines() {
        return machineRepository.getAllMachines();
    }
}