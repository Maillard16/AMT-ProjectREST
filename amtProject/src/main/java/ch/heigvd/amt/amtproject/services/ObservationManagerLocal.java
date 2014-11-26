/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amtproject.services;

import ch.heigvd.amt.amtproject.model.Observation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Zak
 */
@Local
public interface ObservationManagerLocal {
    Observation findObservationById(long observationId);

    List<Observation> findAllObservations();

    long createObservation(Observation observation);

    void updateObservation(Observation observation);

    void deleteObservation(long userId);
}
