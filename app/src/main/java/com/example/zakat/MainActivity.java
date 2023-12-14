package com.example.zakat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zakat.R;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWeight, editTextValue;
    private Spinner spinnerType;
    private Button btnCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextValue = findViewById(R.id.editTextValue);
        spinnerType = findViewById(R.id.spinnerType);
        btnCalculate = findViewById(R.id.btnCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gold_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZakat();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.action_share) {
            // Share using an Intent
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/Darfriz/Zakat-App/tree/master"); // Change the message as needed
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share link via");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            } else {
                // Handle the case when no activity can handle the share intent
                Toast.makeText(MainActivity.this, "No app found to handle the share action", Toast.LENGTH_SHORT).show();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void calculateZakat() {
        if (validateInputs()) {
            double weight = Double.parseDouble(editTextWeight.getText().toString());
            double value = Double.parseDouble(editTextValue.getText().toString());
            String type = spinnerType.getSelectedItem().toString();

            double totalValue = weight * value;

            // Check if weight is less than threshold for zakat
            if ((type.equals("Keep") && weight < 85) || (type.equals("Wear") && weight < 200)) {
                textViewResult.setText("Total Value of Gold: RM" + totalValue + "\n"
                        + "Zakat payable: RM0.00 (Does not exceed Uruf)");
                return;
            }

            double xGram = (type.equals("Keep")) ? 85 : 200;
            double zakatPayableValue = (weight - xGram) * value;

            // Check if zakat payable value is non-negative
            if (zakatPayableValue < 0) {
                zakatPayableValue = 0;
            }

            double zakat = 0.025 * zakatPayableValue;

            String result = "Total Value of Gold: RM" + totalValue + "\n"
                    + "Total Gold Value that is Zakat Payable: RM" + zakatPayableValue + "\n"
                    + "Total Zakat: RM" + zakat;

            textViewResult.setText(result);
        }
    }

    public void openLink(View view) {
        // Define the link you want to open
        String url = "https://github.com/Darfriz/Zakat-App/tree/master";

        // Create an Intent to open the link
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private boolean validateInputs() {
        String weightStr = editTextWeight.getText().toString();
        String valueStr = editTextValue.getText().toString();

        if (weightStr.isEmpty() || valueStr.isEmpty()) {
            textViewResult.setText("Please enter valid weight and value.");
            return false;
        }

        return true;
    }
}
