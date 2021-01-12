package com.pqr.demopqr.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {


    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    public static String getSHA5FromString(final String s){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes(StandardCharsets.UTF_8));
            byte[] byteData = md.digest();

            //convert the byte to hex format
            StringBuilder hexString = new StringBuilder();
            for (byte b: byteData) {
                if (b > 0 && b < 16) hexString.append("0");
                hexString.append(Integer.toHexString(b & 0xff));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return s;
        }
    }

    public static final String FECHA_GUION = "yyyy-MM-dd";

    public static String convertDateToString(Date fechaOrigen, String pattern) {
        if(fechaOrigen == null){
            return null;
        }

        String fechaDestino = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Utils.isEmpty(pattern) ? FECHA_GUION : pattern);
            fechaDestino = dateFormat.format(fechaOrigen);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return fechaDestino;
    }

    public static Date convertStringToDate(String fechaOrigen, String pattern) {
        if(Utils.isEmpty(fechaOrigen) || Utils.isEmpty(pattern)){
            return null;
        }

        Date fechaDestino = null;
        try {
            fechaDestino = new SimpleDateFormat(pattern).parse(fechaOrigen);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return fechaDestino;
    }

    public static String sanitize(String param) {
        if (param == null) {
            return "";
        }
        return param;
    }

    public static boolean isEmpty(String param) {
        return sanitize(param).trim().isEmpty();
    }

    public static Long sanitize(Long param){
        if(param == null){
            return 0L;
        }

        return param;
    }

    public static boolean isEmpty(Long param){
        return sanitize(param) == 0L;
    }

    public static boolean isEmpty(List param){
        return param == null || param.isEmpty();
    }

}
