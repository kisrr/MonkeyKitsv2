package me.kisr.monkeykits.utils;

import me.kisr.monkeykits.Main;

import java.util.Random;

public class KitShareUtils {

    public static String generateCode() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String code = null;

        while (code == null || Main.codeMap.containsKey(code)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int index = random.nextInt(characters.length());
                char randomChar = characters.charAt(index);
                sb.append(randomChar);
            }
            code = sb.toString();
        }
        return code;
    }
}
