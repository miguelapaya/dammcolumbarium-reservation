package com.example.reservationsanjose.ReservationSanJose.Repository;

import com.example.reservationsanjose.ReservationSanJose.Model.Reservation;
import com.example.reservationsanjose.ReservationSanJose.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.userId = ?2 AND (r.firstName LIKE %?1%"
            + " OR r.lastName LIKE %?1%"
            + " OR r.slotNo LIKE %?1%"
            + " OR r.deathCertificate LIKE %?1%)")
    public List<Reservation> search(String keyword, Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.firstName LIKE %?1%"
            + " OR r.lastName LIKE %?1%"
            + " OR r.slotNo LIKE %?1%"
            + " OR r.deathCertificate LIKE %?1%")
    public List<Reservation> searchAll(String keyword);


    /*@Query("SELECT r FROM Reservation  r WHERE r.paymentStatus ='Paid'")
    public List<Reservation> findRental(String keyword);*/

    @Query("SELECT r FROM Reservation  r WHERE r.userId = ?1 AND r.paymentStatus ='Paid'")
    public List<Reservation> findRental(Long id);

    @Query("SELECT r FROM Reservation  r WHERE r.paymentStatus ='Paid'")
    public List<Reservation> findAllRentals();

    @Query("SELECT r.slotNo FROM Reservation  r WHERE r.paymentStatus ='Paid'")
    public List<String> findSlots();

    List<Reservation> findByUserId(Long userId);

    /*@Query("SELECT r.slotNo FROM Reservation  r WHERE r.userId = ?1 ")
    public List<Reservation> findReservesById(Long userId);*/

    Long countByPaymentStatus(String paymentStatus);

  /*  Long countByUserPaymentStatus(String paymentStatus, Long id);*/

    /*LocalDate countDueDates(LocalDate dateExpired);*/
}
