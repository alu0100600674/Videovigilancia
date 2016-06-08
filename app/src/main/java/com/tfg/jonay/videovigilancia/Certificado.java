package com.tfg.jonay.videovigilancia;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import android.util.Base64;

import javax.crypto.KeyAgreement;

/**
 * Created by jonay on 4/06/16.
 */
public class Certificado {

    public static void guardarPublicaSerializada(String filename, PublicKey publicKey) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(publicKey);
        oos.close();
    }

    public static PublicKey leerPublicaSerializada(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        PublicKey publicKey = (PublicKey) ois.readObject();
        ois.close();

        return publicKey;
    }

    public static void guardarPrivadaSerializada(String filename, PrivateKey privateKey) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(privateKey);
        oos.close();
    }

    public static PrivateKey leerPrivadaSerializada(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        PrivateKey privateKey = (PrivateKey) ois.readObject();
        ois.close();

        return privateKey;
    }

    public static KeyPair generatePairKey() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        KeyPair keyPair = null;

        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp128r1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", "SC");
        keyPairGenerator.initialize(ecGenParameterSpec);
        keyPair = keyPairGenerator.generateKeyPair();

        return keyPair;
    }

    public static PublicKey leerClavePublica(Context ctx, int certificadoID) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        InputStream is = ctx.getResources().openRawResource(certificadoID);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> lines = new ArrayList<String>();
        String line = null;
        try {
            while ((line = br.readLine()) != null)
                lines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Eliminar la primera y ultima linea del fichero.
        if (lines.size() > 1 && lines.get(0).startsWith("-----") && lines.get(lines.size()-1).startsWith("-----")) {
            lines.remove(0);
            lines.remove(lines.size()-1);
        }

        // Concatenar las lineas en un solo string
        StringBuilder sb = new StringBuilder();
        for (String aLine: lines)
            sb.append(aLine);
        String keyString = sb.toString();
        Log.d("KEY PUB", "keyString:" + keyString);

        // Convertir el string a publickey
        byte[] keyBytes = Crypto.base64Decode(keyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("ECDH", "SC");
        PublicKey key = keyFactory.generatePublic(spec);

        return key;
    }

    public static PrivateKey leerClavePrivada(Context ctx, int certificadoID) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        InputStream is = ctx.getResources().openRawResource(certificadoID);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> lines = new ArrayList<String>();
        String line = null;
        try {
            while ((line = br.readLine()) != null)
                lines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Eliminar la primera y ultima linea del fichero.
        if (lines.size() > 1 && lines.get(0).startsWith("-----") && lines.get(lines.size()-1).startsWith("-----")) {
            lines.remove(0);
            lines.remove(lines.size()-1);
        }

        // Concatenar las lineas en un solo string
        StringBuilder sb = new StringBuilder();
        for (String aLine: lines)
            sb.append(aLine);
        String keyString = sb.toString();
        Log.d("KEY PRI", "keyString:" + keyString);

        // Convertir el string a privatekey
        byte[] keyBytes = Crypto.base64Decode(keyString);
        PKCS8EncodedKeySpec p8ks = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("ECDH", "SC");
        PrivateKey key = keyFactory.generatePrivate(p8ks);

        return key;
    }

    public static String generarClaveCompartida(PrivateKey privada, PublicKey publica) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "SC");
        keyAgreement.init(privada);
        keyAgreement.doPhase(publica, true);

        byte[] sharedKeyBytes = keyAgreement.generateSecret();
        Crypto.base64Encode(sharedKeyBytes);

        return Crypto.base64Encode(sharedKeyBytes);
    }

}
