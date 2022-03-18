package com.afm.apigateway.saga.orchestrators;

import com.afm.apigateway.saga.core.*;
import com.afm.apigateway.service.FlightsService;
import com.afm.apigateway.service.ReservationService;
import lombok.RequiredArgsConstructor;
import model.prenotation.Reservation;
import model.utils.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
public class ReservationCreationOrchestrator extends Orchestrator {
    private final FlightsService flightsService;
    private final ReservationService reservationService;

    private static Logger logger = LoggerFactory.getLogger(ReservationCreationOrchestrator.class);
    @Override
    protected SagaDefinition buildSaga(SagaBuilder builder) {
        logger.info("Reservation creation saga");

        return builder
                .invoke(reservationService::createReservationsGet).addParam("request").saveTo("reservationCreated")

                .step()
                .invoke(flightsService::bookSeatRestMult).addParam("reservationCreated")
                .build();
    }

    public List<Reservation> createReservation(List<ReservationRequest> request) throws Throwable, SagaException {
        SagaParamsResolver resolver = getExecutor()
                .withArg("request", request)
                .run()
                .collect();
        return resolver.get("reservationCreated");
    }
}
