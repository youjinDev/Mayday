package com.mayday.Mayday;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayday.Mayday.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class QuestFinishActivity extends Activity {

    ImageView imageView;
    Button button;          // 이미지 불러오는 버튼
    public static EditText edtDiary;
    Button btnWrite;
    public static String fileName;
    public static String imgFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_finish);
        setTitle("퀘스트 완료");

        Intent inIntent = getIntent();
        final int btnNum = inIntent.getIntExtra("BtnNum", 0);

        /* 이미지 불러오기 코드 */
        imageView = (ImageView) findViewById(R.id.image);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        /* 텍스트 저장하기 코드 */
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        Date now = new Date();
        SimpleDateFormat sFormat1;  // 날짜
        sFormat1 = new SimpleDateFormat("yyyyMMdd");
        fileName = (sFormat1.format(now)) + "_" + MainActivity.fileNumber + ".txt";

        // final String str = readDiary(fileName);
        // edtDiary.setText(str);

        btnWrite.setOnClickListener(new View.OnClickListener() {        // 저장 버튼 터치해야 퀘스트 버튼의 중복 터치를 막게끔 수정 (0517)
            private Object setOnTouchListner;

            public void onClick(View v) {
                // 저장 버튼 터치시 수행 내용

                // 10개를 모두 채운 경우
                if (MainActivity.fileNumber == 10)
                {
                    Toast.makeText(getApplicationContext(), "오늘의 퀘스트 상한에 도달했어요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tempstr = edtDiary.getText().toString();

                if (tempstr.getBytes().length != 0) {   // 0606추가 editText null 방지용

                    wirteToFile(fileName, tempstr);         //0604추가

                    /* 해당 버튼의 중복 터치 방지(0517 수정) */
                    switch (btnNum) {
                        case 1:
                            MainActivity.startQuestBool1 = true; // 앱 종료시 버튼 클릭 유무에 관계된 부분
                            MainActivity.startQuestBtn1.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });
                            break;
                        case 2:
                            MainActivity.startQuestBool2 = true; // 앱 종료시 버튼 클릭 유무에 관계된 부분
                            MainActivity.startQuestBtn2.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });
                            break;
                        case 3:
                            MainActivity.startQuestBool3 = true; // 앱 종료시 버튼 클릭 유무에 관계된 부분
                            MainActivity.startQuestBtn3.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });
                            break;
                        case 4:
                            MainActivity.startQuestBool4 = true; // 앱 종료시 버튼 클릭 유무에 관계된 부분
                            MainActivity.startQuestBtn4.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });
                            break;
                        case 5:
                            MainActivity.startQuestBool5 = true; // 앱 종료시 버튼 클릭 유무에 관계된 부분
                            MainActivity.startQuestBtn5.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });
                            break;
                    }

                    Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                    outIntent.putExtra("ReturnBtnNum", btnNum);
                    setResult(2, outIntent);
                    finish();

                    setUserLevel();

                } else {
                    Toast.makeText(getApplicationContext(), "완료 인증용 글을 입력하세요", LENGTH_SHORT).show();
                }

            }

        });

        // MainActivity로 돌아가기 버튼
        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }


    /* 이미지 불러오기 코드 (시작점) */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);    // img 변수에 이미지가 저장되는 것 같음.
                    in.close();

                    // 이미지 표시
                    imageView.setImageBitmap(img);

                    // 이미지 저장하기
                    Date now = new Date();
                    SimpleDateFormat sFormat1;  // 날짜
                    sFormat1 = new SimpleDateFormat("yyyyMMdd");
                    imgFileName = (sFormat1.format(now)) + "_" + MainActivity.fileNumber + ".png";

                    try {
                        File file = new File(imgFileName);
                        FileOutputStream fos = openFileOutput(imgFileName, 0);
                        img.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();

                    } catch (Exception e) {
                        Toast.makeText(this, "file error", LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /* 이미지 불러오기 코드 (종료점) */


    /**
     * 유저 레벨 출력 함수
     **/

    public int setUserLevel() {
        MainActivity.questAchieve += 1;
        if (MainActivity.questAchieve % 5 == 0) {  /*완료한 누적 퀘스트 갯수가 5의 배수 일때마다 1레벨 up
                                        즉 완료된 누적 퀘스트 개수 5 -> 레벨 2 됨
                                        완료된 누적 퀘스트 개수 10 -> 레벨 3 됨 ....*/
            MainActivity.userLevel += 1;
            Toast.makeText(this, MainActivity.userNickname + "님의 레벨이 올랐어요!" + "\n" + "리워드를 확인해주세요.", Toast.LENGTH_LONG).show();
        }
        return MainActivity.userLevel;
    }

    ////// 인증 내용 파일에 쓰는 함수 ////// 0604추가

    public void wirteToFile(String fname, String contents) {

        try {
            MainActivity.fileNumber++;
            FileOutputStream outFs = openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outFs);  //0604 추가
            writer.write(contents);
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(),
                     "저장 완료!", LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "FileNotFoundException", LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "IOException", LENGTH_SHORT).show();
        }

    }


}