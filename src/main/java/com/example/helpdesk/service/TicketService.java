package com.example.helpdesk.service;

import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    public List<Ticket> findAll() {
        return repository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return repository.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return repository.save(ticket);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
