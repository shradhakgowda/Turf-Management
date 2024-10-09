package com.crimsonlogic.turfmanagementsystem.service;


import com.crimsonlogic.turfmanagementsystem.dto.TurfDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;

import java.util.List;

public interface TurfService {

    TurfDTO createTurf(TurfDTO turfDTO);

    TurfDTO getTurfById(String turfId);

    List<TurfDTO> getAllTurfs();

    TurfDTO updateTurf(String turfId, TurfDTO turfDTO);

    void deleteTurf(String turfId);
    
    List<Turf> getAvailableTurfs();
}
