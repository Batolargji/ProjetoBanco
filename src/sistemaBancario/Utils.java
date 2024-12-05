package sistemaBancario;

import java.util.Random;

public class Utils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static int gerarIdAleatorio() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Gera um número de 6 dígitos
    }

    public static String gerarNomeUsuario() {
        StringBuilder nomeUsuario = new StringBuilder(12);
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(CHARACTERS.length());
            nomeUsuario.append(CHARACTERS.charAt(index));
        }
        return nomeUsuario.toString();
    }
}
