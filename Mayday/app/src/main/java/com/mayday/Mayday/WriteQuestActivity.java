package com.mayday.Mayday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mayday.Mayday.R;

public class WriteQuestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_write);
        setTitle("퀘스트 직접 입력하기");

        TextView writeNum1, writeNum2, writeNum3, writeNum4, writeNum5;
        final EditText readNum1, readNum2, readNum3, readNum4, readNum5;
        Button finishBtn;

        writeNum1 = (TextView) findViewById(R.id.writeNum1);
        writeNum2 = (TextView) findViewById(R.id.writeNum2);
        writeNum3 = (TextView) findViewById(R.id.writeNum3);
        writeNum4 = (TextView) findViewById(R.id.writeNum4);
        writeNum5 = (TextView) findViewById(R.id.writeNum5);

        readNum1 = (EditText) findViewById(R.id.readNum1);
        readNum2 = (EditText) findViewById(R.id.readNum2);
        readNum3 = (EditText) findViewById(R.id.readNum3);
        readNum4 = (EditText) findViewById(R.id.readNum4);
        readNum5 = (EditText) findViewById(R.id.readNum5);

        finishBtn = (Button) findViewById(R.id.finishBtn);

        Intent inIntent = getIntent();
        final int questNum = inIntent.getIntExtra("TOTAL_NUM", 0);

        // 퀘스트 개수만큼 해당 입력창이 표시된다.
        switch (questNum)
        {
            case 5:
                writeNum5.setVisibility(View.VISIBLE);
                readNum5.setVisibility(View.VISIBLE);
            case 4:
                writeNum4.setVisibility(View.VISIBLE);
                readNum4.setVisibility(View.VISIBLE);
            case 3:
                writeNum3.setVisibility(View.VISIBLE);
                readNum3.setVisibility(View.VISIBLE);
            case 2:
                writeNum2.setVisibility(View.VISIBLE);
                readNum2.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        // 버튼을 눌렀늘때 동작 구현
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent outIntent = new Intent(getApplicationContext(), SelectActivity.class);

                // questNum이 defaultValue인 경우
                if(questNum == 0)
                {
                    Intent outIntent_error = new Intent(getApplicationContext(), SelectActivity.class);
                    setResult(RESULT_CANCELED, outIntent_error);
                    finish();
                }
                // 정상적인 퀘스트 값이 들어온 경우
                else
                {
                    if (questNum > 0)
                    {
                        String text1 = readNum1.getText().toString();
                        if (text1.equals((String)""))
                        {
                            Toast.makeText(getApplicationContext(), "첫번째 퀘스트가 비어있습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        outIntent.putExtra("TEXT1", text1);
                    }
                    if (questNum > 1)
                    {
                        String text2 = readNum2.getText().toString();
                        if (text2.equals((String)""))
                        {
                            Toast.makeText(getApplicationContext(), "두번째 퀘스트가 비어있습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        outIntent.putExtra("TEXT2", text2);
                    }
                    if (questNum > 2)
                    {
                        String text3 = readNum3.getText().toString();
                        if (text3.equals((String)""))
                        {
                            Toast.makeText(getApplicationContext(), "세번째 퀘스트가 비어있습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        outIntent.putExtra("TEXT3", text3);
                    }
                    if (questNum > 3)
                    {
                        String text4 = readNum4.getText().toString();
                        if (text4.equals((String)""))
                        {
                            Toast.makeText(getApplicationContext(), "네번째 퀘스트가 비어있습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        outIntent.putExtra("TEXT4", text4);
                    }
                    if (questNum > 4)
                    {
                        String text5 = readNum5.getText().toString();
                        if (text5.equals((String)""))
                        {
                            Toast.makeText(getApplicationContext(), "다섯번째 퀘스트가 비어있습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        outIntent.putExtra("TEXT5", text5);
                    }

                    setResult(RESULT_OK, outIntent);
                    finish();
                }
            }
        });
    }
}
