package com.springbootLearn.bookTicket.app.Controller;

import com.springbootLearn.bookTicket.app.Entity.Ticket;
import com.springbootLearn.bookTicket.app.Resouce.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TicketBookingController {

    @Autowired
    TicketBookingService ticketBookingService;

    @PostMapping("/createTicket")
    private Ticket createTicket(@RequestBody Ticket ticket){
        return ticketBookingService.createTicket(ticket);
    }

    @GetMapping(path="/allTicket" ,produces = MediaType.APPLICATION_JSON_VALUE)
    private Iterable<Ticket> getAllTicket(){
         return ticketBookingService.getAllTicket();
    }
}
