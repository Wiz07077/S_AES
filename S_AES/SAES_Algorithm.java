package S_AES;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class SAES_Algorithm {
    // S-盒和逆S-盒
    public static final int[][] SBOX = {
            {0x9, 0x4, 0xA, 0xB},
            {0xD, 0x1, 0x8, 0x5},
            {0x6, 0x2, 0x0, 0x3},
            {0xC, 0xE, 0xF, 0x7}
    };

    public static final int[][] INV_SBOX = {
            {0xA, 0x5, 0x9, 0xB},
            {0x1, 0x7, 0x8, 0xF},
            {0x6, 0x0, 0x2, 0x3},
            {0xC, 0x4, 0xD, 0xE}
    };

    // 列混淆和逆列混淆矩阵
    public static final int[][] MIX_COLUMN_MATRIX = {
            {0x1, 0x4},
            {0x4, 0x1}
    };

    public static final int[][] INV_MIX_COLUMN_MATRIX = {
            {0x9, 0x2},
            {0x2, 0x9}
    };

    // 定义 RCON 常量
    public static final int[] RCON = {0b10000000, 0b00110000};
    public static final int[][] rCON = {{0x8, 0x0}, {0x3, 0x0}};

    // 加密函数
    public static int[][] encrypt(int[][] state, int[][] key) {
        int[][][] roundKeys = keyExpansion(key);
        state = addRoundKey(state, roundKeys[0]);
        state = shiftRows(subNibbles(state, SBOX));
        state = mixColumns(state, MIX_COLUMN_MATRIX);
        state = addRoundKey(state, roundKeys[1]);
        state = shiftRows(subNibbles(state, SBOX));
        state = addRoundKey(state, roundKeys[2]);
        return state;
    }

    // 解密函数
    public static int[][] decrypt(int[][] state, int[][] key) {
        int[][][] roundKeys = keyExpansion(key);
        state = addRoundKey(state, roundKeys[2]);
        state = subNibbles(shiftRows(state), INV_SBOX);
        state = addRoundKey(state, roundKeys[1]);
        state = mixColumns(state, INV_MIX_COLUMN_MATRIX);
        state = subNibbles(shiftRows(state), INV_SBOX);
        state = addRoundKey(state, roundKeys[0]);
        return state;
    }

    // 字节替代函数
    public static int[][] subNibbles(int[][] state, int[][] sbox) {
        int[][] result = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = sbox[(state[i][j] >> 2) & 0x03][state[i][j] & 0x03];
            }
        }
        return result;
    }

    // 行移位函数
    public static int[][] shiftRows(int[][] state) {
        int temp = state[1][0];
        state[1][0] = state[1][1];
        state[1][1] = temp;
        return state;
    }

    // 列混淆函数
    public static int[][] mixColumns(int[][] state, int[][] matrix) {
        int[][] result = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = multiply(matrix[i][0], state[0][j]) ^ multiply(matrix[i][1], state[1][j]);
            }
        }
        return result;
    }

    // 轮密钥加函数
    public static int[][] addRoundKey(int[][] state, int[][] key) {
        int[][] result = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = (state[i][j] ^ key[i][j]) & 0xF;
            }
        }
        return result;
    }

    // 密钥扩展函数
    public static int[][][] keyExpansion(int[][] key) {
        int[][] w = new int[6][2];
        w[0][0] = key[0][0]; w[0][1] = key[1][0];
        w[1][0] = key[0][1]; w[1][1] = key[1][1];
        w[2] = xorWords(w[0], xorWords(rCON[0], subNib(rotNib(w[1]))));
        w[3] = xorWords(w[2], w[1]);
        w[4] = xorWords(w[2], xorWords(rCON[1], subNib(rotNib(w[3]))));
        w[5] = xorWords(w[4], w[3]);

        return new int[][][] {
                {{w[0][0], w[1][0]}, {w[0][1], w[1][1]}},
                {{w[2][0], w[3][0]}, {w[2][1], w[3][1]}},
                {{w[4][0], w[5][0]}, {w[4][1], w[5][1]}}
        };
    }

    // 字节旋转和替换函数
    public static int[] rotNib(int[] word) {
        return new int[]{word[1], word[0]};
    }

    public static int[] subNib(int[] word) {
        int[] result = new int[word.length];
        for (int i = 0; i < word.length; i++) {
            int upper = (word[i] >> 2) & 0x03;
            int lower = word[i] & 0x03;
            result[i] = SBOX[upper][lower];
        }
        return result;
    }

    public static int[] xorWords(int[] w1, int[] w2) {
        return new int[]{w1[0] ^ w2[0], w1[1] ^ w2[1]};
    }

    public static int multiply(int a, int b) {
        int product = 0, modulus = 0b10011;
        for (int i = 0; i < 4; i++) {
            if ((b & 1) != 0) product ^= a;
            boolean carry = (a & 0x8) != 0;
            a <<= 1;
            if (carry) a ^= modulus;
            b >>= 1;
        }
        return product & 0xF;
    }

    // 将二进制转换为状态矩阵
    public static int[][] convertToStateMatrix(String binary) {
        int[][] state = new int[2][2];
        if (binary.length() != 16) {
            throw new IllegalArgumentException("需要16位二进制字符串");
        }
        for (int i = 0; i < 4; i++) {
            state[i % 2][i / 2] = Integer.parseInt(binary.substring(i * 4, (i * 4) + 4), 2);
        }
        return state;
    }

    // 将状态矩阵转换为二进制字符串
    public static String convertStateMatrixToString(int[][] state) {
        StringBuilder binaryString = new StringBuilder();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++) {
                String binary = String.format("%4s", Integer.toBinaryString(state[i][j])).replace(' ', '0');
                binaryString.append(binary);
            }
        }
        return binaryString.toString();
    }

    // 中间相遇攻击示例
    public static String[] meetInTheMiddle(String plaintext, String ciphertext) {
        Map<String, String> encMap = new HashMap<>(), decMap = new HashMap<>();
        String[] allKeys = generateAllPossibleKeys();
        int[][] plaintextState = convertToStateMatrix(plaintext), ciphertextState = convertToStateMatrix(ciphertext);

        for (String k1 : allKeys) {
            int[][] key1 = convertToStateMatrix(k1);
            String intermediate = convertStateMatrixToString(encrypt(plaintextState, key1));
            encMap.put(intermediate, k1);
        }

        for (String k2 : allKeys) {
            int[][] key2 = convertToStateMatrix(k2);
            String intermediate = convertStateMatrixToString(decrypt(ciphertextState, key2));
            decMap.put(intermediate, k2);
        }

        for (String midState : encMap.keySet()) {
            if (decMap.containsKey(midState)) {
                String k1 = encMap.get(midState);
                String k2 = decMap.get(midState);
                return new String[]{k1, k2};
            }
        }
        return null;
    }

    // 生成所有可能的16位二进制密钥
    public static String[] generateAllPossibleKeys() {
        int numberOfKeys = 1 << 16;
        String[] keys = new String[numberOfKeys];
        for (int i = 0; i < numberOfKeys; i++) {
            keys[i] = String.format("%16s", Integer.toBinaryString(i)).replace(' ', '0');
        }
        return keys;
    }

    // 生成初始向量
    public static String generateIV() {
        SecureRandom random = new SecureRandom();
        int iv = random.nextInt(1 << 16);
        return String.format("%16s", Integer.toBinaryString(iv)).replace(' ', '0');
    }

    // 将二进制字符串转换为分组块
    public static int[][][] convertBinaryStringToBlocks(String binaryString) {
        int blockSize = 16;
        int numBlocks = binaryString.length() / blockSize;
        int[][][] blocks = new int[numBlocks][2][2];
        for (int i = 0; i < numBlocks; i++) {
            String block = binaryString.substring(i * blockSize, (i + 1) * blockSize);
            blocks[i] = convertToStateMatrix(block);
        }
        return blocks;
    }

    // CBC 加密方法
    public static String cbcEncrypt(String plaintextBinary, int[][] key, String iv) {
        int[][] IV = convertToStateMatrix(iv);
        int[][][] plaintextBlocks = convertBinaryStringToBlocks(plaintextBinary);
        int[][][] ciphertextBlocks = new int[plaintextBlocks.length][2][2];
        int[][] previousBlock = IV;

        for (int i = 0; i < plaintextBlocks.length; i++) {
            int[][] currentBlock = xorStateMatrices(plaintextBlocks[i], previousBlock);
            int[][] encryptedBlock = encrypt(currentBlock, key);
            ciphertextBlocks[i] = encryptedBlock;
            previousBlock = encryptedBlock;
        }

        StringBuilder ciphertextBinary = new StringBuilder();
        for (int i = 0; i < ciphertextBlocks.length; i++) {
            ciphertextBinary.append(convertStateMatrixToString(ciphertextBlocks[i]));
        }
        return ciphertextBinary.toString();
    }

    // 按位异或两个状态矩阵
    public static int[][] xorStateMatrices(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = matrix1[i][j] ^ matrix2[i][j];
            }
        }
        return result;
    }

    // CBC 解密方法
    public static String cbcDecrypt(String ciphertextBinary, int[][] key, String iv) {
        int[][][] ciphertextBlocks = convertBinaryStringToBlocks(ciphertextBinary);
        int[][] previousBlock = convertToStateMatrix(iv);
        int[][][] plaintextBlocks = new int[ciphertextBlocks.length][2][2];

        for (int i = 0; i < ciphertextBlocks.length; i++) {
            int[][] decryptedBlock = decrypt(ciphertextBlocks[i], key);
            plaintextBlocks[i] = xorStateMatrices(decryptedBlock, previousBlock);
            previousBlock = ciphertextBlocks[i];
        }

        StringBuilder plaintextBinary = new StringBuilder();
        for (int i = 0; i < plaintextBlocks.length; i++) {
            plaintextBinary.append(convertStateMatrixToString(plaintextBlocks[i]));
        }
        return plaintextBinary.toString();
    }
}
