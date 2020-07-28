package com.mayday.Mayday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.mayday.Mayday.R;

public class SelectActivity extends Activity {

    /* 앱 상단 툴 바 (1) */
    android.widget.Toolbar mainToolbar;

    /* "퀘스트 개수 정하기" 구현 */
    int finalQnum = 0 ;      // 확정된 퀘스트 숫자 저장하기 (입력된 값이 없으면 '0')

    /* "퀘스트 종류 정하기" 구현 */
    Button randomCatBtn, indoorCatBtn, outdoorCatBtn, dailyCatBtn, challengeCatBtn, writeCatBtn;

    /* 직접 입력 퀘스트 */
    String[] myQuests = new String[5];
    int myQuestCounter = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        setTitle("퀘스트 개수와 종류 선택하기");

        /* 앱 상단 툴 바 (2) */
        mainToolbar =  (android.widget.Toolbar) findViewById(R.id.toolBar);
        setActionBar(mainToolbar);

        /* 뷰플리퍼 구현 (코드 시작) */
        Button btnNext;
        final ViewFlipper vFlipper;

        btnNext = (Button) findViewById(R.id.btnNext);

        vFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vFlipper.showNext();
            }
        });
        /* 뷰플리퍼 구현 (코드 끝) */

        ////////////////* NumberPicker을 통한 퀘스트 개수 설정 시작 *////////////////
        NumberPicker numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        NumberPicker picker1 = (NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setWrapSelectorWheel(false);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldval, int newval) {
                finalQnum = newval;     // 넘버피커 사용시 정해진 값이 newval에 해당한다.
            }
        });

        if (finalQnum == 0) {   // 나머지 숫자는 제대로 값을 입력받지만 1인 경우에는 0이 입력되기 때문에
            finalQnum = 1;
        }

        ////////////////* NumberPicker을 통한 퀘스트 개수 설정 끝 *////////////////

        /** 여기서 부터 "퀘스트 종류 정하기" 화면에 관한 코드 작성
         *
         */
        randomCatBtn = (Button) findViewById(R.id.RandomCatBtn);        // 랜덤 (4)
        indoorCatBtn = (Button) findViewById(R.id.IndoorCatBtn);        // 인도어 활동 (0)
        outdoorCatBtn = (Button) findViewById(R.id.OutdoorCatBtn);      // 아웃도어 활동 (1)
        dailyCatBtn = (Button) findViewById(R.id.DailyCatBtn);          // 일상적인 (2)
        challengeCatBtn = (Button) findViewById(R.id.ChallengeCatBtn);  // 도전적인 (3)
        writeCatBtn = (Button) findViewById(R.id.WriteCatBtn);          // 직접입력 (5)

        randomCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* MainActivity에 퀘스트 개수 값을 저장하기 위하여 */
                Intent outIntnet = new Intent(getApplicationContext(), MainActivity.class);
                outIntnet.putExtra("QNUM", finalQnum);
                outIntnet.putExtra("CATNUM", 4);
                setResult(RESULT_OK, outIntnet);

                ResetList();
                ResetBtn(); // 종전에 '+'버튼을 눌렀을때 하던 초기화 기능을 수행하는 역할
                Toast.makeText(getApplicationContext(), "랜덤 " + finalQnum + "개 선택", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        indoorCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* MainActivity에 퀘스트 개수 값을 저장하기 위하여 */
                Intent outIntnet = new Intent(getApplicationContext(), MainActivity.class);
                outIntnet.putExtra("QNUM", finalQnum);
                outIntnet.putExtra("CATNUM", 0);
                setResult(RESULT_OK, outIntnet);

                ResetList();
                ResetBtn();// 종전에 '+'버튼을 눌렀을때 하던 초기화 기능을 수행하는 역할
                Toast.makeText(getApplicationContext(), "인도어 활동 " + finalQnum + "개 선택", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        outdoorCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* MainActivity에 퀘스트 개수 값을 저장하기 위하여 */
                Intent outIntnet = new Intent(getApplicationContext(), MainActivity.class);
                outIntnet.putExtra("QNUM", finalQnum);
                outIntnet.putExtra("CATNUM", 1);
                setResult(RESULT_OK, outIntnet);

                ResetList();
                ResetBtn();// 종전에 '+'버튼을 눌렀을때 하던 초기화 기능을 수행하는 역할
                Toast.makeText(getApplicationContext(), "아웃도어 활동 " + finalQnum + "개 선택", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        dailyCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* MainActivity에 퀘스트 개수 값을 저장하기 위하여 */
                Intent outIntnet = new Intent(getApplicationContext(), MainActivity.class);
                outIntnet.putExtra("QNUM", finalQnum);
                outIntnet.putExtra("CATNUM", 2);
                setResult(RESULT_OK, outIntnet);

                ResetList();
                ResetBtn();// 종전에 '+'버튼을 눌렀을때 하던 초기화 기능을 수행하는 역할
                Toast.makeText(getApplicationContext(), "일상적인 " + finalQnum + "개 선택", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        challengeCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* MainActivity에 퀘스트 개수 값을 저장하기 위하여 */
                Intent outIntnet = new Intent(getApplicationContext(), MainActivity.class);
                outIntnet.putExtra("QNUM", finalQnum);
                outIntnet.putExtra("CATNUM", 3);
                setResult(RESULT_OK, outIntnet);

                ResetList();
                ResetBtn(); // 종전에 '+'버튼을 눌렀을때 하던 초기화 기능을 수행하는 역할
                Toast.makeText(getApplicationContext(), "도전적인 " + finalQnum + "개 선택", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


        for (int i=0; i<5; i++) // 직접 입력 퀘스트 넣을 배열 초기화
        {
            myQuests[i] ="";
        }

        writeCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), WriteQuestActivity.class);
                intent.putExtra("TOTAL_NUM", finalQnum);
                startActivityForResult(intent, 0);

            }
        });
    }

    ////0607 reset 함수 두 개로 분리 수정////

    public static void ResetList() {
        /* 기존에 존재하는 퀘스트 목록 초기화 */
        MainActivity.startQuestBtn1.setText("");
        MainActivity.qStr1 = "";
        MainActivity.startQuestBtn2.setText("");
        MainActivity.qStr2 = "";
        MainActivity.startQuestBtn3.setText("");
        MainActivity.qStr3 = "";
        MainActivity.startQuestBtn4.setText("");
        MainActivity.qStr4 = "";
        MainActivity.startQuestBtn5.setText("");
        MainActivity.qStr5 = "";
    }
    public static void ResetBtn()
    {
        MainActivity.startQuestBtn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        MainActivity.startQuestBtn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        MainActivity.startQuestBtn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        MainActivity.startQuestBtn4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        MainActivity.startQuestBtn5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        /* 앱 종료시 버튼 클릭 유무에 관계된 부분 */
        MainActivity.startQuestBool1 = false;
        MainActivity.startQuestBool2 = false;
        MainActivity.startQuestBool3 = false;
        MainActivity.startQuestBool4 = false;
        MainActivity.startQuestBool5 = false;
    }

    public static void BtnOff() {

            MainActivity.startQuestBtn1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            MainActivity.startQuestBtn2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            MainActivity.startQuestBtn3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            MainActivity.startQuestBtn4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            MainActivity.startQuestBtn5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            /* 앱 종료시 버튼 클릭 유무에 관계된 부분 */
            MainActivity.startQuestBool1 = true;
            MainActivity.startQuestBool2 = true;
            MainActivity.startQuestBool3 = true;
            MainActivity.startQuestBool4 = true;
            MainActivity.startQuestBool5 = true;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_CANCELED)
        {
            // 퀘스트 개수가 인텐트로 제대로 전달되지 않으면 아무 반응을 하지 않는다.
        }
        if(resultCode == RESULT_OK)
        {
            if (finalQnum > 0)
                myQuests[0] = data.getStringExtra("TEXT1");
            if (finalQnum > 1)
                myQuests[1] = data.getStringExtra("TEXT2");
            if (finalQnum > 2)
                myQuests[2] = data.getStringExtra("TEXT3");
            if (finalQnum > 3)
                myQuests[3] = data.getStringExtra("TEXT4");
            if (finalQnum > 4)
                myQuests[4] = data.getStringExtra("TEXT5");

            /* MainActivity에 퀘스트 개수 값을 저장하기 위하여 */
            Intent outIntnet = new Intent(getApplicationContext(), MainActivity.class);
            outIntnet.putExtra("QNUM", finalQnum);
            outIntnet.putExtra("CATNUM", 5);

            outIntnet.putExtra("MYQUEST1", myQuests[0]);
            outIntnet.putExtra("MYQUEST2", myQuests[1]);
            outIntnet.putExtra("MYQUEST3", myQuests[2]);
            outIntnet.putExtra("MYQUEST4", myQuests[3]);
            outIntnet.putExtra("MYQUEST5", myQuests[4]);

            setResult(RESULT_OK, outIntnet);

            ResetList();
            ResetBtn(); // 종전에 '+'버튼을 눌렀을때 하던 초기화 기능을 수행하는 역할
            finish();
        }
    }
}
