package com.thiago.helpdesk.services;

import com.thiago.helpdesk.domain.Pessoa;
import com.thiago.helpdesk.domain.Tecnico;
import com.thiago.helpdesk.domain.dtos.TecnicoDTO;
import com.thiago.helpdesk.repositories.PessoaRepository;
import com.thiago.helpdesk.repositories.TecnicoRepository;
import com.thiago.helpdesk.resources.TecnicoResource;
import com.thiago.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.thiago.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDto) {
        objDto.setId(null);
        validarPorCpfEEmail(objDto);
        return repository.save(new Tecnico(objDto));
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
        objDto.setId(id);
        Tecnico obj = findById(id);
        validarPorCpfEEmail(objDto);
        obj = new Tecnico(objDto);
        return repository.save(obj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado");
        }
        repository.deleteById(id);
    }

    private void validarPorCpfEEmail(TecnicoDTO objDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        obj = pessoaRepository.findByEmail(objDto.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado");
        }
    }
}
