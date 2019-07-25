package online.patologia.springbootmatchmatching.repo;

import online.patologia.springbootmatchmatching.model.MatchModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchModelRepo extends JpaRepository<MatchModel,Long> {
}
