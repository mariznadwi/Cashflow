package com.dhanifudin.cashflow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dhanifudin.cashflow.models.Transaction;

import static com.dhanifudin.cashflow.MainActivity.INSERT_REQUEST;
import static com.dhanifudin.cashflow.MainActivity.TRANSACTION_KEY;

public class SaveActivity extends AppCompatActivity {

    private EditText descriptionInput;
    private EditText amountInput;
    private RadioGroup typeRadioGroup;
    private Transaction item;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        descriptionInput = findViewById(R.id.input_description);
        amountInput = findViewById(R.id.input_amount);
        typeRadioGroup = findViewById(R.id.group_type);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            item = extras.getParcelable(TRANSACTION_KEY);
            index = extras.getInt(MainActivity.INDEX_KEY, 0);
            descriptionInput.setText(item.getDescription());
            amountInput.setText(String.valueOf(item.getAmount()));
            if (item.getType() == Transaction.Type.DEBIT) {
                typeRadioGroup.check(R.id.radio_debit);
            } else if (item.getType() == Transaction.Type.CREDIT) {
                typeRadioGroup.check(R.id.radio_credit);
            }
        }
    }

    private Transaction.Type getCheckedType() {
        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_debit) {
            return Transaction.Type.DEBIT;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_credit) {
            return Transaction.Type.CREDIT;
        }
        return Transaction.Type.EMPTY;
    }

    public void handleSubmit(View view) {
        boolean invalid = String.valueOf(amountInput.getText()).isEmpty() || descriptionInput.getText().toString().isEmpty() || Integer.parseInt(amountInput.getText().toString()) < 0;
        Transaction.Type type = getCheckedType();

        if (type == Transaction.Type.EMPTY || invalid){
            Toast.makeText(this, "Isi Semua Data!", Toast.LENGTH_SHORT).show();
        }else {

            String description = descriptionInput.getText().toString();
            int amount = Integer.parseInt(amountInput.getText().toString());

            item.setDescription(description);
            item.setAmount(amount);
            item.setType(type);

            Intent intent = new Intent();
            intent.putExtra(TRANSACTION_KEY, item);
            intent.putExtra(MainActivity.INDEX_KEY, index);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
