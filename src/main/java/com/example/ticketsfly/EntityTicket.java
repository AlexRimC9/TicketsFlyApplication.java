package com.example.ticketsfly;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EntityTicket {
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;


}
