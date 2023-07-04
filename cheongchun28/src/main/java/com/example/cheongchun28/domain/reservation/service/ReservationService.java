package service;

import java.util.List;

public class ReservationService {

    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO getReservationById(Long id);
    List<ReservationDTO> getAllReservations();
    void deleteReservation(Long id);

}
