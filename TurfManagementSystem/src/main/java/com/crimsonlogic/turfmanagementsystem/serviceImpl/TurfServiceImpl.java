package com.crimsonlogic.turfmanagementsystem.serviceImpl;

//author:shradha
//turf service calculation
import com.crimsonlogic.turfmanagementsystem.dto.TurfDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import com.crimsonlogic.turfmanagementsystem.service.TurfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurfServiceImpl implements TurfService {

    @Autowired
    private TurfRepository turfRepository;

    @Override
    public TurfDTO createTurf(TurfDTO turfDTO) {
        Turf turf = mapToEntity(turfDTO);
        Turf savedTurf = turfRepository.save(turf);
        return mapToDTO(savedTurf);
    }

    @Override
    public TurfDTO getTurfById(String turfId) {
        try {
            // Attempt to retrieve the turf by ID
            Turf turf = turfRepository.findById(turfId)
                    .orElseThrow(() -> new ResourceNotFoundException("Turf not found with id: " + turfId));
            
            // Return the corresponding DTO
            return mapToDTO(turf);
           
        } catch (Exception e) {
            // Handle other exceptions globally using the GlobalExceptionHandler
            throw new RuntimeException("An error occurred while retrieving the turf.");
        }
    }
    @Override
    public List<TurfDTO> getAllTurfs() {
        List<Turf> turfList = turfRepository.findAll();
        return turfList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public TurfDTO updateTurf(String turfId, TurfDTO turfDTO)  {
        try {
            // Check if the turf exists
            Turf turf = turfRepository.findById(turfId)
                    .orElseThrow(() -> new ResourceNotFoundException("Turf not found with id: " + turfId));

            // Update the fields
            turf.setTurfName(turfDTO.getTurfName());
            turf.setTurfInformation(turfDTO.getTurfInformation());
            turf.setTurfPricePerHour(turfDTO.getTurfPricePerHour());
            turf.setTurfImage(turfDTO.getTurfImage());
            turf.setTurfAvailabality(turfDTO.getTurfAvailabality());
            // Save the updated turf
            Turf updatedTurf = turfRepository.save(turf);

            // Return the updated DTO
            return mapToDTO(updatedTurf);

       
        } catch (Exception e) {
            // Handle other exceptions globally using the GlobalExceptionHandler
            throw new RuntimeException("An error occurred while updating the turf.");
        }
    }



    @Override
    public void deleteTurf(String turfId) {
    	turfRepository.deleteById(turfId);
    }

    // Utility methods to map between entity and DTO
    private TurfDTO mapToDTO(Turf turf) {
        TurfDTO dto = new TurfDTO();
        dto.setTurfId(turf.getTurfId());
        dto.setTurfName(turf.getTurfName());
        dto.setTurfInformation(turf.getTurfInformation());
        dto.setTurfPricePerHour(turf.getTurfPricePerHour());
        dto.setTurfImage(turf.getTurfImage());
        dto.setTurfAvailabality(turf.getTurfAvailabality());
        return dto;
    }

    private Turf mapToEntity(TurfDTO dto) {
        Turf turf = new Turf();
        turf.setTurfId(dto.getTurfId());
        turf.setTurfName(dto.getTurfName());
        turf.setTurfInformation(dto.getTurfInformation());
        turf.setTurfPricePerHour(dto.getTurfPricePerHour());
        turf.setTurfImage(dto.getTurfImage());
        turf.setTurfAvailabality(dto.getTurfAvailabality());
        return turf;
    }
    
    @Override
    public List<Turf> getAvailableTurfs() {
        return turfRepository.findAvailableTurfs();
    }
}
