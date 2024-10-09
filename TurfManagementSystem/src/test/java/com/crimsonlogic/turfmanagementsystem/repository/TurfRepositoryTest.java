package com.crimsonlogic.turfmanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use your actual database or configure for in-memory testing
class TurfRepositoryTest {

    @Autowired
    private TurfRepository turfRepository;

    private Turf testTurf;

    @BeforeEach
    public void setUp() {
        testTurf = new Turf();
        testTurf.setTurfName("Field A");
        testTurf.setTurfInformation("A spacious field suitable for events.");
        testTurf.setTurfPricePerHour(100.0);
        testTurf.setTurfImage("image_url_field_a.jpg");
        testTurf.setTurfAvailabality("available");
        turfRepository.save(testTurf);
    }

    @Test
    public void testFindById_ShouldReturnTurf_WhenTurfExists() {
        Optional<Turf> foundTurf = turfRepository.findById(testTurf.getTurfId());
        assertThat(foundTurf).isPresent();
        assertThat(foundTurf.get()).isEqualTo(testTurf);
    }

    @Test
    public void testFindAvailableTurfs_ShouldReturnAvailableTurfs() {
        List<Turf> availableTurfs = turfRepository.findAvailableTurfs();
        assertThat(availableTurfs).isNotEmpty();
        assertThat(availableTurfs).contains(testTurf);
    }

    @Test
    public void testSaveTurf_ShouldPersistTurf() {
        Turf newTurf = new Turf();
        newTurf.setTurfName("Field B");
        newTurf.setTurfInformation("A cozy field perfect for small gatherings.");
        newTurf.setTurfPricePerHour(150.0);
        newTurf.setTurfImage("image_url_field_b.jpg");
        newTurf.setTurfAvailabality("available");
        Turf savedTurf = turfRepository.save(newTurf);
        
        assertThat(savedTurf.getTurfId()).isNotNull();
        assertThat(savedTurf.getTurfName()).isEqualTo("Field B");
        assertThat(savedTurf.getTurfPricePerHour()).isEqualTo(150.0);
        assertThat(savedTurf.getTurfImage()).isEqualTo("image_url_field_b.jpg");
        assertThat(savedTurf.getTurfAvailabality()).isEqualTo("available");
    }

    @Test
    public void testDeleteTurf_ShouldRemoveTurf() {
        turfRepository.delete(testTurf);
        Optional<Turf> foundTurf = turfRepository.findById(testTurf.getTurfId());
        assertThat(foundTurf).isNotPresent();
    }

    @Test
    public void testFindById_ShouldReturnEmpty_WhenTurfDoesNotExist() {
        Optional<Turf> foundTurf = turfRepository.findById("nonexistentId");
        assertThat(foundTurf).isNotPresent();
    }
}
