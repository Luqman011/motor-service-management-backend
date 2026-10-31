package com.motorservice.motor_vehicle_service.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.motorservice.motor_vehicle_service.entity.Mechanic;
import com.motorservice.motor_vehicle_service.repository.MechanicRepository;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    // Register a new mechanic
    public Mechanic saveMechanic(Mechanic mechanic) {

        if (mechanicRepository.existsByPhoneNumber(mechanic.getPhoneNumber())) {
            throw new IllegalStateException("Mechanic phone number already exists");
        }

        return mechanicRepository.save(mechanic);
    }

    // Get all mechanics
    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    // Get mechanic by ID
    public Optional<Mechanic> getMechanicById(UUID id) {
        return mechanicRepository.findById(id);
    }

    // Update mechanic details
    public Optional<Mechanic> updateMechanic(UUID id, Mechanic updatedMechanic) {

        Optional<Mechanic> mechanicOptional = mechanicRepository.findById(id);

        if (mechanicOptional.isPresent()) {

            Mechanic existingMechanic = mechanicOptional.get();

            if (mechanicRepository.existsByPhoneNumberAndIdNot(
                    updatedMechanic.getPhoneNumber(), id)) {
                throw new IllegalStateException("Mechanic phone number already exists");
            }

            existingMechanic.setFirstName(updatedMechanic.getFirstName());
            existingMechanic.setLastName(updatedMechanic.getLastName());
            existingMechanic.setPhoneNumber(updatedMechanic.getPhoneNumber());
            existingMechanic.setEmail(updatedMechanic.getEmail());
            existingMechanic.setSpecialization(updatedMechanic.getSpecialization());
            existingMechanic.setExperienceYears(updatedMechanic.getExperienceYears());

            return Optional.of(mechanicRepository.save(existingMechanic));
        }

        return Optional.empty();
    }

    // Delete mechanic
    public boolean deleteMechanic(UUID id) {

        Optional<Mechanic> mechanicOptional = mechanicRepository.findById(id);

        if (mechanicOptional.isPresent()) {
            mechanicRepository.delete(mechanicOptional.get());
            return true;
        }

        return false;
    }
}
