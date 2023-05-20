package com.lilianmagalhaes.calculator_android_java;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultOutput, expressionDisplay;
    MaterialButton buttonC, buttonAc, buttonDot, buttonOpenBracket, buttonCloseBracket;
    MaterialButton buttonDivide, buttonMultiply, buttonAdd, buttonSubtract, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4;
    MaterialButton button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultOutput = findViewById(R.id.result_output);
        resultOutput.setShowSoftInputOnFocus(false);
        expressionDisplay = findViewById(R.id.expression_display);

        addBtn(buttonC, R.id.button_c);
        addBtn(buttonOpenBracket, R.id.button_openBracket);
        addBtn(buttonCloseBracket, R.id.button_closeBracket);
        addBtn(buttonDivide, R.id.button_divide);
        addBtn(buttonMultiply, R.id.button_multiply);
        addBtn(buttonAdd, R.id.button_add);
        addBtn(buttonSubtract, R.id.button_subtract);
        addBtn(buttonEquals, R.id.button_equals);
        addBtn(button0, R.id.button_0);
        addBtn(button1, R.id.button_1);
        addBtn(button2, R.id.button_2);
        addBtn(button3, R.id.button_3);
        addBtn(button4, R.id.button_4);
        addBtn(button5, R.id.button_5);
        addBtn(button6, R.id.button_6);
        addBtn(button7, R.id.button_7);
        addBtn(button8, R.id.button_8);
        addBtn(button9, R.id.button_9);
        addBtn(buttonAc, R.id.button_ac);
        addBtn(buttonDot, R.id.button_dot);
    }

    void addBtn(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String expressionToCalculate = expressionDisplay.getText().toString();
        String stringOutput = resultOutput.getText().toString();
        switch (buttonText) {
            case "AC" -> { //ok!
                expressionDisplay.setText("");
                resultOutput.setText("0");
            }
            case "=" -> { //ok!
                String finalResult = getResult(expressionToCalculate);
                resultOutput.setText(finalResult);
                expressionDisplay.setText(" ");
            }
            case "C" -> { //ok!
                expressionToCalculate = expressionToCalculate.substring(0, expressionToCalculate.length() - 1);
                expressionDisplay.setText(expressionToCalculate);
            }
            /*case "+", "-", "x", "÷" -> {
                if (expressionToCalculate.equals(" ")) {
                    expressionToCalculate = stringOutput + buttonText;
                    expressionDisplay.setText(expressionToCalculate);
                } else {
                    expressionDisplay.setText(expressionToCalculate);
                }
            }*/
            default -> {  //ok!
                expressionToCalculate = expressionToCalculate + buttonText;
                expressionDisplay.setText(expressionToCalculate);
            }
        }
    }
        String getResult (String expressionToCalculate){
            try {
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
                String valideExpression = expressionToCalculate.replace("x", "*").replace("÷", "/");
                String result = engine.eval(valideExpression).toString();
                if (result == null) {
                    // Handle other types of results if necessary
                    return "Err";
                } else {
                    String finalResult = result;
                    // Handle the string result
                    if (finalResult.endsWith(".0")) {
                        finalResult = finalResult.replace(".0", "");
                    }
                    return finalResult;
                }
            } catch (Exception e) {
                Log.println(Log.INFO, valueOf(e), "error");
                return "Err";
            }
        }
    }