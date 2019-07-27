package com.springbootLearn.bookTicket.app.Resouce;

import com.springbootLearn.bookTicket.app.Dao.TicketBookingRepository;
import com.springbootLearn.bookTicket.app.Entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketBookingService {
    @Autowired
    TicketBookingRepository ticketBookingDao;

    public Ticket createTicket(Ticket ticket) {
        return ticketBookingDao.save(ticket);
    }

    public Iterable<Ticket> getAllTicket() {
       return ticketBookingDao.findAll();
    }
}
