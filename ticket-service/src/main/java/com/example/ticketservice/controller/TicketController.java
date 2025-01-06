package com.example.ticketservice.controller;

import com.example.ticketservice.model.Ticket;
import com.example.ticketservice.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ticket createTicket(@Valid @RequestBody Ticket ticket) {
        return service.save(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @Valid @RequestBody Ticket updatedTicket) {
        return service.findById(id)
                .map(existingTicket -> {
                    existingTicket.setTitle(updatedTicket.getTitle());
                    existingTicket.setDescription(updatedTicket.getDescription());
                    existingTicket.setPriority(updatedTicket.getPriority());
                    existingTicket.setStatus(updatedTicket.getStatus());
                    service.save(existingTicket);
                    return ResponseEntity.ok(existingTicket);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
