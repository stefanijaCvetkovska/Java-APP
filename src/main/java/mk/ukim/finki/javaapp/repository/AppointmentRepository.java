package mk.ukim.finki.javaapp.repository;

import mk.ukim.finki.javaapp.model.Appointment;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.VetService;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDate(LocalDate date);

    Page<Appointment> findAllByOrderByIdDesc(Pageable pageable);

    Page<Appointment> findAllByOwner(User owner, Pageable pageable);

    Page<Appointment> findAllByStatus(BookingStatus status, Pageable pageable);

    Page<Appointment> findAllByService(VetService service, Pageable pageable);

    Page<Appointment> findAllByDateGreaterThanEqual(LocalDate date, Pageable pageable);
}
