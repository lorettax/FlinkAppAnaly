package com.lorettax.analy.sinks;

import com.alibaba.fastjson.JSONObject;
import com.lorettax.analy.Mockcommon.BrandUser;
import com.lorettax.analy.dao.BranduserDao;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BrandSink implements SinkFunction<BrandUser> {
    @Override
    public void invoke(BrandUser value, Context context) throws Exception {
        Long cnts = value.getCnts();
        String timevalue = value.getTimevalue();
        String appPlatform = value.getAppPlatform();
        String appChannel = value.getAppChannel();
        String appid = value.getAppId();
        Long activeusers = value.getActiveuser();
        Long newusers = value.getNewusers();
        String brand = value.getBrand();

        String tablename = "branduserinfodaily";
        Document doc = BranduserDao.findoneby(tablename, appid,  appChannel,appPlatform,timevalue,brand);
        if(doc == null ){
            doc = new Document();
            doc = Document.parse(JSONObject.toJSONString(value));
            ObjectId objectId = new  ObjectId();
            doc.put("_id",objectId);
        }else {
            Long cntspre = 0l;
            Long newuserpre = 0l;
            Long activeuserpre = 0l;
            try {
                cntspre  = (Long) doc.get("cnts");
                activeuserpre = (Long) doc.get("activeusers");
                newuserpre = (Long) doc.get("newusers");
            }catch (ClassCastException e){
                cntspre = Long.valueOf((Integer)doc.get("cnts"));
                activeuserpre = Long.valueOf((Integer)doc.get("activeusers"));
                newuserpre = Long.valueOf((Integer)doc.get("newusers"));
            }
            doc.put("cnts",cntspre+cnts);
            doc.put("activeusers",activeusers+activeuserpre);
            doc.put("newusers",newusers+newuserpre);
        }
        BranduserDao.saveorupdatemongo(tablename,doc);

    }
}
