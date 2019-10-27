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
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class PartituraService {

    @Autowired
    PartituraRepository partituraRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void crearPartitura(String usuarioNombre, Partitura partitura) {
        Usuario usuario = getUsuario(usuarioNombre);
        Optional<Partitura> partituraEncontrada = partituraRepository.findById(partitura.getPartitura_id());
        Partitura partituraACrear = obtenerPartituraACrear(usuario, partitura, partituraEncontrada);
        usuario.agregarPartitura(partituraACrear);
        usuarioRepository.save(usuario);
    }

    public List<PartituraListada> getPartiturasPara(String nombreUsuario) {
        Usuario usuario = getUsuario(nombreUsuario);
        List<Partitura> partiturasDeUsuario = partituraRepository.findByUsuario(usuario).orElse(new ArrayList<>());
        return partiturasDeUsuario.stream().map(PartituraListada::new).collect(toList());
    }

    public DetallePartitura getPartitura(String nombreUsuario, String partituraId) {
        Partitura partitura = getPartitura(Long.parseLong(partituraId));

        if (partitura.getEsPublica() || partituraPerteneceAlUsuario(partitura, getUsuario(nombreUsuario))) {
            return new DetallePartitura(partitura);
        } else throw new PartituraNoEncontradaException("Partitura no accesible");

    }

    public void guardarPartitura(String nombreUsuario, Partitura partituraRecibida) {
        validarPartituraYUsuario(nombreUsuario, partituraRecibida.getPartitura_id());
        Partitura partituraEncontrada = getPartitura(partituraRecibida.getPartitura_id());
        //Copia todas las propiedades modificadas de la partitura menos el usuario que llega con null
        BeanUtils.copyProperties(partituraRecibida, partituraEncontrada, "usuario");
        partituraRepository.save(partituraEncontrada);

    }

    public void marcarPartituraComoPublica(String nombreUsuario, Long partituraId) {
        validarPartituraYUsuario(nombreUsuario, partituraId);
        Partitura partituraEncontrada = getPartitura(partituraId);
        partituraEncontrada.setEsPublica(true);
        partituraRepository.save(partituraEncontrada);
    }

    private Partitura obtenerPartituraACrear(Usuario usuario, Partitura partitura, Optional<Partitura> partituraEncontrada) {
        if (partituraEncontrada.isPresent() && !partituraPerteneceAlUsuario(partituraEncontrada.get(), usuario)) {
            return Partitura.copiarDesde(partitura);
        } else {
            return partitura;
        }
    }

    private Boolean partituraPerteneceAlUsuario(Partitura partitura, Usuario usuario) {
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
        Usuario usuario = getUsuario(nombreUsuario);
        Partitura partitura = getPartitura(partituraId);
        if (!partituraPerteneceAlUsuario(partitura, usuario)) {
            throw new PartituraNoEncontradaException("El usuario no tiene permisos para manipular esta partitura");
        }
    }
}
