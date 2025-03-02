package mk.ukim.finki.javaapp.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.VetService;
import mk.ukim.finki.javaapp.repository.VetServiceRepository;
import mk.ukim.finki.javaapp.service.VetServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VetServiceServiceImpl implements VetServiceService {

    private final VetServiceRepository vetServiceRepository;

    @Override
    public List<VetService> listAll() {
        return this.vetServiceRepository.findAll();
    }
}
