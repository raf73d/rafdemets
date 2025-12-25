package be.vdab.extraoef;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DatabaseMensService {
    private final DataBaseMensRepository dataBaseMensRepository;
    DatabaseMensService (DataBaseMensRepository databaseMensRepository) {
        this.dataBaseMensRepository = databaseMensRepository;
    }
    @Transactional
    long createDatabaseMens(NieuweDataBaseMens nieuweDataBaseMens) {
        try {
            var databaseMens = new DatabaseMens(nieuweDataBaseMens.naam()
                    , nieuweDataBaseMens.hoogte(), nieuweDataBaseMens.gewicht());
            dataBaseMensRepository.save(databaseMens);
            return databaseMens.getId();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseMensBestaatAlException();
        }
    }

}
