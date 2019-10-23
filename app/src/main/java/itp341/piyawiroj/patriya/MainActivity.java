package itp341.piyawiroj.patriya;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Logger;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int CARD_INTENT_REQUEST_CODE = 0;
    public static final int ORDER_INTENT_REQUEST_CODE = 1;
    public static final String TICKET_EXTRA = "piyawiroj.patriya.A7.extra.tickets";
    public static final String CARD_EXTRA = "piyawiroj.patriya.A7.extra.cards";
    public static final String TYPE_EXTRA = "piyawiroj.patriya.A7.extra.type";
    public static final String PRIORITY_EXTRA = "piyawiroj.patriya.A7.extra.priority";

    private TextView totalTicketsSoldTextView;
    private Spinner startLocationSpinner;
    private Spinner endLocationSpinner;
    private RadioGroup ticketTypeGroup;
    private RadioGroup priorityGroup;
    private RadioButton oneWayButton;
    private RadioButton roundTripButton;
    private RadioButton dayPassButton;
    private RadioButton noButton;
    private RadioButton disabledButton;
    private RadioButton pregnantButton;
    private RadioButton elderlyButton;
    private RadioButton veteranButton;
    private Button useCardButton;
    private Button verifyButton;

    private Card card;
    private Integer totalTicketsSold = 0;
    private int selectedTrip = 0;
    private int selectedPriority = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getResources().getString(R.string.actionBar));

        totalTicketsSoldTextView = findViewById(R.id.totalNumberTextView);
        startLocationSpinner = findViewById(R.id.startSpinner);
        endLocationSpinner = findViewById(R.id.endSpinner);
        oneWayButton = findViewById(R.id.oneWayButton);
        roundTripButton = findViewById(R.id.roundTripButton);
        dayPassButton = findViewById(R.id.dayPassButton);
        noButton = findViewById(R.id.noButton);
        disabledButton = findViewById(R.id.disabledButton);
        pregnantButton = findViewById(R.id.pregnantButton);
        elderlyButton = findViewById(R.id.elderlyButton);
        veteranButton = findViewById(R.id.veteranButton);
        useCardButton = findViewById(R.id.useCardButton);
        verifyButton = findViewById(R.id.verifyButton);
        ticketTypeGroup = findViewById(R.id.ticketTypeButtonGroup);
        priorityGroup = findViewById(R.id.priorityButtonGroup);

        useCardButton.setOnClickListener(new ButtonListener());
        verifyButton.setOnClickListener(new ButtonListener());

        oneWayButton.setChecked(true);
        noButton.setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, String.format("Activity returned intent %d and result $d", requestCode, resultCode));
        card = data.getSerializableExtra(CARD_EXTRA) == null ? null : (Card) data.getSerializableExtra(CARD_EXTRA);
        if (requestCode == ORDER_INTENT_REQUEST_CODE) {
            switch (resultCode) {
                case (RESULT_OK):
                    clearFields();
                    displayToast("Thank you for your order!");
                    totalTicketsSold++;
                    totalTicketsSoldTextView.setText(totalTicketsSold.toString());
                    break;
                case (RESULT_CANCELED):
                    displayToast("Please make your changes.");
                    updateTicket((Ticket) data.getSerializableExtra(TICKET_EXTRA));
                    break;
            }
        }
    }

    private void clearFields() {
        startLocationSpinner.setSelection(0);
        endLocationSpinner.setSelection(0);
        oneWayButton.setChecked(true);
        noButton.setChecked(true);
        selectedPriority = ticketTypeGroup.getCheckedRadioButtonId();
        selectedTrip = priorityGroup.getCheckedRadioButtonId();
    }

    private void updateTicket(Ticket ticket) {
        startLocationSpinner.setSelection(ticket.getStartingLocation());
        endLocationSpinner.setSelection(ticket.getEndingLocation());
        ((RadioButton)ticketTypeGroup.findViewById(selectedTrip)).setChecked(true);
        ((RadioButton)priorityGroup.findViewById(selectedPriority)).setChecked(true);
    }

    private void displayToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        int [] values = {startLocationSpinner.getSelectedItemPosition(),
        endLocationSpinner.getSelectedItemPosition(),
        ticketTypeGroup.getCheckedRadioButtonId(),
        priorityGroup.getCheckedRadioButtonId()};
        outState.putIntArray("values", values);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.useCardButton:
                    selectedTrip = ticketTypeGroup.getCheckedRadioButtonId();
                    selectedPriority = priorityGroup.getCheckedRadioButtonId();
                    Intent cardIntent = new Intent(getApplicationContext(), CardActivity.class);
                    startActivityForResult(cardIntent, CARD_INTENT_REQUEST_CODE);
                    break;
                case R.id.verifyButton:
                    selectedTrip = ticketTypeGroup.getCheckedRadioButtonId();
                    selectedPriority = priorityGroup.getCheckedRadioButtonId();
                    Ticket ticket = new Ticket(startLocationSpinner.getSelectedItemPosition(),
                            endLocationSpinner.getSelectedItemPosition(),
                            ((RadioButton)findViewById(selectedTrip)).getText().toString(),
                            ((RadioButton)findViewById(selectedPriority)).getText().toString()
                    );
                    Log.d(TAG, ticket.toString());
                    Intent orderIntent = new Intent(getApplicationContext(), ViewOrderActivity.class);
                    orderIntent.putExtra(TICKET_EXTRA, ticket);
                    orderIntent.putExtra(CARD_EXTRA, card);
                    startActivityForResult(orderIntent, ORDER_INTENT_REQUEST_CODE);
                    break;
            }
        }
    }
}
