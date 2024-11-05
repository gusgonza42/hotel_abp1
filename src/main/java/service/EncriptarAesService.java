package service;

import utils.Constantes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncriptarAesService {

    public static SecretKey generateAndSaveAESKey() throws Exception {
        try {
            // Generar una clave AES de 128 bits
            KeyGenerator keyGen = KeyGenerator.getInstance(Constantes.ALGORITHM);
            keyGen.init(128, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();

            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Clave en Base64: " + encodedKey);

            // Construir la ruta completa del archivo
            String filePath = System.getProperty("user.dir") + "/" + Constantes.KEY_FILE;
            File keyFile = new File(filePath);
            if (!keyFile.getParentFile().exists()) {
                keyFile.getParentFile().mkdirs(); // crear carpeta si no existe
            }

            // Guardar la clave en el archivo
            try (FileOutputStream writer = new FileOutputStream(keyFile)) {
                writer.write(encodedKey.getBytes(StandardCharsets.UTF_8));
                System.out.println("🚀 Clave AES generada y guardada en " + filePath);
            } catch (IOException e) {
                System.err.println("🗝️⚠️Error al guardar la clave en el archivo: " + e.getMessage() + "️🗝️⚠️");
                throw new IOException("Error al guardar la clave en el archivo.", e);
            }
            return secretKey;
        } catch (Exception e) {
            System.err.println("🗝️Error al generar la clave AES: " + e.getMessage() + "🗝️");
            throw new Exception("No se pudo generar la clave AES.", e);
        }
    }


    public static SecretKey loadAESKey() throws IOException {
        File file = new File(Constantes.KEY_FILE);
        if (!file.exists() || file.length() == 0) {
            System.err.println("⚠️ El archivo de clave está vacío o no existe. Generando una nueva clave...");
            try {
                return generateAndSaveAESKey(); // Generar una nueva clave si no existe
            } catch (Exception e) {
                System.err.println("⚠️ Error al generar una nueva clave: " + e.getMessage() + "⚠️");
                throw new IOException("Error al generar una nueva clave.", e);
            }
        }

        // Leer la clave desde el archivo
        String encodedKey;
        try (FileReader reader = new FileReader(Constantes.KEY_FILE)) {
            char[] keyChars = new char[(int) file.length()];
            reader.read(keyChars);
            encodedKey = new String(keyChars);
        } catch (IOException e) {
            System.err.println("⚠️ Error al leer el archivo de clave: " + e.getMessage() + "⚠️");
            throw new IOException("Error al leer el archivo de clave.", e);
        }

        // Decodificar la clave desde Base64
        try {
            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
            return new SecretKeySpec(decodedKey, Constantes.ALGORITHM);
        } catch (IllegalArgumentException e) {
            System.err.println("⚠️ Error: La clave en el archivo está corrupta o en un formato inválido.⚠️ ");
            throw new IOException("La clave en el archivo está corrupta o en un formato inválido.", e);
        }
    }

    // Encriptar texto
    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(Constantes.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData); // Convertir a Base64 para almacenamiento
        } catch (Exception e) {
            System.err.println("⚠️Error en la encriptación: " + e.getMessage() + "⚠️");
            throw new Exception("No se pudo encriptar los datos.", e);
        }
    }

    // Desencriptar texto
    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(Constantes.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData); // Decodificar Base64 a bytes
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("⚠️ Error en la desencriptación: " + e.getMessage() + "⚠️");
            throw new Exception("No se pudo desencriptar los datos.", e);
        }
    }

    // Método principal para pruebas
    public static void main(String[] args) {
        try {
            // Verificar y cargar la clave AES necesaria para desencriptar
            SecretKey secretKey;
            File keyFile = new File(Constantes.KEY_FILE);
            if (!keyFile.exists()) {
                System.out.println("⚠️🗝️No se encontró ninguna clave. Generando y guardando una nueva clave AES... ⚠️🗝️");
                secretKey = EncriptarAesService.generateAndSaveAESKey();
            } else {
                System.out.println("✅🔭Clave existente encontrada. 🔄️Cargando clave AES para desencriptar...🔄️");
                secretKey = EncriptarAesService.loadAESKey();
            }

            // Ejemplo de encriptación y desencriptación de datos
            String textoOriginal = "Este es un mensaje secreto";
            String textoCifrado = EncriptarAesService.encrypt(textoOriginal, secretKey);
            System.out.println("🗝️Texto cifrado: <<" + textoCifrado + ">> 🗝️");

            String textoDescifrado = EncriptarAesService.decrypt(textoCifrado, secretKey);
            System.out.println("🪢Texto descifrado: <<" + textoDescifrado + ">>🪢");

        } catch (Exception e) {
            System.err.println("⚠️??Error al procesar la clave AES: " + e.getMessage()  + "??⚠️");
        }
    }

}