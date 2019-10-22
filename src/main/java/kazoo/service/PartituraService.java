package kazoo.service;

import kazoo.controller.to.DetallePartitura;
import kazoo.controller.to.PartituraListada;
import kazoo.excepciones.partituras.PartituraNoEncontradaException;
import kazoo.excepciones.usuario.DatosDeLogueoInvalidosException;
import kazoo.model.Partitura;
import kazoo.model.Usuario;
import kazoo.repository.PartituraRepository;
import kazoo.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PartituraService {

    @Autowired
    PartituraRepository partituraRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void crearPartitura(String usuarioNombre, Partitura partitura) {
        Usuario usuario = getUsuario(usuarioNombre);
        usuario.agregarPartitura(partitura);
        usuarioRepository.save(usuario);
    }

    public List<PartituraListada> getPartiturasPara(String nombreUsuario) {
        Usuario usuario = getUsuario(nombreUsuario);
        List<Partitura> partiturasDeUsuario = partituraRepository.findByUsuario(usuario).orElse(new ArrayList<>());
        return partiturasDeUsuario.stream().map(PartituraListada::new).collect(toList());
    }

    public DetallePartitura getPartitura(String nombreUsuario, String partituraId) {
        Partitura partitura = getPartitura(Long.parseLong(partituraId));

        if (partitura.getEsPublica() || perteneceAlUsuario(partitura, nombreUsuario)) {
            return new DetallePartitura(partitura);
        } else throw new PartituraNoEncontradaException("Partitura no accesible");

    }

    public void guardarPartitura(String nombreUsuario, Partitura partituraRecibida) {
        Partitura partituraEncontrada = getPartitura(partituraRecibida.getPartitura_id());

        if (perteneceAlUsuario(partituraEncontrada, nombreUsuario)) {
            //Copia todas las propiedades modificadas de la partitura menos el usuario que llega con null
            BeanUtils.copyProperties(partituraRecibida, partituraEncontrada, "usuario");
            partituraRepository.save(partituraEncontrada);
        } else throw new PartituraNoEncontradaException("No existe la partitura para el usuario solicitado");
    }

    public void marcarPartituraComoPublica(String nombreUsuario, Long partituraId) {
        Partitura partituraEncontrada = getPartitura(partituraId);

        if (perteneceAlUsuario(partituraEncontrada, nombreUsuario)) {
            partituraEncontrada.setEsPublica(true);
            partituraRepository.save(partituraEncontrada);
        } else throw new PartituraNoEncontradaException("El usuario no tiene permiso para publicar esta partitura");
    }

    private Boolean perteneceAlUsuario(Partitura partitura, String nombreUsuario) {
        Usuario usuario = getUsuario(nombreUsuario);

        return partitura.getUsuario().equals(usuario);
    }

    private Partitura getPartitura(Long id) {
        return partituraRepository.findById(id)
                .orElseThrow(() -> new PartituraNoEncontradaException("No existe la partitura seleccionada"));
    }

    private Usuario getUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombre(nombreUsuario)
                .orElseThrow(() -> new DatosDeLogueoInvalidosException("No existe el usuario"));
    }

    public void eliminarPartitura(String nombreUsuario, String partituraId) {
        Long idPartitura = Long.parseLong(partituraId);
        validarPartituraYUsuario(nombreUsuario, idPartitura);
        partituraRepository.deleteById(idPartitura);
    }

    private void validarPartituraYUsuario(String nombreUsuario, Long partituraId) {
        getUsuario(nombreUsuario);
        getPartitura(partituraId);
    }
}
