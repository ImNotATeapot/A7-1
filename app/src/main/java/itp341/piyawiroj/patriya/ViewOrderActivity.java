package itp341.piyawiroj.patriya;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class ViewOrderActivity extends AppCompatActivity {

    private static final String TAG = ViewOrderActivity.class.getSimpleName();

    private TextView tripFromTextView;
    private TextView tripToTextView;
    private TextView tripTypeTextView;
    private TextView prioritiesTextView;
    private TextView cardNumberTextView;
    private TextView nameOnCardTextView;
    private Button editTicketButton;
    private Button editCardButton;
    private Button purchaseButton;
    private Card card;
    private Ticket ticket;

    private String[] locations;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        getSupportActionBar().setTitle(getResources().getString(R.string.actionBar));
        locations = getResources().getStringArray(R.array.locationsArray);
        tripFromTextView = findViewById(R.id.tripFromTextView);
        tripToTextView = findViewById(R.id.tripToTextView);
        tripTypeTextView = findViewById(R.id.tripTypeTextView);
        prioritiesTextView = findViewById(R.id.prioritiesTextView);
        cardNumberTextView = findViewById(R.id.cardNumberTextView);
        nameOnCardTextView = findViewById(R.id.nameOnCardTextView);
        editTicketButton = findViewById(R.id.editTicketButton);
        editCardButton = findViewById(R.id.editCardButton);
        purchaseButton = findViewById(R.id.purchaseButton);

        editCardButton.setOnClickListener(new ButtonListener());
        editTicketButton.setOnClickListener(new ButtonListener());
        purchaseButton.setOnClickListener(new ButtonListener());

        ticket = getIntent().getSerializableExtra(MainActivity.TICKET_EXTRA) == null ?
                null : (Ticket) getIntent().getSerializableExtra(MainActivity.TICKET_EXTRA);
        card = getIntent().getSerializableExtra(MainActivity.CARD_EXTRA) == null?
                null : (Card) getIntent().getSerializableExtra(MainActivity.CARD_EXTRA);

        if (ticket != null) {
            setTicket(locations[ticket.getStartingLocation()],
                    locations[ticket.getEndingLocation()],
                    ticket.getTripType(),
                    ticket.getPriorities());
        } else {setTicket("", "", "", "");}
        if (card != null) {
            setCard(card.getNumber(), card.getName());
        } else {setCard(0,"");}
    }

    private void setTicket(String from, String to, String type, String priorities) {
        tripFromTextView.setText(String.format(getString(R.string.tripFrom), from));
        tripToTextView.setText(String.format(getString(R.string.tripTo), to));
        tripTypeTextView.setText(String.format(getString(R.string.tripType), type));
        prioritiesTextView.setText(String.format(getString(R.string.priorities), priorities));
    }

    private void setCard(long cardNumber, String nameOnCard) {
        if (cardNumber == 0) {cardNumberTextView.setText("Card Number: ");} else {
        cardNumberTextView.setText(String.format(getString(R.string.cardNumber), cardNumber));}
        nameOnCardTextView.setText(String.format(getString(R.string.nameOnCard), nameOnCard));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, String.format("Activity returned intent %d and result $d", requestCode, resultCode));
        if (requestCode == MainActivity.CARD_INTENT_REQUEST_CODE) {
            card = data.getSerializableExtra(MainActivity.CARD_EXTRA) == null ? null : (Card) data.getSerializableExtra(MainActivity.CARD_EXTRA);
            if (card != null) {
                setCard(card.getNumber(), card.getName());
            } else {setCard(0,"");}
        }
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.purchaseButton:
                    // Let MainActivity know that user has confirmed order and return up to date Card and finish
                    getIntent().putExtra(MainActivity.CARD_EXTRA, card);
                    setResult(RESULT_OK, getIntent());
                    Log.d(TAG, "User confirmed " + card);
                    finish();
                    break;
                case R.id.editTicketButton:
                    getIntent().putExtra(MainActivity.CARD_EXTRA, card);
                    setResult(RESULT_CANCELED, getIntent());
                    Log.d(TAG, "User cancelled " + card);
                    finish();

                    break;
                case R.id.editCardButton:
                    Intent cardIntent = new Intent(getApplicationContext(), CardActivity.class);
                    cardIntent.putExtra(MainActivity.CARD_EXTRA, card);
                    startActivityForResult(cardIntent, MainActivity.CARD_INTENT_REQUEST_CODE);
                    break;
            }
        }
    }
}
