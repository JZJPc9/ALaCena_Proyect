package com.example.alacena.clases;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import android.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Cifrado {
    private static final String ALGORITMO_AES = "AES";
    private static final String ALGORITMO_SHA256 = "SHA-256";
    private static final String clave = "ESPRHVJJPJZ";

    // Método para cifrar datos con AES
    public static String cifrar(String textoPlano) {
        try {
            SecretKey secretKey = generarKey(clave);
            Cipher cipher = Cipher.getInstance(ALGORITMO_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] datosCifrados = cipher.doFinal(textoPlano.getBytes());
            return Base64.encodeToString(datosCifrados, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para descifrar datos con AES
    public static String descifrar(String textoCifrado ) {
        try {
            SecretKey secretKey = generarKey(clave);
            Cipher cipher = Cipher.getInstance(ALGORITMO_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] datosDescifrados = cipher.doFinal(Base64.decode(textoCifrado, Base64.DEFAULT));
            return new String(datosDescifrados);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método privado para generar la clave usando SHA-256
    private static SecretKey generarKey(String clave) {
        try {
            MessageDigest sha = MessageDigest.getInstance(ALGORITMO_SHA256);
            byte[] claveBytes = clave.getBytes();
            sha.update(claveBytes);
            byte[] claveDigest = sha.digest();
            return new SecretKeySpec(claveDigest, ALGORITMO_AES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
