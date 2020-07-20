package com.omidipoor.cla.database;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.omidipoor.cla.database.fence.Fence;
import com.omidipoor.cla.database.participate.Participate;
import com.omidipoor.cla.database.user.User;

import java.util.Calendar;
import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    // this methods used for adding pre define data to db
    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static Fence addFence(final AppDatabase db, Fence fence) {
        db.fenceDao().insertAll(fence);
        return fence;
    }

    private static Participate addParticipate(final AppDatabase db, Participate participate) {
        db.participateDao().insertAll(participate);
        return participate;
    }

    private static void populateWithTestData(AppDatabase db) {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();

        user1.setFirstName("first_name1");
        user2.setFirstName("first_name2");
        user3.setFirstName("first_name3");
        user4.setFirstName("first_name4");
        user5.setFirstName("first_name5");

        user1.setLastName("last_name1");
        user2.setLastName("last_name2");
        user3.setLastName("last_name3");
        user4.setLastName("last_name4");
        user5.setLastName("last_name5");

        user1.setAge(20);
        user2.setAge(21);
        user3.setAge(31);
        user4.setAge(41);
        user5.setAge(51);

        user1.setLastLong(51.387519836425774);
        user2.setLastLong(51.392197608947754);
        user3.setLastLong(51.39309883117675);
        user4.setLastLong(51.391167640686035);
        user5.setLastLong(51.39884948730469);

        user1.setLastLat(35.72554153563889);
        user2.setLastLat(35.7269699495863);
        user3.setLastLat(35.72160455486483);
        user4.setLastLat(35.734390322042174);
        user5.setLastLat(35.72979186282795);

        user1.setImage_url("https://p.kindpng.com/picc/s/78-785904_block-chamber-of-commerce-avatar-white-avatar-icon.png");
        user2.setImage_url("https://p.kindpng.com/picc/s/78-785904_block-chamber-of-commerce-avatar-white-avatar-icon.png");
        user3.setImage_url("https://p.kindpng.com/picc/s/78-785904_block-chamber-of-commerce-avatar-white-avatar-icon.png");
        user4.setImage_url("https://p.kindpng.com/picc/s/78-785904_block-chamber-of-commerce-avatar-white-avatar-icon.png");
        user5.setImage_url("https://p.kindpng.com/picc/s/78-785904_block-chamber-of-commerce-avatar-white-avatar-icon.png");

        addUser(db, user1);
        addUser(db, user2);
        addUser(db, user3);
        addUser(db, user4);
        addUser(db, user5);


        Fence fence1 = new Fence();
        Fence fence2 = new Fence();
        Fence fence3 = new Fence();

        String polygonGeometry1 = "{\"type\":\"Polygon\",\"coordinates\":[[[51.39472961425781,35.72456601871174],[51.39378547668457,35.73000088946137],[51.400909423828125,35.73794349503082],[51.40159606933594,35.74205383068037],[51.40151023864746,35.74282014097602],[51.394901275634766,35.74086951844211],[51.386919021606445,35.743238125301026],[51.38400077819824,35.74184483568319],[51.38357162475585,35.72832866103417],[51.3819408416748,35.72519313810766],[51.38923645019531,35.723311765109536],[51.39472961425781,35.72456601871174]]]}";
        String polygonGeometry2 = "{\"type\":\"Polygon\",\"coordinates\":[[[51.40048027038574,35.72202265055218],[51.39927864074707,35.72348596817958],[51.39799118041992,35.72453117860056],[51.39605998992919,35.72446149833246],[51.39344215393066,35.723242083774856],[51.38872146606445,35.722893676187226],[51.38897895812988,35.719688254891416],[51.392154693603516,35.719932150175005],[51.40048027038574,35.72202265055218]]]}";
        String polygonGeometry3 = "{\"type\":\"Polygon\",\"coordinates\":[[[51.40022277832031,35.73372843896833],[51.39979362487793,35.73341491348694],[51.397690773010254,35.73031442838294],[51.395888328552246,35.728642206538495],[51.397433280944824,35.728293822568624],[51.39970779418945,35.73007056488393],[51.40206813812256,35.73365876674578],[51.40022277832031,35.73372843896833]]]}";

        String criteriaObject1 = "{\"c1\":\"\",\"c2\":\"\",\"c3\":\"\",\"c4\":\"\",\"c5\":\"\"}";
        String criteriaObject2 = "{\"c1\":\"\",\"c2\":\"\",\"c3\":\"\",\"c4\":\"\",\"c5\":\"\"}";
        String criteriaObject3 = "{\"c1\":\"\",\"c2\":\"\",\"c3\":\"\",\"c4\":\"\",\"c5\":\"\"}";

        fence1.setFenceTitle("Parking site selection");
        fence2.setFenceTitle("Green site selection");
        fence3.setFenceTitle("Gas station site selection");

        fence1.setPolygonGeometry(polygonGeometry1);
        fence2.setPolygonGeometry(polygonGeometry2);
        fence3.setPolygonGeometry(polygonGeometry3);

        fence1.setCriteriaObject(criteriaObject1);
        fence2.setCriteriaObject(criteriaObject2);
        fence3.setCriteriaObject(criteriaObject3);

        addFence(db, fence1);
        addFence(db, fence2);
        addFence(db, fence3);



        Participate participate1 = new Participate();
        Participate participate2 = new Participate();
        Participate participate3 = new Participate();

        participate1.setCreatedAt(Calendar.getInstance().getTime());
        participate1.setModifiedAt(Calendar.getInstance().getTime());
        participate1.setFenceId(fence1.fenceId);
        participate1.setUserOwnerId(user1.getUserId());

        participate2.setCreatedAt(Calendar.getInstance().getTime());
        participate2.setModifiedAt(Calendar.getInstance().getTime());
        participate2.setFenceId(fence2.fenceId);
        participate2.setUserOwnerId(user2.getUserId());

        participate3.setCreatedAt(Calendar.getInstance().getTime());
        participate3.setModifiedAt(Calendar.getInstance().getTime());
        participate3.setFenceId(fence3.fenceId);
        participate3.setUserOwnerId(user3.getUserId());

        addParticipate(db, participate1);
        addParticipate(db, participate2);
        addParticipate(db, participate3);

        //List<User> userList = db.userDao().getAll();
        //Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());

    }



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
