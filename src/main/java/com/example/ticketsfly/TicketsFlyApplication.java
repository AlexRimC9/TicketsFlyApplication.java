package com.example.ticketsfly;

import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class TicketsFlyApplication {

    public TicketsFlyApplication() throws ParseException {
    }

    public static void main(String[] args) throws ParseException {
        List<EntityTicket> tickets = new ArrayList<>(Service.parserFile("tickets.json"));

        long averageTimeFlight = Service.averageFlyTime(tickets);
        System.out.println("Среднее время полета: " + averageTimeFlight / 60 / 60 + " часов " + (averageTimeFlight / 60) % 60 + " минут");

        long percentile = Service.percentile(tickets);
        System.out.println( "90-й процентиль времени полета между городами  Владивосток и Тель-Авив: "
                + percentile / 60 / 60 + " часов " + (percentile / 60) % 60 + " минут");
    }




}
