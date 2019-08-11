package kazoo.controller;

import kazoo.pitch.AlgoritmoDeMcLeod;
import kazoo.pitch.ResultadoDeDeteccion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.Arrays;

@RestController
public class TranscripcionController {

    @GetMapping(path = "/transcripcion")
    public ResultadoDeDeteccion transcribir(@RequestParam("compas") MultipartFile[] audio) throws IOException, UnsupportedAudioFileException {
        //todo: chequear si me mandan algo antes de asumirlo
        InputStream inputStream = audio[0].getInputStream();
        AudioInputStream archivoWav = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
        float frecuenciaDeMuestreo = archivoWav.getFormat().getSampleRate();
        double[] amplitudes = Arrays.copyOf(this.bytesToDoubles(IOUtils.toByteArray(inputStream)), (int) frecuenciaDeMuestreo);
        AlgoritmoDeMcLeod algoritmoDeDeteccion = new AlgoritmoDeMcLeod(frecuenciaDeMuestreo);
        return algoritmoDeDeteccion.estimarAltura(amplitudes);
    }

    private double[] bytesToDoubles(byte[] bytes) throws IOException {
        ByteArrayInputStream bas = new ByteArrayInputStream(bytes);
        DataInputStream ds = new DataInputStream(bas);
        double[] doubleArray = new double[bytes.length / 8];  // 8 bytes per double
        for (int i = 0; i < doubleArray.length; i++) {
            doubleArray[i] = ds.readDouble();
        }
        return doubleArray;
    }
}
