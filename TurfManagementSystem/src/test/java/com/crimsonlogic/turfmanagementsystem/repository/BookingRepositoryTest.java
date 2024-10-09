package com.crimsonlogic.turfmanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;


import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
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
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use your actual database or configure for in-memory testing
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private  RolesRepository rolesRepository;
  

    @Autowired
    private TurfRepository turfRepository;
    
    @Autowired
    private UsersRepository usersRepository; 
    
    @Autowired 
    private TimeSlotRepository timeSlotRepository;
    
    private Booking testBooking;
    private Users testCustomer;
    private Users testTrainer;
    private Turf testTurf;
    private TimeSlot testSlot;
    private Roles testRole;

    @BeforeEach
    public void setUp() {
        // Create and save a test role
        testRole = new Roles();
        testRole.setRoleName("Customer");
        rolesRepository.save(testRole);

        // Create and save test users
        testCustomer = new Users();
        testCustomer.setEmail("customer@example.com");
        testCustomer.setPassword("password123");
        testCustomer.setRole(testRole);
        testCustomer = usersRepository.save(testCustomer);

        testTrainer = new Users();
        testTrainer.setEmail("trainer@example.com");
        testTrainer.setPassword("password123");
        testTrainer.setRole(testRole);
        testTrainer = usersRepository.save(testTrainer);

        // Create and save the turf
        testTurf = new Turf();
        testTurf.setTurfName("Field A");
        testTurf.setTurfInformation("Large grassy field");
        testTurf.setTurfPricePerHour(100.0);
        testTurf.setTurfImage("field_a.jpg");
        testTurf.setTurfAvailabality("available");
        turfRepository.save(testTurf); // Persist Turf

        // Create and save the time slot
        testSlot = new TimeSlot();
        testSlot.setTurf(testTurf);
        testSlot.setStartTime(LocalTime.of(10, 0));
        testSlot.setEndTime(LocalTime.of(12, 0));
        testSlot.setSlotAvailability("available");
        testSlot.setSlotDate(LocalDate.now());
        timeSlotRepository.save(testSlot); // Persist TimeSlot

        // Create and save the booking
        testBooking = new Booking();
        testBooking.setCustomer(testCustomer);
        testBooking.setAssignedTrainer(testTrainer);
        testBooking.setTurf(testTurf);
        testBooking.setSlot(testSlot);
        testBooking.setBookingDate(LocalDate.now());
        testBooking.setStatus("confirmed");
        testBooking.setTotalAmount(150.0);
        bookingRepository.save(testBooking);
    }

    @Test
    public void testFindByCustomerId_ShouldReturnBookings_WhenCustomerExists() {
        List<Booking> bookings = bookingRepository.findByCustomer_UserId(testCustomer.getUserId());
        assertThat(bookings).isNotEmpty();
    }

    @Test
    public void testFindByAssignedTrainerId_ShouldReturnBookings_WhenTrainerExists() {
        List<Booking> bookings = bookingRepository.findByAssignedTrainer_UserId(testTrainer.getUserId());
        assertThat(bookings).isNotEmpty();
    }


    @Test
    public void testFindById_ShouldReturnBooking_WhenBookingExists() {
        Optional<Booking> foundBooking = bookingRepository.findById(testBooking.getBookingId());
        assertThat(foundBooking).isPresent();
        assertThat(foundBooking.get()).isEqualTo(testBooking);
    }

    @Test
    public void testFindByCustomer_UserId_ShouldReturnBookings_WhenCustomerExists() {
        // Use the actual ID from the testCustomer created in setUp
        List<Booking> foundBookings = bookingRepository.findByCustomer_UserId(testCustomer.getUserId());
        assertThat(foundBookings).isNotEmpty();
        assertThat(foundBookings).contains(testBooking);
    }

    @Test
    public void testFindByAssignedTrainer_UserId_ShouldReturnBookings_WhenTrainerExists() {
        // Use the actual ID from the testTrainer created in setUp
        List<Booking> foundBookings = bookingRepository.findByAssignedTrainer_UserId(testTrainer.getUserId());
        assertThat(foundBookings).isNotEmpty();
        assertThat(foundBookings).contains(testBooking);
    }


    @Test
    public void testDeleteBooking_ShouldRemoveBooking() {
        bookingRepository.delete(testBooking);
        Optional<Booking> foundBooking = bookingRepository.findById(testBooking.getBookingId());
        assertThat(foundBooking).isNotPresent();
    }

    @Test
    public void testFindById_ShouldReturnEmpty_WhenBookingDoesNotExist() {
        Optional<Booking> foundBooking = bookingRepository.findById("nonexistentId");
        assertThat(foundBooking).isNotPresent();
    }
}
