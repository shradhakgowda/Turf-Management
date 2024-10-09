package com.crimsonlogic.turfmanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.entity.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use your actual database or configure for in-memory testing
class TimeSlotRepositoryTest {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TurfRepository turfRepository;

    private Turf testTurf;
    private TimeSlot testSlot;

    @BeforeEach
    public void setUp() {
        // Create and save the turf
        testTurf = new Turf();
        testTurf.setTurfName("Field A");
        testTurf.setTurfInformation("Large grassy field");
        testTurf.setTurfPricePerHour(100.0);
        testTurf.setTurfImage("field_a.jpg");
        testTurf.setTurfAvailabality("available");
        testTurf = turfRepository.save(testTurf); // Persist Turf

        // Create and save a test time slot
        testSlot = new TimeSlot();
        testSlot.setTurf(testTurf);
        testSlot.setStartTime(LocalTime.of(10, 0));
        testSlot.setEndTime(LocalTime.of(12, 0));
        testSlot.setSlotAvailability("available");
        testSlot.setSlotDate(LocalDate.now());
        testSlot = timeSlotRepository.save(testSlot); // Persist TimeSlot
    }


    @Test
    public void testFindByTurfAndDateAndAvailability_ShouldReturnAvailableTimeSlots() {
        List<TimeSlot> slots = timeSlotRepository.findByTurf_TurfIdAndSlotDateAndSlotAvailability(
                testTurf.getTurfId(), LocalDate.now(), "available");
        assertThat(slots).isNotEmpty();
        assertThat(slots).contains(testSlot);
    }

    @Test
    public void testFindByTurfAndDateAfterAndAvailability_ShouldReturnAvailableTimeSlots() {
        List<TimeSlot> slots = timeSlotRepository.findByTurf_TurfIdAndSlotDateAfterAndSlotAvailability(
                testTurf.getTurfId(), LocalDate.now().minusDays(1), "available");
        assertThat(slots).isNotEmpty();
        assertThat(slots).contains(testSlot);
    }



   
}
