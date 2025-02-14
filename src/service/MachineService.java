package service;
import model.CarProducingMachine;
import repository.CarProducingMachineRepository;
public class MachineService {
    private final CarProducingMachineRepository machineRepo = new CarProducingMachineRepository();
    public void addMachine(CarProducingMachine machine) { machineRepo.addMachine(machine); }
}