package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private static int numberOfCup = 0;
    private static boolean isWhippedAdded = false;
    private static boolean isChocolateAdded = false;
    private static String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String message = createOrderSummary();
        displayMessage(message);
        String title = getResources().getString(R.string.order_summary_email_subject, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "19vlad_son90@bk.ru");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        display(++numberOfCup);
    }

    public void decrement(View view) {
        if (numberOfCup == 0)
            display(0);
        else
            display(--numberOfCup);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity) {
        int price = 5;
        if (isWhippedAdded)
            price += 1;
        if (isChocolateAdded)
            price += 2;
        return quantity * price;
    }

    private String createOrderSummary() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        isWhippedAdded = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        isChocolateAdded = chocolateCheckBox.isChecked();

        EditText editText = (EditText) findViewById(R.id.name);
        name = editText.getText().toString();

        return getString(R.string.order_summary_name, name) +"\n" +
                getString(R.string.order_summary_whipped_cream, isWhippedAdded) + "\n" +
                getString(R.string.order_summary_chocolate, isChocolateAdded) + "\n" +
                getString(R.string.order_summary_quantity, numberOfCup) + "\n" +
                getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(calculatePrice(numberOfCup))) + "\n" +
                getString(R.string.thank_you);
    }
}
