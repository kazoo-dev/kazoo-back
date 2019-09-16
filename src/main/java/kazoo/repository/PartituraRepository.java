package kazoo.repository;

import kazoo.model.Partitura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartituraRepository extends CrudRepository<Partitura, Long> {
}
