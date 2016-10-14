package de.cokuss.chhe.pinmoney;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class HistoryAdapter extends ArrayAdapter<PinMoneyEnrty> {
    HistoryAdapter(Context context, ArrayList<PinMoneyEnrty> pinMoneyEnrties) {
        super(context, 0, (List<PinMoneyEnrty>) pinMoneyEnrties);
    }


    //PinMoneyEnrty (Zahlungen zahlungen, Date entryDate, String kontoName, String action)

    private DateHelper dateHelper = new DateHelper();

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PinMoneyEnrty pinMoneyEnrty = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_buchungssatz, parent, false);
        }
        Zahlungen zahlungen = pinMoneyEnrty.getZahlungen();
        // Lookup view for data population
        TextView tvEntryDate = (TextView) convertView.findViewById(R.id.iHiEntryDate);
        TextView tvAccountName = (TextView) convertView.findViewById(R.id.iHiAccountName);
        TextView tvAction = (TextView) convertView.findViewById(R.id.iHiAction);
        TextView tvBetrag = (TextView) convertView.findViewById(R.id.iHiValue);
        TextView tvStartDate = (TextView) convertView.findViewById(R.id.iHiStartDate);
        TextView tvCycle = (TextView) convertView.findViewById(R.id.iHiCycle);
        // Populate the data into the template view using the data object

        if (pinMoneyEnrty != null) {
            if (pinMoneyEnrty.getEntryDate() != null) {
                tvEntryDate.setText(dateHelper.sdfShort.format(pinMoneyEnrty.getEntryDate()));
            } else {
                tvEntryDate.setText(R.string.no);
            }
            tvAction.setText(pinMoneyEnrty.getAction());
            tvAccountName.setText(pinMoneyEnrty.getKontoName());
            if (zahlungen.getDate() != null) {
                tvStartDate.setText(dateHelper.sdfShort.format(zahlungen.getDate()));
            } else {
                tvStartDate.setText(R.string.no);
            }
            tvBetrag.setText(String.format(Locale.getDefault(), "%.2f", zahlungen.getBetrag()));
            tvCycle.setText(String.format(Locale.getDefault(), "%.2f", zahlungen.getTurnusStr()));
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
