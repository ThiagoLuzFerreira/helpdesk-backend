package com.thiago.helpdesk.resources;

import com.thiago.helpdesk.domain.Chamado;
import com.thiago.helpdesk.domain.dtos.ChamadoDTO;
import com.thiago.helpdesk.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {

    @Autowired
    private ChamadoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll(){
        List<Chamado> list = service.findAll();
        List<ChamadoDTO> listDto = list.stream().map(x -> new ChamadoDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<Chamado> create(@Valid @RequestBody ChamadoDTO objDto){
        Chamado obj = service.create(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
