package com.lazylibs.tfw.tel;

import android.net.Uri;
import android.os.Build;
import android.telecom.Call;
import android.telecom.CallScreeningService;
import android.telecom.Connection;


public class ScreeningService extends CallScreeningService {

    @Override
    public void onScreenCall(Call.Details callDetails) {
        String phoneNumber = callDetails.getHandle().getSchemeSpecificPart();
        System.out.println("NewCall:" + phoneNumber);

        if (callDetails.getCallDirection() == Call.Details.DIRECTION_INCOMING) {

            CallResponse.Builder builder = new CallScreeningService.CallResponse.Builder();
            builder.setRejectCall(true).setDisallowCall(true).setSilenceCall(false).setSkipCallLog(false);
            respondToCall(callDetails, builder.build());

            Uri handle = callDetails.getHandle();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                switch (callDetails.getCallerNumberVerificationStatus()) {
                    case Connection.VERIFICATION_STATUS_FAILED:
                        // Network verification failed, likely an invalid/spam call.


                        break;
                    case Connection.VERIFICATION_STATUS_PASSED:
                        // Network verification passed, likely a valid call.
                        break;
                    default:
                        // Network could not perform verification.
                        // This branch matches Connection.VERIFICATION_STATUS_NOT_VERIFIED
                }
            }

        }


    }

}