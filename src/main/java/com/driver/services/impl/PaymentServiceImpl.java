package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation= reservationRepository2.findById(reservationId).get();

        Spot spot= reservation.getSpot();
        int pricePerHour= spot.getPricePerHour();

        Payment payment=new Payment();
        int bill= reservation.getNumberOfHours()* pricePerHour;

        if(amountSent<bill){
            throw new Exception("Insufficient Amount");
        }

        if(mode!="cash" || mode!="card" || mode!="UPI"){
            throw new Exception("Payment mode not detected");
        }
        if(mode=="cash"){
            payment.setPaymentMode(PaymentMode.CASH);
        } else if(mode=="card"){
            payment.setPaymentMode(PaymentMode.CARD);
        }else if(mode=="UPI"){
            payment.setPaymentMode(PaymentMode.UPI);
        }
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);

        reservationRepository2.save(reservation);

        return payment;
    }
}
