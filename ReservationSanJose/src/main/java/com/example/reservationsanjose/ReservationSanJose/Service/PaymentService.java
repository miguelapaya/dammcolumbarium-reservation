package com.example.reservationsanjose.ReservationSanJose.Service;

import com.example.reservationsanjose.ReservationSanJose.Model.Payment;
import com.example.reservationsanjose.ReservationSanJose.Model.Reservation;
import com.example.reservationsanjose.ReservationSanJose.Model.User;
import com.example.reservationsanjose.ReservationSanJose.Repository.PaymentRepository;
import com.example.reservationsanjose.ReservationSanJose.Repository.ReservationRepository;
import com.example.reservationsanjose.ReservationSanJose.UserDetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public void save(Payment payment){

        LocalDate currentDate =  LocalDate.now();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        payment.setUserId(user.getId());
        payment.setPaymentFor("Reservation");
        payment.setAmountPaid(500);
        payment.setPaymentDate(currentDate);


        paymentRepository.save(payment);
    }
    public void saveRental(Payment payment){

        LocalDate currentDate =  LocalDate.now();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        payment.setUserId(user.getId());
        payment.setPaymentFor("Rental");
        payment.setAmountPaid(500);
        payment.setPaymentDate(currentDate);


        paymentRepository.save(payment);
    }

    public List<Payment> listAll(String keyword)
    {
        if (keyword != null) {
            return paymentRepository.searchAll(keyword);
        }
        return paymentRepository.findAll();
    }

    public List<Payment> listAllPayment(String keyword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        String username = ((CustomUserDetails) authentication.getPrincipal()).getFullName();

        Payment payment = new Payment();
        payment.setUserId(user.getId());
        if(keyword != null){
            return paymentRepository.searchAll(keyword);
        }
        return paymentRepository.findAll();
        /* if (user.getRoles().stream().filter(user -> user.getName().equals("Admin")).findFirst().isPresent())*/
//        if(payment.getUserName().equals(username)){
//            if(keyword != null){
//                return paymentRepository.searchAll(keyword);
//            }
//            return paymentRepository.findAll();
//        } else {
//            if(keyword != null){
//                return paymentRepository.search(keyword, username);
//            }
//            return paymentRepository.findByUserName(username);
//        }
    }
    public List<Payment> listAllPaymentById(String keyword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        if (user.getRoles().stream().filter(role -> role.getName().equals("Admin")).findFirst().isPresent()) {

            return paymentRepository.findAll();

        } else {

            return paymentRepository.searchPaymentById(user.getId());
        }
    }


    public Payment get(Long id){
        return paymentRepository.findById(id).get();
    }

}
