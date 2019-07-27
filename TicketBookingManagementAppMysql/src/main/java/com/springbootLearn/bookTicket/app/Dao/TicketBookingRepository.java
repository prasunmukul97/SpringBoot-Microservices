package com.springbootLearn.bookTicket.app.Dao;

import com.springbootLearn.bookTicket.app.Entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketBookingRepository extends CrudRepository<Ticket,Integer> {
}
