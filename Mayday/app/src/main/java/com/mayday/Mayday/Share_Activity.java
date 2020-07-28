package com.mayday.Mayday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.mayday.Mayday.R;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.util.HashMap;
import java.util.Map;

public class Share_Activity extends Activity {

    int btnNum; // 어떤 버튼이 눌렸는가?
    String description;  // 출력될 내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.share_activity);

        Intent intent = getIntent();
        btnNum = intent.getIntExtra("BtnNum", 0);

        switch (btnNum)
        {
            case 1:
                description = MainActivity.qStr1;
                break;
            case 2:
                description = MainActivity.qStr2;
                break;
            case 3:
                description = MainActivity.qStr3;
                break;
            case 4:
                description = MainActivity.qStr4;
                break;
            case 5:
                description = MainActivity.qStr5;
                break;

            default:
                description = "함께 하면 재미는 두배!";
                break;
        }
    }
    public void btnClick(View view) {

        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("[MayDay]친구가 퀘스트를 공유했어요!",
                        "https://",
                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption(description)
                        .build())
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();
        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");


        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
            }
        });

    }
}