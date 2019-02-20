package com.example.android.tipcalculatoralbertsen;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button reset;
    private Button calculate;
    private RadioButton ten;
    private RadioButton fifteen;
    private RadioButton twenty;
    private RadioButton custom;
    private EditText party;
    private EditText bill;
    private EditText tip;
    private TextView finalTip;
    private TextView totalPrice;
    private TextView pricePP;
    private int rbutton;
    private float amount;
    private int numPeople;
    private int tipAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reset = findViewById(R.id.resetButton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });

        party = findViewById(R.id.numPeople);
        party.setOnKeyListener(mKeyListener);
        bill = findViewById(R.id.billAmount);
        bill.setOnKeyListener(mKeyListener);
        tip = findViewById(R.id.customTip);
        tip.setOnKeyListener(mKeyListener);

        finalTip = findViewById(R.id.finalTip);
        totalPrice = findViewById(R.id.billTotal);
        pricePP = findViewById(R.id.pricePP);

        ten = findViewById(R.id.radioTen);
        fifteen = findViewById(R.id.radioFifteen);
        twenty = findViewById(R.id.radioTwenty);
        custom = findViewById(R.id.radioCustom);

        rbutton = 1;

        if (!bill.getText().toString().equals("") &&
                !party.getText().toString().equals("") &&
                !tip.getText().toString().equals("")) {
            if ((Float.parseFloat(bill.getText().toString()) > 1
                    && Integer.parseInt(party.getText().toString()) > 1
                    && Integer.parseInt(tip.getText().toString()) > 1)){
                calculate.setEnabled(true);
            }
        }
        else {
            calculate.setEnabled(false);
        }
    }

    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//            if (keyEvent.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER) {

//            System.out.println("keyCode: " + keyCode);

            switch (view.getId()) {
                case R.id.billAmount:
                    if (keyCode == keyEvent.KEYCODE_ENTER) {
                        if (!bill.getText().toString().equals("")) {
                            if (Float.parseFloat(bill.getText().toString()) < 1) {
                                // show error
                                showErrorAlert("Bill can't be less than $1", R.id.billAmount);
                                calculate.setEnabled(false);
//                            System.out.println("bill error");
                            }
                            // else, ?
                            else {
//                                calculate.setEnabled(true);
                                return true;
                            }
                        }
                    }
                    break;
                case R.id.numPeople:
                    if (keyCode == keyEvent.KEYCODE_ENTER) {
                        if (!party.getText().toString().equals("")) {

                            if (Integer.parseInt(party.getText().toString()) < 1) {
                                // show error
                                showErrorAlert("Can't have fewer than 1 person", R.id.numPeople);
                                calculate.setEnabled(false);
//                            System.out.println("people error");
                            }
                            // else, ?
                            else {
//                                calculate.setEnabled(true);
                                return true;
                            }
                        }
                    }
                    break;
                case R.id.customTip:
                    if (keyCode == keyEvent.KEYCODE_ENTER) {

                        if (!tip.getText().toString().equals("")) {

                            if (Integer.parseInt(tip.getText().toString()) < 1) {
                                // show error
                                showErrorAlert("Can't tip less than 1%", R.id.customTip);
                                calculate.setEnabled(false);
//                            System.out.println("Tip error");
                            }
                            // else, ?
                            else {
//                                calculate.setEnabled(true);
                                return true;
                            }
                        }
                    }
                    break;
            }
            return false;
        }
    };

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("button", rbutton);

        String billString = bill.getText().toString();
        Log.d("rotate", billString);
        if (!billString.equals("")) {
            amount = Float.parseFloat(billString);
            savedInstanceState.putFloat("billAmount", amount);
        }
        else {
            savedInstanceState.putFloat("billAmount", 0);
        }

//        amount = Float.parseFloat(bill.getText().toString());
//        savedInstanceState.putFloat("billAmount", amount);

        String numPeopleText = party.getText().toString();
        if (!numPeopleText.equals("")) {
            numPeople = Integer.parseInt(numPeopleText);
            savedInstanceState.putInt("numPeople", numPeople);
        }
        else {
            savedInstanceState.putInt("numPeople", 0);
        }
//        numPeople = Integer.parseInt(party.getText().toString());
//        savedInstanceState.putInt("numPeople", numPeople);

        String tipText = tip.getText().toString();
        if (!tipText.equals("")) {
            tipAmount = Integer.parseInt(tipText);
            savedInstanceState.putInt("tipAmount", tipAmount);
        }
        else {
            savedInstanceState.putInt("tipAmount", 0);
        }
//        tipAmount = Integer.parseInt(tip.getText().toString());
//        savedInstanceState.putInt("tipAmount", tipAmount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        rbutton = savedInstanceState.getInt("button");

        amount = savedInstanceState.getFloat("billAmount");
        bill.setText(String.valueOf(amount));

        numPeople = savedInstanceState.getInt("numPeople");
        party.setText(String.valueOf(numPeople));

        tipAmount = savedInstanceState.getInt("tipAmount");
        tip.setText(String.valueOf(tipAmount));
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioTen:
                if (checked) {
                    rbutton = 1;
                    if (!bill.getText().toString().equals("") &&
                            !party.getText().toString().equals("")) {
                        if ((Float.parseFloat(bill.getText().toString()) >= 1
                                && Integer.parseInt(party.getText().toString()) > 0)){
                            calculate.setEnabled(true);
                        }
                    }
                    else {
                        calculate.setEnabled(false);
                    }
                }
                break;
            case R.id.radioFifteen:
                if (checked) {
                    rbutton = 2;
                    if (!bill.getText().toString().equals("") &&
                            !party.getText().toString().equals("")) {
                        if ((Float.parseFloat(bill.getText().toString()) > 1
                                && Integer.parseInt(party.getText().toString()) > 0)){
                            calculate.setEnabled(true);
                        }
                    }
                    else {
                        calculate.setEnabled(false);
                    }
                }
                break;
            case R.id.radioTwenty:
                if (checked) {
                    rbutton = 3;
                    if (!bill.getText().toString().equals("") &&
                            !party.getText().toString().equals("")) {
                        if ((Float.parseFloat(bill.getText().toString()) > 1
                                && Integer.parseInt(party.getText().toString()) > 0)){
                            calculate.setEnabled(true);
                        }
                    }
                    else {
                        calculate.setEnabled(false);
                    }
                }
                break;
            case R.id.radioCustom:
                if (checked) {
                    rbutton = 4;
                    if (!bill.getText().toString().equals("") &&
                            !party.getText().toString().equals("") &&
                            !tip.getText().toString().equals("")) {
                        if ((Float.parseFloat(bill.getText().toString()) > 1
                                && Integer.parseInt(party.getText().toString()) > 0
                                && Integer.parseInt(tip.getText().toString()) > 1)){
                            calculate.setEnabled(true);
                        }
                    }
                    else {
                        calculate.setEnabled(false);
                    }
                }
                break;
        }
    }

    public void calculate() {
        double billTotal;
        double tempBill;
        double tipPercent = 0;
        double tipAmount;
        double ppTotal;
        switch (rbutton) {
            case 1:
//                Log.d("calculate", "case 1");

                tipPercent = 0.1;
                break;
            case 2:
//                Log.d("calculate", "case 2");

                tipPercent = 0.15;
                break;
            case 3:
//                Log.d("calculate", "case 3");

                tipPercent = 0.2;
                break;
            case 4:
//                Log.d("calculate", "case 4");
                tipPercent = Integer.parseInt(tip.getText().toString()) / 100.0;
//                Toast toast = Toast.makeText(getApplicationContext(), "custom tip" + String.valueOf(tipPercent), Toast.LENGTH_SHORT);
//                toast.show();
                break;
        }
        tempBill = Float.parseFloat(bill.getText().toString());
        tipAmount = tempBill * tipPercent;
        billTotal = tempBill + tipAmount + 0.0;
        ppTotal = billTotal / (Integer.parseInt(party.getText().toString()) + 0.0);

//        Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(tipAmount), Toast.LENGTH_SHORT);
//        toast.show();
        finalTip.setText(String.format("Tip: $%.2f", tipAmount));
        totalPrice.setText(String.format("Bill Total: $%.2f", billTotal));
        pricePP.setText(String.format("Price per Person: $%.2f", ppTotal));
    }

    public void reset() {
        bill.setText(null);
        party.setText(null);
        tip.setText(null);
        finalTip.setText(null);
        totalPrice.setText(null);
        pricePP.setText(null);
    }
}
