import CryptoJS from "crypto-js";

export default {
    decrypt(encryptedCipherText, encryptedBase64Key) {
        

        var keyForCryptoJS = CryptoJS.enc.Base64.parse(encryptedBase64Key);

        var decodeBase64 = CryptoJS.enc.Base64.parse(encryptedCipherText);

        var decryptedData = CryptoJS.AES.decrypt(
            { ciphertext: decodeBase64 },
            keyForCryptoJS,
            { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 }
        );

        var decryptedText = decryptedData.toString(CryptoJS.enc.Utf8);

        return decryptedText;
    },
}