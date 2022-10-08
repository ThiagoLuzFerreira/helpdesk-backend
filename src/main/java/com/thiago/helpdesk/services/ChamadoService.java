package com.thiago.helpdesk.services;

import com.thiago.helpdesk.domain.Chamado;
import com.thiago.helpdesk.domain.Pessoa;
import com.thiago.helpdesk.domain.Tecnico;
import com.thiago.helpdesk.domain.dtos.ChamadoDTO;
import com.thiago.helpdesk.domain.dtos.TecnicoDTO;
import com.thiago.helpdesk.repositories.ChamadoRepository;
import com.thiago.helpdesk.repositories.PessoaRepository;
import com.thiago.helpdesk.repositories.TecnicoRepository;
import com.thiago.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.thiago.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " + id));
    }

    public List<Chamado> findAll(){
        return repository.findAll();
    }
}