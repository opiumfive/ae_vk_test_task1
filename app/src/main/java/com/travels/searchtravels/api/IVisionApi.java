package com.travels.searchtravels.api;

import android.graphics.Bitmap;

public interface IVisionApi {
    void findLocation(Bitmap bitmap, String token, OnVisionApiListener onVisionApiListener);
}
