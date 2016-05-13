package com.loongme.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Share {
	public void shareMsg(String activityTitle, String msgTitle, String msgText,  
            String imgPath,Context context) {  
        Intent intent = new Intent(Intent.ACTION_SEND);  
        if (imgPath == null || imgPath.equals("")) {  
            intent.setType("text/plain"); // 纯文本  
        } else {  
            File f = new File(imgPath);  
            if (f != null && f.exists() && f.isFile()) {  
                intent.setType("image/jpg");  
                Uri u = Uri.fromFile(f);  
                intent.putExtra(Intent.EXTRA_STREAM, u);  
            }  
        }  
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);  
        intent.putExtra(Intent.EXTRA_TEXT, msgText);  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        context.startActivity(Intent.createChooser(intent, activityTitle));  
    }  
}
