package com.thiago.helpdesk.services;

import com.thiago.helpdesk.domain.Tecnico;
import com.thiago.helpdesk.domain.dtos.TecnicoDTO;
import com.thiago.helpdesk.repositories.TecnicoRepository;
import com.thiago.helpdesk.resources.TecnicoResource;
import com.thiago.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDto) {
        objDto.setId(null);
        return repository.save(new Tecnico(objDto));
    }
}
