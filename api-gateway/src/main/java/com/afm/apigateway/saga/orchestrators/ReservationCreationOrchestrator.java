package com.afm.apigateway.saga.orchestrators;

import com.afm.apigateway.saga.core.*;
import com.afm.apigateway.security.jwt.JwtService;
import com.afm.apigateway.service.AuthService;
import com.afm.apigateway.service.FlightService;
import com.afm.apigateway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import model.utils.UserPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class ReservationCreationOrchestrator extends Orchestrator {
    private final FlightService flightService;
    private final UserDetails userDetailsSerivice;
    //private final ReservationService reservationService;

    private static Logger logger = LoggerFactory.getLogger(UserCreationOrchestrator.class);
    @Override
    protected SagaDefinition buildSaga(SagaBuilder builder) {
        /*logger.info("Prenotation creation saga");

        return builder
                .invoke(authService::createUser).addParam("reservationRequest").saveTo("userDatailsData")
                .withCompensation(authService::deleteUser)

                .step()
                .invoke(profileService::saveDetails).addParam("userDatailsData")

                .step()
                .invoke(authService::authenticateUserSaga).addParam("credentials").saveTo("currentUser")

                .step()
                .invoke(jwtService::generateJwt).addParam("currentUser").saveTo("jwt")
                .build();*/
        return null;
    }

    public String createReservation(UserPayload credentials) throws Throwable, SagaException {
        SagaParamsResolver resolver = getExecutor()
                .withArg("reservationRequest", credentials)
                .run()
                .collect();
        return resolver.get("reservation");
    }
}
