/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.google.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "fireprotect",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.google.com",
    ownerName = "backend.google.com",
    packagePath=""
  )
)
public class MyEndpoint {

    @ApiMethod(name = "storeMesure")
    public void storeMesures (MesureBean mesure) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            Key mesParentKey = KeyFactory.createKey("MesureParent", "todo.txt");
            Entity mesEntity = new Entity("Mesure", mesure.getId(), mesParentKey);
            mesEntity.setProperty("date", mesure.getDate());
            mesEntity.setProperty("temperature", mesure.getTemperature());
            mesEntity.setProperty("humidity", mesure.getHumidity());
            mesEntity.setProperty("longitude", mesure.getLongitude());
            mesEntity.setProperty("latitude", mesure.getLatitude());

            datastoreService.put(mesEntity);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @ApiMethod(name = "getMesures")
    public List<MesureBean> getMesures() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Key mesParentKey = KeyFactory.createKey("MesureParent", "todo.txt");
        Query query = new Query(mesParentKey);
        List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
        ArrayList<MesureBean> mesures = new ArrayList<>();
        for (Entity result : results) {
            MesureBean mes = new MesureBean();
            mes.setId(result.getKey().getId());
            mes.setDate((String) result.getProperty("date"));
            mes.setTemperature((Double) result.getProperty("temperature"));
            mes.setHumidity((Double) result.getProperty("humidity"));
            mes.setLongitude((Double) result.getProperty("longitude"));
            mes.setLatitude((Double) result.getProperty("latitude"));
            mesures.add(mes);
        }

        return mesures;
    }

    @ApiMethod(name = "clearMesures")
    public void clearMesures() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            Key mesParentKey = KeyFactory.createKey("MesureParent", "todo.txt");
            Query query = new Query(mesParentKey);
            List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
            for (Entity result : results) {
                datastoreService.delete(result.getKey());
            }
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
