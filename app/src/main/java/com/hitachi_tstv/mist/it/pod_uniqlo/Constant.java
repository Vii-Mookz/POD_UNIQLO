package com.hitachi_tstv.mist.it.pod_uniqlo;

/**
 * Created by Vipavee on 24/01/2018.
 */

public class Constant {
    public static final String serverString = "http://203.154.103.39/";
    public static final String projectString = "mmth";
    public static final String pathString = "/app/CenterService/";
    public static final String urlGetUserLogin = serverString + projectString + pathString + "getUserLogin.php";
    public static final String urlGetPlanDate = serverString + projectString + pathString + "getPlanDate.php";
    public static final String urlGetTrip = serverString + projectString + pathString + "getTrip.php";
    public static final String urlGetUpdateDCStart = serverString + projectString + pathString + "UpdateDCStart.php";
    public static final String urlGetPlanTrip = serverString + projectString + pathString + "getPlanTrip.php";
    public static final String urlUpdateArrival = serverString + projectString + pathString + "updateArrival.php";
    public static final String urlUpdateDCFinish = serverString + projectString + pathString + "updateDCFinish.php";
    public static final String urlGetTripDetailPickup = serverString + projectString + pathString + "getTripDetailPickup.php";
    public static final String urlUpdateDeparture = serverString + projectString + pathString + "updateDeparture.php";
    public static final String urlGetTripDetailDelivery = serverString + projectString + pathString + "getTripDetailDelivery.php";
    public static final String urlSavePicture = serverString + projectString + pathString + "savePicturePath.php";
    public static final String urlUploadPicture = serverString + projectString + pathString + "uploadPicture.php";
    public static final String[] getColumnLogin = new String[]{"UserId","UserName"};
    public static final int getColumnLoginSize = getColumnLogin.length;
}
