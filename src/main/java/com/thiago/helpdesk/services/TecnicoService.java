package com.thiago.helpdesk.services;

import com.thiago.helpdesk.domain.Tecnico;
import com.thiago.helpdesk.domain.dtos.TecnicoDTO;
import com.thiago.helpdesk.repositories.TecnicoRepository;
import com.thiago.helpdesk.resources.TecnicoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElse(null);
    }
}
