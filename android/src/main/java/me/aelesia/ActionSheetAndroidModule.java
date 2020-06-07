package me.aelesia;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import me.aelesia.R;

import java.util.ArrayList;
import java.util.List;

public class ActionSheetAndroidModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private isShowingDialog = false

    public ActionSheetAndroidModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ActionSheetAndroid";
    }

    @ReactMethod
    public void options(String title, String message, String cancel, ReadableArray option, int destructiveIndex, String tintColor, Promise promise) {
        if (isShowingDialog == true) {
            List<Object> messageStrList = option.toArrayList();
            List<String> strList = new ArrayList<String>();

            for (Object msg: messageStrList) {
                if (msg instanceof String) {
                    strList.add((String) msg);
                }
            }


            BottomSheetDialog dialog = new BottomSheetDialog(getCurrentActivity(), R.style.BottomSheetDialog);
            dialog.setContentView(R.layout.actionsheet);

            if (title != null || message != null) {
                LinearLayout header = dialog.findViewById(R.id.actionsheet_header);
                header.setPadding(0, 24, 0, 24);
                if (title != null) {
                    TextView titleText = dialog.findViewById(R.id.actionsheet_title);
                    titleText.setText(title);
                    titleText.setVisibility(View.VISIBLE);
                }

                if (message != null) {
                    TextView messageText = dialog.findViewById(R.id.actionsheet_message);
                    messageText.setText(message);
                    messageText.setPadding(0, 12, 0, 0);
                    messageText.setVisibility(View.VISIBLE);
                }
            }


            dialog.setOnCancelListener(dialog1 -> {
                promise.resolve(-1);
            });

            ListView listView = dialog.findViewById(R.id.actionsheet_list);
            ActionSheetListAdapter adapter = new ActionSheetListAdapter(reactContext, strList, destructiveIndex, tintColor, position -> {
                dialog.dismiss();
                isShowingDialog = false
                promise.resolve(position);
            });
            listView.setAdapter(adapter);

            if (cancel != null) {
                TextView cancelText = dialog.findViewById(R.id.actionsheet_cancel);
                try {
                    cancelText.setTextColor(Color.parseColor(tintColor));
                } catch(Exception e) {
                    cancelText.setTextColor(Color.parseColor("#222222"));
                }
                cancelText.setText(cancel);
                cancelText.setVisibility(View.VISIBLE);
                LinearLayout cancelView = (LinearLayout) dialog.findViewById(R.id.actionsheet_cancel_view);
                cancelView.setOnClickListener(v -> {
                    dialog.dismiss();
                    isShowingDialog = false
                    promise.resolve(-1);
                });
            }

            isShowingDialog = true
            dialog.show();
        }
    }
}
