package com.example.ticketsfly;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Service {

    public static List<EntityTicket> parserFile(String file) throws ParseException {
        JSONParser parser = new JSONParser();
        List<EntityTicket> listTickets = new ArrayList<>();
        try (Reader reader = new FileReader(file)) {

            StringBuilder buffer = new StringBuilder();
            int c;
            boolean fl = false;
            while ((c = reader.read()) != -1) {
                if (fl) {
                    buffer.append((char) c);
                } else if (c == '{') {
                    fl = true;
                    buffer.append((char) c);
                }
            }

            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(buffer));
            JSONArray jsonArray = (JSONArray) jsonObject.get("tickets");

            for (Object o : jsonArray) {
                EntityTicket ticket = new EntityTicket();
                JSONObject objectInTicket = (JSONObject) o;
                ticket.setDepartureTime(LocalDateTime.parse(objectInTicket.get("departure_date") + " " + objectInTicket.get("departure_time"), DateTimeFormatter.ofPattern("d.M.y H:m")));
                ticket.setArrivalTime(LocalDateTime.parse(objectInTicket.get("arrival_date") + " " + objectInTicket.get("arrival_time"), DateTimeFormatter.ofPattern("d.M.y H:m")));
                listTickets.add(ticket);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listTickets;
    }
        public static long flyTime(EntityTicket entityTicket){
         long flyTime = entityTicket.getArrivalTime().toEpochSecond(ZoneOffset.ofHours(3)) -
                entityTicket.getDepartureTime().toEpochSecond(ZoneOffset.ofHours(10));
         return flyTime;
    }

    public static long averageFlyTime(List<EntityTicket> tickets){
        long average = 0;
        for (EntityTicket entityTicket: tickets){
            average += flyTime(entityTicket)  ;
        }
        return average/ tickets.size() ;
    }
    public static long percentile(List<EntityTicket> entityTickets) {
        List<Long> flyTimeList = new ArrayList<>();
        for (EntityTicket entityTicket : entityTickets) {
            flyTimeList.add(flyTime(entityTicket));
        }
        Collections.sort(flyTimeList);
        int index = (int) Math.ceil(90.0 / 100.0 * flyTimeList.size());
        return flyTimeList.get(index - 1);
    }
}
