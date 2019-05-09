package com.example.kalkulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringJoiner;

public class Advanced extends AppCompatActivity {
    TextView screen;
    String resultMemory = new String();
    String operationMemory = new String();
    String operationMemoryTextView = "";
    String rej1 = "";
    String rejtemp="";
    String rej2 = "";
    String operator = "";
    Double mainResult = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        screen = findViewById(R.id.screen);
    }

    private boolean isSign(String sign) {
        if (sign.equals("+") || sign.equals("-") || sign.equals("*") || sign.equals("/")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNumber(String number) {
        if (number.equals("0") || number.equals("1") || number.equals("2") || number.equals("3") || number.equals("4") || number.equals("5") || number.equals("6") || number.equals("7") || number.equals("8") || number.equals("9")) {
            return true;
        } else {
            return false;
        }
    }

    /////////seterry
    public void setOperator(String s) {

        operator = s;
    }

    public void setRegisters() {

        if (rej1.length() == 0) {
            rej1 = resultMemory.toString();
        } else {
            rej2 = resultMemory.toString();
        }

        Log.i("rej1", rej1);
        Log.i("rej2", rej2);
    }


    public Double addition() {
        try {

            Double result = 0.0;
            result = Double.parseDouble(rej1) + Double.parseDouble(rej2);
            rej1 = result.toString();
            rejtemp=rej1+" "+"+";
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Double subtraction() {
        try {

            Double result = 0.0;
            result = Double.parseDouble(rej1) - Double.parseDouble(rej2);
            rej1 = result.toString();
            rejtemp=rej1+" "+"-";
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Double multiplication() {
        try {

            Double result = 0.0;
            result = Double.parseDouble(rej1) * Double.parseDouble(rej2);
            rej1 = result.toString();
            rejtemp=rej1+" "+"*";
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Double division() {
        try {

            Double result = 0.0;
            result = Double.parseDouble(rej1) / Double.parseDouble(rej2);


            if (Double.parseDouble(rej2) == 0.0) {
                result = 0.0;
                Toast.makeText(Advanced.this, "Nie dziel przez 0!", Toast.LENGTH_LONG).show();
            }

            rej1 = result.toString();
            rejtemp=rej1+" "+"/";

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void computeResult() {

        if (rej1.length() != 0 && rej2.length() != 0) {

            switch (operator) {
                case "+":
                    mainResult = addition();
                    Log.i("main result", mainResult.toString());
                    break;
                case "-":
                    mainResult = subtraction();
                    Log.i("main result", mainResult.toString());
                    break;
                case "*":
                    mainResult = multiplication();
                    Log.i("main result", mainResult.toString());
                    break;
                case "/":
                    mainResult = division();
                    Log.i("main result", mainResult.toString());
                    break;
                default:
                    Log.i("ERROR", "NO SUCH OPERATION");
            }
        }
    }

    //////////clear
    public void clearOperationMemory() {
        operationMemory = "";
        updateMemoryTextView();
    }

    public void clearResultMemory() {
        resultMemory = "";
        updateResultTextView();
    }

    public void clearRegisters(View view) {
        rej1 = "";
        rej2 = "";
        mainResult = 0.0;
        operator = "";
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
            if(operationMemory!=null) {
                screen.setText((resultMemory + " " + operator));
            } else {
                screen.setText(resultMemory + " " + operator);
            }
        } else {
            screen.setText(resultMemory.substring(resultMemory.length() - 11));
        }
    }

    private void updateResultTextView(String resultMemory) {
        if (resultMemory.length() < 11) {
            if(operationMemory!=null) {
                screen.setText((resultMemory));
            } else {
                screen.setText(resultMemory);
            }
        } else {
            screen.setText(resultMemory.substring(resultMemory.length() - 11));
        }
    }

    public void equalsClicked(View view) {
        clearOperationMemory();
        setRegisters();
        computeResult();
        clearResultMemory();
        if (rej1.length() == 0 && rej2.length() == 0) {
            screen.setText("");
        } else {
            updateResultTextViewWithResult();
        }
        clearRegisters(view);

    }

    public void bkspClicked(View view) {
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
                int leng= resultMemory.length();
                resultMemory.substring(1, leng); /////////////////todo
            } else {
                String tempResultMemory = resultMemory;
                Toast.makeText(Advanced.this, tempResultMemory, Toast.LENGTH_LONG).show();
                resultMemory = "";
                String temp= resultMemory+"-"+tempResultMemory;
                operationMemory=temp;
                Log.i("result memory after", resultMemory);
            }
        }

        updateResultTextView();

    }

    String tempMemory = "";

    public void dotClicked(View view) {

        if (resultMemory.length() == 0) {
            String temp= resultMemory+"0"+".";
            resultMemory=temp;
        } else if (!(resultMemory.charAt(resultMemory.length() - 1) == '.')) {
            String temp= resultMemory+".";
            resultMemory=temp;

        }

        updateResultTextView();

    }
    public void buttonPress(View view) {
        Log.i("abc " ,"abc");
        Log.i("result memory:", resultMemory);
        Button pressedButton = (Button) view;
        String pressedButtonTag = "";
        pressedButtonTag = pressedButton.getText().toString();

        if (isNumber(pressedButtonTag)) {

            if (resultMemory.length() > 0) {
                if (!(pressedButtonTag.equals("0") && resultMemory.charAt(resultMemory.length() - 1) == '0' && resultMemory.length() == 1)) {
                    String temp= resultMemory+pressedButtonTag;
                    resultMemory=temp;
                    Log.i("result memory:", resultMemory);
                    updateResultTextView();
                }
            } else {

                String temp= resultMemory+pressedButtonTag;
                resultMemory=temp;
                updateResultTextView();
                Log.i("result memory:", resultMemory);

            }
            tempMemory = tempMemory + pressedButtonTag;

        }

        if (operationMemory.length() == 0 && resultMemory.length() == 0 && isSign(pressedButtonTag)) {

            String temp= resultMemory+"0"+" ";
            resultMemory=temp;

        }

        if (isSign(pressedButtonTag)) {

            setRegisters();

            computeResult();
            operator = pressedButtonTag;

            setOperator(pressedButtonTag);
            Log.i("result memory:", resultMemory);
            Log.i("operacja",operationMemory);

            if (rej1.length() != 0 && rej2.length() != 0) {
                updateResultTextViewWithResult();
            }


            if (resultMemory.length() != 0) {

                String temp= operationMemory+resultMemory+" ";
                operationMemory=temp;
                resultMemory="";
            }

            if (operationMemory.length() != 0 && !isSign(String.valueOf(operationMemory.charAt(operationMemory.length() - 2)))) {
                String temp= operationMemory+pressedButtonTag+" ";
                operationMemory=temp;
                operationMemory=resultMemory+" "+pressedButtonTag;
                Log.i("operation memory: ",resultMemory);
            }

            if (operationMemory.length() != 0 && isSign(String.valueOf(operationMemory.charAt(operationMemory.length() - 2)))) {
                operationMemory.substring(operationMemory.length() - 2);
                String temp= operationMemory+pressedButtonTag+" ";
                operationMemory=temp;

                updateResultTextView(tempMemory);
                tempMemory = "";

            }

            tempMemory = tempMemory + " " + pressedButtonTag;


        }

        Log.i("MEMOERY:",tempMemory);
        //updateResultTextView();
        Log.i("Memory:",resultMemory + " " + operator);
        updateResultTextView(tempMemory);

    }
    private void updateMemoryTextView() {
   operationMemory=operationMemoryTextView;
    }

    private void updateMemoryTextView(String operationMemory) {
        operationMemory=operationMemoryTextView;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){

        outState.putString("resultMemory",resultMemory);
        outState.putString("operationMemory",operationMemory);
        outState.putString("rej1",rej1);
        outState.putString("rejtemp",rejtemp);
        outState.putString("rej2",rej2);
        outState.putDouble("result",mainResult);
        outState.putString("operator",operator);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        String result = savedInstanceState.getString("resultMemory");
        String operation = savedInstanceState.getString("operationMemory");

        String rej1Temp = savedInstanceState.getString("rej1");
        String rejtempTemp = savedInstanceState.getString("rejtemp");
        String rej2Temp = savedInstanceState.getString("rej2");
        Double mainResultTemp = savedInstanceState.getDouble("result");
        String operatorTemp = savedInstanceState.getString("operator");

        if(rej1Temp != null) {
            rej1 = rej1Temp;
        }

        if(rejtempTemp != null) {
            rejtemp = rejtempTemp;
        }

        if(rej2Temp != null){
            rej2 = rej2Temp;
        }

        if(mainResultTemp != null){
            mainResult = mainResultTemp;
        }

        if(operatorTemp != null){
            operator = operatorTemp;

        }

        if(result != null){
            updateResultTextView(result);
            String temp= resultMemory+result;
            resultMemory=temp;

        }

        if (operation != null){
            updateMemoryTextView(operation);
            String temp= operationMemory+result;
            operationMemory=temp;
        }

    }

}


