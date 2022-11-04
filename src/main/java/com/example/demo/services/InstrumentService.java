package com.example.demo.services;

import com.example.demo.models.Instrument;

import java.util.List;

public interface InstrumentService {
    List<Instrument> getAllInstruments();

    List<Instrument> getAllAvailableInstruments();

    Instrument saveInstrument(Instrument instrument);

    Instrument getInstrument(Long id);

    void deleteInstrument(Long id);

    List<Instrument> saveAllInstruments(List<Instrument> instrumentsList);
}
