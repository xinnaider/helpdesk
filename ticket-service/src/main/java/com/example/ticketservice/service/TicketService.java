    package com.example.ticketservice.service;

    import com.example.ticketservice.integration.UserServiceIntegration;
    import com.example.ticketservice.model.Ticket;
    import com.example.ticketservice.repository.TicketRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class TicketService {
        @Autowired
        private TicketRepository repository;
        @Autowired
        private UserServiceIntegration userServiceIntegration;

        public List<Ticket> findAll() {
            return repository.findAll();
        }

        public Optional<Ticket> findById(Long id) {
            return repository.findById(id);
        }

        public Ticket save(Ticket ticket) {
            if (userServiceIntegration.existingId(ticket.getUserId())) {
                return repository.save(ticket);
            } else {
                throw new IllegalArgumentException("User with id " + ticket.getUserId() + " does not exist");
            }
        }

        public void deleteById(Long id) {
            repository.deleteById(id);
        }
    }
