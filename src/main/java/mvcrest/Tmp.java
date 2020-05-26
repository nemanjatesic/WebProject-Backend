package mvcrest;

import mvcrest.avioni.*;
import mvcrest.database.Database;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Tmp {

    public Tmp() throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        AvionskaKarta avionskaKarta = new AvionskaKarta()
//                .one_way(true)
//                .depart_date(sdf.parse("2018-09-10"))
//                .return_date(null)
//                .version(0)
//                .avionskaKompanija(new AvionskaKompanija().name("Air Serbia"))
//                .available_count(10);
//
//        Grad grad = new Grad().name("Hong Kong");
//        Grad grad1 = new Grad().name("Beograd");
//
//        Let let = new Let()
//                .grad_destination(grad)
//                .grad_origin(grad1);
//
//        avionskaKarta.flight(let);

        //Database.getInstance().addFlight(let);

        //Database.getInstance().getAllCards().forEach(System.out::println);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Database.getInstance().modifyKarta(
//                new AvionskaKarta()
//                .id(5)
//                .one_way(false)
//                .depart_date(sdf.parse("2018-09-09"))
//                .return_date(sdf.parse("2018-10-10"))
//                .available_count(150)
//                .version(1)
//                .flight(new Let().id(6))
//                .avionskaKompanija(new AvionskaKompanija().id(4)));


//        Let let = new Let();
//        let
//            .version(0)
//            .grad_origin(grad)
//            .grad_destination(grad1);
//
//        Database.getInstance().addCard(avionskaKarta);
//        /*
//        Database.getInstance().addCompany(new AvionskaKompanija()
//                .name("Emirates")
//                .version(0));
//        */
//        Database.getInstance().getAllCards().forEach(System.out::println);
//        Database.getInstance().getAllCompanies().forEach(System.out::println);
    }

    public static void main(String[] args) throws Exception {
        new Tmp();
    }
}
