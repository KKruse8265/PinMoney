package de.cokuss.chhe.pinmoney;

import android.content.Context;



import java.util.List;

interface BuchungDAO extends KontoDAO {
    List<Booking> getAllBuchungen (String name);

    void createBuchung (Konto konto, Booking booking);
    Booking calcSavings(Context context, String owner);
}

