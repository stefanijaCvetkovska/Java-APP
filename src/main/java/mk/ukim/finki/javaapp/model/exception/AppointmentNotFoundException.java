package mk.ukim.finki.javaapp.model.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long id) {
        super(String.format("Appointment with id: %d was not found", id));
    }
}
