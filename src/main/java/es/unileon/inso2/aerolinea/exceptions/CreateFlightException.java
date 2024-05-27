package es.unileon.inso2.aerolinea.exceptions;

import modelo.Vuelo;

public class CreateFlightException extends Exception {
    private final Vuelo flight;

    public CreateFlightException(String message, Vuelo flight) {
        super(message);
        this.flight = flight;
    }

    public Vuelo getFlight() {
        return flight;
    }
}
