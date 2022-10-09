package com.thiago.helpdesk.services;

import com.thiago.helpdesk.domain.Chamado;
import com.thiago.helpdesk.domain.Cliente;
import com.thiago.helpdesk.domain.Pessoa;
import com.thiago.helpdesk.domain.Tecnico;
import com.thiago.helpdesk.domain.dtos.ChamadoDTO;
import com.thiago.helpdesk.domain.dtos.TecnicoDTO;
import com.thiago.helpdesk.domain.enums.Prioridade;
import com.thiago.helpdesk.domain.enums.Status;
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
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " + id));
    }

    public List<Chamado> findAll(){
        return repository.findAll();
    }

    public Chamado create(@Valid ChamadoDTO objDto){
        return repository.save(newChamado(objDto));
    }

    private Chamado newChamado(ChamadoDTO obj){
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Chamado chamado = new Chamado();
        if(obj.getId() != null){
            chamado.setId(obj.getId());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;
    }
}