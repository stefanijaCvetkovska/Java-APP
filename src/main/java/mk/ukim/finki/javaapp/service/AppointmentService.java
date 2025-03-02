package mk.ukim.finki.javaapp.service;

import mk.ukim.finki.javaapp.model.Appointment;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    List<Appointment> listAll();

    List<String> availableHours();

    Page<Appointment> listAllAppointments(int pageNo, int pageSize);

    Page<Appointment> listAllAppointmentsByOwner(int pageNo, int pageSize, Long ownerId);

    Page<Appointment> listAllAppointmentsByStatus(int pageNo, int pageSize, BookingStatus status);

    Page<Appointment> listAllAppointmentsByService(int pageNo, int pageSize, Long serviceId);

    Page<Appointment> listAllAppointmentsByDateGreaterThanEqual(int pageNo, int pageSize, LocalDate date);

    Appointment create(String email, String petName, LocalDate date, String time, Long serviceId, String reason);

    Appointment accept(Long id);

    Appointment cancel(Long id);

    Appointment finish(Long id);

    int countAppointmentsForToday();

    int countAppointmentsByStatus(BookingStatus status);

}
