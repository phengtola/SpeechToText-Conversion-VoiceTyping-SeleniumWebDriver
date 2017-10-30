package com.cbnu.cgac.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MediaFileRepository {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void saveGeneratedMediaFIle(MediaFile mediaFile){
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.save(mediaFile);
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String args[]){
        new MediaFileRepository().saveGeneratedMediaFIle(new MediaFile("test","docs", "","1","tola"));
    }




}
