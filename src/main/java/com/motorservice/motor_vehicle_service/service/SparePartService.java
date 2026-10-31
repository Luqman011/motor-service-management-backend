package com.motorservice.motor_vehicle_service.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.motorservice.motor_vehicle_service.entity.RepairJob;
import com.motorservice.motor_vehicle_service.entity.SparePart;
import com.motorservice.motor_vehicle_service.repository.RepairJobRepository;
import com.motorservice.motor_vehicle_service.repository.SparePartRepository;

@Service
public class SparePartService {

    private final SparePartRepository sparePartRepository;
    private final RepairJobRepository repairJobRepository;

    public SparePartService(
            SparePartRepository sparePartRepository,
            RepairJobRepository repairJobRepository) {

        this.sparePartRepository = sparePartRepository;
        this.repairJobRepository = repairJobRepository;
    }

    // Register spare part
    public SparePart saveSparePart(SparePart sparePart) {

        UUID repairJobId = sparePart.getRepairJob().getId();

        Optional<RepairJob> repairJobOptional =
                repairJobRepository.findById(repairJobId);

        if (repairJobOptional.isEmpty()) {
            throw new IllegalArgumentException("Repair job not found");
        }

        sparePart.setRepairJob(repairJobOptional.get());

        return sparePartRepository.save(sparePart);
    }

    // Get all spare parts
    public List<SparePart> getAllSpareParts() {
        return sparePartRepository.findAll();
    }

    // Get spare part by ID
    public Optional<SparePart> getSparePartById(UUID id) {
        return sparePartRepository.findById(id);
    }

    // Get spare parts by repair job
    public List<SparePart> getSparePartsByRepairJob(UUID repairJobId) {

        if (!repairJobRepository.existsById(repairJobId)) {
            throw new IllegalArgumentException("Repair job not found");
        }

        return sparePartRepository.findByRepairJob_Id(repairJobId);
    }

    // Update spare part
    public Optional<SparePart> updateSparePart(
            UUID id, SparePart updatedSparePart) {

        Optional<SparePart> sparePartOptional =
                sparePartRepository.findById(id);

        if (sparePartOptional.isPresent()) {

            UUID repairJobId = updatedSparePart.getRepairJob().getId();

            Optional<RepairJob> repairJobOptional =
                    repairJobRepository.findById(repairJobId);

            if (repairJobOptional.isEmpty()) {
                throw new IllegalArgumentException("Repair job not found");
            }

            SparePart existingSparePart = sparePartOptional.get();

            existingSparePart.setRepairJob(repairJobOptional.get());
            existingSparePart.setPartName(updatedSparePart.getPartName());
            existingSparePart.setDescription(updatedSparePart.getDescription());
            existingSparePart.setQuantity(updatedSparePart.getQuantity());

            return Optional.of(sparePartRepository.save(existingSparePart));
        }

        return Optional.empty();
    }

    // Delete spare part
    public boolean deleteSparePart(UUID id) {

        Optional<SparePart> sparePartOptional =
                sparePartRepository.findById(id);

        if (sparePartOptional.isPresent()) {
            sparePartRepository.delete(sparePartOptional.get());
            return true;
        }

        return false;
    }
}
