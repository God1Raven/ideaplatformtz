package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;



public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        URL url = Ticket.class.getResource("/tickets.json");
        Tickets tickets = mapper.readValue(url, Tickets.class);
        List<Ticket> filtredTickets = new ArrayList<>();
        for (Ticket ticket : tickets.getTickets()) {
            if(ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV")){
                filtredTickets.add(ticket);
            }
        }
        Map<String, List<Ticket>> processedTickets= new HashMap<>();
        for (Ticket filtredTicket : filtredTickets) {
            processedTickets.computeIfAbsent(filtredTicket.getCarrier(), x -> new ArrayList<>()).add(filtredTicket);
        }
        Map<String, Duration> result = new HashMap<>();
        for (String carrier : processedTickets.keySet()) {
            Duration duration = null;
            for (Ticket ticket : processedTickets.get(carrier)) {
                OffsetDateTime departureDateTime = OffsetDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime(), ZoneOffset.ofHours(10));
                OffsetDateTime arrivalDateTime = OffsetDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime(), ZoneOffset.ofHours(3));
                Duration flyDuration = Duration.between(departureDateTime, arrivalDateTime);
                if (duration == null || flyDuration.compareTo(duration) < 0) {
                    duration = flyDuration;
                    System.out.println("Авиакомпания " + carrier + " минимальное времая полёта " + duration);
                }
            }
            result.put(carrier, duration);
        }
        filtredTickets.sort(Comparator.comparingDouble(Ticket::getPrice));
        double avg = 0;
        int i = 0;
        double sum = 0;
        for (Ticket filtredTicket : filtredTickets) {
            sum += filtredTickets.get(i++).getPrice();
            avg  = sum/filtredTickets.size();
        }
        double mediana;
            if(filtredTickets.size() % 2 == 1){
                 mediana = filtredTickets.get(filtredTickets.size() / 2).getPrice();
            } else {
                mediana = filtredTickets.get(filtredTickets.size() / 2).getPrice() + filtredTickets.get(filtredTickets.size() / 2 - 1).getPrice();
                mediana /= 2;
            }
        System.out.println("Разница между средней ценой и медианой " + Math.abs(avg - mediana));
    }
}