package com.mayday.Mayday;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.mayday.Mayday.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    /*캘린더용 변수 선언*/
    CompactCalendarView compactCalendarView;
    SimpleDateFormat simpleMonthFormat = new SimpleDateFormat("yyyy 년 MM 월", Locale.KOREA);
    public static SimpleDateFormat matchFilenNameFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);     // 완료된 퀘스트가 저장된 파일의 앞 부분에 해당하는 포맷
    TextView ymdate;
    Date selectedDate;

    /* addBtnQuest용 fab 선언 */
    FloatingActionButton fab;

    /* 앱 상단 툴 바 (1) */
    android.widget.Toolbar mainToolbar;

    /* "퀘스트" 화면 관련*/
    int qCounter = 0;       // 출력할 퀘스트의 개수를 저장
    int cSelector;      //  카테고리의 종류를 저장

    /*유저 닉네임 설정 관련*/
    View dialogView;
    EditText dlgEditNickname;

    /*리워드 탭 관련*/
    TextView rwdText;      // level 1 일때 리워드 탭 텍스트 표시

    /*유저에 관한 변수*/
    public static String userNickname = "수룡";     // 유저 닉네임 변수
    public static int userLevel = 0;          //유저 레벨 변수 (시작시 1, 최대 50)
    public static int questAchieve = 0;       //유저가 달성한 퀘스트 개수


    /* 퀘스트 출력 버튼에 관한 변수 */
    public static Button startQuestBtn1, startQuestBtn2, startQuestBtn3, startQuestBtn4, startQuestBtn5;

    /* 퀘스트 출력 버튼 클릭 유무 상태 저장 */
    public static boolean startQuestBool1, startQuestBool2, startQuestBool3, startQuestBool4, startQuestBool5;


    /* 앱 종료시 퀘스트 목록 저장에 관한 변수 */
    public static String qStr1, qStr2, qStr3, qStr4, qStr5;

    /* 직접 입력 퀘스트 */
    String[] myQuests = new String[5];

    /* 완료된 퀘스트 텍스트 저장과 출력 */
    Date now = new Date();
    SimpleDateFormat sFormat;
    String todayDate;
    public static int fileNumber = 0;
    TextView openFile1, openFile2, openFile3, openFile4, openFile5, openFile6, openFile7, openFile8, openFile9, openFile10;
    TextView[] openFiles = new TextView[10];
    ImageView openImage1, openImage2, openImage3, openImage4, openImage5, openImage6, openImage7, openImage8, openImage9, openImage10;
    ImageView[] openImages = new ImageView[10];

    /* 데이터 베이스 추가 */
    public static final String ROOT_DIR = "/data/data/com.mayday.Mayday/";   // 파일 저장위치 변경 시 바꿔야함.
    public SQLiteDatabase db;
    categoryDBHelper myHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Mayday");                                         // 앱 이름 정해지면 수정

        /*
         *캘린더 테스트용 (0525)
         */
        selectedDate = new Date();


        ymdate = (TextView)findViewById(R.id.year_month);

        openFile1 =  (TextView)findViewById(R.id.openFile1);    // 저장된 파일의 내용이 출력될 TextView
        openFile2 =  (TextView)findViewById(R.id.openFile2);    // 저장된 파일의 내용이 출력될 TextView
        openFile3 =  (TextView)findViewById(R.id.openFile3);    // 저장된 파일의 내용이 출력될 TextView
        openFile4 =  (TextView)findViewById(R.id.openFile4);    // 저장된 파일의 내용이 출력될 TextView
        openFile5 =  (TextView)findViewById(R.id.openFile5);    // 저장된 파일의 내용이 출력될 TextView
        openFile6 =  (TextView)findViewById(R.id.openFile6);    // 저장된 파일의 내용이 출력될 TextView
        openFile7 =  (TextView)findViewById(R.id.openFile7);    // 저장된 파일의 내용이 출력될 TextView
        openFile8 =  (TextView)findViewById(R.id.openFile8);    // 저장된 파일의 내용이 출력될 TextView
        openFile9 =  (TextView)findViewById(R.id.openFile9);    // 저장된 파일의 내용이 출력될 TextView
        openFile10 =  (TextView)findViewById(R.id.openFile10);    // 저장된 파일의 내용이 출력될 TextView

        openFiles[0] = openFile1;
        openFiles[1] = openFile2;
        openFiles[2] = openFile3;
        openFiles[3] = openFile4;
        openFiles[4] = openFile5;
        openFiles[5] = openFile6;
        openFiles[6] = openFile7;
        openFiles[7] = openFile8;
        openFiles[8] = openFile9;
        openFiles[9] = openFile10;

        // 저장된 파일의 내용이 출력될 ImageView
        openImage1 =  (ImageView)findViewById(R.id.openImage1);
        openImage2 =  (ImageView)findViewById(R.id.openImage2);
        openImage3 =  (ImageView)findViewById(R.id.openImage3);
        openImage4 =  (ImageView)findViewById(R.id.openImage4);
        openImage5 =  (ImageView)findViewById(R.id.openImage5);
        openImage6 =  (ImageView)findViewById(R.id.openImage6);
        openImage7 =  (ImageView)findViewById(R.id.openImage7);
        openImage8 =  (ImageView)findViewById(R.id.openImage8);
        openImage9 =  (ImageView)findViewById(R.id.openImage9);
        openImage10 =  (ImageView)findViewById(R.id.openImage10);

        openImages[0] = openImage1;
        openImages[1] = openImage2;
        openImages[2] = openImage3;
        openImages[3] = openImage4;
        openImages[4] = openImage5;
        openImages[5] = openImage6;
        openImages[6] = openImage7;
        openImages[7] = openImage8;
        openImages[8] = openImage9;
        openImages[9] = openImage10;


        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                ymdate.setText(simpleMonthFormat.format(dateClicked));
                selectedDate = dateClicked;

                // 일단 달력의 날짜를 클릭 시 모든 텍스트 뷰를 초기화한 후 새로 값을 넣어준다.
                // 초기화 부분
                for (int i=0; i<10; i++)    // 하루에 존재하는 파일을 총 10개
                {
                    openFiles[i].setText("");
                }
                // 새로 값을 넣어주는 부분
                String diaryStr = null;
                FileInputStream inFs;
                for (int i=0; i<10; i++)    // 하루에 존재하는 파일을 총 10개
                {
                    try {
                        inFs = openFileInput(matchFilenNameFormat.format(dateClicked) + "_" + i + ".txt");   // 파일명 "yyyyMMdd_i.txt"
                        byte[] txt = new byte[500];
                        inFs.read(txt);
                        inFs.close();
                        diaryStr = (new String(txt)).trim();
                        openFiles[i].setText(diaryStr);    // 해당하는 파일명의 내용을 textview에 출력
                    } catch (IOException e) {

                    }
                }

                // 해당 이미지가 없으면 설정이 안된다.
                for (int i=0; i<10; i++)    // 하루에 존재하는 파일을 총 10개
                {
                    try{
                        String imgpath = ROOT_DIR + "files/" + matchFilenNameFormat.format(dateClicked) + "_" + i + ".png";
                        Bitmap bm = BitmapFactory.decodeFile(imgpath);
                        openImages[i].setImageBitmap(bm);
                    }catch(Exception e){Toast.makeText(getApplicationContext(), "load error", Toast.LENGTH_SHORT).show();}
                }

                Log.d("dateclick",dateClicked+"");
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                ymdate.setText(simpleMonthFormat.format(firstDayOfNewMonth));
            }

        });


        /* 앱 상단 툴 바 (2) */
        mainToolbar = (android.widget.Toolbar) findViewById(R.id.toolBar);
        setActionBar(mainToolbar);

        /* 리워드 탭 그리드뷰 선언 */
        final GridView gv = (GridView) findViewById(R.id.gridView);
        final MyGridAdapter gAdapter = new MyGridAdapter(this);

        /* 탭 호스트 구현 (코드 시작) */
        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecMain = tabHost.newTabSpec("MAIN").setIndicator("퀘스트");
        tabSpecMain.setContent(R.id.tabMain);
        tabHost.addTab(tabSpecMain);

        TabHost.TabSpec tabSpecFinish = tabHost.newTabSpec("FINISH").setIndicator("완료된\n퀘스트");
        tabSpecFinish.setContent(R.id.tabFinish);
        tabHost.addTab(tabSpecFinish);

        TabHost.TabSpec tabSpecReward = tabHost.newTabSpec("REWARD").setIndicator("리워드");
        tabSpecReward.setContent(R.id.tabReward);
        tabHost.addTab(tabSpecReward);

        tabHost.setCurrentTab(0);
        /* 탭 호스트 구현 (코드 끝) */

        /* 탭 호스트 눌렀을 때 동작 구현 (시작점) */
        getTabHost().setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                int i = getTabHost().getCurrentTab();
                Log.i("MAIN", "------" + i);

                if (i == 0) {
                    Log.i("MAIN", "onClick tab");
                    // MAIN 탭 클릭 시 발생할 이벤트 구현
                }
                else if (i ==1) {
                    Log.i("FINISH", "onClick tab");
                    // FINISH 탭 클릭 시 발생할 이벤트 구현
                }
                else if (i == 2) {
                    Log.i("REWARD", "onClick tab");
                    // REWARD 탭 클릭 시 발생할 이벤트 구현
                    RewardText();   // 리워드 불러올 때 rwdText.setVisibility 설정하는 함수
                    gv.setAdapter(gAdapter);
                }

            }
        });
        /* 탭 호스트 눌렀을 때 동작 구현 (종료점) */

        /* 데이터 베이스 추가 */
        setDB(this);
        myHelper = new categoryDBHelper(this);
        db = myHelper.getWritableDatabase();

        /* 퀘스트 목록 버튼 */
        startQuestBtn1 = (Button) findViewById(R.id.startQuestBtn1);
        startQuestBtn2 = (Button) findViewById(R.id.startQuestBtn2);
        startQuestBtn3 = (Button) findViewById(R.id.startQuestBtn3);
        startQuestBtn4 = (Button) findViewById(R.id.startQuestBtn4);
        startQuestBtn5 = (Button) findViewById(R.id.startQuestBtn5);

        sFormat = new SimpleDateFormat("yyyyMMdd");
        todayDate = sFormat.format(now); // 오늘 날짜를 받아서 저장한다.

        /** 저장된 정보 불러오기 (시작점) **/

        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);

        //text(숫자)라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환

        String text1 = sf.getString("text1","");
        qStr1 = text1;
        startQuestBtn1.setText(text1);

        String text2 = sf.getString("text2","");
        qStr2 = text2;
        startQuestBtn2.setText(text2);

        String text3 = sf.getString("text3","");
        qStr3 = text3;
        startQuestBtn3.setText(text3);

        String text4 = sf.getString("text4","");
        qStr4 = text4;
        startQuestBtn4.setText(text4);

        String text5 = sf.getString("text5","");
        qStr5 = text5;
        startQuestBtn5.setText(text5);

        int counter = sf.getInt("counter",0);
        qCounter = counter;

        int level = sf.getInt("UserLevel", 1);
        userLevel = level;

        int qAchieve = sf.getInt("QuestAchieve", 0);
        questAchieve = qAchieve;

        String userName = sf.getString("UserNickname", " ");
        userNickname = userName;

        boolean bool1 = sf.getBoolean("StartQuestBool1", false);
        startQuestBool1 = bool1;
        startQuestBtn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return startQuestBool1 ;
            }
        });

        boolean bool2 = sf.getBoolean("StartQuestBool2", false);
        startQuestBool2 = bool2;
        startQuestBtn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return startQuestBool2 ;
            }
        });

        boolean bool3 = sf.getBoolean("StartQuestBool3", false);
        startQuestBool3 = bool3;
        startQuestBtn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return startQuestBool3 ;
            }
        });

        boolean bool4 = sf.getBoolean("StartQuestBool4", false);
        startQuestBool4 = bool4;
        startQuestBtn4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return startQuestBool4 ;
            }
        });

        boolean bool5 = sf.getBoolean("StartQuestBool5", false);
        startQuestBool5 = bool5;
        startQuestBtn5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return startQuestBool5 ;
            }
        });

        int fNum = sf.getInt("fileNumber",0);
        fileNumber = fNum;

        String lastDate = sf.getString("lastDate",""); // 마지막으로 종료시 날짜 불러오기
        if( !lastDate.equals(todayDate) )   // 최종 종료 날짜와 현재 날짜가 다르다면
            fileNumber = 0; // fileNumber을 초기화 시킨다.

        /** 저장된 정보 불러오기 (종료점) **/


        /** 여기서 부터 "퀘스트" 화면에 관한 코드 작성**/


        /*FloatActionButton과 Select Activity를 Intent*/
        /*add 버튼 클릭시 동작 0516 수정*/

        fab = (findViewById(R.id.addQuestBtn));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                /* 값을 가져옴 */
                startActivityForResult(intent, 0);
            }
        });

        /** 여기서 부터 "리워드" 화면에 관한 코드 작성**/

        rwdText = (TextView) findViewById(R.id.rwdText); //level_1일때 리워드 미획득 표시


        /* 코드를 위로 이동시킴 */
        // final GridView gv = (GridView) findViewById(R.id.gridView);
        // final MyGridAdapter gAdapter = new MyGridAdapter(this);

        /* 기존에 이미 작성하신 코드 (시작점) */
        // final 선언을 위하여 선언문을 외부에 선언한거 외에는 동일
        if (userLevel == 1) {
            rwdText.setTextSize(20);
            rwdText.setVisibility(View.VISIBLE);
        }
        else {
            gv.setAdapter(gAdapter);
        }
        /* 기존에 이미 작성하신 코드 (종료점) */

        /* 퀘스트 완료 확정하는 기능 (시작점) 0516 수정*/
        startQuestBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qCounter > 0)
                {
                    Intent intent = new Intent(getApplicationContext(), QuestFinishActivity.class);
                    intent.putExtra("BtnNum", 1);
                    startActivityForResult(intent, 0);
                }
            }
        });
        startQuestBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qCounter > 1)
                {
                    Intent intent = new Intent(getApplicationContext(), QuestFinishActivity.class);
                    intent.putExtra("BtnNum", 2);
                    startActivityForResult(intent, 0);

                }
            }
        });
        startQuestBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qCounter > 2)
                {
                    Intent intent = new Intent(getApplicationContext(), QuestFinishActivity.class);
                    intent.putExtra("BtnNum", 3);
                    startActivityForResult(intent, 0);

                }
            }
        });
        startQuestBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qCounter > 3)
                {
                    Intent intent = new Intent(getApplicationContext(), QuestFinishActivity.class);
                    intent.putExtra("BtnNum", 4);
                    startActivityForResult(intent, 0);

                }
            }
        });
        startQuestBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qCounter > 4)
                {
                    Intent intent = new Intent(getApplicationContext(), QuestFinishActivity.class);
                    intent.putExtra("BtnNum", 5);
                    startActivityForResult(intent, 0);
                }
            }
        });

        startQuestBtn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(qCounter > 0){
                    Intent intent = new Intent(getApplicationContext(),Share_Activity.class);
                    intent.putExtra("BtnNum",1);
                    startActivity(intent);
                }
                return true;
            }
        });

        startQuestBtn2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(qCounter > 1){
                    Intent intent = new Intent(getApplicationContext(),Share_Activity.class);
                    intent.putExtra("BtnNum",2);
                    startActivity(intent);
                }
                return true;
            }
        });

        startQuestBtn3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(qCounter > 2){
                    Intent intent = new Intent(getApplicationContext(),Share_Activity.class);
                    intent.putExtra("BtnNum",3);
                    startActivity(intent);
                }
                return true;
            }
        });

        startQuestBtn4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(qCounter > 3){
                    Intent intent = new Intent(getApplicationContext(),Share_Activity.class);
                    intent.putExtra("BtnNum",4);
                    startActivity(intent);
                }
                return true;
            }
        });

        startQuestBtn5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(qCounter > 4){
                    Intent intent = new Intent(getApplicationContext(),Share_Activity.class);
                    intent.putExtra("BtnNum",5);
                    startActivity(intent);
                }
                return true;
            }
        });



        /* 퀘스트 완료 확정하는 기능 (종료점) */

    }
    //onCreate 끝

    protected void onResume() {
        super.onResume();
        ymdate.setText(simpleMonthFormat.format(compactCalendarView.getFirstDayOfCurrentMonth()));
    }

    /* 리워드 불러오기를 위해 추가한 함수 */
    public void RewardText()
    {
        if (userLevel == 1) {
            rwdText.setTextSize(20);
            rwdText.setVisibility(View.VISIBLE);
        } else
        {
            rwdText.setVisibility(View.GONE);
        }
    }

    /* 데이터 베이스 추가 (시작점) */
    public static void setDB(Context ctx) {
        // check
        File folder = new File(ROOT_DIR + "databases");
        folder.mkdirs();
        File outfile = new File(ROOT_DIR + "databases/" + "categoryDB.db");
        if (outfile.length() <= 0) {
            AssetManager assetManager = ctx.getResources().getAssets();
            try {
                InputStream is = assetManager.open("categoryDB.db", AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte [] tempdata = new byte[(int)filesize];
                is.read(tempdata);
                is.close();

                outfile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class categoryDBHelper extends SQLiteOpenHelper {  //새로 생성한 adapter 속성은 SQLiteOpenHelper이다.
        public categoryDBHelper(Context context) {
            super(context, "categoryDB.db", null, 1);    // db명과 버전만 정의 한다.
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }
    /* 데이터 베이스 추가 (종료점) */




    /**
     * 리워드 그리드뷰 코드
     **/
    public class MyGridAdapter extends BaseAdapter {
        Context context;

        public MyGridAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() { //표시할 리워드 개수 리턴
            return userLevel - 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) { //한개의 리워드 출력
            final ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);

            imageview.setImageResource(rewardID[position]);

            final int pos = position;
            imageview.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    View dialogView = (View) View.inflate(
                            MainActivity.this, R.layout.dialog_reward, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(
                            MainActivity.this);
                    ImageView ivPoster = (ImageView) dialogView
                            .findViewById(R.id.rewardImg);
                    ivPoster.setImageResource(rewardID[pos]);
                    dlg.setTitle(rewardName[pos]); //이걸로 할지 아니면 dialog에 있는 textView로 할건지, 아니면 둘 다 사용할건지 회의해보기.
                    TextView gridText = (TextView) dialogView.findViewById(R.id.gridText);
                    gridText.setText(rewardStory[pos]);

                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageview;
        }

        Integer[] rewardID = {
                R.drawable.rwd2, R.drawable.rwd3, R.drawable.rwd4,
                R.drawable.rwd5, R.drawable.rwd6, R.drawable.rwd7,
                R.drawable.rwd8, R.drawable.rwd9, R.drawable.rwd10,
                R.drawable.rwd11, R.drawable.rwd12, R.drawable.rwd13,
                R.drawable.rwd14, R.drawable.rwd15, R.drawable.rwd16,
                R.drawable.rwd17, R.drawable.rwd18, R.drawable.rwd19,
                R.drawable.rwd20, R.drawable.rwd21, R.drawable.rwd22,
                R.drawable.rwd23, R.drawable.rwd24, R.drawable.rwd25,
                R.drawable.rwd26, R.drawable.rwd27, R.drawable.rwd28,
                R.drawable.rwd29, R.drawable.rwd30, R.drawable.rwd31,
                R.drawable.rwd32, R.drawable.rwd33, R.drawable.rwd34,
                R.drawable.rwd35, R.drawable.rwd36, R.drawable.rwd37,
                R.drawable.rwd38, R.drawable.rwd39, R.drawable.rwd40,
                R.drawable.rwd41, R.drawable.rwd42, R.drawable.rwd43,
                R.drawable.rwd44, R.drawable.rwd45, R.drawable.rwd46, R.drawable.rwd47,
                R.drawable.rwd48, R.drawable.rwd49, R.drawable.rwd50
        };

        String[] rewardName = { //리워드 이름
                "시작의 발자국", "달달한 향수", "셔틀콕", "새콤달콤 파인애플", "보라색 우산", "보물지도",
                "트럼프카드", "기린 친구", "구급상자", "추억의 게임기", "모래시계",
                "만능 열쇠", "기사의 투구", "레벨 15 달성", "고소한 프레첼", "롤러 스케이트",
                "행운의 네잎클로버", "다이아몬드", "축구공", "보라색 데이지", "무알콜 칵테일", "카메라",
                "코끼리 친구", "레벨 25 달성", "잘익은 적포도", "지구본", "따뜻한 커피 한잔", "돼지 저금통",
                "악어 친구", "가랜드", "인싸 썬글라스", "초코 도넛", "럭키 박스", "롤리팝", "예쁜 단풍잎",
                "요리사 모자", "인생의 나침반", "나비넥타이", "레벨 40 달성", "전설의 깃털", "로봇 장난감",
                "메론 소다", "잘 나는 연", "베어먹은 쿠키", "빼곡한 일기장", "쿠키 인형", "해적의 깃발",
                "어디든 무료 티켓", "레벨 50 달성"
        };

        String[] rewardStory = { //리워드 상세내용 넣을 수 있음. 추후 수정 가능
                "첫 레벨업을 축하해요!", "기분 좋아지는 플로랄 계열 향기", "작고 귀여운 장식용 셔틀콕", "보기만 해도 침이 고이는 그 맛", "비오는 날을 기다리게 돼요", "모험심을 자극하는 보물 지도",
                "카드 게임 한 판 어때요?", "다른 기린에 비해 목이 짧대요", "하나쯤 챙겨두면 유용해요", "추억에 젖게 만드는 아날로그 감성", "아무 생각 없이 떨어지는 모래를 보고 있게 돼요",
                "원하는 것은 뭐든지 열어주는 열쇠", "고대 로마 검투사의 것", "벌써 15 레벨!?", "안에는 크림 치즈가 듬뿍", "생각 없이 씽씽 달리기 좋은 스케이트",
                "당신에게 행운이 가득하기를", "몇 캐럿 일까요?", "유명한 축구 선수의 싸인이 적혀있어요", "꽃말은 순수함, 천진난만", "청소년도 함께 즐겨요", "순간을 기록한다는 기능에 충실한 카메라",
                "아시아 코끼리라고 오해 받아 슬픈 아프리카 코끼리", "레벨 25 달성! 축하해요!", "한 알 만 먹어도 달콤함이 가득", "가보고 싶은 나라는 어디인가요?", "커피 한 잔의 여유를 부려봐요", "한푼 두푼 알뜰하게 모아온 저금통",
                "늪지대에는 악어가 살아요", "침대 맡에 장식해보고 싶은 가랜드", "쓰고 나가보고 싶어요!", "혀가 녹아버릴 것 같은 달콤함", "무엇이 들어있으면 좋겠나요?", "깨물어 먹기 아까운 사탕", "책갈피로 만들어 보관하고 싶어져요",
                "고든 램지도 울고갈 요리사가 되는 모자", "가끔은 헤매도 괜찮아요", "어른도 하면 안되나요?", "레벨 40 달성! 축하해요!", "불사조의 깃털일까요? 비둘기의 깃털일까요?", "인공지능이 탑재되어 있는 로봇",
                "톡 쏘는 달콤함", "절대 끊어지지 않는 실", "내 쿠키 누가 먹었어?", "몰래 구경하고 싶어지는 일기장", "금방이라도 달릴 것만 같은 쿠키 인형", "해적왕이 되고싶은 자들이여, 오라!",
                "이것만 있으면 어디든 프리 패스예요", "50 레벨 달성! 멋져요!"
        };
    }


    /* Intent를 통해 카테고리 개수와 종류 설정 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            qCounter = data.getIntExtra("QNUM", 0);
            cSelector = data.getIntExtra("CATNUM", 0);

            if (cSelector == 5) // 퀘스트를 직접 입력한 경우
            {
                myQuests[0] = data.getStringExtra("MYQUEST1");
                myQuests[1] = data.getStringExtra("MYQUEST2");
                myQuests[2] = data.getStringExtra("MYQUEST3");
                myQuests[3] = data.getStringExtra("MYQUEST4");
                myQuests[4] = data.getStringExtra("MYQUEST5");
            }


            /* 퀘스트 출력 구현 (2020.05.13) */
            Cursor cursor = db.rawQuery("SELECT * FROM categoryTBL;", null);

            Random random = new Random();   // 난수 생성
            String questStr;    // 데이터 베이스에서 출력한 값 임시 저장 변수

            /* 인도어 활동, 아웃도어 활동, 일상적인 활동, 도전적인 활동에 관한 퀘스트 출력 설정 (시작점) */
            if (0 <= cSelector && cSelector < 4)
            {
                // 퀘스트 개수가 1개인 경우
                if (qCounter == 1)
                {
                    int i = 0;
                    int randomNum = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                    while (cursor.moveToNext()) {
                        questStr = cursor.getString(cSelector);
                        if (i==randomNum)
                        {
                            qStr1 = questStr;
                            startQuestBtn1.setText(questStr);
                        }
                        i++;
                    }
                }

                // 퀘스트 개수가 2개인 경우
                if (qCounter == 2)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        questStr = cursor.getString(cSelector);
                        if (i==randomNum1)
                        {
                            qStr1 = questStr;
                            startQuestBtn1.setText(questStr);
                        } else if(i==randomNum2)
                        {
                            qStr2 = questStr;
                            startQuestBtn2.setText(questStr);
                        }
                        i++;
                    }
                }

                // 퀘스트 개수가 3개인 경우
                if (qCounter == 3)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    int randomNum3 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum3 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum3 != randomNum1 && randomNum3 != randomNum2) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        questStr = cursor.getString(cSelector);
                        if (i==randomNum1)
                        {
                            qStr1 = questStr;
                            startQuestBtn1.setText(questStr);
                        } else if(i==randomNum2)
                        {
                            qStr2 = questStr;
                            startQuestBtn2.setText(questStr);
                        } else if(i==randomNum3)
                        {
                            qStr3 = questStr;
                            startQuestBtn3.setText(questStr);
                        }
                        i++;
                    }
                }

                // 퀘스트 개수가 4개인 경우
                if (qCounter == 4)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    int randomNum3 = 0;
                    int randomNum4 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum3 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum3 != randomNum1 && randomNum3 != randomNum2) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum4 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum4 != randomNum1 && randomNum4 != randomNum2 && randomNum4 != randomNum3) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        questStr = cursor.getString(cSelector);
                        if (i==randomNum1)
                        {
                            qStr1 = questStr;
                            startQuestBtn1.setText(questStr);
                        } else if(i==randomNum2)
                        {
                            qStr2 = questStr;
                            startQuestBtn2.setText(questStr);
                        } else if(i==randomNum3)
                        {
                            qStr3 = questStr;
                            startQuestBtn3.setText(questStr);
                        } else if(i==randomNum4)
                        {
                            qStr4 = questStr;
                            startQuestBtn4.setText(questStr);
                        }
                        i++;
                    }
                }

                // 퀘스트 개수가 5개인 경우
                if (qCounter == 5)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    int randomNum3 = 0;
                    int randomNum4 = 0;
                    int randomNum5 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum3 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum3 != randomNum1 && randomNum3 != randomNum2) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum4 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum4 != randomNum1 && randomNum4 != randomNum2 && randomNum4 != randomNum3) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum5 = random.nextInt(42); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum5 != randomNum1 && randomNum5 != randomNum2 && randomNum5 != randomNum3 && randomNum5 != randomNum4) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        questStr = cursor.getString(cSelector);
                        if (i==randomNum1)
                        {
                            qStr1 = questStr;
                            startQuestBtn1.setText(questStr);
                        } else if(i==randomNum2)
                        {
                            qStr2 = questStr;
                            startQuestBtn2.setText(questStr);
                        } else if(i==randomNum3)
                        {
                            qStr3 = questStr;
                            startQuestBtn3.setText(questStr);
                        } else if(i==randomNum4)
                        {
                            qStr4 = questStr;
                            startQuestBtn4.setText(questStr);
                        } else if(i==randomNum5)
                        {
                            qStr5 = questStr;
                            startQuestBtn5.setText(questStr);
                        }
                        i++;
                    }
                }
            }
            /* 인도어 활동, 아웃도어 활동, 일상적인 활동, 도전적인 활동에 관한 퀘스트 출력 설정 (종료점) */

            /* 랜덤 퀘스트 출력 설정 (시작점) */
            if (cSelector == 4)
            {
                // 퀘스트 개수가 1개인 경우
                if (qCounter == 1)
                {
                    int i = 0;
                    int randomNum = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                    while (cursor.moveToNext()) {
                        for(int j=0; j<4; j++)
                        {
                            questStr = cursor.getString(j);
                            if (i==randomNum)
                            {
                                qStr1 = questStr;
                                startQuestBtn1.setText(questStr);
                            }
                            i++;
                        }
                    }
                }

                // 퀘스트 개수가 2개인 경우
                if (qCounter == 2)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        for(int j=0; j<4; j++)
                        {
                            questStr = cursor.getString(j);
                            if (i==randomNum1)
                            {
                                qStr1 = questStr;
                                startQuestBtn1.setText(questStr);
                            } else if(i==randomNum2)
                            {
                                qStr2 = questStr;
                                startQuestBtn2.setText(questStr);
                            }
                            i++;
                        }
                    }
                }

                // 퀘스트 개수가 3개인 경우
                if (qCounter == 3)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    int randomNum3 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum3 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum3 != randomNum1 && randomNum3 != randomNum2) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        for(int j=0; j<4; j++)
                        {
                            questStr = cursor.getString(j);
                            if (i==randomNum1)
                            {
                                qStr1 = questStr;
                                startQuestBtn1.setText(questStr);
                            } else if(i==randomNum2)
                            {
                                qStr2 = questStr;
                                startQuestBtn2.setText(questStr);
                            } else if(i==randomNum3)
                            {
                                qStr3 = questStr;
                                startQuestBtn3.setText(questStr);
                            }
                            i++;
                        }
                    }
                }

                // 퀘스트 개수가 4개인 경우
                if (qCounter == 4)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    int randomNum3 = 0;
                    int randomNum4 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum3 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum3 != randomNum1 && randomNum3 != randomNum2) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum4 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum4 != randomNum1 && randomNum4 != randomNum2 && randomNum4 != randomNum3) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        for(int j=0; j<4; j++)
                        {
                            questStr = cursor.getString(j);
                            if (i==randomNum1)
                            {
                                qStr1 = questStr;
                                startQuestBtn1.setText(questStr);
                            } else if(i==randomNum2)
                            {
                                qStr2 = questStr;
                                startQuestBtn2.setText(questStr);
                            } else if(i==randomNum3)
                            {
                                qStr3 = questStr;
                                startQuestBtn3.setText(questStr);
                            } else if(i==randomNum4)
                            {
                                qStr4 = questStr;
                                startQuestBtn4.setText(questStr);
                            }
                            i++;
                        }
                    }
                }

                // 퀘스트 개수가 5개인 경우
                if (qCounter == 5)
                {
                    int i = 0;
                    int randomNum1 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                    int randomNum2 = 0;
                    int randomNum3 = 0;
                    int randomNum4 = 0;
                    int randomNum5 = 0;
                    boolean check = true;
                    while (check)
                    {
                        randomNum2 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum2 != randomNum1) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum3 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum3 != randomNum1 && randomNum3 != randomNum2) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum4 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum4 != randomNum1 && randomNum4 != randomNum2 && randomNum4 != randomNum3) { check = false; } // 중복 방지
                    }
                    check = true;
                    while (check)
                    {
                        randomNum5 = random.nextInt(168); // 출력될 값의 위치값을 난수로 지정
                        if(randomNum5 != randomNum1 && randomNum5 != randomNum2 && randomNum5 != randomNum3 && randomNum5 != randomNum4) { check = false; } // 중복 방지
                    }
                    while (cursor.moveToNext()) {
                        for (int j = 0; j < 4; j++) {
                            questStr = cursor.getString(j);
                            if (i == randomNum1) {
                                qStr1 = questStr;
                                startQuestBtn1.setText(questStr);
                            } else if (i == randomNum2) {
                                qStr2 = questStr;
                                startQuestBtn2.setText(questStr);
                            } else if (i == randomNum3) {
                                qStr3 = questStr;
                                startQuestBtn3.setText(questStr);
                            } else if (i == randomNum4) {
                                qStr4 = questStr;
                                startQuestBtn4.setText(questStr);
                            } else if (i == randomNum5) {
                                qStr5 = questStr;
                                startQuestBtn5.setText(questStr);
                            }
                            i++;
                        }
                    }
                }
            }
            /* 랜덤 퀘스트 출력 설정 (종료점) */

            /* 직접입력 퀘스트 출력 설정 (시작점) */
            if (cSelector == 5)
            {
                if (qCounter > 0)
                {
                    startQuestBtn1.setText(myQuests[0]);
                    qStr1 = myQuests[0];
                }
                if (qCounter > 1)
                {
                    startQuestBtn2.setText(myQuests[1]);
                    qStr2 = myQuests[1];
                }
                if (qCounter > 2)
                {
                    startQuestBtn3.setText(myQuests[2]);
                    qStr3 = myQuests[2];
                }
                if (qCounter > 3)
                {
                    startQuestBtn4.setText(myQuests[3]);
                    qStr4 = myQuests[3];
                }
                if (qCounter > 4)
                {
                    startQuestBtn5.setText(myQuests[4]);
                    qStr5 = myQuests[4];
                }
            }
            /* 직접입력 퀘스트 출력 설정 (종료점) */
        }

        if(resultCode == 2) // QuestFinishActivity 관련 intent 설정 (해당 버튼 내용 삭제하기) 0516 수정
        {
            int returnBtnNum = data.getIntExtra("ReturnBtnNum", 0);
            switch (returnBtnNum)
            {
                case 1:
                    qStr1 = " ";
                    startQuestBtn1.setText("Done!");
                    break;
                case 2:
                    qStr2 = " ";
                    startQuestBtn2.setText("Done!");
                    break;
                case 3:
                    qStr3 = " ";
                    startQuestBtn3.setText("Done!");
                    break;
                case 4:
                    qStr4 = " ";
                    startQuestBtn4.setText("Done!");
                    break;
                case 5:
                    qStr5 = " ";
                    startQuestBtn5.setText("Done!");
                    break;
                default:
                    break;
            }
        }
    }

    /* 메인화면 옵션 메뉴 (step 1) */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    /* 메인화면 옵션 메뉴 (step 2) : 동작 구현 --> 교재 292pg */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showLevel:         // 1. 나의 레벨 확인하기
                AlertDialog.Builder dlgLevel = new AlertDialog.Builder(this);
                dlgLevel.setIcon(R.drawable.ic_user);
                dlgLevel.setTitle("나의 레벨 확인");
                dlgLevel.setMessage(userNickname + " 님의 현재 레벨은 " + userLevel + "입니다.");    // 사용자 레벨 변수 출력
                dlgLevel.setPositiveButton("확인", null);
                dlgLevel.show();
                return true;

            case R.id.ChangeNickname:       // 2. 닉네임 수정하기
                dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog_nickname, null);
                AlertDialog.Builder dlgNickname = new AlertDialog.Builder(this);
                dlgNickname.setIcon(R.drawable.ic_user);
                dlgNickname.setTitle("닉네임 수정");
                dlgNickname.setView(dialogView);

                /*수정 버튼 클릭시 발생하는 동작*/
                dlgNickname.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dlgEditNickname = (EditText) dialogView.findViewById(R.id.dlgEditNickname);
                        userNickname = dlgEditNickname.getText().toString();
                        Toast toast = Toast.makeText(MainActivity.this, userNickname + "(으)로 수정 완료!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 50);
                        toast.show();
                    }
                });
                /*취소 버튼 클릭시 발생하는 동작*/
                dlgNickname.setNegativeButton("취소", null);
                dlgNickname.show();
                return true;

            case R.id.Helper:
                Intent intent = new Intent(getApplicationContext(), HelperActivity.class);
                startActivityForResult(intent, 1);
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() { //액티비티가 여전히 화면에 보이나, 포커스를 잃은 상태. 다른 액티비티가 화면을 가리고 있을 때
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("text1",qStr1); // key, value를 이용하여 저장하는 형태
        editor.putString("text2",qStr2); // key, value를 이용하여 저장하는 형태
        editor.putString("text3",qStr3); // key, value를 이용하여 저장하는 형태
        editor.putString("text4",qStr4); // key, value를 이용하여 저장하는 형태
        editor.putString("text5",qStr5); // key, value를 이용하여 저장하는 형태

        editor.putInt("counter", qCounter); // 출력된 퀘스트 개수 저장

        editor.putInt("UserLevel", userLevel); // 유저 레벨 저장
        editor.putInt("QuestAchieve", questAchieve); // 유저가 달성한 퀘스트 개수 저장
        editor.putString("UserNickname", userNickname); // 유저가 설정한 닉네임 저장


        editor.putBoolean("StartQuestBool1", startQuestBool1);  // 퀘스트 버튼 클릭 유무 저장
        editor.putBoolean("StartQuestBool2", startQuestBool2);  // 퀘스트 버튼 클릭 유무 저장
        editor.putBoolean("StartQuestBool3", startQuestBool3);  // 퀘스트 버튼 클릭 유무 저장
        editor.putBoolean("StartQuestBool4", startQuestBool4);  // 퀘스트 버튼 클릭 유무 저장
        editor.putBoolean("StartQuestBool5", startQuestBool5);  // 퀘스트 버튼 클릭 유무 저장

        editor.putInt("fileNumber", fileNumber); // 완료된 퀘스트의 저장 파일명의 인덱스 번호 저장
        editor.putString("lastDate", todayDate); // 마지막으로 접속한 날짜를 저장한다.

        //다양한 형태의 변수값을 저장할 수 있다.
        //editor.putString();
        //editor.putBoolean();
        //editor.putFloat();
        //editor.putLong();
        //editor.putInt();
        //editor.putStringSet();

        //최종 커밋
        editor.commit();
    }




}