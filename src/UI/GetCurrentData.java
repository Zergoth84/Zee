/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author z.moszko
 */
public class GetCurrentData {

    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public GetCurrentData() {


    }

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
