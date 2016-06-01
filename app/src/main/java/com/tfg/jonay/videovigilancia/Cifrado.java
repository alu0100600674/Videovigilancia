package com.tfg.jonay.videovigilancia;

import android.os.Environment;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jonay on 28/05/16.
 */
public class Cifrado {

    public static String cryptRSA(String plain){
        Cipher pkCipher = null;
        try {
            pkCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            pkCipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] encryptedInByte = new byte[0];
        try {
            encryptedInByte = pkCipher.doFinal(plain.getBytes("UTF-8"));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encryptedInByte, Base64.DEFAULT);
    }

    public static String decryptRSA(String encode){
        Cipher pkCipher = null;
        try {
            pkCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            pkCipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] encryptedInByte = null;
        try {
            encryptedInByte = pkCipher.doFinal(Base64.decode(encode,Base64.DEFAULT));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        try {
            return new String(encryptedInByte,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PrivateKey getPrivateKey(){
        PrivateKey privateKey = null;
        try {
            privateKey = Clave.getPrivateKey(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tfgopenssl/client-cert.der");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    private static PublicKey getPublicKey(){
        PublicKey publicKey = null;
        try {
            publicKey = Clave.getPublicKey(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tfgopenssl/servidor-cert.der");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
