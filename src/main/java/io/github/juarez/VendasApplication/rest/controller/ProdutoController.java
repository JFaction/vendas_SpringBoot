package io.github.juarez.VendasApplication.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.juarez.VendasApplication.domain.entity.Produto;
import io.github.juarez.VendasApplication.domain.repository.Produtos;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    private Produtos repository;   

    public ProdutoController(Produtos repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save( @RequestBody @Valid Produto produto){
        return repository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        repository.findById(id)
                .map( Produto -> {
                    repository.delete(Produto);
                    return Produto;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Produto não encontrando")); 
        
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id,
                        @RequestBody @Valid Produto Produto){
                        repository.
                            findById(id)
                            .map( ProdutoExistente -> {
                                Produto.setId(ProdutoExistente.getId());
                                repository.save(Produto);
                                return ProdutoExistente;
                            }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrando")); 

    }

    @GetMapping
    public List<Produto> find( Produto filtro ){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                        ExampleMatcher.StringMatcher.CONTAINING );
        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    
    @GetMapping("{id}")
    public Produto getProdutoById( @PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Produto não encontrando"));        
    }

}