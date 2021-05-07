let argv = process.argv.slice(2);

if (argv.length < 1) {
    console.error(`Usage: ${process.argv[1]} <erratic|fluctuating|fast|medium-fast|medium-slow|slow>`);
    process.exit(1);
}

const growthType = argv[0];

if (growthType == 'erratic') {
    for (let i = 0; i <= 100; i++) {
        let exp;

        let i3 = i * i * i;
        if (i < 50) {
            // | 0 effectively truncates floating point portion
            exp = ((i3 * (100 - i)) / 50) | 0;
        } else if (i < 68) {
            exp = ((i3 * (150 - i)) / 100) | 0;
        } else if (i < 98) {
            exp = ((i3 * ((1911 - (10 * i)) / 3)) / 500) | 0;
        } else {
            exp = ((i3 * (160 - i)) / 100) | 0;
        }
        console.log(`${exp},`);
    }
} else if (growthType == 'fluctuating') {
    for (let i = 0; i <= 100; i++) {
        let exp;

        let i3 = i * i * i;
        if (i < 15) {
            exp = (i3 * ((((i + 1) / 3) + 24) / 50)) | 0;
        } else if (i < 36) {
            exp = (i3 * ((i + 14) / 50)) | 0;
        } else {
            exp = (i3 * (((i / 2) + 32) / 50)) | 0;
        }
        console.log(`${exp},`);
    }
} else if (growthType == 'fast') {
    for (let i = 0; i <= 100; i++) {
        let i3 = i * i * i;
        let exp = ((4 * i3) / 5) | 0;

        console.log(`${exp},`);
    }
} else if (growthType == 'medium-fast') {
    for (let i = 0; i <= 100; i++) {
        let i3 = i * i * i;
        let exp = i3;

        console.log(`${exp},`);
    }
} else if (growthType == 'medium-slow') {
    for (let i = 0; i <= 100; i++) {
        let i2 = i * i;
        let i3 = i * i * i;
        let exp = (((6 / 5) * i3) - (15 * i2) + (100 * i) - 140) | 0;

        console.log(`${exp},`);
    }
} else if (growthType == 'slow') {
    for (let i = 0; i <= 100; i++) {
        let i3 = i * i * i;
        let exp = ((5 * i3) / 3) | 0;

        console.log(`${exp},`);
    }
}
