public class Blake32 {
	private static int instanceCounter = 0;
	private int[] v = new int[16];
	private int[] m = new int[16];
	private int round;
	private static int[] h32 = new int[8];
	private int[] salt32 = new int[4];
	private byte[] datablock;
	private static int[] t32 = new int[2];
	private final int NB_ROUNDS32 = 10;
	private final int head_mask = 1;
	private int IV32[] = { 0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A,
			0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19 };
	private int c32[] = { 0x243F6A88, 0x85A308D3, 0x13198A2E, 0x03707344,
			0xA4093822, 0x299F31D0, 0x082EFA98, 0xEC4E6C89, 0x452821E6,
			0x38D01377, 0xBE5466CF, 0x34E90C6C, 0xC0AC29B7, 0xC97C50DD,
			0x3F84D5B5, 0xB5470917 };

	Blake32(int control, byte[] d, int[] s) {
		instanceCounter++;
		if ((control & head_mask) > 0) {
			for (int i = 0; i < 8; i++) {
				h32[i] = IV32[i];
			}
			t32[0] = 0;
			t32[1] = 0;
		}
		if (s == null) {
			salt32[0] = 0;
			salt32[1] = 0;
			salt32[2] = 0;
			salt32[3] = 0;
		} else {
			salt32 = s;
		}
		datablock = d;
	}

	private char sigma[][] = {
			{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
			{ 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3 },
			{ 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4 },
			{ 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8 },
			{ 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13 },
			{ 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9 },
			{ 12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11 },
			{ 13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10 },
			{ 6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5 },
			{ 10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0 },
			{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
			{ 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3 },
			{ 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4 },
			{ 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8 },
			{ 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13 },
			{ 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9 },
			{ 12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11 },
			{ 13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10 },
			{ 6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5 },
			{ 10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0 } };

	private static String padHexFront(String bits) {
		while (bits.length() < 8) {
			bits = "0" + bits;
		}
		return bits;
	}

	public static String getHash() {
		String hash = "";

		for (int x : h32) {
			hash = hash + padHexFront(Integer.toHexString(x));
		}
		return hash;
	}

	private byte[] getFourByte(int start) {
		byte[] fourchar = new byte[4];
		fourchar[0] = datablock[start];
		fourchar[0] = datablock[start + 1];
		fourchar[0] = datablock[start + 2];
		fourchar[0] = datablock[start + 3];
		return fourchar;
	}

	public void compress32() {

		for (int i = 0; i < 16; i++) {
			m[i] = U8TO32_BE(getFourByte(i * 4));
		}

		/* initialization */
		for (int i = 0; i < 8; i++) {
			v[i] = h32[i];
		}
		v[8] = salt32[0] ^ c32[0];
		v[9] = salt32[1] ^ c32[1];
		v[10] = salt32[2] ^ c32[2];
		v[11] = salt32[3] ^ c32[3];

		v[12] = t32[0] ^ c32[4];
		v[13] = t32[0] ^ c32[5];
		v[14] = t32[1] ^ c32[6];
		v[15] = t32[1] ^ c32[7];

		/* do 10 rounds */
		for (round = 0; round < NB_ROUNDS32; ++round) {

			/* column step */
			G32(0, 4, 8, 12, 0);
			G32(1, 5, 9, 13, 1);
			G32(2, 6, 10, 14, 2);
			G32(3, 7, 11, 15, 3);

			/* diagonal step */
			G32(0, 5, 10, 15, 4);
			G32(1, 6, 11, 12, 5);
			G32(2, 7, 8, 13, 6);
			G32(3, 4, 9, 14, 7);

		}

		/* finalization */
		h32[0] ^= v[0] ^ v[8] ^ salt32[0];
		h32[1] ^= v[1] ^ v[9] ^ salt32[1];
		h32[2] ^= v[2] ^ v[10] ^ salt32[2];
		h32[3] ^= v[3] ^ v[11] ^ salt32[3];
		h32[4] ^= v[4] ^ v[12] ^ salt32[0];
		h32[5] ^= v[5] ^ v[13] ^ salt32[1];
		h32[6] ^= v[6] ^ v[14] ^ salt32[2];
		h32[7] ^= v[7] ^ v[15] ^ salt32[3];

		t32[0] += 512;
		if (t32[0] == 0)
			t32[1]++;

	}

	private int ROT32(int x, int n) {
		return ((x) << (32 - n) | ((x) >>> (n)));
	}

	private int ADD32(int x, int y) {
		return ((int) ((x) + (y)));
	}

	private int XOR32(int x, int y) {
		return ((int) ((x) ^ (y)));
	}

	private void G32(int a, int b, int c, int d, int i) {
		v[a] = ADD32(v[a], v[b])
				+ XOR32(m[sigma[round][2 * i]], c32[sigma[round][2 * i + 1]]);
		v[d] = ROT32(XOR32(v[d], v[a]), 16);
		v[c] = ADD32(v[c], v[d]);
		v[b] = ROT32(XOR32(v[b], v[c]), 12);
		v[a] = ADD32(v[a], v[b])
				+ XOR32(m[sigma[round][2 * i + 1]], c32[sigma[round][2 * i]]);
		v[d] = ROT32(XOR32(v[d], v[a]), 8);
		v[c] = ADD32(v[c], v[d]);
		v[b] = ROT32(XOR32(v[b], v[c]), 7);
	}

	private int U8TO32_BE(byte p[]) {
		return (((int) ((p)[0]) << 24) | ((int) ((p)[1]) << 16)
				| ((int) ((p)[2]) << 8) | ((int) ((p)[3])));
	}

}
