package itp341.piyawiroj.patriya;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CardActivity extends AppCompatActivity {

    private static final String TAG = CardActivity.class.getSimpleName();

    private EditText cardNumber;
    private EditText nameOnCard;
    private Button saveCard;

   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_card);
       getSupportActionBar().setTitle(getResources().getString(R.string.actionBar));

        cardNumber = findViewById(R.id.cardNumberEditText);
        nameOnCard = findViewById(R.id.nameOnCardEditText);
        saveCard = findViewById(R.id.saveCardButton);
        saveCard.setOnClickListener(new Listener());

        if (getIntent().getSerializableExtra(MainActivity.CARD_EXTRA) != null) {
            Card card = (Card) getIntent().getSerializableExtra(MainActivity.CARD_EXTRA);
            cardNumber.setText(""+card.getNumber());
            nameOnCard.setText(card.getName());
        }
   }

   private class Listener implements View.OnClickListener {
       @Override
       public void onClick(View v) {
           Log.d(TAG, cardNumber.getText().toString() + nameOnCard.getText().toString());
           Card card = new Card(Long.parseLong(cardNumber.getText().toString()), nameOnCard.getText().toString(), -1);
           getIntent().putExtra(MainActivity.CARD_EXTRA, card);
           setResult(RESULT_OK, getIntent());
           finish();
       }
   }
}
