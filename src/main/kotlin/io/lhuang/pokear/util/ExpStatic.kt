package io.lhuang.pokear.util

class ExpStatic {
    companion object {
        val ERRATIC = arrayOf<Int>(
                0,
                1,
                15,
                52,
                122,
                237,
                406,
                637,
                942,
                1326,
                1800,
                2369,
                3041,
                3822,
                4719,
                5737,
                6881,
                8155,
                9564,
                11111,
                12800,
                14632,
                16610,
                18737,
                21012,
                23437,
                26012,
                28737,
                31610,
                34632,
                37800,
                41111,
                44564,
                48155,
                51881,
                55737,
                59719,
                63822,
                68041,
                72369,
                76800,
                81326,
                85942,
                90637,
                95406,
                100237,
                105122,
                110052,
                115015,
                120001,
                125000,
                131324,
                137795,
                144410,
                151165,
                158056,
                165079,
                172229,
                179503,
                186894,
                194400,
                202013,
                209728,
                217540,
                225443,
                233431,
                241496,
                249633,
                258043,
                267406,
                276915,
                286567,
                296358,
                306286,
                316344,
                326531,
                336840,
                347269,
                357812,
                368464,
                379221,
                390077,
                401028,
                412067,
                423190,
                434391,
                445663,
                457001,
                468398,
                479848,
                491346,
                502883,
                514453,
                526049,
                537664,
                549291,
                560922,
                572550,
                583539,
                591882,
                600000
        )
        val FLUCTUATING = arrayOf<Int>(
                0,
                0,
                4,
                13,
                32,
                65,
                113,
                182,
                276,
                398,
                553,
                745,
                979,
                1259,
                1591,
                1957,
                2457,
                3046,
                3732,
                4526,
                5440,
                6482,
                7666,
                9003,
                10506,
                12187,
                14060,
                16140,
                18439,
                20974,
                23760,
                26811,
                30146,
                33780,
                37731,
                42017,
                46656,
                51159,
                55969,
                61098,
                66560,
                72367,
                78533,
                85072,
                91998,
                99326,
                107069,
                115243,
                123863,
                132943,
                142500,
                152548,
                163105,
                174186,
                185807,
                197986,
                210739,
                224083,
                238036,
                252616,
                267840,
                283726,
                300293,
                317559,
                335544,
                354266,
                373744,
                393999,
                415050,
                436916,
                459620,
                483179,
                507617,
                532953,
                559209,
                586406,
                614566,
                643711,
                673863,
                705045,
                737280,
                770589,
                804997,
                840526,
                877201,
                915046,
                954084,
                994339,
                1035837,
                1078602,
                1122660,
                1168035,
                1214753,
                1262840,
                1312322,
                1363226,
                1415577,
                1469403,
                1524731,
                1581587,
                1640000
        )
        val FAST = arrayOf<Int>(
                0,
                0,
                6,
                21,
                51,
                100,
                172,
                274,
                409,
                583,
                800,
                1064,
                1382,
                1757,
                2195,
                2700,
                3276,
                3930,
                4665,
                5487,
                6400,
                7408,
                8518,
                9733,
                11059,
                12500,
                14060,
                15746,
                17561,
                19511,
                21600,
                23832,
                26214,
                28749,
                31443,
                34300,
                37324,
                40522,
                43897,
                47455,
                51200,
                55136,
                59270,
                63605,
                68147,
                72900,
                77868,
                83058,
                88473,
                94119,
                100000,
                106120,
                112486,
                119101,
                125971,
                133100,
                140492,
                148154,
                156089,
                164303,
                172800,
                181584,
                190662,
                200037,
                209715,
                219700,
                229996,
                240610,
                251545,
                262807,
                274400,
                286328,
                298598,
                311213,
                324179,
                337500,
                351180,
                365226,
                379641,
                394431,
                409600,
                425152,
                441094,
                457429,
                474163,
                491300,
                508844,
                526802,
                545177,
                563975,
                583200,
                602856,
                622950,
                643485,
                664467,
                685900,
                707788,
                730138,
                752953,
                776239,
                800000
        )
        val MEDIUM_FAST = arrayOf<Int>(
                0,
                1,
                8,
                27,
                64,
                125,
                216,
                343,
                512,
                729,
                1000,
                1331,
                1728,
                2197,
                2744,
                3375,
                4096,
                4913,
                5832,
                6859,
                8000,
                9261,
                10648,
                12167,
                13824,
                15625,
                17576,
                19683,
                21952,
                24389,
                27000,
                29791,
                32768,
                35937,
                39304,
                42875,
                46656,
                50653,
                54872,
                59319,
                64000,
                68921,
                74088,
                79507,
                85184,
                91125,
                97336,
                103823,
                110592,
                117649,
                125000,
                132651,
                140608,
                148877,
                157464,
                166375,
                175616,
                185193,
                195112,
                205379,
                216000,
                226981,
                238328,
                250047,
                262144,
                274625,
                287496,
                300763,
                314432,
                328509,
                343000,
                357911,
                373248,
                389017,
                405224,
                421875,
                438976,
                456533,
                474552,
                493039,
                512000,
                531441,
                551368,
                571787,
                592704,
                614125,
                636056,
                658503,
                681472,
                704969,
                729000,
                753571,
                778688,
                804357,
                830584,
                857375,
                884736,
                912673,
                941192,
                970299,
                1000000
        )
        val MEDIUM_SLOW = arrayOf<Int>(
                -140,
                -53,
                9,
                57,
                96,
                135,
                179,
                236,
                314,
                419,
                560,
                742,
                973,
                1261,
                1612,
                2035,
                2535,
                3120,
                3798,
                4575,
                5460,
                6458,
                7577,
                8825,
                10208,
                11735,
                13411,
                15244,
                17242,
                19411,
                21760,
                24294,
                27021,
                29949,
                33084,
                36435,
                40007,
                43808,
                47846,
                52127,
                56660,
                61450,
                66505,
                71833,
                77440,
                83335,
                89523,
                96012,
                102810,
                109923,
                117360,
                125126,
                133229,
                141677,
                150476,
                159635,
                169159,
                179056,
                189334,
                199999,
                211060,
                222522,
                234393,
                246681,
                259392,
                272535,
                286115,
                300140,
                314618,
                329555,
                344960,
                360838,
                377197,
                394045,
                411388,
                429235,
                447591,
                466464,
                485862,
                505791,
                526260,
                547274,
                568841,
                590969,
                613664,
                636935,
                660787,
                685228,
                710266,
                735907,
                762160,
                789030,
                816525,
                844653,
                873420,
                902835,
                932903,
                963632,
                995030,
                1027103,
                1059860
        )
        val SLOW = arrayOf<Int>(
                0,
                1,
                13,
                45,
                106,
                208,
                360,
                571,
                853,
                1215,
                1666,
                2218,
                2880,
                3661,
                4573,
                5625,
                6826,
                8188,
                9720,
                11431,
                13333,
                15435,
                17746,
                20278,
                23040,
                26041,
                29293,
                32805,
                36586,
                40648,
                45000,
                49651,
                54613,
                59895,
                65506,
                71458,
                77760,
                84421,
                91453,
                98865,
                106666,
                114868,
                123480,
                132511,
                141973,
                151875,
                162226,
                173038,
                184320,
                196081,
                208333,
                221085,
                234346,
                248128,
                262440,
                277291,
                292693,
                308655,
                325186,
                342298,
                360000,
                378301,
                397213,
                416745,
                436906,
                457708,
                479160,
                501271,
                524053,
                547515,
                571666,
                596518,
                622080,
                648361,
                675373,
                703125,
                731626,
                760888,
                790920,
                821731,
                853333,
                885735,
                918946,
                952978,
                987840,
                1023541,
                1060093,
                1097505,
                1135786,
                1174948,
                1215000,
                1255951,
                1297813,
                1340595,
                1384306,
                1428958,
                1474560,
                1521121,
                1568653,
                1617165,
                1666666
        )
    }
}