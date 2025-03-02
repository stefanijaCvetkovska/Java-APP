package mk.ukim.finki.javaapp.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Appointment;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.VetService;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;
import mk.ukim.finki.javaapp.model.exception.AppointmentNotAvailableException;
import mk.ukim.finki.javaapp.model.exception.AppointmentNotFoundException;
import mk.ukim.finki.javaapp.repository.AppointmentRepository;
import mk.ukim.finki.javaapp.repository.UserRepository;
import mk.ukim.finki.javaapp.repository.VetServiceRepository;
import mk.ukim.finki.javaapp.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final VetServiceRepository vetServiceRepository;

    @Override
    public List<Appointment> listAll() {
        return this.appointmentRepository.findAll();
    }

    @Override
    public List<String> availableHours() {
        List<String> hours = new ArrayList<>();
        hours.add("8:00");
        hours.add("10:00");
        hours.add("11:30");
        hours.add("12:45");
        hours.add("14:30");
        hours.add("15:45");
        hours.add("16:30");
        return hours;
    }

    @Override
    public Page<Appointment> listAllAppointments(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.appointmentRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public Page<Appointment> listAllAppointmentsByOwner(int pageNo, int pageSize, Long ownerId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        User owner = this.userRepository.findById(ownerId).get();
        return this.appointmentRepository.findAllByOwner(owner, pageable);
    }

    @Override
    public Page<Appointment> listAllAppointmentsByStatus(int pageNo, int pageSize, BookingStatus status) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        if (status != null) {
            return this.appointmentRepository.findAllByStatus(status, pageable);
        } else {
            return this.appointmentRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Appointment> listAllAppointmentsByService(int pageNo, int pageSize, Long serviceId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        VetService service = this.vetServiceRepository.findById(serviceId).get();

        if (service != null) {
            return this.appointmentRepository.findAllByService(service, pageable);
        } else {
            return this.appointmentRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Appointment> listAllAppointmentsByDateGreaterThanEqual(int pageNo, int pageSize, LocalDate date) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        if (date != null) {
            return this.appointmentRepository.findAllByDateGreaterThanEqual(date, pageable);
        } else {
            return this.appointmentRepository.findAll(pageable);
        }
    }


    @Override
    public Appointment create(String email,
                              String petName,
                              LocalDate date,
                              String time,
                              Long serviceId,
                              String reason) {
        User owner = this.userRepository.findByEmail(email);
        VetService service = this.vetServiceRepository.findById(serviceId).get();
        BookingStatus status = BookingStatus.PENDING;
        Appointment appointment = new Appointment(owner, petName, date, time, status, service, reason);
        return this.appointmentRepository.save(appointment);

    }

    @Override
    public Appointment accept(Long id) {
        Appointment appointment = this.appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));;
        BookingStatus status = BookingStatus.ACCEPTED;
        appointment.setStatus(status);
        return this.appointmentRepository.save(appointment);
    }

    @Override
    public Appointment cancel(Long id) {
        Appointment appointment = this.appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));;
        BookingStatus status = BookingStatus.CANCELED;
        appointment.setStatus(status);
        return this.appointmentRepository.save(appointment);
    }

    @Override
    public Appointment finish(Long id) {
        Appointment appointment = this.appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        BookingStatus status = BookingStatus.FINISHED;
        appointment.setStatus(status);
        return this.appointmentRepository.save(appointment);
    }

    @Override
    public int countAppointmentsForToday() {
        List<Appointment> appointments = this.appointmentRepository.findAll();
        int count = 0;

        for (Appointment appointment: appointments) {
            String dateCreated = appointment.getCreatedAt().toLocalDate().toString();
            String now = LocalDateTime.now().toLocalDate().toString();
            if(dateCreated == now){
                count += 1;
            }
        }
        return count;
    }

    @Override
    public int countAppointmentsByStatus(BookingStatus status) {
        List<Appointment> appointments = this.appointmentRepository.findAll();
        int count = 0;
        for (Appointment appointment: appointments) {
            if(appointment.getStatus().equals(status)){
                count += 1;
            }
        }
        return count;
    }
}
