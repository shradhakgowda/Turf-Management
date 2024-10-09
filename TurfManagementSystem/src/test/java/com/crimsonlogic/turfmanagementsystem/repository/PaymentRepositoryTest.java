package com.crimsonlogic.turfmanagementsystem.repository;

import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.entity.Payment;
import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TurfRepository turfRepository;

    @Autowired
    private RolesRepository rolesRepository;

    private Payment testPayment;
    private Users testUser;
    private Booking testBooking;
    private Turf testTurf;
    private Roles testRole;

    @BeforeEach
    public void setUp() {
        // Create and save a test role
        testRole = new Roles();
        testRole.setRoleName("Customer");
        rolesRepository.save(testRole);

        // Create and save test user
        testUser = new Users();
        testUser.setEmail("user@example.com");
        testUser.setPassword("password123");
        testUser.setRole(testRole);
        testUser = usersRepository.save(testUser);

        // Create and save the turf
        testTurf = new Turf();
        testTurf.setTurfName("Field B");
        testTurf.setTurfInformation("Medium-sized field");
        testTurf.setTurfPricePerHour(150.0);
        testTurf.setTurfImage("field_b.jpg");
        testTurf.setTurfAvailabality("available");
        turfRepository.save(testTurf);

        
            // Create and save a booking
            testBooking = new Booking();
            testBooking.setCustomer(testUser);
            testBooking.setTurf(testTurf);
            testBooking.setBookingDate(LocalDate.now());  // Ensure this is set
            testBooking.setStatus("confirmed");
            testBooking.setTotalAmount(150.0);
            bookingRepository.save(testBooking);

            // Create and save the payment
            testPayment = new Payment();
            testPayment.setUser(testUser);
            testPayment.setBooking(testBooking);
            testPayment.setAmount(150.0);
            testPayment.setTransactionType("Debit");
            testPayment.setTurf(testTurf);
            paymentRepository.save(testPayment);
        }
    

    @Test
    public void testFindById_ShouldReturnPayment_WhenPaymentExists() {
        Optional<Payment> foundPayment = paymentRepository.findById(testPayment.getPaymentId());
        assertThat(foundPayment).isPresent();
        assertThat(foundPayment.get()).isEqualTo(testPayment);
    }

    @Test
    public void testFindById_ShouldReturnEmpty_WhenPaymentDoesNotExist() {
        Optional<Payment> foundPayment = paymentRepository.findById("nonexistentId");
        assertThat(foundPayment).isNotPresent();
    }

    @Test
    public void testDeletePayment_ShouldRemovePayment() {
        paymentRepository.delete(testPayment);
        Optional<Payment> foundPayment = paymentRepository.findById(testPayment.getPaymentId());
        assertThat(foundPayment).isNotPresent();
    }
}
