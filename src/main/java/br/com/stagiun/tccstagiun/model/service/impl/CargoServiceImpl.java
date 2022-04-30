package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Cargo;
import br.com.stagiun.tccstagiun.model.repository.CargoRepository;
import br.com.stagiun.tccstagiun.model.service.CargoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Override
    public Cargo salvar(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    @Override
    public Cargo editar(Long id, Cargo cargo) {
        cargo.setId(id);
        return cargoRepository.save(cargo);
    }

    @Override
    public List<Cargo> list() {
        return cargoRepository.findAll();
    }

    @Override
    public Optional<Cargo> findById(Long id) {
        return cargoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        cargoRepository.deleteById(id);
    }
}
