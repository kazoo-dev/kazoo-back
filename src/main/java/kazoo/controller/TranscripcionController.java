package kazoo.controller;

import kazoo.pitch.McLeodPitchMethod;
import kazoo.pitch.PitchDetectionResult;
import kazoo.wav.WavFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@RestController
public class TranscripcionController {

    @GetMapping(path ="/transcripcion")
    public PitchDetectionResult transcribir(@RequestParam("file") MultipartFile[] audio) throws IOException, UnsupportedAudioFileException {
        //todo: chequear si me mandan algo antes de asumirlo
        InputStream inputStream = audio[0].getInputStream();
        AudioInputStream archivoWav = AudioSystem.getAudioInputStream(inputStream);

        float frecuenciaDeMuestreo = archivoWav.getFormat().getSampleRate();

        byte[] asdasd = IOUtils.toByteArray(inputStream);
        McLeodPitchMethod algoritmoDeDeteccion = new McLeodPitchMethod(frecuenciaDeMuestreo);

        //todo: la quede aca, porque me devuelve byte

//        PitchDetectionResult xxxx = algoritmoDeDeteccion.getPitch(asdasd);

        return new PitchDetectionResult();
    }

}
