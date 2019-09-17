package kazoo.repository;

import kazoo.model.Partitura;
import kazoo.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartituraRepository extends CrudRepository<Partitura, Long> {

    Optional<List<Partitura>> findByUsuario(Usuario usuario);

}
