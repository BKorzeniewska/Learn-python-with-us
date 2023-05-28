

const cyrb53 = (str: string, seed: number = 0) => {
    let h1 = 0xdeadbeef ^ seed, h2 = 0x41c6ce57 ^ seed;
    for (let i = 0, ch; i < str.length; i++) {
        ch = str.charCodeAt(i);
        h1 = Math.imul(h1 ^ ch, 2654435761);
        h2 = Math.imul(h2 ^ ch, 1597334677);
    }
    h1 = Math.imul(h1 ^ (h1 >>> 16), 2246822507);
    h1 ^= Math.imul(h2 ^ (h2 >>> 13), 3266489909);
    h2 = Math.imul(h2 ^ (h2 >>> 16), 2246822507);
    h2 ^= Math.imul(h1 ^ (h1 >>> 13), 3266489909);

    return 4294967296 * (2097151 & h2) + (h1 >>> 0);
};

export function generateRandomPixels(str: string): string {
    const width = 4; // width of the image
    const height = 4; // height of the image
    const imageData = new Uint8ClampedArray(width * height * 4); // create an empty buffer for image data
    var rgb = require('hsv-rgb');

    // fill the buffer with random pixel values
    for (let i = 0; i < imageData.length; i += 4) {
        let val1 = (cyrb53(str, i) % 100) / 100;
        let val2 = (cyrb53(str, i + 234) % 100) / 100;

        let [r, g, b] = rgb(70 * val1 + 240, 75, 30 * val2 + 80);
        imageData[i] = Math.floor(r); // red
        imageData[i + 1] = Math.floor(g); // green
        imageData[i + 2] = Math.floor(b); // blue
        imageData[i + 3] = 255; // alpha
    }

    // create a canvas element to draw the image
    const canvas = document.createElement('canvas');
    canvas.width = width;
    canvas.height = height;
    const ctx = canvas.getContext('2d')!;
    const imageDataObj = new ImageData(imageData, width, height);

    // draw the image on the canvas
    ctx.putImageData(imageDataObj, 0, 0);

    // encode the canvas as a base64 PNG
    const dataUrl = canvas.toDataURL('image/png');
    const base64Data = dataUrl.replace(/^data:image\/png;base64,/, '');

    return base64Data;
}