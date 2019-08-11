package kazoo.controller;

import kazoo.pitch.McLeodPitchMethod;
import kazoo.pitch.PitchDetectionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

@RestController
public class TranscripcionController {

    @GetMapping(path = "/transcripcion")
    public PitchDetectionResult transcribir(@RequestParam("compas") MultipartFile[] audio) throws IOException, UnsupportedAudioFileException {
        //todo: chequear si me mandan algo antes de asumirlo
        InputStream inputStream = audio[0].getInputStream();
        AudioInputStream archivoWav = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
        float frecuenciaDeMuestreo = archivoWav.getFormat().getSampleRate();
        float[] amplitudes = Arrays.copyOf(this.foo(IOUtils.toByteArray(inputStream)), (int) frecuenciaDeMuestreo);
        McLeodPitchMethod algoritmoDeDeteccion = new McLeodPitchMethod(frecuenciaDeMuestreo, (int) frecuenciaDeMuestreo);
        return algoritmoDeDeteccion.getPitch(amplitudes);
    }

    private double[] byteToFloat(byte[] input) {
        double[] ret = new double[input.length / 4];
        for (int x = 0; x < input.length; x += 4) {
            ret[x / 4] = ByteBuffer.wrap(input, x, 8).getDouble();
        }
        return ret;
    }

    private float[] foo(byte[] bar) throws IOException {
        ByteArrayInputStream bas = new ByteArrayInputStream(bar);
        DataInputStream ds = new DataInputStream(bas);
        float[] fArr = new float[bar.length / 4];  // 4 bytes per float
        for (int i = 0; i < fArr.length; i++) {
            fArr[i] = ds.readFloat();
        }
        return fArr;
    }
}
