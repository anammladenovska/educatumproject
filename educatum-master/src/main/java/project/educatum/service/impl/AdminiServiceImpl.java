package project.educatum.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.NastavniciJpa;
import project.educatum.repository.UceniciJpa;
import project.educatum.service.AdminiService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminiServiceImpl implements AdminiService {

    private final AdminiJpa adminiRepository;
    private final NastavniciJpa nastavniciRepository;
    private final UceniciJpa uceniciRepository;

    public AdminiServiceImpl(AdminiJpa adminiRepository, NastavniciJpa nastavniciRepository, UceniciJpa uceniciRepository) {
        this.adminiRepository = adminiRepository;
        this.nastavniciRepository = nastavniciRepository;
        this.uceniciRepository = uceniciRepository;
    }

    @Override
    public List<Admini> findAll() {
        return adminiRepository.findAll();
    }

    @Override
    public List<Admini> listAll() {
        return this.adminiRepository.findAll();
    }
}
