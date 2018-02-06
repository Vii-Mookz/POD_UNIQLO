package com.hitachi_tstv.mist.it.pod_uniqlo;

/**
 * Created by Vipavee on 24/01/2018.
 */

public interface Constant {

    String projectString = "DMS_NK";
    String serverString = "http://203.154.103.43/";
    //        public static final String serverString = "http://service.eternity.co.th/";
    String urlGetUser = serverString + projectString + "/app/centerservice/serviceuniqlo/getuser.php";
    String urlGetJobList = serverString + projectString + "/app/centerservice/ServiceUniqlo/getJobList.php";
    String urlGetJobListDate = serverString + projectString + "/app/centerservice/ServiceUniqlo/getListJobDate.php";
    String urlGetJob = serverString + projectString + "/app/centerservice/ServiceUniqlo/getJob.php";
    String urlGetJobDetail = serverString + projectString + "/app/centerservice/ServiceUniqlo/getJobDetail.php";
    String[] LoginFieldName = {"pUser"};
    int LoginFieldNumber = LoginFieldName.length;

}
