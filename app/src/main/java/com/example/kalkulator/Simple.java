package com.example.kalkulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Simple extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        screen = findViewById(R.id.screen);
    }

    TextView screen;
    StringBuilder resultMemory = new StringBuilder();
    StringBuilder operationMemory = new StringBuilder();

    private boolean isSign(String sign) {
        if (sign.equals("+") || sign.equals("-") || sign.equals("*") || sign.equals("/")) {
            return true;
        }else {
            return false;
        }
    }

    private boolean isNumber(String number) {
        if (number.equals("0")||number.equals("1")||number.equals("2")||number.equals("3")||number.equals("4")||number.equals("5")||number.equals("6")||number.equals("7")||number.equals("8")||number.equals("9")) {
            return true;
        }else {
            return false;
        }
    }

    String number1 = "";
    String number2 = "";// jest też wynikiem poprzedniego działania
    String operator = "";

    public void setOperator(String sign) {
        operator = sign;
    }

    public void setRegisters() {

        if (number1.length() == 0) {
            number1 = resultMemory.toString();
        } else {
            number2 = resultMemory.toString();
        }
    }

    private Double mainResult = 0.0;

    public Double add() {
        try {

            Double result = 0.0;
            result = Double.parseDouble(number1) + Double.parseDouble(number2);
            number1 = result.toString();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Double sub() {
        try {

            Double result = 0.0;
            result = Double.parseDouble(number1) - Double.parseDouble(number2);
            number1 = result.toString();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Double mul() {
        try {
            Double result = Double.parseDouble(number1) * Double.parseDouble(number2);
            number1 = result.toString();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Double div() {
        try {

            Double result = Double.parseDouble(number1) / Double.parseDouble(number2);


            if (Double.parseDouble(number2) == 0.0) {
                result = 0.0;
                Toast.makeText(Simple.this, "Division by0!!", Toast.LENGTH_LONG).show();
            }

            number1 = result.toString();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void computeResult() {

        if (number1.length() != 0 && number2.length() != 0) {

            switch (operator) {
                case "+":
                    mainResult = add();
                    break;
                case "-":
                    mainResult = sub();
                    break;
                case "*":
                    mainResult = mul();
                    break;
                case "/":
                    mainResult = div();
                    break;
                default:
                    Log.i("ERROR", "NO SUCH OPERATION");
            }
        }

    }

    public void clearOperationMemory() {
        operationMemory.delete(0, operationMemory.length());
        UpdateMemoryTextWithOperation();
    }

    public void clearResultMemory() {
        resultMemory.delete(0, resultMemory.length());
        updateResultTextView();
    }

    public void clearRegisters(View view) {
        number1 = "";
        number2 = "";
        mainResult = 0.0;
        operator = "";
    }

    public void equalsClicked(View view) {
        clearOperationMemory();
        setRegisters();
        computeResult();
        clearResultMemory();
        if (number1.length() == 0 && number2.length() == 0) {
            screen.setText("");
        } else {
            updateResultTextViewWithResult();
        }
        clearRegisters(view);

    }

    public void bskpClicked(View view) {
        clearOperationMemory();
        setRegisters();
        computeResult();
        clearResultMemory();
        clearRegisters(view);
        screen.setText("");
    }

    public void plusMinusClicked(View view) {

        if (resultMemory.length() != 0) {
            if (resultMemory.charAt(0) == '-') {
                resultMemory.delete(0, 1);
            } else {
                String tempResultMemory = resultMemory.toString();
                Toast.makeText(Simple.this, tempResultMemory.toString(), Toast.LENGTH_LONG).show();
                resultMemory.delete(0, resultMemory.length());
                resultMemory.append("-").append(tempResultMemory);
                Log.i("result memory after", resultMemory.toString());
            }
        }

        updateResultTextView();

    }

    public void dotClicked(View view) {

        if (resultMemory.length() == 0) {
            resultMemory.append("0").append(".");
        } else if (!(resultMemory.charAt(resultMemory.length() - 1) == '.')) {
            resultMemory.append(".");
        }

        updateResultTextView();

    }



    public void pressButton(View view) {
        Button pressedButton = (Button) view;
        Log.i("Button pressed:", pressedButton.toString());
        String pressedButtonTag = "";
        pressedButtonTag = pressedButton.getText().toString();

        if (isNumber(pressedButtonTag)) {

            if (resultMemory.length() > 0) {
                if (!(pressedButtonTag.equals("0") && resultMemory.charAt(resultMemory.length() - 1) == '0' && resultMemory.length() == 1)) {
                    resultMemory.append(pressedButtonTag);
                    updateResultTextView();
                }
            } else {

                resultMemory.append(pressedButtonTag);
                updateResultTextView();

            }


        }

        if (operationMemory.length() == 0 && resultMemory.length() == 0 && isSign(pressedButtonTag)) {

            resultMemory.append("0").append(" ");

        }

        if (isSign(pressedButtonTag)) {

            setRegisters();

            computeResult();

            setOperator(pressedButtonTag);

            if (number1.length() != 0 && number2.length() != 0) {
                updateResultTextViewWithResult();
            }


            if (resultMemory.length() != 0) {
                operationMemory.append(resultMemory).append(" ");
                resultMemory.delete(0, resultMemory.length());
            }

            if (operationMemory.length() != 0 && !isSign(String.valueOf(operationMemory.charAt(operationMemory.length() - 2)))) {
                operationMemory.append(pressedButtonTag).append(" ");
            }

            if (operationMemory.length() != 0 && isSign(String.valueOf(operationMemory.charAt(operationMemory.length() - 2)))) {
                operationMemory.delete(operationMemory.length() - 2, operationMemory.length());
                operationMemory.append(pressedButtonTag).append(" ");
            }

        }

        UpdateMemoryTextWithOperation();

    }
String temp="";
    private void UpdateMemoryTextWithOperation() {
        if (operationMemory.length() < 22) {
            screen.setText((operationMemory.toString()+resultMemory));
            temp= (operationMemory.toString()+resultMemory);
        } else {
            screen.setText(operationMemory.substring(operationMemory.length() - 22));
        }
    }

    private void UpdateMemoryTextWithOperation(String operationMemory) {
        if (operationMemory.length() < 22) {
            screen.setText((operationMemory.toString()+resultMemory));
            temp= (operationMemory.toString()+resultMemory);
        } else {
            screen.setText(operationMemory.substring(operationMemory.length() - 22));
        }
    }

    private void updateResultTextViewWithResult() {

        String mainResultSting = "";

        try {
            Integer mainResultInteger = Integer.parseInt(mainResult.toString());
            mainResultSting = mainResultInteger.toString();
        } catch (Exception e) {
            Log.i("ERROR", "I CAN'T PARSE TO INT");
            mainResultSting = mainResult.toString();
            if (mainResultSting.length() > 11) {
                mainResultSting = mainResultSting.substring(0, 11);
            }
        } finally {
            if (mainResultSting.length() < 11) {
                screen.setText(mainResultSting);
            } else {
                screen.setText(mainResultSting.substring(mainResultSting.length() - 11));
            }
        }

    }

    private void updateResultTextView() {
        if (resultMemory.length() < 11) {
            screen.setText(resultMemory);
        } else {
            screen.setText(resultMemory.substring(resultMemory.length() - 11));
        }
    }

    private void updateResultTextView(String resultMemory) {
        if (resultMemory.length() < 11) {
            screen.setText(resultMemory);
        } else {
            screen.setText(resultMemory.substring(resultMemory.length() - 11));
        }
    }

    public void CClick(View view) {

                    if (resultMemory.length() > 0) {
                        resultMemory = resultMemory.delete(resultMemory.length() - 1, resultMemory.length());
                        updateResultTextView();
                    }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){

        outState.putString("resultMemory",resultMemory.toString());
        outState.putString("operationMemory",operationMemory.toString());
        outState.putString("number1", number1);
        outState.putString("number2", number2);
        outState.putDouble("result",mainResult);
        outState.putString("operator",operator);
        outState.putString("temp",temp);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        String result = savedInstanceState.getString("resultMemory");
        String operation = savedInstanceState.getString("operationMemory");

        String rej1Temp = savedInstanceState.getString("number1");
        String rej2Temp = savedInstanceState.getString("number2");
        Double mainResultTemp = savedInstanceState.getDouble("result");
        String operatorTemp = savedInstanceState.getString("operator");
        String temp2=savedInstanceState.getString("temp");

        if(rej1Temp != null) {
            number1 = rej1Temp;
        }

        if(rej2Temp != null){
            number2 = rej2Temp;
        }

        if(mainResultTemp != null){
            mainResult = mainResultTemp;
        }

        if(operatorTemp != null){
            operator = operatorTemp;

        }

        if(result != null){
            updateResultTextView(result);
            resultMemory.append(result);
        }

        if (operation != null){
            UpdateMemoryTextWithOperation(operation);
            operationMemory.append(result);
        }
        if(temp2 != null) {
            updateResultTextView(temp2);
        }

    }


}