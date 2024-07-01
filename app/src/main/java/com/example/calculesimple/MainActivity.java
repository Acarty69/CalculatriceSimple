package com.example.calculesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private enum Ops {
        PLUS, MOINS, FOIS, DIV
    }
    private TextView screen;
    private int op1 = 0;
    private int op2 = 0;
    private Ops operator = null;
    private boolean isOp1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateDisplay(int value) {
        screen.setText(String.valueOf(value));
    }

    private void calculateIntermediateResult() {
        if (operator != null) {
            switch (operator) {
                case PLUS:
                    op1 += op2;
                    break;
                case MOINS:
                    op1 -= op2;
                    break;
                case FOIS:
                    op1 *= op2;
                    break;
                case DIV:
                    if (op2 != 0) {
                        op1 /= op2;
                    } else {
                        Toast.makeText(this, "Division par zéro", Toast.LENGTH_LONG).show();
                        resetCalculator(null);
                        return;
                    }
                    break;
            }
            op2 = 0;
            updateDisplay(op1);
        }
    }
    public void computeResult(View v) {
        calculateIntermediateResult();
        operator = null; // Clear the operator after computing the final result
        isOp1 = true;    // Reset to the initial state
    }

    public void setOperator(View v) {
        if (!isOp1) {
            calculateIntermediateResult();
        }
        Button b = (Button) v;
        switch (b.getText().toString()) {
            case "+":
                operator = Ops.PLUS;
                break;
            case "-":
                operator = Ops.MOINS;
                break;
            case "*":
                operator = Ops.FOIS;
                break;
            case "/":
                operator = Ops.DIV;
                break;
        }
        isOp1 = false;
    }

    public void addNumber(View v) {
        Button b = (Button) v;
        try {
            int val = Integer.parseInt(b.getText().toString());
            if (isOp1) {
                op1 = op1 * 10 + val;
                updateDisplay(op1);
            } else {
                op2 = op2 * 10 + val;
                updateDisplay(op2);
            }
        } catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Valeur erronée",Toast.LENGTH_LONG).show();
        }
    }
    public void resetCalculator(View v) {
        op1 = 0;
        op2 = 0;
        operator = null;
        isOp1 = true;
        updateDisplay(0);
        Toast.makeText(this, "Calculatrice réinitialisée", Toast.LENGTH_SHORT).show();
    }
}