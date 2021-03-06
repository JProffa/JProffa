package net.jproffa.profiler;

/**
 * Knows the approximate relative costs of different JVM instructions.
 * 
 * Derived from <a href="http://www.jopdesign.com/doc/timing.pdf">http://www.jopdesign.com/doc/timing.pdf</a>.
 * More research into this subject would be welcome.
 */
public class InstructionCost {

    private static int[] complexityCost = new int[256];
    private static boolean wantToUse = true;

    static {
        complexityCost[0] = 1;
        complexityCost[1] = 1;
        complexityCost[2] = 1;
        complexityCost[3] = 1;
        complexityCost[4] = 1;
        complexityCost[5] = 1;
        complexityCost[6] = 1;
        complexityCost[7] = 1;
        complexityCost[8] = 1;
        complexityCost[9] = 2;
        complexityCost[10] = 2;
        complexityCost[11] = 1;
        complexityCost[12] = 1;
        complexityCost[13] = 1;
        complexityCost[14] = 0;
        complexityCost[15] = 0;
        complexityCost[16] = 2;
        complexityCost[17] = 3;
        complexityCost[18] = 7;
        complexityCost[19] = 8;
        complexityCost[20] = 17;
        complexityCost[21] = 2;
        complexityCost[22] = 11;
        complexityCost[23] = 2;
        complexityCost[24] = 11;
        complexityCost[25] = 2;
        complexityCost[26] = 1;
        complexityCost[27] = 1;
        complexityCost[28] = 1;
        complexityCost[29] = 1;
        complexityCost[30] = 2;
        complexityCost[31] = 2;
        complexityCost[32] = 2;
        complexityCost[33] = 11;
        complexityCost[34] = 1;
        complexityCost[35] = 1;
        complexityCost[36] = 1;
        complexityCost[37] = 1;
        complexityCost[38] = 2;
        complexityCost[39] = 2;
        complexityCost[40] = 2;
        complexityCost[41] = 11;
        complexityCost[42] = 1;
        complexityCost[43] = 1;
        complexityCost[44] = 1;
        complexityCost[45] = 1;
        complexityCost[46] = 7;
        complexityCost[47] = 43;
        complexityCost[48] = 7;
        complexityCost[49] = 0;
        complexityCost[50] = 7;
        complexityCost[51] = 7;
        complexityCost[52] = 7;
        complexityCost[53] = 7;
        complexityCost[54] = 2;
        complexityCost[55] = 11;
        complexityCost[56] = 2;
        complexityCost[57] = 11;
        complexityCost[58] = 2;
        complexityCost[59] = 1;
        complexityCost[60] = 1;
        complexityCost[61] = 1;
        complexityCost[62] = 1;
        complexityCost[63] = 2;
        complexityCost[64] = 2;
        complexityCost[65] = 2;
        complexityCost[66] = 11;
        complexityCost[67] = 1;
        complexityCost[68] = 1;
        complexityCost[69] = 1;
        complexityCost[70] = 1;
        complexityCost[71] = 2;
        complexityCost[72] = 2;
        complexityCost[73] = 2;
        complexityCost[74] = 11;
        complexityCost[75] = 1;
        complexityCost[76] = 1;
        complexityCost[77] = 1;
        complexityCost[78] = 1;
        complexityCost[79] = 10;
        complexityCost[80] = 48;
        complexityCost[81] = 10;
        complexityCost[82] = 0;
        complexityCost[83] = 1;
        complexityCost[84] = 10;
        complexityCost[85] = 10;
        complexityCost[86] = 10;
        complexityCost[87] = 1;
        complexityCost[88] = 2;
        complexityCost[89] = 1;
        complexityCost[90] = 5;
        complexityCost[91] = 7;
        complexityCost[92] = 6;
        complexityCost[93] = 8;
        complexityCost[94] = 10;
        complexityCost[95] = 4;
        complexityCost[96] = 1;
        complexityCost[97] = 1;
        complexityCost[98] = 1;
        complexityCost[99] = 0;
        complexityCost[100] = 1;
        complexityCost[101] = 1;
        complexityCost[102] = 1;
        complexityCost[103] = 0;
        complexityCost[104] = 35;
        complexityCost[105] = 1;
        complexityCost[106] = 1;
        complexityCost[107] = 0;
        complexityCost[108] = 1;
        complexityCost[109] = 1;
        complexityCost[110] = 1;
        complexityCost[111] = 1;
        complexityCost[112] = 1;
        complexityCost[113] = 0;
        complexityCost[114] = 1;
        complexityCost[115] = 1;
        complexityCost[116] = 4;
        complexityCost[117] = 1;
        complexityCost[118] = 1;
        complexityCost[119] = 0;
        complexityCost[120] = 1;
        complexityCost[121] = 1;
        complexityCost[122] = 1;
        complexityCost[123] = 1;
        complexityCost[124] = 1;
        complexityCost[125] = 1;
        complexityCost[126] = 1;
        complexityCost[127] = 1;
        complexityCost[128] = 1;
        complexityCost[129] = 1;
        complexityCost[130] = 1;
        complexityCost[131] = 1;
        complexityCost[132] = 8;
        complexityCost[133] = 1;
        complexityCost[134] = 1;
        complexityCost[135] = 0;
        complexityCost[136] = 3;
        complexityCost[137] = 0;
        complexityCost[138] = 0;
        complexityCost[139] = 1;
        complexityCost[140] = 0;
        complexityCost[141] = 0;
        complexityCost[142] = 0;
        complexityCost[143] = 0;
        complexityCost[144] = 0;
        complexityCost[145] = 1;
        complexityCost[146] = 2;
        complexityCost[147] = 1;
        complexityCost[148] = 1;
        complexityCost[149] = 1;
        complexityCost[150] = 1;
        complexityCost[151] = 0;
        complexityCost[152] = 0;
        complexityCost[153] = 4;
        complexityCost[154] = 4;
        complexityCost[155] = 4;
        complexityCost[156] = 4;
        complexityCost[157] = 4;
        complexityCost[158] = 4;
        complexityCost[159] = 4;
        complexityCost[160] = 4;
        complexityCost[161] = 4;
        complexityCost[162] = 4;
        complexityCost[163] = 4;
        complexityCost[164] = 4;
        complexityCost[165] = 4;
        complexityCost[166] = 4;
        complexityCost[167] = 4;
        complexityCost[168] = 1;
        complexityCost[169] = 1;
        complexityCost[170] = 1;
        complexityCost[171] = 1;
        complexityCost[172] = 23;
        complexityCost[173] = 25;
        complexityCost[174] = 23;
        complexityCost[175] = 25;
        complexityCost[176] = 23;
        complexityCost[177] = 21;
        complexityCost[178] = 12;
        complexityCost[179] = 13;
        complexityCost[180] = 17;
        complexityCost[181] = 20;
        complexityCost[182] = 100;
        complexityCost[183] = 74;
        complexityCost[184] = 74;
        complexityCost[185] = 114;
        complexityCost[186] = 0;
        complexityCost[187] = 1;
        complexityCost[188] = 1;
        complexityCost[189] = 1;
        complexityCost[190] = 6;
        complexityCost[191] = 1;
        complexityCost[192] = 1;
        complexityCost[193] = 1;
        complexityCost[194] = 11;
        complexityCost[195] = 14;
        complexityCost[196] = 1;
        complexityCost[197] = 1;
        complexityCost[198] = 4;
        complexityCost[199] = 4;
        complexityCost[200] = 1;
        complexityCost[201] = 1;
        complexityCost[202] = 0;
        complexityCost[203] = 0;
        complexityCost[204] = 0;
        complexityCost[205] = 0;
        complexityCost[206] = 0;
        complexityCost[207] = 0;
        complexityCost[208] = 0;
        complexityCost[209] = 4;
        complexityCost[210] = 5;
        complexityCost[211] = 4;
        complexityCost[212] = 5;
        complexityCost[213] = 3;
        complexityCost[214] = 3;
        complexityCost[215] = 3;
        complexityCost[216] = 4;
        complexityCost[217] = 1;
        complexityCost[218] = 2;
        complexityCost[219] = 27;
        complexityCost[220] = 27;
        complexityCost[221] = 1;
        complexityCost[222] = 1;
        complexityCost[223] = 5;
        complexityCost[224] = 1;
        complexityCost[225] = 1;
        complexityCost[226] = 1;
        complexityCost[227] = 1;
        complexityCost[228] = 1;
        complexityCost[229] = 1;
        complexityCost[230] = 1;
        complexityCost[231] = 1;
        complexityCost[232] = 0;
        complexityCost[233] = 0;
        complexityCost[234] = 0;
        complexityCost[235] = 0;
        complexityCost[236] = 0;
        complexityCost[237] = 0;
        complexityCost[238] = 0;
        complexityCost[239] = 0;
        complexityCost[240] = 0;
        complexityCost[241] = 0;
        complexityCost[242] = 0;
        complexityCost[243] = 0;
        complexityCost[244] = 0;
        complexityCost[245] = 0;
        complexityCost[246] = 0;
        complexityCost[247] = 0;
        complexityCost[248] = 0;
        complexityCost[249] = 0;
        complexityCost[250] = 0;
        complexityCost[251] = 0;
        complexityCost[252] = 0;
        complexityCost[253] = 0;
        complexityCost[254] = 0;
        complexityCost[255] = 0;

    }

    public static void setCost(int opcode, int cost) {
        complexityCost[opcode] = cost;
    }

    public static int getCost(int opcode) {
        if (wantToUse) {
            return complexityCost[opcode];
        }
        return 1;
    }

    public static void enableComplexityCost() {
        wantToUse = true;
    }

    public static void disableComplexityCost() {
        wantToUse = false;
    }
}
