package com.example.toeicstudynote;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout selectedItemsLayout;
    private TextView resultTextView;
    private Button clearButton;
    private List<String> selectedItems = new ArrayList<>();
    private List<Boolean> correctResults = new ArrayList<>();
    private String[] choiceNames = {"選択肢1", "選択肢2", "選択肢3", "選択肢4"}; // 初期の選択肢
    private EditText[] choiceEditTexts = new EditText[4]; // 選択肢変更用の EditText
    private Button[] optionButtons = new Button[4]; // 選択肢ボタン

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedItemsLayout = findViewById(R.id.selectedItemsLayout);
        resultTextView = findViewById(R.id.resultTextView);
        clearButton = findViewById(R.id.clearButton);

        optionButtons[0] = findViewById(R.id.option1Button);
        optionButtons[1] = findViewById(R.id.option2Button);
        optionButtons[2] = findViewById(R.id.option3Button);
        optionButtons[3] = findViewById(R.id.option4Button);

        Button gradeButton = findViewById(R.id.gradeButton);
        Button updateChoicesButton = findViewById(R.id.updateChoicesButton);

        choiceEditTexts[0] = findViewById(R.id.choiceEditText1);
        choiceEditTexts[1] = findViewById(R.id.choiceEditText2);
        choiceEditTexts[2] = findViewById(R.id.choiceEditText3);
        choiceEditTexts[3] = findViewById(R.id.choiceEditText4);

        // 選択ボタンのリスナー設定
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            optionButtons[i].setOnClickListener(v -> addItem(choiceNames[finalI]));
        }

        gradeButton.setOnClickListener(v -> grade());
        clearButton.setOnClickListener(v -> clear());
        updateChoicesButton.setOnClickListener(v -> updateChoices());

        // 初期値の設定
        for (int i = 0; i < 4; i++) {
            choiceEditTexts[i].setText(choiceNames[i]);
            optionButtons[i].setText(choiceNames[i]);
        }
    }

    private void addItem(String item) {
        selectedItems.add(item);
        updateSelectedItemsDisplay();
    }

    private void updateSelectedItemsDisplay() {
        selectedItemsLayout.removeAllViews();
        for (int i = 0; i < selectedItems.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText((i + 1) + ". " + selectedItems.get(i));
            textView.setTextSize(16);
            selectedItemsLayout.addView(textView);
        }
    }

    private void grade() {
        selectedItemsLayout.removeAllViews();
        correctResults.clear();

        for (int i = 0; i < selectedItems.size(); i++) {
            String selectedItem = selectedItems.get(i);

            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView textView = new TextView(this);
            textView.setText((i + 1) + ". " + selectedItem + " ");
            textView.setTextSize(16);
            itemLayout.addView(textView);

            Button correctButton = new Button(this);
            correctButton.setText("〇");
            int finalI = i;
            correctButton.setOnClickListener(v -> {
                correctResults.set(finalI, true);
                updateResult();
            });

            Button incorrectButton = new Button(this);
            incorrectButton.setText("×");
            incorrectButton.setOnClickListener(v -> {
                correctResults.set(finalI, false);
                updateResult();
            });

            correctResults.add(false); // 初期値は不正解とする

            itemLayout.addView(correctButton);
            itemLayout.addView(incorrectButton);
            selectedItemsLayout.addView(itemLayout);
        }

        clearButton.setVisibility(View.VISIBLE);
    }

    private void updateResult() {
        int correctCount = 0;
        for (boolean result : correctResults) {
            if (result) correctCount++;
        }
        resultTextView.setText("正解数: " + correctCount + "件");
    }

    private void clear() {
        selectedItems.clear();
        correctResults.clear();
        selectedItemsLayout.removeAllViews();
        resultTextView.setText("");
        clearButton.setVisibility(View.GONE);
    }

    private void updateChoices() {
        for (int i = 0; i < 4; i++) {
            choiceNames[i] = choiceEditTexts[i].getText().toString();
            optionButtons[i].setText(choiceNames[i]); // ボタンのテキストを更新
        }
    }
}
